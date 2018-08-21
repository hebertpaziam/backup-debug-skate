package dskt.model.dao;

import dskt.model.ItemDeSolicitacao;
import dskt.model.Peca;
import dskt.model.SolicitacaoPecas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dskt.util.ConexaoPostgreSQL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolicitacaoPecasDAO {

    public boolean inserirSolicitacaoNova(SolicitacaoPecas sol) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            int i = 0;
            while (i < sol.getItensDeSolicitacao().size()) {
                PreparedStatement pstmt = conexao.prepareStatement("insert into solicitacao ("
                        + "sol_id,"
                        + "sol_peca,"
                        + "sol_qtd_pecas,"
                        + "sol_status) "
                        + "values(?,?,?,false)");
                pstmt.setInt(1, sol.getIdSol());
                pstmt.setInt(2, sol.getItensDeSolicitacao().get(i).getPeca().getId());
                pstmt.setInt(3, sol.getItensDeSolicitacao().get(i).getQtdPecas());
                pstmt.execute();
                i++;
            }
            return true;
            
        } catch (SQLException | ClassNotFoundException err) {
            Logger.getLogger(SolicitacaoPecasDAO.class.getName()).log(Level.SEVERE, null, err);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException err2) {
                throw new RuntimeException(err2);
            }
        }
        return false;
    }

    public Integer IdDaProximaSolicitacao() {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select sum((select max(sol_id) from solicitacao) + 1)");
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt("sum");
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(SolicitacaoPecasDAO.class.getName()).log(Level.SEVERE, null, sql);
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

   
    public boolean verificarUltimaSolicitacao() {
        Connection conexao = null;
        boolean status = false;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("Select sol_status FROM solicitacao order by sol_id DESC LIMIT 1");
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            status = (rs.getBoolean("sol_status"));
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(SolicitacaoPecasDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex2) {
                throw new RuntimeException(ex2);
            }
        }
        return status;
    }

    public SolicitacaoPecas retornaUltimaSolicitacao() {
        Connection conexao = null;
        SolicitacaoPecas solicitacaoPendente = new SolicitacaoPecas();
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from solicitacao where sol_id = (select sol_id from solicitacao order by sol_id desc limit 1) and sol_status = false");
            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                solicitacaoPendente.setIdSol(rs.getInt("sol_id"));
                solicitacaoPendente.getItensDeSolicitacao().add(new ItemDeSolicitacao(new Peca(rs.getInt("sol_peca"))));
                solicitacaoPendente.getItensDeSolicitacao().get(i).setQtdPecas(rs.getInt("sol_qtd_pecas"));
                solicitacaoPendente.getItensDeSolicitacao().get(i).setIdSol(rs.getInt("sol_id"));
                i++;
            }
            return solicitacaoPendente;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(SolicitacaoPecasDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public void excluiSolicitacaoPendente(SolicitacaoPecas sol) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("delete from solicitacao where sol_id = ?");
            pstmt.setInt(1, sol.getIdSol());
            pstmt.execute();
        } catch (SQLException | ClassNotFoundException err) {
            Logger.getLogger(SolicitacaoPecasDAO.class.getName()).log(Level.SEVERE, null, err);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException err2) {
                throw new RuntimeException(err2);
            }
        }
    }

    public void enviarSolicitacaoPendente(SolicitacaoPecas sol) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("update solicitacao set sol_status = true where sol_id = ?");
            pstmt.setInt(1, sol.getIdSol());
            pstmt.execute();
        } catch (SQLException | ClassNotFoundException err) {
            Logger.getLogger(SolicitacaoPecasDAO.class.getName()).log(Level.SEVERE, null, err);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException err2) {
                throw new RuntimeException(err2);
            }
        }
    }
}
