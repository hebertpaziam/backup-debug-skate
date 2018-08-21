package dskt.model;

import java.util.ArrayList;
import java.util.List;

public class Venda {

    private Integer id;
    private List<Skate> skates;
    private Cliente cliente;
    private Double valorVenda;
    private Double valorFrete;
    private Double valorTotal;
    private String dtPedido;
    private Boolean flgPagamento;
    private String dtPagamento;
    private String hrPagamento;
    private Boolean flgCancelamento;
    private String dtCancelamento;
    private String hrCancelamento;
    private String cupomDesconto;

    //CONTRUTORES

    public Venda() {
        this.skates = new ArrayList<>();
        this.cliente = new Cliente();
        this.valorFrete = 0.0;
        this.valorVenda = 0.0;
        this.valorTotal = 0.0;
    }

    //GETTERS E SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Skate> getSkates() {
        return skates;
    }

    public void setSkates(List<Skate> skates) {
        this.skates = skates;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDtPedido() {
        return dtPedido;
    }

    public void setDtPedido(String dtPedido) {
        this.dtPedido = dtPedido;
    }

    public Boolean getFlgPagamento() {
        return flgPagamento;
    }

    public void setFlgPagamento(Boolean flgPagamento) {
        this.flgPagamento = flgPagamento;
    }

    public String getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(String dtPagamento) {
        this.dtPagamento = dtPagamento;
    }

    public String getHrPagamento() {
        return hrPagamento;
    }

    public void setHrPagamento(String hrPagamento) {
        this.hrPagamento = hrPagamento;
    }

    public Boolean getFlgCancelamento() {
        return flgCancelamento;
    }

    public void setFlgCancelamento(Boolean flgCancelamento) {
        this.flgCancelamento = flgCancelamento;
    }

    public String getDtCancelamento() {
        return dtCancelamento;
    }

    public void setDtCancelamento(String dtCancelamento) {
        this.dtCancelamento = dtCancelamento;
    }

    public String getHrCancelamento() {
        return hrCancelamento;
    }

    public void setHrCancelamento(String hrCancelamento) {
        this.hrCancelamento = hrCancelamento;
    }

    public String getCupomDesconto() {
        return cupomDesconto;
    }

    public void setCupomDesconto(String cupomDesconto) {
        this.cupomDesconto = cupomDesconto;
    }


}
