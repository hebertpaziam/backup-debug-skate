package dskt.controller;

import dskt.model.Fornecedor;
import dskt.model.Peca;
import dskt.model.ItemDeSolicitacao;
import dskt.model.SolicitacaoPecas;
import dskt.model.dao.FornecedorDAO;
import dskt.model.dao.PecaDAO;
import dskt.model.dao.SolicitacaoPecasDAO;
import dskt.util.EnviaEmail;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControleSolicitacao extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); response.setCharacterEncoding("UTF-8");

        //VERIFICA SE O USUARIO ESTÁ LOGADO 
        HttpSession session = request.getSession();//BUSCA A SESSÃO
        if (session.getAttribute("token") != session.getId()) {
            session.invalidate();
            request.getRequestDispatcher("login.jsp").forward(request, response);    
        }

        //INSTANCIA OBJETOS QUE SERÃO USADOS PELA SERVLET
        PecaDAO pecDAO = new PecaDAO();
        FornecedorDAO fornDAO = new FornecedorDAO();
        SolicitacaoPecasDAO spDAO = new SolicitacaoPecasDAO();
        List<Peca> pecas = pecDAO.buscarTudo();         //Lista com todas as peças do Banco
        List<Fornecedor> forns = fornDAO.buscarTudo();  //Lista com todos os fornecedores do Banco        
        SolicitacaoPecas solicitacao = new SolicitacaoPecas(spDAO.IdDaProximaSolicitacao());  //Objeto Solicitação de Peças com ID da próxima solicitacao
        EnviaEmail mail = new EnviaEmail();     //Objeto que irá enviar as solicitacoes de pecas

        //POPULA O OBJETO SOLICITACAO COM AS A LISTA DE PECAS VINDAS DA DAO
        solicitacao = this.populaPecas(solicitacao, pecas);

        //POPULANDO OS FORNECEDORES NA LISTA DE SOLICITACAO
        solicitacao = this.populaFornecedores(solicitacao, forns);

        //SETA COMO ATRIBUTO A LISTA DE SOLICITAÇÃO COMPLETA
        request.setAttribute("spPecas", solicitacao);

        String Acao = request.getParameter("Acao");
        String IdObjeto = request.getParameter("IdObjeto");

        if (!Acao.isEmpty()) {
            switch (Acao) {
                case "LIST_SOLICITACAO": //LISTA TODAS PEÇAS QUE PRECISAM SER SOLICITADAS

                    request.getRequestDispatcher("solicitacaoPecas.jsp").forward(request, response);
                    break;

                case "NEW_SOLICITACAO": //GRAVAÇÃO DA SOLICITAÇÃO NO BANCO, CASO NÃO EXISTA SOLICITAÇÃO PENDENTE

                    if (spDAO.verificarUltimaSolicitacao() == true) {   //VERIFICA SE HÁ SOLICITAÇÃO PENDENTE
                        spDAO.inserirSolicitacaoNova(solicitacao);   //GRAVA NO BANCO A SOLICITAÇAO
                        response.sendRedirect("ControlePeca?Acao=LIST_PECAS"); //RETORNA PRA CONTROLE DE PEÇAS APÓS A INSERÇÃO NO BANCO4
                    } else {
                        //QUANDO HÁ SOLICITAÇÃO PENDENTE
                        String msg = "Existem Solicitações em aberto. Favor Rejeitar ou Aprovar";
                        request.setAttribute("erro", msg);
                        request.getRequestDispatcher("ControlePeca?Acao=LIST_PECAS").forward(request, response);
                    }
                    break;

                case "APROV_SOLICITACAO": //ENVIA PARA TELA DE APROVAÇÃO DE SOLICITAÇÃO.
                    solicitacao = spDAO.retornaUltimaSolicitacao();
                    int i = 0;
                    while (i < solicitacao.getItensDeSolicitacao().size()) {
                        int j = 0;
                        while (j < pecas.size()) {
                            if (Objects.equals(solicitacao.getItensDeSolicitacao().get(i).getPeca().getId(), pecas.get(j).getId())) {
                                solicitacao.getItensDeSolicitacao().get(i).setPeca(pecas.get(j));
                            }
                            j++;
                        }
                        i++;
                    }
                    solicitacao = this.populaFornecedores(solicitacao, forns);
                    request.setAttribute("spPecas", solicitacao);
                    request.getRequestDispatcher("solicitacaoPendente.jsp").forward(request, response);
                    break;

                case "DELETE_SOLICITACAO": //EXCLUIR SOLICITAÇÃO PENDENTE 
                    solicitacao = new SolicitacaoPecas(Integer.parseInt(IdObjeto));
                    spDAO.excluiSolicitacaoPendente(solicitacao);
                    response.sendRedirect("ControlePeca?Acao=LIST_PECAS");
                    break;

                case "SEND_SOLICITACAO": //ENVIAR SOLICITAÇÃO APROVADA
                    solicitacao = this.populaPecas(new SolicitacaoPecas(Integer.parseInt(IdObjeto)), pecas);
                    spDAO.enviarSolicitacaoPendente(solicitacao);
                    mail.send(solicitacao);     //PASSA A LISTA DE SOLICITAÇÃO PARA MONTAGEM DOS EMAILS E ENVIO
                    response.sendRedirect("ControlePeca?Acao=LIST_PECAS");
                    break;

                default:
                    session.invalidate();
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    break;
            }
        }
    }

    //MÉTODO QUE POPULA AS PEÇAS DENTRO DO OBJETO SOLICITACAO
    private SolicitacaoPecas populaPecas(SolicitacaoPecas solicitacao, List<Peca> pecas) {
        for (Peca peca : pecas) {
            if (peca.getQtd_estoque() < 10) {   //CONDICAO PARA UMA PECA ENTRAR PARA A LISTA DE SOLICITACAO
                ItemDeSolicitacao item = new ItemDeSolicitacao(peca); //ADCIONA NA SOLICITACAO UM ITEM DE SOLICITACAO, CRIADO A PARTIR DE UM OBJETO PECA
                item.setQtdPecas(10 - peca.getQtd_estoque());
                item.setIdSol(solicitacao.getIdSol());
                solicitacao.getItensDeSolicitacao().add(item);
            }
        }
        return solicitacao;
    }

    //MÉTODO QUE POPULA OS FORNECEDORES DOS OBJETOS PECA DO OBJETO SOLICITACAO
    private SolicitacaoPecas populaFornecedores(SolicitacaoPecas solicitacao, List<Fornecedor> forns) {
        int n = 0;
        while (n < solicitacao.getItensDeSolicitacao().size()) {
            int m = 0;
            while (m < forns.size()) {
                if (solicitacao.getItensDeSolicitacao().get(n).getPeca().getFornecedor().getId().equals(forns.get(m).getId())) {
                    solicitacao.getItensDeSolicitacao().get(n).getPeca().setFornecedor(forns.get(m));
                }
                m++;
            }
            n++;
        }
        return solicitacao;
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
