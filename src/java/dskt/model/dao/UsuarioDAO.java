package dskt.model.dao;

import dskt.model.PerfilDeAcesso;
import dskt.model.Usuario;
import dskt.util.ConexaoPostgreSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    public List<Usuario> buscarTudo() {
        Connection conexao = null;
        List<Usuario> usuarios = new ArrayList<>();
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from usuario order by usr_id");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("usr_id"));
                usuario.setLogin(rs.getString("usr_login"));
                usuario.setSenha(null);
                usuario.setPerfil(PerfilDeAcesso.valueOf(rs.getString("usr_perfil")));

                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, sql);
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

    public Usuario validar(Usuario usuario) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from usuario where usr_login = ? and usr_senha = ?");
            pstmt.setString(1, usuario.getLogin());
            pstmt.setString(2, usuario.getSenha());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (!rs.getString("usr_login").isEmpty() || !rs.getString("usr_senha").isEmpty()) {
                    usuario.setId(rs.getInt("usr_id"));
                    usuario.setLogin(rs.getString("usr_login"));
                    usuario.setSenha(null);
                    usuario.setPerfil(PerfilDeAcesso.valueOf(rs.getString("usr_perfil")));
                    return usuario;
                } else {
                    return null;
                }
            }

        } catch (SQLException | ClassNotFoundException | NullPointerException err) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, err);

        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException err) {
                throw new RuntimeException(err);
            }
        }
        return null;
    }

    public Usuario buscarPorLogin(Usuario usuario) {
        Connection conexao = null;

        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from usuario where usr_login = ?");
            pstmt.setString(1, usuario.getLogin());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // monta o objeto usuario a partir dos dados que retornam da query
                usuario.setId(rs.getInt("usr_id"));
                usuario.setLogin(rs.getString("usr_login"));
                usuario.setSenha(null);
                usuario.setPerfil(PerfilDeAcesso.valueOf(rs.getString("usr_perfil")));
            }
            return usuario;

        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(PecaDAO.class
                    .getName()).log(Level.SEVERE, null, sql);
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

    public Usuario buscarPorId(Usuario usuario) {
        Connection conexao = null;

        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("select * from usuario where usr_id = ?");
            pstmt.setInt(1, usuario.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // monta o objeto usuario a partir dos dados que retornam da query
                
                usuario.setId(rs.getInt("usr_id"));
                usuario.setLogin(rs.getString("usr_login"));
                usuario.setSenha(null);
                usuario.setPerfil(PerfilDeAcesso.valueOf(rs.getString("usr_perfil")));
            }
            return usuario;

        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(PecaDAO.class
                    .getName()).log(Level.SEVERE, null, sql);
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

    public boolean inserir(Usuario usuario) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("INSERT INTO usuario ("
                    + "usr_login,"
                    + " usr_senha,"
                    + " usr_perfil)"
                    + " VALUES (?, ?, ?)");
            pstmt.setString(1, usuario.getLogin());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setString(3, usuario.getPerfil().toString());

            pstmt.execute();
            return true;

        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(PecaDAO.class
                    .getName()).log(Level.SEVERE, null, sql);
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

    public boolean atualizar(Usuario usuario) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("UPDATE usuario SET "
                    + "usr_login = ?,"
                    + " usr_senha = ?,"
                    + " usr_perfil = ?"
                    + " WHERE usr_id = ?");
            pstmt.setString(1, usuario.getLogin());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setString(3, usuario.getPerfil().toString());

            pstmt.setInt(4, usuario.getId());
            pstmt.execute();
            return true;

        } catch (SQLException | ClassNotFoundException sql) {
            Logger.getLogger(PecaDAO.class
                    .getName()).log(Level.SEVERE, null, sql);
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

    public boolean deletar(Usuario usuario) {
        Connection conexao = null;
        try {
            conexao = ConexaoPostgreSQL.getConnection();
            PreparedStatement pstmt = conexao.prepareStatement("delete from usuario where usr_id = ?");
            pstmt.setInt(1, usuario.getId());
            pstmt.execute();
            return true;
        } catch (SQLException ex1) {
            throw new RuntimeException(ex1);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
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
