package ara.planificadorrecetas.modelo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Criterio {
    private static final Logger LOGGER = Logger.getLogger(Criterio.class.getName());

    private PeriodoDia.NombrePeriodo periodo;
    private Set<TipoReceta> tipoReceta;
    private Set<Ingrediente> ingredientes;
    
    public Criterio() {
        this.tipoReceta = new HashSet<>();
        this.ingredientes = new HashSet<>();
    }
    
    public void setPeriodo(final PeriodoDia.NombrePeriodo periodo) {
        this.periodo = periodo;
    }
    
    public PeriodoDia.NombrePeriodo getPeriodo() {
        return this.periodo;
    }
    
    public void addTipoReceta(final TipoReceta tipoReceta) {
        this.tipoReceta.add(tipoReceta);
    }
    
    public void setTipoReceta(final Set<TipoReceta> tipoReceta) {
        this.tipoReceta = tipoReceta;
    }
    
    public Set<TipoReceta> getTipoReceta() {
        return this.tipoReceta;
    }
    
    public void addIngrediente(final Ingrediente ingrediente) {
        this.ingredientes.add(ingrediente);
    }
    
    public void setIngredientes(final Set<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    public Set<Ingrediente> getIngredientes() {
        return this.ingredientes;
    }
    
    @Override
    public String toString() {
        StringBuilder serialCriterio = new StringBuilder();
        serialCriterio.append(this.periodo.toString() + ";");
        serialCriterio.append(this.tipoReceta.size());
        Iterator<TipoReceta> iTipoReceta = this.tipoReceta.iterator();
        while (iTipoReceta.hasNext()) {
            serialCriterio.append(";" + iTipoReceta.next().getNombre());
        }
        serialCriterio.append(";" + this.ingredientes.size());
        Iterator<Ingrediente> iIngrediente = this.ingredientes.iterator();
        while (iIngrediente.hasNext()) {
            serialCriterio.append(";" + iIngrediente.next().getNombre());
        }
        return serialCriterio.toString();
    }
    
    public void print() {
        LOGGER.log(Level.INFO, "{0}", this);
    }
}
