package dskt.model;

public class Funcionario extends AbsPessoa {

    private String data_nasc;
    private Integer matricula;
    private Usuario usuario;

    //Construtores
    public Funcionario() {
        this.usuario = new Usuario();
    }
    public Funcionario(Integer id) {
        this.id = id;
        this.usuario = new Usuario();
    }
    public Funcionario(Usuario user) {
        this.usuario = user;
    }
    // fim dos construtores

    public String getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
