package ara.planificadorrecetas.modelo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UnidadCantidad {
    private static final Logger LOGGER = Logger.getLogger(UnidadCantidad.class.getName());

    private String unidad;
    
    public UnidadCantidad() {
        this.unidad = null;
    }
    
    public void setUnidad(final String unidad) {
        this.unidad = unidad;
    }
    
    public String getUnidad() {
        return this.unidad;
    }
    
    @Override
    public String toString() {
        String serialUnidad = null;
        serialUnidad = this.unidad;
        return serialUnidad;
    }
    
    public void print() {
        LOGGER.log(Level.INFO, "{0}", this);
    }
}
