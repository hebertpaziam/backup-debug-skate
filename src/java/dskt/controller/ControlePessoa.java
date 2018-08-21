package dskt.controller;

//IMPORT MODELS E DAOS
import dskt.model.dao.UsuarioDAO;

import dskt.model.Funcionario;
import dskt.model.dao.FuncionarioDAO;
import dskt.model.Cliente;
import dskt.model.dao.ClienteDAO;
import dskt.model.Fornecedor;
import dskt.model.PerfilDeAcesso;
import dskt.model.Usuario;
import dskt.model.dao.FornecedorDAO;
import dskt.util.Hash;

// DEMAIS IMPORTS
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

public class ControlePessoa extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //VERIFICA SE O USUARIO ESTÁ LOGADO 
        HttpSession session = request.getSession();//BUSCA A SESSÃO

        String Acao = request.getParameter("Acao");
        String IdObjeto = request.getParameter("IdObjeto");

        if (!Acao.isEmpty()) {

//CLIENTE=====================================================================//            
            switch (Acao) {
                case "NEW_CLIENTE": // ENVIA PARA TELA DE NOVO CLIENTE
                    new_cliente(request, response);
                    break;

                case "EDIT_CLIENTE": // ENVIA O CLIENTE PARA EDITAR
                    edit_cliente(request, response, IdObjeto);
                    break;

                case "INSERT_UPDATE_CLIENTE": // INSERE OU ATUALIZA O CLIENTE
                    insert_update_cliente(request, response);
                    break;

                case "DELETE_CLIENTE": // DELETA O CLIENTE
                    delete_cliente(response, IdObjeto);
                    break;

                case "LIST_CLIENTES": // LISTA TODOS OS CLIENTES    
                    list_clientes(request, response);
                    break;

//FUNCIONARIO=====================================================================//            
                case "NEW_FUNCIONARIO": // ENVIA PARA TELA DE NOVO FUNCIONARIO
                    new_funcionario(request, response);
                    break;

                case "EDIT_FUNCIONARIO": // ENVIA O FUNCIONARIO PARA EDITAR
                    edit_funcionario(request, response, IdObjeto);
                    break;

                case "INSERT_UPDATE_FUNCIONARIO": // INSERE OU ATUALIZA O FUNCIONARIO
                    insert_update_funcionario(request, response);
                    break;

                case "DELETE_FUNCIONARIO": // DELETA O FUNCIONARIO
                    delete_funcionario(response, IdObjeto);
                    break;

                case "LIST_FUNCIONARIOS": // LISTA TODOS OS FUNCIONARIOS    
                    list_funcionarios(request, response);
                    break;

//FORNECEDOR==================================================================//   
                case "NEW_FORNECEDOR": // ENVIA PARA TELA DE NOVO FORNECEDOR
                    new_fornecedor(request, response);
                    break;

                case "EDIT_FORNECEDOR": // ENVIA O FORNECEDOR PARA EDITAR
                    edit_fornecedor(request, response, IdObjeto);
                    break;

                case "INSERT_UPDATE_FORNECEDOR": // INSERE OU ATUALIZA O FORNECEDOR
                    insert_update_fornecedor(request, response);
                    break;

                case "DELETE_FORNECEDOR": // DELETA O FORNECEDOR
                    delete_fornecedor(response, IdObjeto);
                    break;

                case "LIST_FORNECEDORES": // LISTA TODOS OS FORNECEDORES   
                    list_fornecedores(request, response);
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
    private void new_cliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("cliente.jsp").forward(request, response);
    }

    private void edit_cliente(HttpServletRequest request, HttpServletResponse response, String IdObjeto) throws ServletException, IOException {

        ClienteDAO cliDAO = new ClienteDAO();
        UsuarioDAO userDAO = new UsuarioDAO();

        Cliente cliente = cliDAO.buscarPorId(new Cliente(Integer.parseInt(IdObjeto)));
        cliente.setUsuario(userDAO.buscarPorId(cliente.getUsuario()));
        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher("cliente.jsp").forward(request, response);
    }

    private void insert_update_cliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        if (request.getParameter("CliId").isEmpty()) {
            //INSERE CLIENTE

            userDAO.inserir(cliente.getUsuario());
            cliente.setUsuario(userDAO.buscarPorLogin(cliente.getUsuario()));

            cliDAO.inserir(cliente);
            
            request.getRequestDispatcher("ControlePessoa?Acao=LIST_CLIENTES").forward(request, response);

        } else {
            //ATUALIZA CLIENTE
            cliente.setUsuario(userDAO.buscarPorLogin(cliente.getUsuario()));
            cliente.getUsuario().setSenha(Hash.sha512((request.getParameter("CliSenha"))));
            cliente.setId(Integer.parseInt(request.getParameter("CliId")));

            userDAO.atualizar(cliente.getUsuario());
            cliDAO.atualizar(cliente);

            request.getRequestDispatcher("ControlePessoa?Acao=LIST_CLIENTES").forward(request, response);
        }
    }

    private void delete_cliente(HttpServletResponse response, String IdObjeto) throws ServletException, IOException {
        Cliente cliente = new Cliente();
        ClienteDAO cliDAO = new ClienteDAO();
        UsuarioDAO userDAO = new UsuarioDAO();

        cliente.setId(Integer.parseInt(IdObjeto));
        cliDAO.buscarPorId(cliente);

        cliDAO.deletar(cliente);
        userDAO.deletar(cliente.getUsuario());
        
        response.sendRedirect("ControlePessoa?Acao=LIST_CLIENTES");

    }

    private void list_clientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ClienteDAO cliDAO = new ClienteDAO();
        UsuarioDAO userDAO = new UsuarioDAO();

        List<Cliente> clientes = new ArrayList(cliDAO.buscarTudo());
        List<Usuario> usuarios = new ArrayList(userDAO.buscarTudo());

        for (Cliente cli : clientes) {
            for (Usuario user : usuarios) {
                if (cli.getUsuario().getId().equals(user.getId())) {
                    cli.setUsuario(user);
                }
            }
        }

        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente cli1, Cliente cli2) {
                return cli1.getNome().compareToIgnoreCase(cli2.getNome());
            }
        });
        
        request.setAttribute("clientes", clientes);
        request.getRequestDispatcher("listaClientes.jsp").forward(request, response);
    }

    //
    //
    // *************************************** METODOS DE AÇÃO FUNCIONARIO (SWITCH) ***********************************************************
    //
    //
    private void new_funcionario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("funcionario.jsp").forward(request, response);
    }

    private void edit_funcionario(HttpServletRequest request, HttpServletResponse response, String IdObjeto) throws ServletException, IOException {
        FuncionarioDAO funcDAO = new FuncionarioDAO();
        UsuarioDAO userDAO = new UsuarioDAO();

        Funcionario funcionario = funcDAO.buscarPorId(new Funcionario(Integer.parseInt(IdObjeto)));
        funcionario.setUsuario(userDAO.buscarPorId(funcionario.getUsuario()));
        
        request.setAttribute("funcionario", funcionario);
        request.getRequestDispatcher("funcionario.jsp").forward(request, response);
    }

    private void insert_update_funcionario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Funcionario funcionario = new Funcionario();
        FuncionarioDAO funcDAO = new FuncionarioDAO();
        UsuarioDAO userDAO = new UsuarioDAO();

        funcionario.setNome(request.getParameter("FuncNome"));
        funcionario.setCpf_cnpj(request.getParameter("FuncCpf"));
        funcionario.setEmail(request.getParameter("FuncEmail"));
        funcionario.setTelefone(request.getParameter("FuncTelefone"));
        funcionario.setEndereco(request.getParameter("FuncEndereco"));
        funcionario.setUf(request.getParameter("FuncUf"));
        funcionario.setData_nasc(request.getParameter("FuncDtNasc"));
        funcionario.setMatricula(Integer.parseInt(request.getParameter("FuncMatricula")));

        funcionario.getUsuario().setLogin(request.getParameter("FuncLogin"));
        funcionario.getUsuario().setSenha(Hash.sha512((request.getParameter("FuncSenha"))));

        funcionario.getUsuario().setPerfil(PerfilDeAcesso.valueOf(request.getParameter("FuncPerfil")));

        if (request.getParameter("FuncId").isEmpty()) {
            //INSERE FUNCIONARIO

            userDAO.inserir(funcionario.getUsuario());
            funcionario.setUsuario(userDAO.buscarPorLogin(funcionario.getUsuario()));

            funcDAO.inserir(funcionario);

            request.getRequestDispatcher("ControlePessoa?Acao=LIST_FUNCIONARIOS").forward(request, response);

        } else {
            //ATUALIZA FUNCIONARIO
            funcionario.setId(Integer.parseInt(request.getParameter("FuncId")));

            funcionario.setUsuario(userDAO.buscarPorLogin(funcionario.getUsuario()));
            funcionario.getUsuario().setSenha(Hash.sha512((request.getParameter("FuncSenha"))));
            funcionario.getUsuario().setPerfil(PerfilDeAcesso.valueOf(request.getParameter("FuncPerfil")));

            userDAO.atualizar(funcionario.getUsuario());
            
            funcDAO.atualizar(funcionario);

            request.getRequestDispatcher("ControlePessoa?Acao=LIST_FUNCIONARIOS").forward(request, response);
        }
    }

    private void delete_funcionario(HttpServletResponse response, String IdObjeto) throws ServletException, IOException {
        Funcionario funcionario = new Funcionario();
        FuncionarioDAO funcDAO = new FuncionarioDAO();
        UsuarioDAO userDAO = new UsuarioDAO();

        funcionario.setId(Integer.parseInt(IdObjeto));
        funcDAO.buscarPorId(funcionario);

        funcDAO.deletar(funcionario);
        userDAO.deletar(funcionario.getUsuario());
        
        response.sendRedirect("ControlePessoa?Acao=LIST_FUNCIONARIOS");
    }

    private void list_funcionarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FuncionarioDAO funcDAO = new FuncionarioDAO();
        UsuarioDAO userDAO = new UsuarioDAO();
        List<Funcionario> funcionarios = new ArrayList(funcDAO.buscarTudo());
        List<Usuario> usuarios = new ArrayList(userDAO.buscarTudo());

        for (Funcionario func : funcionarios) {
            for (Usuario user : usuarios) {
                if (func.getUsuario().getId().equals(user.getId())) {
                    func.setUsuario(user);
                }
            }
        }
        Collections.sort(funcionarios, new Comparator<Funcionario>() {
            @Override
            public int compare(Funcionario func1, Funcionario func2) {
                return func1.getNome().compareToIgnoreCase(func2.getNome());
            }
        });
        request.setAttribute("funcionarios", funcionarios);
        request.getRequestDispatcher("listaFuncionarios.jsp").forward(request, response);
    }

    //
    //
    // *************************************** METODOS DE AÇÃO FORNECEDOR (SWITCH) ***********************************************************
    //
    //
    private void new_fornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("fornecedor.jsp").forward(request, response);
    }

    private void edit_fornecedor(HttpServletRequest request, HttpServletResponse response, String IdObjeto) throws ServletException, IOException {
        FornecedorDAO fornDAO = new FornecedorDAO();

        Fornecedor fornecedor = fornDAO.buscarPorId(new Fornecedor(Integer.parseInt(IdObjeto)));
        request.setAttribute("fornecedor", fornecedor);
        request.getRequestDispatcher("fornecedor.jsp").forward(request, response);
    }

    private void insert_update_fornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Fornecedor fornecedor = new Fornecedor();
        FornecedorDAO fornDAO = new FornecedorDAO();

        fornecedor.setNome(request.getParameter("FornNome"));
        fornecedor.setCpf_cnpj(request.getParameter("FornCnpj"));
        fornecedor.setEmail(request.getParameter("FornEmail"));
        fornecedor.setTelefone(request.getParameter("FornTelefone"));
        fornecedor.setEndereco(request.getParameter("FornEndereco"));
        fornecedor.setUf(request.getParameter("FornUf"));
        fornecedor.setContato(request.getParameter("FornContato"));

        if (request.getParameter("FornId").isEmpty()) {
            fornDAO.inserir(fornecedor);
        } else {
            fornecedor.setId(Integer.parseInt(request.getParameter("FornId")));
            fornDAO.atualizar(fornecedor);
        }

        request.getRequestDispatcher("ControlePessoa?Acao=LIST_FORNECEDORES").forward(request, response);
    }

    private void delete_fornecedor(HttpServletResponse response, String IdObjeto) throws ServletException, IOException {
        Fornecedor fornecedor = new Fornecedor();
        FornecedorDAO fornDAO = new FornecedorDAO();

        fornecedor.setId(Integer.parseInt(IdObjeto));
        fornDAO.deletar(fornecedor);
        
        response.sendRedirect("ControlePessoa?Acao=LIST_FORNECEDORES");
    }

    private void list_fornecedores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FornecedorDAO fornDAO = new FornecedorDAO();

        request.setAttribute("fornecedores", fornDAO.buscarTudo());
        request.getRequestDispatcher("listaFornecedores.jsp").forward(request, response);
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

    @Override
    protected void doPost(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
        processRequest(Request, Response);
    }

    @Override
    protected void doGet(HttpServletRequest Request, HttpServletResponse Response) throws ServletException, IOException {
        processRequest(Request, Response);
    }
}
