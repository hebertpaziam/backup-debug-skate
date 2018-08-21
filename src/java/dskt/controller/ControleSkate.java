package dskt.controller;

import dskt.model.Carrinho;
import dskt.model.Fornecedor;
import dskt.model.Peca;
import dskt.model.Skate;
import dskt.model.dao.FornecedorDAO;
import dskt.model.dao.PecaDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControleSkate extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        // TESTES DO CARRINHO NA SESSÃO
        //Pega o carrinho da sessão
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        //se não exite um carrinho na sessão o valor será igual a null
        if (carrinho == null) {
            //cria um carrinho 
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);
        }
        //
        //

        if ("EDIT_SK8".equals(request.getParameter("Acao"))) {

            Skate skate = carrinho.getSkatePosition(Integer.parseInt(request.getParameter("IdSk8")));
            skate.setId(Integer.parseInt(request.getParameter("IdSk8")));
            request.setAttribute("skateEdit", skate);
        }

        if ("DELETE_SK8".equals(request.getParameter("Acao"))) {
            carrinho.getSkates().remove(Integer.parseInt(request.getParameter("IdSk8")));
            request.getRequestDispatcher("home.jsp").forward(request, response);

        }

        PecaDAO pecDAO = new PecaDAO();
        FornecedorDAO fornDAO = new FornecedorDAO();

        //LISTA TODOS AS PEÇAS
        List<Peca> pecas = new ArrayList(pecDAO.buscarTudo());
        List<Fornecedor> fornecedores = new ArrayList(fornDAO.buscarTudo());

        for (Peca p : pecas) {
            for (Fornecedor f : fornecedores) {
                if (p.getFornecedor().getId().equals(f.getId())) {
                    p.setFornecedor(f);
                }
            }
        }
        request.setAttribute("pecas", pecas);
        request.getRequestDispatcher("skate.jsp").forward(request, response); //FRONT END FUNCIONANDO
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // TESTES DO CARRINHO NA SESSÃO
        //Pega o carrinho da sessão
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        //se não exite um carrinho na sessão o valor será igual a null
        if (carrinho == null) {
            //cria um carrinho 
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);
        }
        //
        //
        if (carrinho.getSkates().isEmpty()) {
            session.setAttribute("cpfClienteVenda", request.getParameter("cpfClienteVenda"));
        }

        //INSTANCIA AS CLASSES QUE SERÃO USADAS
        Skate skate = new Skate();

        PecaDAO pecaDAO = new PecaDAO();

        //MONTA OBJETO SKATE QUE SERÁ UTILIZADO
        skate.setShape(pecaDAO.buscarPorId(new Peca(Integer.parseInt(request.getParameter("SktShape")))));
        skate.setTruck(pecaDAO.buscarPorId(new Peca(Integer.parseInt(request.getParameter("SktTruck")))));
        skate.setRoda(pecaDAO.buscarPorId(new Peca(Integer.parseInt(request.getParameter("SktRoda")))));
        skate.setLixa(pecaDAO.buscarPorId(new Peca(Integer.parseInt(request.getParameter("SktLixa")))));

        //INSERIR SKATE
        if (request.getParameter("SktId").isEmpty() || request.getParameter("SktId") == null) {
            //sktDAO.inserir(skate);
            System.err.println(request.getParameter("SktId"));
            carrinho.addSkate(skate);

            //ATUALIZAR  SKATE
        } else {
            skate.setId(Integer.parseInt(request.getParameter("SktId")));
            carrinho.setSkatePosition(skate);

        }

        request.getRequestDispatcher("home.jsp").forward(request, response);

    }
}
