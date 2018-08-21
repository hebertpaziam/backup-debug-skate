package dskt.model.dao;

import dskt.model.Fornecedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dskt.util.ConexaoPostgreSQL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FornecedorDAO {

    public Fornecedor buscarPorId(Fornecedor fornecedor) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from fornecedor where forn_id = ?");
            pstmt.setInt(1, fornecedor.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                fornecedor.setId(rs.getInt("forn_id"));
                fornecedor.setNome(rs.getString("forn_nome"));
                fornecedor.setCpf_cnpj(rs.getString("forn_cnpj"));
                fornecedor.setEmail(rs.getString("forn_email"));
                fornecedor.setTelefone(rs.getString("forn_telefone"));
                fornecedor.setEndereco(rs.getString("forn_endereco"));
                fornecedor.setUf(rs.getString("forn_uf"));
                fornecedor.setContato(rs.getString("forn_contato"));
            }
            return fornecedor;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, sql);
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
    
    public List<Fornecedor> buscarTudo() {
        Connection conexao = null;
        List<Fornecedor> fornecedores = new ArrayList<>();
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from fornecedor order by forn_id");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();

                fornecedor.setId(rs.getInt("forn_id"));
                fornecedor.setNome(rs.getString("forn_nome"));
                fornecedor.setCpf_cnpj(rs.getString("forn_cnpj"));
                fornecedor.setEmail(rs.getString("forn_email"));
                fornecedor.setTelefone(rs.getString("forn_telefone"));
                fornecedor.setEndereco(rs.getString("forn_endereco"));
                fornecedor.setUf(rs.getString("forn_uf"));
                fornecedor.setContato(rs.getString("forn_contato"));

                fornecedores.add(fornecedor);
            }
            return fornecedores;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, sql);
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
    
    public boolean inserir(Fornecedor fornecedor) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement(""
                    + "insert into fornecedor (forn_nome,"
                    + "forn_cnpj,"
                    + " forn_email,"
                    + " forn_telefone,"
                    + " forn_endereco,"
                    + " forn_uf,"
                    + " forn_contato)"
                    + " values (?,?,?,?,?,?,?)");
            pstmt.setString(1, fornecedor.getNome());
            pstmt.setString(2, fornecedor.getCpf_cnpj());
            pstmt.setString(3, fornecedor.getEmail());
            pstmt.setString(4, fornecedor.getTelefone());
            pstmt.setString(5, fornecedor.getEndereco());
            pstmt.setString(6, fornecedor.getUf());
            pstmt.setString(7, fornecedor.getContato());
            pstmt.execute();
            return true;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public boolean atualizar(Fornecedor fornecedor) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("update fornecedor set"
                    + " forn_nome = ?,"
                    + " forn_cnpj = ?,"
                    + " forn_email = ?,"
                    + " forn_telefone = ?,"
                    + " forn_endereco = ?,"
                    + " forn_uf = ?,"
                    + " forn_contato = ?"
                    + "  where forn_id = ?");
            pstmt.setString(1, fornecedor.getNome());
            pstmt.setString(2, fornecedor.getCpf_cnpj());
            pstmt.setString(3, fornecedor.getEmail());
            pstmt.setString(4, fornecedor.getTelefone());
            pstmt.setString(5, fornecedor.getEndereco());
            pstmt.setString(6, fornecedor.getUf());
            pstmt.setString(7, fornecedor.getContato());
            pstmt.setInt(8, fornecedor.getId());
            pstmt.execute();
            return true;
        } catch (SQLException ex1) {
            throw new RuntimeException(ex1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    public boolean deletar(Fornecedor fornecedor) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("delete from fornecedor where forn_id = ?");
            pstmt.setInt(1, fornecedor.getId());
            pstmt.execute();
            return true;
        } catch (SQLException ex1) {
            throw new RuntimeException(ex1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FornecedorDAO.class.getName()).log(Level.SEVERE, null, ex);
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
