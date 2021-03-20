package ara.planificadorrecetas.modelo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TipoReceta {
    private static final Logger LOGGER = Logger.getLogger(TipoReceta.class.getName());

    private String nombre;
    
    public TipoReceta() {
        this.nombre = null;
    }
    
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    @Override
    public String toString() {
        String serialTipoReceta = null;
        serialTipoReceta = this.nombre;
        return serialTipoReceta;
    }
    
    public void print() {
        LOGGER.log(Level.INFO, "{0}", this);
    }
}
