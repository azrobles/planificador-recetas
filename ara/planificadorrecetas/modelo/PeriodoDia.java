package ara.planificadorrecetas.modelo;

import java.util.HashSet;
import java.util.Set;

public class PeriodoDia {
    private NombrePeriodo periodo;
    private Set<Receta> recetas;
    
    public PeriodoDia() {
        this.recetas = new HashSet<>();
    }
    
    public void setPeriodo(final NombrePeriodo periodo) {
        this.periodo = periodo;
    }
    
    public NombrePeriodo getPeriodo() {
        return this.periodo;
    }
    
    public void addRecetas(final Receta receta) {
        this.recetas.add(receta);
    }
    
    public void setRecetas(final Set<Receta> recetas) {
        this.recetas = recetas;
    }
    
    public Set<Receta> getRecetas() {
        return this.recetas;
    }
    
    public enum NombrePeriodo {
        COMIDA, 
        CENA;
    }
}
