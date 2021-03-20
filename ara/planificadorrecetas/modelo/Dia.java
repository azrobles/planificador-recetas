package ara.planificadorrecetas.modelo;

import java.util.HashSet;
import java.util.Set;

public class Dia {
    private int numero;
    private Set<PeriodoDia> periodos;
    
    public Dia() {
        this.numero = 0;
        this.periodos = new HashSet<>();
    }
    
    public void setNumero(final int numero) {
        this.numero = numero;
    }
    
    public int getNumero() {
        return this.numero;
    }
    
    public void addPeriodo(final PeriodoDia periodo) {
        this.periodos.add(periodo);
    }
    
    public void setPeriodos(final Set<PeriodoDia> periodos) {
        this.periodos = periodos;
    }
    
    public Set<PeriodoDia> getPeriodos() {
        return this.periodos;
    }
}
