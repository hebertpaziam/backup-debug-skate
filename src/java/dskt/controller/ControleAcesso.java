package dskt.controller;

import dskt.model.Carrinho;
import dskt.model.Cliente;
import dskt.model.Funcionario;
import dskt.model.PerfilDeAcesso;
import dskt.model.Usuario;
import dskt.model.dao.ClienteDAO;
import dskt.model.dao.FuncionarioDAO;
import dskt.model.dao.UsuarioDAO;
import dskt.util.Hash;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControleAcesso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();//BUSCA A SESSÃO
        String Acao = request.getParameter("Acao");

        switch (Acao) {
            case "LOGIN": //FAZ O LOGIN NO SISTEMA

                UsuarioDAO dao = new UsuarioDAO();

                Usuario usuarioValido = dao.validar(new Usuario(request.getParameter("UsrLogin"), (Hash.sha512((request.getParameter("UsrSenha"))))));

                if (usuarioValido != null) {//SE O USUÁRIO E SENHA EXISTIR NO BANCO E FOR VÁLIDO 

                    //COLOCA O USUÁRIO VALIDO NA SESSÃO E REDIRECIONA PARA A HOME
                    if (usuarioValido.getPerfil().equals(PerfilDeAcesso.COMUM)) {
                        ClienteDAO cliDAO = new ClienteDAO();
                        Cliente cliente = cliDAO.buscarPorUsuario(new Cliente(usuarioValido));

                        session.setAttribute("clienteLogado", cliente);
                        session.setAttribute("user", cliente.getUsuario());

                    } else {
                        FuncionarioDAO funcDAO = new FuncionarioDAO();
                        Funcionario funcionario = funcDAO.buscarPorUsuario(new Funcionario(usuarioValido));

                        session.setAttribute("funcionarioLogado", funcionario);
                        session.setAttribute("user", funcionario.getUsuario());
                    }

                    Carrinho carrinho = new Carrinho();
                    session.setAttribute("carrinho", carrinho);

                    session.setAttribute("token", session.getId());
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                } else {
                    session.invalidate();
                    request.getRequestDispatcher("home.jsp").forward(request, response);
                }
                break;

            case "HOME":
                request.getRequestDispatcher("home.jsp").forward(request, response);
                break;

            case "CADASTRO_CLIENTE":
                Cliente cliente = new Cliente();
                ClienteDAO cliDAO = new ClienteDAO();
                UsuarioDAO userDAO = new UsuarioDAO();

                cliente.setNome(request.getParameter("CliNome"));
                cliente.setCpf_cnpj(request.getParameter("CliCpf"));
                cliente.setEmail(request.getParameter("CliEmail"));
                cliente.setTelefone(request.getParameter("CliTelefone"));
                cliente.setEndereco(request.getParameter("CliEndereco"));
                cliente.setUf(request.getParameter("CliUf"));
                cliente.setData_nasc(request.getParameter("CliDtNasc"));

                cliente.getUsuario().setLogin(request.getParameter("CliLogin"));
                cliente.getUsuario().setSenha(Hash.sha512((request.getParameter("CliSenha"))));
                cliente.getUsuario().setPerfil(PerfilDeAcesso.COMUM);

                if (cliDAO.verificarCPF(cliente)) {
                    userDAO.inserir(cliente.getUsuario());
                    cliente.setUsuario(userDAO.buscarPorLogin(cliente.getUsuario()));

                    cliente.setId(cliDAO.inserirReturnandoId(cliente).getId());

                    session.setAttribute("clienteLogado", cliente);//COLOCA O USUÁRIO VALIDO NA SESSÃO E REDIRECIONA PARA A HOME
                    session.setAttribute("user", cliente.getUsuario());//COLOCA O USUÁRIO VALIDO NA SESSÃO E REDIRECIONA PARA A HOME
                    session.setAttribute("token", session.getId());

                    request.getRequestDispatcher("ControleAcesso?Acao=HOME").forward(request, response);
                }else{
                    request.getRequestDispatcher("ControleAcesso?Acao=LOGOFF").forward(request, response);
                }
                
                break;

            case "LOGOFF": // FAZ O LOGOFF NO SISTEMA
                session.invalidate();
                request.getRequestDispatcher("home.jsp").forward(request, response);
                break;

            default:
                session.invalidate();
                request.getRequestDispatcher("home.jsp").forward(request, response);
                break;
        }

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
