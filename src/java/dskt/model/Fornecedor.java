package dskt.model;

public class Fornecedor extends AbsPessoa {

    private String contato;

//  INICIO METODOS CONSTRUTORES
    public Fornecedor() {
    }

    public Fornecedor(Integer id) {
        this.setId(id);
    }
    
    
//  FIM METODOS CONSTRUTORES

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}
