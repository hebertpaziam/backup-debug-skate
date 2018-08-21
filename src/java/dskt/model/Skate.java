package dskt.model;

public class Skate {

    private Integer id;
    private Peca truck;
    private Peca shape;
    private Peca roda;
    private Peca lixa;
    private Integer idVenda;
    private Boolean flgMontagem;
    private String dtMontagem;
    private Integer idFuncionario;

    //MÉTODOS CONSTRUTORES
    public Skate() {
        this.shape = new Peca();
        this.truck = new Peca();
        this.roda = new Peca();
        this.lixa = new Peca();
    }

    public Skate(Integer id) {
        this.id = id;
        this.shape = new Peca();
        this.truck = new Peca();
        this.roda = new Peca();
        this.lixa = new Peca();
    }

    //MÉTODOS GETTERS E SETTERS
    public Skate getVenda(Integer idVenda) {
        this.idVenda = idVenda;
        
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Peca getTruck() {
        return truck;
    }

    public void setTruck(Peca truck) {
        this.truck = truck;
    }

    public Peca getShape() {
        return shape;
    }

    public void setShape(Peca shape) {
        this.shape = shape;
    }

    public Peca getRoda() {
        return roda;
    }

    public void setRoda(Peca roda) {
        this.roda = roda;
    }

    public Peca getLixa() {
        return lixa;
    }

    public void setLixa(Peca lixa) {
        this.lixa = lixa;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Boolean getFlgMontagem() {
        return flgMontagem;
    }

    public void setFlgMontagem(Boolean flgMontagem) {
        this.flgMontagem = flgMontagem;
    }

    public String getDtMontagem() {
        return dtMontagem;
    }

    public void setDtMontagem(String dtMontagem) {
        this.dtMontagem = dtMontagem;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

}
