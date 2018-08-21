package dskt.model.dao;

import dskt.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dskt.util.ConexaoPostgreSQL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO {
    
    public Cliente buscarPorId(Cliente cliente) {

        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from cliente where cli_id = ?");
            pstmt.setInt(1, cliente.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente.setId(rs.getInt("cli_id"));
                cliente.setNome(rs.getString("cli_nome"));
                cliente.setCpf_cnpj(rs.getString("cli_cpf"));
                cliente.setEmail(rs.getString("cli_email"));
                cliente.setTelefone(rs.getString("cli_telefone"));
                cliente.setEndereco(rs.getString("cli_endereco"));
                cliente.setUf(rs.getString("cli_uf"));
                cliente.setData_nasc(rs.getString("cli_data_nasc"));
                cliente.getUsuario().setId(rs.getInt("cli_usuario"));
            }
            return cliente;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex2) {
                throw new RuntimeException(ex2);
            }
        }
        return null;
    }
    public Cliente buscarPorCPF(Cliente cliente) {

        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from cliente where cli_cpf = ?");
            pstmt.setString(1, cliente.getCpf_cnpj());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente.setId(rs.getInt("cli_id"));
                cliente.setNome(rs.getString("cli_nome"));
                cliente.setCpf_cnpj(rs.getString("cli_cpf"));
                cliente.setEmail(rs.getString("cli_email"));
                cliente.setTelefone(rs.getString("cli_telefone"));
                cliente.setEndereco(rs.getString("cli_endereco"));
                cliente.setUf(rs.getString("cli_uf"));
                cliente.setData_nasc(rs.getString("cli_data_nasc"));
                cliente.getUsuario().setId(rs.getInt("cli_usuario"));
            }
            return cliente;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex2) {
                throw new RuntimeException(ex2);
            }
        }
        return null;
    }

    public Cliente buscarPorUsuario(Cliente cliente) {

        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from cliente where cli_usuario = ?");
            pstmt.setInt(1, cliente.getUsuario().getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cliente.setId(rs.getInt("cli_id"));
                cliente.setNome(rs.getString("cli_nome"));
                cliente.setCpf_cnpj(rs.getString("cli_cpf"));
                cliente.setEmail(rs.getString("cli_email"));
                cliente.setTelefone(rs.getString("cli_telefone"));
                cliente.setEndereco(rs.getString("cli_endereco"));
                cliente.setUf(rs.getString("cli_uf"));
                cliente.setData_nasc(rs.getString("cli_data_nasc"));
                cliente.getUsuario().setId(rs.getInt("cli_usuario"));
            }
            return cliente;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex2) {
                throw new RuntimeException(ex2);
            }
        }
        return null;
    }
    public Boolean verificarCPF(Cliente cliente) {

        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from cliente where cli_cpf = ?");
            pstmt.setString(1, cliente.getCpf_cnpj());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return false;
            }
            return true;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex2) {
                throw new RuntimeException(ex2);
            }
        }
        return null;
    }

    public List<Cliente> buscarTudo() {
        Connection conexao = null;
        List<Cliente> clientes = new ArrayList<>();
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from cliente order by cli_id");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("cli_id"));
                cliente.setNome(rs.getString("cli_nome"));
                cliente.setCpf_cnpj(rs.getString("cli_cpf"));
                cliente.setEmail(rs.getString("cli_email"));
                cliente.setTelefone(rs.getString("cli_telefone"));
                cliente.setEndereco(rs.getString("cli_endereco"));
                cliente.setUf(rs.getString("cli_uf"));
                cliente.setData_nasc(rs.getString("cli_data_nasc"));
                cliente.getUsuario().setId(rs.getInt("cli_usuario"));

                clientes.add(cliente);
            }
            return clientes;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex2) {
                throw new RuntimeException(ex2);
            }
        }
        return null;
    }

    public boolean inserir(Cliente cliente) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement(""
                    + "insert into cliente (cli_nome,"
                    + " cli_cpf,"
                    + " cli_email,"
                    + " cli_telefone,"
                    + " cli_endereco,"
                    + " cli_uf,"
                    + " cli_data_nasc,"
                    + " cli_usuario)"
                    + " values (?,?,?,?,?,?,?,?)");
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf_cnpj());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getTelefone());
            pstmt.setString(5, cliente.getEndereco());
            pstmt.setString(6, cliente.getUf());
            pstmt.setString(7, cliente.getData_nasc());
            pstmt.setInt(8, cliente.getUsuario().getId());
            pstmt.execute();
            return true;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex2) {
                throw new RuntimeException(ex2);
            }
        }
        return false;
    }
    public Cliente inserirReturnandoId(Cliente cliente) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement(""
                    + "insert into cliente (cli_nome,"
                    + " cli_cpf,"
                    + " cli_email,"
                    + " cli_telefone,"
                    + " cli_endereco,"
                    + " cli_uf,"
                    + " cli_data_nasc,"
                    + " cli_usuario)"
                    + " values (?,?,?,?,?,?,?,?) returning cli_id");
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf_cnpj());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getTelefone());
            pstmt.setString(5, cliente.getEndereco());
            pstmt.setString(6, cliente.getUf());
            pstmt.setString(7, cliente.getData_nasc());
            pstmt.setInt(8, cliente.getUsuario().getId());
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                cliente.setId(rs.getInt("cli_id"));
            }
            
            return cliente;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex2) {
                throw new RuntimeException(ex2);
            }
        }
        return null;
    }

    public boolean atualizar(Cliente cliente) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("update cliente set"
                    + " cli_nome = ?,"
                    + " cli_cpf = ?,"
                    + " cli_email = ?,"
                    + " cli_telefone = ?,"
                    + " cli_endereco = ?,"
                    + " cli_uf = ?,"
                    + " cli_data_nasc = ?"
                    + " where cli_usuario = ?");
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf_cnpj());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getTelefone());
            pstmt.setString(5, cliente.getEndereco());
            pstmt.setString(6, cliente.getUf());
            pstmt.setString(7, cliente.getData_nasc());
            pstmt.setInt(8, cliente.getUsuario().getId());
            pstmt.execute();
            return true;
        } catch (SQLException ex1) {
            throw new RuntimeException(ex1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex2) {
                throw new RuntimeException(ex2);
            }
        }
        return false;
    }

    public boolean deletar(Cliente cliente) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("delete from cliente where cli_id = ?");
            pstmt.setInt(1, cliente.getId());
            pstmt.execute();
            return true;
        } catch (SQLException ex1) {
            throw new RuntimeException(ex1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex2) {
                throw new RuntimeException(ex2);
            }
        }
        return false;
    }

}
