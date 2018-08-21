package dskt.model.dao;

import dskt.model.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dskt.util.ConexaoPostgreSQL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendaDAO {

    public Venda buscarPorId(Venda venda) {
        Connection conexao = null;

        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from venda where vd_id = ?");
            pstmt.setInt(1, venda.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // monta o objeto venda a partir dos dados que retornam da query
                venda.setId(rs.getInt("vd_id"));
                venda.getCliente().setId(rs.getInt("vd_cliente"));
                venda.setValorVenda(rs.getDouble("vd_valor_venda"));
                venda.setValorFrete(rs.getDouble("vd_valor_frete"));
                venda.setDtPedido(rs.getString("vd_dt_pedido"));
                venda.setFlgPagamento(rs.getBoolean("vd_flg_pagamento"));
                venda.setDtPagamento(rs.getString("vd_dt_pagamento"));
                venda.setFlgCancelamento(rs.getBoolean("vd_flg_cancelamento"));
                venda.setDtCancelamento(rs.getString("vd_dt_cancelamento"));
            }
            return venda;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public List<Venda> buscarVendasAguardandoPagamento() {
        Connection conexao = null;
        List<Venda> vendas = new ArrayList<>();

        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from venda where vd_flg_pagamento = false and vd_flg_cancelamento = false");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda();

                venda.setId(rs.getInt("vd_id"));
                venda.getCliente().setId(rs.getInt("vd_cliente"));
                venda.setValorVenda(rs.getDouble("vd_valor_venda"));
                venda.setValorFrete(rs.getDouble("vd_valor_frete"));
                venda.setDtPedido(rs.getString("vd_dt_pedido"));
                venda.setFlgPagamento(rs.getBoolean("vd_flg_pagamento"));
                venda.setFlgCancelamento(rs.getBoolean("vd_flg_cancelamento"));

                vendas.add(venda);
            }
            return vendas;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public List<Venda> buscarVendasPagasNaoConcluidas() {
        Connection conexao = null;
        List<Venda> vendas = new ArrayList<>();

        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from venda where vd_flg_pagamento = true and vd_flg_cancelamento = false and vd_id in (select distinct skt_venda from venda_skates where skt_flg_montagem = false);");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda();

                venda.setId(rs.getInt("vd_id"));
                venda.getCliente().setId(rs.getInt("vd_cliente"));
                venda.setValorVenda(rs.getDouble("vd_valor_venda"));
                venda.setValorFrete(rs.getDouble("vd_valor_frete"));
                venda.setDtPedido(rs.getString("vd_dt_pedido"));
                venda.setFlgPagamento(rs.getBoolean("vd_flg_pagamento"));
                venda.setFlgCancelamento(rs.getBoolean("vd_flg_cancelamento"));

                vendas.add(venda);
            }
            return vendas;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, sql);
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
    public List<Venda> buscarVendasConcluidas() {
        Connection conexao = null;
        List<Venda> vendas = new ArrayList<>();

        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from venda where vd_flg_pagamento = true and vd_flg_cancelamento = false and vd_id not in (select distinct skt_venda from venda_skates where skt_flg_montagem = false);");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda();

                venda.setId(rs.getInt("vd_id"));
                venda.getCliente().setId(rs.getInt("vd_cliente"));
                venda.setValorVenda(rs.getDouble("vd_valor_venda"));
                venda.setValorFrete(rs.getDouble("vd_valor_frete"));
                venda.setDtPedido(rs.getString("vd_dt_pedido"));
                venda.setFlgPagamento(rs.getBoolean("vd_flg_pagamento"));
                venda.setFlgCancelamento(rs.getBoolean("vd_flg_cancelamento"));

                vendas.add(venda);
            }
            return vendas;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public Venda inserirRetornandoId(Venda venda) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("insert into venda ("
                    + "vd_cliente,"
                    + "vd_valor_venda, "
                    + "vd_valor_frete,"
                    + "vd_valor_total,"
                    + "vd_dt_pedido)"
                    + "values(?,?,?,?,?) returning vd_id");
            pstmt.setInt(1, venda.getCliente().getId());
            pstmt.setDouble(2, venda.getValorVenda());
            pstmt.setDouble(3, venda.getValorFrete());
            pstmt.setDouble(4, venda.getValorTotal());
            pstmt.setString(5, venda.getDtPedido());
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                venda.setId(rs.getInt("vd_id"));
            }
            return venda;

        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public boolean aprovarPagamento(Venda venda) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("update venda set "
                    + "vd_flg_pagamento = ?,"
                    + "vd_dt_pagamento = ?"
                    + "where vd_id = ?");
            pstmt.setBoolean(1, venda.getFlgPagamento());
            pstmt.setString(2, venda.getDtPagamento());

            pstmt.setInt(3, venda.getId());
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

    public boolean cancelarVenda(Venda venda) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("update venda set "
                    + "vd_flg_cancelamento = ?,"
                    + "vd_dt_cancelamento = ?"
                    + "where vd_id = ?");
            pstmt.setBoolean(1, venda.getFlgCancelamento());
            pstmt.setString(2, venda.getDtCancelamento());
            pstmt.setInt(3, venda.getId());
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
