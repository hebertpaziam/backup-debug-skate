
package dskt.model;

public class Usuario {
    private Integer id;
    private String login;
    private String senha;
    private PerfilDeAcesso perfil;

    public Usuario(Usuario user) {
    }

    //construtores
    public Usuario(Integer id) {
        this.id = id;
    }
    
    public Usuario(String login){
        this.login = login;
    }
    
    public Usuario(String login, String senha){
        this.login = login;
        this.senha = senha;
    }
    
     public Usuario() {
    }
    //fim de construtores
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilDeAcesso getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilDeAcesso perfil) {
        this.perfil = perfil;
    }


}