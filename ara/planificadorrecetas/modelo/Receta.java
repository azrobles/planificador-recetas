package ara.planificadorrecetas.modelo;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Receta {
    private static final Logger LOGGER = Logger.getLogger(Receta.class.getName());

    private String nombre;
    private String localizacion;
    private Dificultad dificultad;
    private Tiempo tiempoPreparacion;
    private Estacion estacion;
    private TipoReceta tipo;
    private Set<ItemListaCompra> ingredientes;
    private Integer frecuencia;
    
    public Receta() {
        this.nombre = null;
        this.localizacion = null;
        this.dificultad = Dificultad.MEDIA;
        this.estacion = Estacion.CUALQUIERA;
        this.tipo = null;
        this.ingredientes = new HashSet<>();
        this.frecuencia = 0;
    }
    
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setLocalizacion(final String localizacion) {
        this.localizacion = localizacion;
    }
    
    public String getLocalizacion() {
        return this.localizacion;
    }
    
    public void setDificultad(final Dificultad dificultad) {
        this.dificultad = dificultad;
    }
    
    public Dificultad getDificultad() {
        return this.dificultad;
    }
    
    public void setTiempoPreparacion(final Tiempo tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }
    
    public Tiempo getTiempoPreparacion() {
        return this.tiempoPreparacion;
    }
    
    public void setEstacion(final Estacion estacion) {
        this.estacion = estacion;
    }
    
    public Estacion getEstacion() {
        return this.estacion;
    }
    
    public void setTipo(final TipoReceta tipo) {
        this.tipo = tipo;
    }
    
    public TipoReceta getTipo() {
        return this.tipo;
    }
    
    public void addIngrediente(final Ingrediente ingrediente, final Cantidad cantidad) {
        final ItemListaCompra item = new ItemListaCompra();
        item.setIngrediente(ingrediente);
        item.setCantidad(cantidad);
        this.ingredientes.add(item);
    }
    
    public void setIngredientes(final Set<ItemListaCompra> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    public Set<ItemListaCompra> getIngredientes() {
        return this.ingredientes;
    }
    
    public void aumentarFrecuencia() {
        ++this.frecuencia;
    }
    
    public void disminuirFrecuencia() {
        --this.frecuencia;
    }
    
    public void setFrecuencia(final Integer frecuencia) {
        this.frecuencia = frecuencia;
    }
    
    public Integer getFrecuencia() {
        return this.frecuencia;
    }
    
    @Override
    public String toString() {
        String serialReceta = null;
        serialReceta = this.nombre + ";";
        serialReceta = serialReceta + this.localizacion + ";";
        serialReceta = serialReceta + this.dificultad.toString() + ";";
        serialReceta = serialReceta + this.tiempoPreparacion.getCantidad().toString() + ";";
        serialReceta = serialReceta + this.tiempoPreparacion.getUnidadDeMedida().toString() + ";";
        serialReceta = serialReceta + this.estacion.toString() + ";";
        serialReceta += this.frecuencia.toString();
        return serialReceta;
    }
    
    public void print() {
        LOGGER.log(Level.INFO, "{0}", this);
        LOGGER.log(Level.INFO, "Tipo asociado:");
        this.tipo.print();
        LOGGER.log(Level.INFO, "Ingredientes asociados:");
        for (final ItemListaCompra item : this.ingredientes) {
            item.print();
        }
    }
    
    public enum Dificultad {
        BAJA, 
        MEDIA, 
        ALTA;
    }
    
    public enum Estacion {
        CUALQUIERA, 
        VERANO, 
        INVIERNO;
    }
}
