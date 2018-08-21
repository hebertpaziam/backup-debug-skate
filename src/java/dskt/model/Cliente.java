
package dskt.model;

public class Cliente extends AbsPessoa {
    private String data_nasc;
    private Usuario usuario;

    //Construtores

    public Cliente() {
        this.usuario = new Usuario();
    }
    
    public Cliente(Integer id) {
        this.id = id;
        this.usuario = new Usuario();
    }
    
    public Cliente(Usuario user) {
        this.usuario = user;
    }
    public Cliente(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
        this.usuario = new Usuario();
    }
    // fim dos construtores
    
    public String getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}