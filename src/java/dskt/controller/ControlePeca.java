package dskt.controller;

import dskt.model.Fornecedor;
import dskt.model.Peca;
import dskt.model.dao.FornecedorDAO;
import dskt.model.dao.PecaDAO;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ControlePeca extends HttpServlet {

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
                case ("NEW_PECA"): // NOVA PEÇA
                    new_peca(request, response);
                    break;

                case "EDIT_PECA": //EDITA A PEÇA
                    edit_peca(request, response, IdObjeto);
                    break;

                case "INSERT_UPDATE_PECA": //INSERE ou ATUALIZA A PEÇA E FAZ UPLOAD DA IMAGEM
                    insert_update_peca(request, response);
                    break;

                case "DELETE_PECA": // DELETA A PEÇA
                    delete_peca(response, IdObjeto);
                    break;

                case "LIST_PECAS":
                    list_pecas(request, response, IdObjeto);
                    break;

                default:
                    session.invalidate();
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    break;
            }

        } else {
            session.invalidate();
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void new_peca(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FornecedorDAO fornDAO = new FornecedorDAO();

        request.setAttribute("forns", fornDAO.buscarTudo());
        request.getRequestDispatcher("peca.jsp").forward(request, response);
    }

    private void edit_peca(HttpServletRequest request, HttpServletResponse response, String IdObjeto) throws ServletException, IOException {

        PecaDAO pecDAO = new PecaDAO();
        FornecedorDAO fornDAO = new FornecedorDAO();

        Peca peca = pecDAO.buscarPorId(new Peca(Integer.parseInt(IdObjeto)));
        peca.setFornecedor(fornDAO.buscarPorId(peca.getFornecedor()));
        request.setAttribute("peca", peca);
        request.setAttribute("forns", fornDAO.buscarTudo());
        request.getRequestDispatcher("peca.jsp").forward(request, response);
    }

    private void insert_update_peca(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Peca peca = new Peca();
        PecaDAO pecDAO = new PecaDAO();
        FornecedorDAO fornDAO = new FornecedorDAO();

        Integer pecId = null;
        Integer pecFornecedor = null;
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        item.write(new File(getServletContext().getRealPath("/images/") + "/" + peca.getTipo().toLowerCase() + "s/" + File.separator + name));
                        peca.setRef_imagem("images/" + peca.getTipo().toLowerCase() + "s/" + item.getName());
                    } else {
                        switch (item.getFieldName()) {
                            case "PecId":
                                if (item.getString() != null && !"".equals(item.getString())) {
                                    pecId = Integer.parseInt(item.getString());
                                }
                                break;
                            case "PecModelo":
                                peca.setModelo(item.getString());
                                break;
                            case "PecDescricao":
                                peca.setDescricao(item.getString());
                                break;
                            case "PecCor":
                                peca.setCor(item.getString());
                                break;
                            case "PecTamanho":
                                peca.setTamanho(item.getString());
                                break;
                            case "PecQtd_estoque":
                                peca.setQtd_estoque(Integer.parseInt(item.getString()));
                                break;
                            case "PecValor_compra":
                                peca.setValor_compra(Double.parseDouble(item.getString()));
                                break;
                            case "PecValor_venda":
                                peca.setValor_venda(Double.parseDouble(item.getString()));
                                break;
                            case "PecTipo":
                                peca.setTipo(item.getString());
                                break;
                            case "PecFornecedor":
                                pecFornecedor = Integer.parseInt(item.getString());
                                break;
                        }
                    }
                }
                //File uploaded successfully
                request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception ex) {
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }
        }
        //passa o id da peca para buscar o fornecedor da peca.
        peca.setFornecedor(fornDAO.buscarPorId(new Fornecedor(pecFornecedor)));
        if (pecId == null) {
            pecDAO.inserir(peca);
        } else {
            peca.setId(pecId);
            
            if(peca.getRef_imagem() == null || peca.getRef_imagem().isEmpty() || peca.getRef_imagem().equals(" ")){
                peca.setRef_imagem(pecDAO.buscarPorId(peca).getRef_imagem());
            }
            
            pecDAO.atualizar(peca);
        }
        request.getRequestDispatcher("ControlePeca?Acao=LIST_PECAS").forward(request, response);
    }

    private void delete_peca(HttpServletResponse response, String IdObjeto) throws ServletException, IOException {
        Peca peca = new Peca();
        PecaDAO pecDAO = new PecaDAO();

        peca.setId(Integer.parseInt(IdObjeto));
        pecDAO.deletar(peca);
        response.sendRedirect("ControlePeca?Acao=LIST_PECAS");
    }

    private void list_pecas(HttpServletRequest request, HttpServletResponse response, String IdObjeto) throws ServletException, IOException {
        
        PecaDAO pecDAO = new PecaDAO();
        FornecedorDAO fornDAO = new FornecedorDAO();

        List<Peca> pecas;
        List<Fornecedor> fornecedores;

        //LISTA TODOS AS PEÇAS
        pecas = new ArrayList(pecDAO.buscarTudo());
        fornecedores = new ArrayList(fornDAO.buscarTudo());

        for (Peca p : pecas) {
            for (Fornecedor f : fornecedores) {
                if (p.getFornecedor().getId().equals(f.getId())) {
                    p.setFornecedor(f);
                }
            }
        }

        Collections.sort(pecas, new Comparator<Peca>() {
            @Override
            public int compare(Peca p1, Peca p2) {
                return p1.getId().compareTo(p2.getId());
                //return p1.getModelo().compareToIgnoreCase(p2.getModelo());
            }
        });

        request.setAttribute("pecas", pecas);
        request.getRequestDispatcher("listaPecas.jsp").forward(request, response);
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
