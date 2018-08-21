package dskt.controller;

//IMPORT MODELS E DAOS
import dskt.model.Carrinho;
import dskt.model.Cliente;
import dskt.model.Funcionario;
import dskt.model.Peca;
import dskt.model.PerfilDeAcesso;
import dskt.model.Skate;
import dskt.model.Usuario;
import dskt.model.Venda;
import dskt.model.dao.ClienteDAO;
import dskt.model.dao.PecaDAO;
import dskt.model.dao.SkateDAO;
import dskt.model.dao.VendaDAO;
import dskt.util.GerarBoleto;

// DEMAIS IMPORTS
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;

public class ControleVenda extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //VERIFICA SE O USUARIO ESTÁ LOGADO 
        HttpSession session = request.getSession();//BUSCA A SESSÃO

        String Acao = request.getParameter("Acao");
        String IdObjeto = request.getParameter("IdObjeto");

        if (!Acao.isEmpty()) {

            switch (Acao) {
                case "NEW_VENDA": // ENVIA PARA TELA DE NOVO CLIENTE
                    new_venda(response, session);
                    break;

                case "APR_PAGAMENTO_VENDA":
                    apr_pagamento_venda(response, IdObjeto);
                    break;

                case "APR_MONTAGEM_VENDA":
                    apr_montagem_venda(response, session, IdObjeto);
                    break;

                case "LIST_VENDAS":
                    list_vendas(request, response, session);
                    break;

                case "CANCEL_VENDA":
                    cancel_venda(response, IdObjeto);
                    break;

                case "CARRINHO":
                    carrinho(request, response);
                    break;

                default:
                    logoff(session, request, response);
                    break;
            }
        } else {
            logoff(session, request, response);
        }
    }

    //
    //
    // *************************************** METODOS DE AÇÃO CLIENTE (SWITCH) ***********************************************************
    //
    //
    private void new_venda(HttpServletResponse response, HttpSession session) throws ServletException, IOException {

            VendaDAO vdDAO = new VendaDAO();
            SkateDAO sktDAO = new SkateDAO();
            Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
            Usuario usuario = (Usuario) session.getAttribute("user");
            Venda venda = new Venda();
            
            venda.setSkates(carrinho.getSkates());

            if (usuario.getPerfil().equals(PerfilDeAcesso.COMUM)) {
                Cliente cliente = (Cliente) session.getAttribute("clienteLogado");
                venda.setCliente(cliente);
            } else {
                ClienteDAO cliDAO = new ClienteDAO();
                Cliente cliente = new Cliente(((String) session.getAttribute("cpfClienteVenda")));
                Cliente cli = cliDAO.buscarPorCPF(cliente);
                venda.setCliente(cli);

            }

            //ADICIONA A DATA ATUAL PARA A DATA DE PEDIDO, JA NO FORMATO CORRETO
            Calendar dtPeididoCalendar = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String dtPedido = df.format(dtPeididoCalendar.getTime());
            venda.setDtPedido(dtPedido);

            //CALCULA O VALOR DAS PEÇAS
            Double valorVenda = 0.0;
            for (Skate skate : venda.getSkates()) {
                valorVenda += skate.getShape().getValor_venda();
                valorVenda += skate.getTruck().getValor_venda();
                valorVenda += skate.getRoda().getValor_venda();
                valorVenda += skate.getLixa().getValor_venda();
            }

            venda.setValorVenda(valorVenda);
            venda.setValorTotal(venda.getValorVenda() + venda.getValorFrete());

            //GRAVA A VENDA NO BANCO PARA GERAR O ID DA VENDA, ASSIM LINKANDO OS SKATES À VENDA NO BANCO.
            venda.setId(vdDAO.inserirRetornandoId(venda).getId());

            for (Skate skate : venda.getSkates()) {
                skate.setIdVenda(venda.getId());
                sktDAO.inserir(skate);
            }

            gerarBoleto(response, venda);

            session.removeAttribute("cpfClienteVenda");
            session.removeAttribute("carrinho");

    }

    private void list_vendas(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {

        VendaDAO vdDAO = new VendaDAO();
        SkateDAO sktDAO = new SkateDAO();
        PecaDAO pecDAO = new PecaDAO();
        ClienteDAO cliDAO = new ClienteDAO();
        Usuario user = (Usuario) session.getAttribute("user");

        List<Venda> pagas = new ArrayList();
        List<Venda> nPagas = new ArrayList();

        // ================= VERIFICA O PERFIL DE ACESSO E BUSCA VENDAS PAGAS ========================================== //
        if (user.getPerfil().equals(PerfilDeAcesso.ATENDENTE)) {

            pagas.addAll(vdDAO.buscarVendasPagasNaoConcluidas());

            for (Venda venda : pagas) {
                Integer idVenda = venda.getId();
                venda.setSkates(sktDAO.buscarSkatesNaoMontados(new Skate().getVenda(idVenda)));

                for (int i = 0; i < venda.getSkates().size(); i++) {
                    venda.getSkates().get(i).setShape(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getShape().getId())));
                    venda.getSkates().get(i).setTruck(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getTruck().getId())));
                    venda.getSkates().get(i).setRoda(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getRoda().getId())));
                    venda.getSkates().get(i).setLixa(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getLixa().getId())));
                }

                venda.setCliente(cliDAO.buscarPorId(venda.getCliente()));

            }

        }

        // ================= VERIFICA O PERFIL DE ACESSO E BUSCA VENDAS COM PAGAMENTO PENDENTE ======================== //
        if (user.getPerfil().equals(PerfilDeAcesso.GERENTE)) {

            nPagas.addAll(vdDAO.buscarVendasAguardandoPagamento());

            for (Venda venda : nPagas) {
                Integer idVenda = venda.getId();
                venda.setSkates(sktDAO.buscarSkatesNaoMontados(new Skate().getVenda(idVenda)));

                for (int i = 0; i < venda.getSkates().size(); i++) {
                    venda.getSkates().get(i).setShape(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getShape().getId())));
                    venda.getSkates().get(i).setTruck(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getTruck().getId())));
                    venda.getSkates().get(i).setRoda(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getRoda().getId())));
                    venda.getSkates().get(i).setLixa(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getLixa().getId())));
                }

                venda.setCliente(cliDAO.buscarPorId(venda.getCliente()));
            }
        }

        // ================= VERIFICA O PERFIL DE ACESSO E BUSCA VENDAS PAGAS ========================================== //
        if (user.getPerfil().equals(PerfilDeAcesso.ADMINISTRADOR)) {

            pagas.addAll(vdDAO.buscarVendasPagasNaoConcluidas());
            nPagas.addAll(vdDAO.buscarVendasAguardandoPagamento());

            for (Venda venda : pagas) {
                Integer idVenda = venda.getId();
                venda.setSkates(sktDAO.buscarSkatesNaoMontados(new Skate().getVenda(idVenda)));

                for (int i = 0; i < venda.getSkates().size(); i++) {
                    venda.getSkates().get(i).setShape(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getShape().getId())));
                    venda.getSkates().get(i).setTruck(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getTruck().getId())));
                    venda.getSkates().get(i).setRoda(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getRoda().getId())));
                    venda.getSkates().get(i).setLixa(pecDAO.buscarPorId(new Peca(venda.getSkates().get(i).getLixa().getId())));
                }

                venda.setCliente(cliDAO.buscarPorId(venda.getCliente()));

            }

        }

        Collections.sort(pagas, new Comparator<Venda>() {
            @Override
            public int compare(Venda vd1, Venda vd2) {
                return vd1.getDtPedido().compareToIgnoreCase(vd2.getDtPedido());
            }
        });
        Collections.sort(nPagas, new Comparator<Venda>() {
            @Override
            public int compare(Venda vd1, Venda vd2) {
                return vd1.getDtPedido().compareToIgnoreCase(vd2.getDtPedido());
            }
        });

        request.setAttribute("pagas", pagas);
        request.setAttribute("nPagas", nPagas);
        request.getRequestDispatcher("listaVendas.jsp").forward(request, response);
    }

    private void apr_pagamento_venda(HttpServletResponse response, String IdObjeto) throws ServletException, IOException {
        VendaDAO vdDAO = new VendaDAO();
        Venda venda = new Venda();

        venda.setFlgPagamento(true);

        //ADICIONA A DATA ATUAL PARA A DATA DE PAGAMENTO, JA NO FORMATO CORRETO
        Calendar dtPagamentoCalendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dtPagamento = df.format(dtPagamentoCalendar.getTime());
        venda.setDtPagamento(dtPagamento);

        venda.setId(Integer.parseInt(IdObjeto));
        vdDAO.aprovarPagamento(venda);
        response.sendRedirect("ControleVenda?Acao=LIST_VENDAS");
    }

    private void apr_montagem_venda(HttpServletResponse response, HttpSession session, String IdObjeto) throws ServletException, IOException {

        Funcionario funcionario = (Funcionario) session.getAttribute("funcionarioLogado");
        SkateDAO sktDAO = new SkateDAO();
        Skate skate = new Skate(Integer.parseInt(IdObjeto));

        //ADICIONA A DATA ATUAL PARA A DATA DE MONTAGEM, JA NO FORMATO CORRETO
        Calendar dtMontagemCalendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dtMontagem = df.format(dtMontagemCalendar.getTime());

        skate.setFlgMontagem(true);
        skate.setDtMontagem(dtMontagem);
        skate.setIdFuncionario(funcionario.getId());

        sktDAO.aprovarMontagem(skate);

        response.sendRedirect("ControleVenda?Acao=LIST_VENDAS");
    }

    private void cancel_venda(HttpServletResponse response, String IdObjeto) throws ServletException, IOException {
        VendaDAO vdDAO = new VendaDAO();
        Venda venda = new Venda();

        venda.setFlgCancelamento(true);

        //ADICIONA A DATA ATUAL PARA A DATA DE CANCELAMENTO, JA NO FORMATO CORRETO
        Calendar dtCancelamentoCalendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dtCancelamento = df.format(dtCancelamentoCalendar.getTime());
        venda.setDtCancelamento(dtCancelamento);

        venda.setId(Integer.parseInt(IdObjeto));
        vdDAO.cancelarVenda(venda);
        response.sendRedirect("ControleVenda?Acao=LIST_VENDAS");
    }

    private void carrinho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("carrinho.jsp").forward(request, response);
    }

    //
    //
    // *************************************** METODOS HTTP e UTIL ***********************************************************
    //
    //
    private void logoff(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        session.invalidate();
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    private void gerarBoleto(HttpServletResponse response, Venda venda) throws ServletException, IOException {

        Boleto boleto = GerarBoleto.getBoleto(venda);
        BoletoViewer viewer = new BoletoViewer(boleto);
        byte[] pdfAsBytes = viewer.getPdfAsByteArray();

        response.setContentType("application/pdf");
        response.setHeader("Content Type", "attachment; filename=boleto.pdf");

        OutputStream output = response.getOutputStream();
        output.write(pdfAsBytes);

        response.flushBuffer();
    }

    @Override
    protected void doPost(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
        processRequest(Request, Response);
    }

    @Override
    protected void doGet(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
        processRequest(Request, Response);
    }
}
