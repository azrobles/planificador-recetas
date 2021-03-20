package ara.planificadorrecetas.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configurador {
    private static final Logger LOGGER = Logger.getLogger(Configurador.class.getName()); 

    private Set<Receta.Estacion> estaciones;
    private Receta.Dificultad dificultad;
    private Ingrediente.Disponibilidad disponibilidad;
    private List<Criterio> criterios;
    
    public Configurador() {
        this.estaciones = new HashSet<>();
        this.estaciones.add(Receta.Estacion.CUALQUIERA);
        this.dificultad = Receta.Dificultad.MEDIA;
        this.disponibilidad = Ingrediente.Disponibilidad.FACIL;
        this.criterios = new ArrayList<>();
    }
    
    public void addEstacion(final Receta.Estacion estacion) {
        this.estaciones.add(estacion);
    }
    
    public void setEstaciones(final Set<Receta.Estacion> estacion) {
        this.estaciones = estacion;
    }
    
    public Set<Receta.Estacion> getEstaciones() {
        return this.estaciones;
    }
    
    public void setDificultad(final Receta.Dificultad dificultad) {
        this.dificultad = dificultad;
    }
    
    public Receta.Dificultad getDificultad() {
        return this.dificultad;
    }
    
    public void setDisponibilidad(final Ingrediente.Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
    public Ingrediente.Disponibilidad getDisponibilidad() {
        return this.disponibilidad;
    }
    
    public void addCriterios(final Criterio criterios) {
        this.criterios.add(criterios);
    }
    
    public void setCriterios(final List<Criterio> criterios) {
        this.criterios = criterios;
    }
    
    public List<Criterio> getCriterios() {
        return this.criterios;
    }
    
    public List<String> toStrings() {
        final List<String> serialConfigurador = new ArrayList<>();
        serialConfigurador.add(String.valueOf(this.estaciones.size()));
        Iterator<Receta.Estacion> iEstacion = this.estaciones.iterator();
        while (iEstacion.hasNext()) {
            serialConfigurador.add(iEstacion.next().toString());
        }
        serialConfigurador.add(this.dificultad.toString());
        serialConfigurador.add(this.disponibilidad.toString());
        serialConfigurador.add(String.valueOf(this.criterios.size()));
        Iterator<Criterio> iCriterio = this.criterios.iterator();
        while (iCriterio.hasNext()) {
            serialConfigurador.add(iCriterio.next().toString());
        }
        return serialConfigurador;
    }
    
    public void print() {
        final List<String> serialConfigurador = this.toStrings();
        final Iterator<String> i = serialConfigurador.iterator();
        while (i.hasNext()) {
            LOGGER.log(Level.INFO, "{0}", i.next());
        }
    }
}
