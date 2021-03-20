package ara.planificadorrecetas.modelo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Ingrediente {
    private static final Logger LOGGER = Logger.getLogger(Ingrediente.class.getName());

    private String nombre;
    private Disponibilidad disponibilidad;
    private String supermercado;
    
    public Ingrediente() {
        this.nombre = null;
        this.supermercado = null;
    }
    
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setDisponibilidad(final Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
    public Disponibilidad getDisponibilidad() {
        return this.disponibilidad;
    }
    
    public void setSupermercado(final String supermercado) {
        this.supermercado = supermercado;
    }
    
    public String getSupermercado() {
        return this.supermercado;
    }
    
    @Override
    public String toString() {
        String serialIngrediente = null;
        serialIngrediente = this.nombre + ";";
        serialIngrediente = serialIngrediente + this.disponibilidad.toString() + ";";
        serialIngrediente += this.supermercado;
        return serialIngrediente;
    }
    
    public void print() {
        LOGGER.log(Level.INFO, "{0}", this);
    }
    
    public enum Disponibilidad {
        FACIL, 
        DIFICIL;
    }
}
