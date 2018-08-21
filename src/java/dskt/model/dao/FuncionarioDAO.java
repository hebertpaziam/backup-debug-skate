package dskt.model.dao;

import dskt.model.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dskt.util.ConexaoPostgreSQL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuncionarioDAO {

    public Funcionario buscarPorId(Funcionario funcionario) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from funcionario where func_id = ?");
            pstmt.setInt(1, funcionario.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                funcionario.setId(rs.getInt("func_id"));
                funcionario.setNome(rs.getString("func_nome"));
                funcionario.setCpf_cnpj(rs.getString("func_cpf"));
                funcionario.setEmail(rs.getString("func_email"));
                funcionario.setTelefone(rs.getString("func_telefone"));
                funcionario.setEndereco(rs.getString("func_endereco"));
                funcionario.setUf(rs.getString("func_uf"));
                funcionario.setData_nasc(rs.getString("func_data_nasc"));
                funcionario.setMatricula(rs.getInt("func_matricula"));
                funcionario.getUsuario().setId(rs.getInt("func_usuario"));
            }
            return funcionario;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public Funcionario buscarPorUsuario(Funcionario funcionario) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from funcionario where func_usuario = ?");
            pstmt.setInt(1, funcionario.getUsuario().getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                funcionario.setId(rs.getInt("func_id"));
                funcionario.setNome(rs.getString("func_nome"));
                funcionario.setCpf_cnpj(rs.getString("func_cpf"));
                funcionario.setEmail(rs.getString("func_email"));
                funcionario.setTelefone(rs.getString("func_telefone"));
                funcionario.setEndereco(rs.getString("func_endereco"));
                funcionario.setUf(rs.getString("func_uf"));
                funcionario.setData_nasc(rs.getString("func_data_nasc"));
                funcionario.setMatricula(rs.getInt("func_matricula"));
                funcionario.getUsuario().setId(rs.getInt("func_usuario"));
            }
            return funcionario;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public List<Funcionario> buscarTudo() {
        Connection conexao = null;
        List<Funcionario> funcionarios = new ArrayList<>();
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from funcionario order by func_id");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();

                funcionario.setId(rs.getInt("func_id"));
                funcionario.setNome(rs.getString("func_nome"));
                funcionario.setCpf_cnpj(rs.getString("func_cpf"));
                funcionario.setEmail(rs.getString("func_email"));
                funcionario.setTelefone(rs.getString("func_telefone"));
                funcionario.setEndereco(rs.getString("func_endereco"));
                funcionario.setUf(rs.getString("func_uf"));
                funcionario.setData_nasc(rs.getString("func_data_nasc"));
                funcionario.setMatricula(rs.getInt("func_matricula"));
                funcionario.getUsuario().setId(rs.getInt("func_usuario"));

                funcionarios.add(funcionario);
            }
            return funcionarios;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public boolean inserir(Funcionario funcionario) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement(""
                    + "insert into funcionario (func_nome,"
                    + " func_cpf,"
                    + " func_email,"
                    + " func_telefone,"
                    + " func_endereco,"
                    + " func_uf,"
                    + " func_data_nasc,"
                    + " func_matricula,"
                    + " func_usuario)"
                    + " values (?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getCpf_cnpj());
            pstmt.setString(3, funcionario.getEmail());
            pstmt.setString(4, funcionario.getTelefone());
            pstmt.setString(5, funcionario.getEndereco());
            pstmt.setString(6, funcionario.getUf());
            pstmt.setString(7, funcionario.getData_nasc());
            pstmt.setInt(8, funcionario.getMatricula());
            pstmt.setInt(9, funcionario.getUsuario().getId());
            pstmt.execute();
            return true;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public boolean atualizar(Funcionario funcionario) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("update funcionario set"
                    + " func_nome = ?,"
                    + " func_cpf = ?,"
                    + " func_email = ?,"
                    + " func_telefone = ?,"
                    + " func_endereco = ?,"
                    + " func_uf = ?,"
                    + " func_data_nasc = ?,"
                    + " func_matricula = ?"
                    + " where func_usuario = ?");
            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getCpf_cnpj());
            pstmt.setString(3, funcionario.getEmail());
            pstmt.setString(4, funcionario.getTelefone());
            pstmt.setString(5, funcionario.getEndereco());
            pstmt.setString(6, funcionario.getUf());
            pstmt.setString(7, funcionario.getData_nasc());
            pstmt.setInt(8, funcionario.getMatricula());
            pstmt.setInt(9, funcionario.getUsuario().getId());
            pstmt.execute();
            return true;
        } catch (SQLException ex1) {
            throw new RuntimeException(ex1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    public boolean deletar(Funcionario funcionario) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("delete from funcionario where func_id = ?");
            pstmt.setInt(1, funcionario.getId());
            pstmt.execute();
            return true;
        } catch (SQLException ex1) {
            throw new RuntimeException(ex1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
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
