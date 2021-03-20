package ara.planificadorrecetas.modelo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Cantidad {
    private static final Logger LOGGER = Logger.getLogger(Cantidad.class.getName());

    private int numero;
    private UnidadCantidad unidad;
    
    public void setNumero(final int numero) {
        this.numero = numero;
    }
    
    public int getNumero() {
        return this.numero;
    }
    
    public void setUnidad(final UnidadCantidad unidad) {
        this.unidad = unidad;
    }
    
    public UnidadCantidad getUnidad() {
        return this.unidad;
    }
    
    @Override
    public String toString() {
        String serialCantidad = null;
        serialCantidad = this.numero + ";";
        serialCantidad += this.unidad.toString();
        return serialCantidad;
    }
    
    public void print() {
        LOGGER.log(Level.INFO, "{0}", this);
    }
}
