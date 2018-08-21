package dskt.model;

public class ItemDeSolicitacao {

    private Integer idSol;
    private Peca peca;
    private Integer qtdPecas;

    //METODOS CONSTRUTORES
    public ItemDeSolicitacao() {
    }

    public ItemDeSolicitacao(Peca peca) {
        this.peca = peca;
    }
    
    //METODOS GETTERS E SETTERS
    public Integer getIdSol() {
        return idSol;
    }

    public void setIdSol(Integer idSol) {
        this.idSol = idSol;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Integer getQtdPecas() {
        return qtdPecas;
    }

    public void setQtdPecas(Integer qtdPecas) {
        this.qtdPecas = qtdPecas;
    }

}
