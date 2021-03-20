package ara.planificadorrecetas.modelo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemListaCompra {
    private static final Logger LOGGER = Logger.getLogger(ItemListaCompra.class.getName());

    private Ingrediente ingrediente;
    private Cantidad cantidad;
    private Boolean opcional;
    
    public ItemListaCompra() {
        this.ingrediente = new Ingrediente();
        this.cantidad = new Cantidad();
        this.opcional = false;
    }
    
    public void setIngrediente(final Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
    
    public Ingrediente getIngrediente() {
        return this.ingrediente;
    }
    
    public void setCantidad(final Cantidad cantidad) {
        this.cantidad = cantidad;
    }
    
    public Cantidad getCantidad() {
        return this.cantidad;
    }
    
    public void setOpcional(final Boolean opcional) {
        this.opcional = opcional;
    }
    
    public Boolean getOpcional() {
        return this.opcional;
    }
    
    @Override
    public String toString() {
        String serialItem = null;
        serialItem = this.ingrediente.getNombre() + ";";
        serialItem = serialItem + this.cantidad.toString() + ";";
        serialItem += this.opcional.toString();
        return serialItem;
    }
    
    public void print() {
        LOGGER.log(Level.INFO, "{0}", this);
    }
}
