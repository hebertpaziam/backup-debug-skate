package dskt.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoPecas {

    private Integer idSol;
    private List<ItemDeSolicitacao> itensDeSolicitacao;
    private Date dataSolicitacao;

    //MÉTODOS CONSTRUTORES
    public SolicitacaoPecas() {
        this.itensDeSolicitacao = new ArrayList<ItemDeSolicitacao>();
    }
    
    public SolicitacaoPecas(Integer solId) {
        this.idSol=solId;
        this.itensDeSolicitacao = new ArrayList<ItemDeSolicitacao>();
    }

    //METODOS GETTERS E SETTERS
    public Integer getIdSol() {
        return idSol;
    }

    public void setIdSol(Integer idSol) {
        this.idSol = idSol;
    }

    public List<ItemDeSolicitacao> getItensDeSolicitacao() {
        return itensDeSolicitacao;
    }

    public void setItensDeSolicitacao(List<ItemDeSolicitacao> solicitacao) {
        this.itensDeSolicitacao = solicitacao;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

}
