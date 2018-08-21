package dskt.model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private Integer id;
    private List<Skate> skates;

    //MÉTODOs CONSTRUTORES
    public Carrinho() {
        this.skates = new ArrayList<Skate>();
    }

    public Skate getSkatePosition(Integer pos) {
        return this.getSkates().get(pos);
    }

    public void setSkatePosition(Skate skate) {
        this.skates.set(skate.getId(), skate);
    }

    public void addSkate(Skate skate) {
        this.skates.add(skate);
    }
    
    public void removeSkate(Skate skate) {
        this.skates.remove(skate);
    }

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

}
