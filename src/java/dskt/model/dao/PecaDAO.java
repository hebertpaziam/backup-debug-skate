package dskt.model.dao;

import dskt.model.Peca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dskt.util.ConexaoPostgreSQL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PecaDAO {

    public Peca buscarPorId(Peca peca) {
        Connection conexao = null;

        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from peca where pec_id = ?");
            pstmt.setInt(1, peca.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // monta o objeto peca a partir dos dados que retornam da query
                peca.setId(rs.getInt("pec_id"));
                peca.setModelo(rs.getString("pec_modelo"));
                peca.setDescricao(rs.getString("pec_descricao"));
                peca.setCor(rs.getString("pec_cor"));
                peca.setTamanho(rs.getString("pec_tamanho"));
                peca.getFornecedor().setId(rs.getInt("pec_fornecedor")); //seta o ID do fornecedor para que o DAO do fornecedor monte o objeto
                peca.setQtd_estoque(rs.getInt("pec_qtd_estoque"));
                peca.setValor_compra(rs.getDouble("pec_valor_compra"));
                peca.setValor_venda(rs.getDouble("pec_valor_venda"));
                peca.setRef_imagem(rs.getString("pec_ref_imagem"));
                peca.setTipo(rs.getString("pec_tipo"));
            }
            return peca;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(PecaDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public List<Peca> buscarTudo() {
        Connection conexao = null;
        List<Peca> pecas = new ArrayList<>();

        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from peca order by pec_modelo;");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                //Instancia um objeto peca que será retornado para a controller
                Peca peca = new Peca();
                peca.setId(rs.getInt("pec_id"));
                peca.setModelo(rs.getString("pec_modelo"));
                peca.setDescricao(rs.getString("pec_descricao"));
                peca.setCor(rs.getString("pec_cor"));
                peca.setTamanho(rs.getString("pec_tamanho"));
                peca.getFornecedor().setId(rs.getInt("pec_fornecedor")); //seta o ID do fornecedor para que o DAO do fornecedor monte o objeto
                peca.setQtd_estoque(rs.getInt("pec_qtd_estoque"));
                peca.setValor_compra(rs.getDouble("pec_valor_compra"));
                peca.setValor_venda(rs.getDouble("pec_valor_venda"));
                peca.setRef_imagem(rs.getString("pec_ref_imagem"));
                peca.setTipo(rs.getString("pec_tipo"));

                pecas.add(peca); //adciona o objeto peça uma lista de peca que ira retornar para a controller
            }
            return pecas;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(PecaDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public boolean inserir(Peca peca) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("insert into peca ("
                    + "pec_modelo,"
                    + "pec_descricao,"
                    + "pec_cor,"
                    + "pec_tamanho, "
                    + "pec_fornecedor,"
                    + "pec_qtd_estoque,"
                    + "pec_valor_compra,"
                    + "pec_valor_venda,"
                    + "pec_ref_imagem,"
                    + "pec_tipo)"
                    + "values(?,?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, peca.getModelo());
            pstmt.setString(2, peca.getDescricao());
            pstmt.setString(3, peca.getCor());
            pstmt.setString(4, peca.getTamanho());
            pstmt.setInt(5, peca.getFornecedor().getId());
            pstmt.setInt(6, peca.getQtd_estoque());
            pstmt.setDouble(7, peca.getValor_compra());
            pstmt.setDouble(8, peca.getValor_venda());
            pstmt.setString(9, peca.getRef_imagem());
            pstmt.setString(10, peca.getTipo());

            pstmt.execute();
            return true;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(PecaDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public boolean atualizar(Peca peca) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("update peca set "
                    + "pec_modelo = ?,"
                    + "pec_descricao = ?,"
                    + "pec_cor = ?,"
                    + "pec_tamanho = ?,"
                    + "pec_fornecedor = ?,"
                    + "pec_qtd_estoque = ?,"
                    + "pec_valor_compra = ?,"
                    + "pec_valor_venda = ?,"
                    + "pec_ref_imagem = ?,"
                    + "pec_tipo = ?"
                    + "  where pec_id = ?");
            pstmt.setString(1, peca.getModelo());
            pstmt.setString(2, peca.getDescricao());
            pstmt.setString(3, peca.getCor());
            pstmt.setString(4, peca.getTamanho());
            pstmt.setInt(5, peca.getFornecedor().getId());
            pstmt.setInt(6, peca.getQtd_estoque());
            pstmt.setDouble(7, peca.getValor_compra());
            pstmt.setDouble(8, peca.getValor_venda());
            pstmt.setString(9, peca.getRef_imagem());
            pstmt.setString(10, peca.getTipo());

            pstmt.setInt(11, peca.getId());
            pstmt.execute();
            return true;
        } catch (SQLException ex1) {
            throw new RuntimeException(ex1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PecaDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    public boolean deletar(Peca peca) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("delete from peca where pec_id = ?");
            pstmt.setInt(1, peca.getId());
            pstmt.execute();
            return true;
        } catch (SQLException ex1) {
            throw new RuntimeException(ex1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PecaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
