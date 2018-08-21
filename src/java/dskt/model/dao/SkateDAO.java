package dskt.model.dao;

import dskt.model.Skate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dskt.util.ConexaoPostgreSQL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SkateDAO {

    public Skate buscarPorId(Skate skate) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from venda_skates where skt_id = ?");
            pstmt.setInt(1, skate.getId());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                skate.setId(rs.getInt("skt_id"));
                skate.getShape().setId(rs.getInt("skt_shape"));
                skate.getTruck().setId(rs.getInt("skt_truck"));
                skate.getRoda().setId(rs.getInt("skt_roda"));
                skate.getLixa().setId(rs.getInt("skt_lixa"));
                skate.setFlgMontagem((rs.getBoolean("skt_flg_montagem")));
                skate.setDtMontagem((rs.getString("skt_dt_montagem")));
                skate.setIdFuncionario((rs.getInt("skt_funcionario")));
                skate.setIdVenda(rs.getInt("skt_venda"));
            }
            return skate;

        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(PecaDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    public List<Skate> buscarSkatesMontados(Skate skate) {
        Connection conexao = null;
        List<Skate> listaSkate = new ArrayList<>();

        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from venda_skates where skt_venda = ? and skt_flg_montagem = true");
            pstmt.setInt(1, skate.getIdVenda());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                skate.setId(rs.getInt("skt_id"));
                skate.getShape().setId(rs.getInt("skt_shape"));
                skate.getTruck().setId(rs.getInt("skt_truck"));
                skate.getRoda().setId(rs.getInt("skt_roda"));
                skate.getLixa().setId(rs.getInt("skt_lixa"));
                skate.setFlgMontagem((rs.getBoolean("skt_flg_montagem")));
                skate.setDtMontagem((rs.getString("skt_dt_montagem")));
                skate.setIdFuncionario((rs.getInt("skt_funcionario")));
                skate.setIdVenda(rs.getInt("skt_venda"));

                listaSkate.add(skate);
            }
            return listaSkate;

        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(PecaDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    public List<Skate> buscarSkatesNaoMontados(Skate skate) {
        Connection conexao = null;
        List<Skate> listaSkate = new ArrayList<>();

        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from venda_skates where skt_venda = ? and skt_flg_montagem = false");
            pstmt.setInt(1, skate.getIdVenda());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                skate.setId(rs.getInt("skt_id"));
                skate.getShape().setId(rs.getInt("skt_shape"));
                skate.getTruck().setId(rs.getInt("skt_truck"));
                skate.getRoda().setId(rs.getInt("skt_roda"));
                skate.getLixa().setId(rs.getInt("skt_lixa"));
                skate.setFlgMontagem((rs.getBoolean("skt_flg_montagem")));
                skate.setDtMontagem((rs.getString("skt_dt_montagem")));
                skate.setIdFuncionario((rs.getInt("skt_funcionario")));
                skate.setIdVenda(rs.getInt("skt_venda"));

                listaSkate.add(skate);
            }
            return listaSkate;

        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(PecaDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    public boolean marcarComoMontado(Skate skate) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("update venda_skates set "
                    + "skt_flg_montagem = ?,"
                    + "skt_dt_montagem = ?,"
                    + "skt_funcionario = ?"
                    + "where skt_id = ?");
            pstmt.setBoolean(1, skate.getFlgMontagem());
            pstmt.setString(2, skate.getDtMontagem());
            pstmt.setInt(3, skate.getIdFuncionario());
            
            pstmt.setInt(4, skate.getId());
            pstmt.execute();
            return true;
        } catch (SQLException ex1) {
            throw new RuntimeException(ex1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<Skate> buscarTudo() {
        Connection conexao = null;
        List<Skate> skates = new ArrayList<>();

        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from venda_skates");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Skate skate = new Skate();

                skate.setId(rs.getInt("skt_id"));
                skate.getShape().setId(rs.getInt("skt_shape"));
                skate.getTruck().setId(rs.getInt("skt_truck"));
                skate.getRoda().setId(rs.getInt("skt_roda"));
                skate.getLixa().setId(rs.getInt("skt_lixa"));
                skate.setFlgMontagem((rs.getBoolean("skt_flg_montagem")));
                skate.setDtMontagem((rs.getString("skt_dt_montagem")));
                skate.setIdFuncionario((rs.getInt("skt_funcionario")));
                skate.setIdVenda(rs.getInt("skt_venda"));

                skates.add(skate);
            }
            return skates;

        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(PecaDAO.class.getName()).log(Level.SEVERE, null, sql);
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    public boolean inserir(Skate skate) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("insert into venda_skates ("
                    + "skt_shape,"
                    + "skt_truck,"
                    + "skt_roda,"
                    + "skt_lixa,"
                    + "skt_venda)"
                    + "values(?,?,?,?,?)");
            pstmt.setInt(1, skate.getShape().getId());
            pstmt.setInt(2, skate.getTruck().getId());
            pstmt.setInt(3, skate.getRoda().getId());
            pstmt.setInt(4, skate.getLixa().getId());
            pstmt.setInt(5, skate.getIdVenda());

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

    public boolean atualizar(Skate skate) {
        //IMPLEMENTAR LÓGIGA
        return true;
    }

    public boolean aprovarMontagem(Skate skate) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("update venda_skates set "
                    + "skt_flg_montagem = ?,"
                    + "skt_dt_montagem = ?"
                    + "where skt_id = ?");
            pstmt.setBoolean(1, skate.getFlgMontagem());
            pstmt.setString(2, skate.getDtMontagem());

            pstmt.setInt(3, skate.getId());
            pstmt.execute();
            return true;
        } catch (SQLException ex1) {
            throw new RuntimeException(ex1);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
