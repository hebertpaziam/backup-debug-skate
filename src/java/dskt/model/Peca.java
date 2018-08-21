package dskt.model;

public class Peca {

    private Integer id;
    private String modelo;
    private String descricao;
    private String cor;
    private String tamanho;
    private Fornecedor fornecedor;
    private Integer qtd_estoque;
    private Double valor_compra;
    private Double valor_venda;
    private String ref_imagem;
    private String tipo;

    //METODOS CONSTRUTORES
    public Peca(Integer id) {
        this.id = id;
        this.fornecedor = new Fornecedor(); //INSTANCIA UM OBJETO FORNECEDOR EM CONJUNTO COM O OBJETO PECA
    }
    
    public Peca() {
        this.fornecedor = new Fornecedor(); //INSTANCIA UM OBJETO FORNECEDOR EM CONJUNTO COM O OBJETO PECA
    }
    
    //METODOS GETTERS E SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Integer getQtd_estoque() {
        return qtd_estoque;
    }

    public void setQtd_estoque(Integer qtd_estoque) {
        this.qtd_estoque = qtd_estoque;
    }

    public Double getValor_compra() {
        return valor_compra;
    }

    public void setValor_compra(Double valor_compra) {
        this.valor_compra = valor_compra;
    }

    public Double getValor_venda() {
        return valor_venda;
    }

    public void setValor_venda(Double valor_venda) {
        this.valor_venda = valor_venda;
    }

    public String getRef_imagem() {
        return ref_imagem;
    }

    public void setRef_imagem(String ref_imagem) {
        this.ref_imagem = ref_imagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
