package ara.planificadorrecetas.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Planificador {
    private static final Logger LOGGER = Logger.getLogger(Planificador.class.getName());

    private Configurador configurador;
    private List<Dia> dias;
    private RecetaDAO recetaDAO;
    private List<Receta> recetasDisponibles;
    
    public Planificador() {
        this.recetaDAO = null;
        this.dias = new ArrayList<>();
    }
    
    public void planificarCalendario(final Configurador config, final RecetaDAO recetasBBDD) {
        final Dia dia = new Dia();
        this.configurador = config;
        this.recetaDAO = recetasBBDD;
        this.recetasDisponibles = recetasBBDD.getAllRecetas();
        for (final Criterio criterio : this.configurador.getCriterios()) {
            final PeriodoDia periodo = new PeriodoDia();
            periodo.setPeriodo(criterio.getPeriodo());
            final Receta receta = this.getRecetaCriterio(criterio);
            if (receta != null) {
                periodo.addRecetas(receta);
            }
            dia.addPeriodo(periodo);
        }
        this.dias.add(dia);
    }
    
    private Receta getRecetaCriterio(final Criterio criterio) {
        final HashMap<String, Peso> recetasDisponiblesPonderadas = new HashMap<>();
        final List<Receta> recetasCandidatas = new ArrayList<>();
        for (final Receta receta : this.recetasDisponibles) {
            final Peso peso = this.asignarPeso(receta, criterio);
            recetasDisponiblesPonderadas.put(receta.getNombre(), peso);
        }
        final Peso pesoMaximo = new Peso(1);
        for (final Map.Entry<String, Peso> m : recetasDisponiblesPonderadas.entrySet()) {
            final Peso value = m.getValue();
            if (value.getValor() > pesoMaximo.getValor()) {
                pesoMaximo.setValor(value.getValor());
            }
        }
        for (final Map.Entry<String, Peso> m : recetasDisponiblesPonderadas.entrySet()) {
            final String key = m.getKey();
            final Peso value2 = m.getValue();
            if (value2.getValor() == pesoMaximo.getValor()) {
                recetasCandidatas.add(this.recetaDAO.readReceta(key));
            }
        }
        if (recetasCandidatas.isEmpty()) {
            return null;
        }
        final Receta recetaElegida = this.getRecetaMenorFrecuencia(recetasCandidatas);
        this.recetasDisponibles.remove(recetaElegida);
        this.recetaDAO.updateReceta(recetaElegida);
        return recetaElegida;
    }
    
    private Peso asignarPeso(final Receta receta, final Criterio criterio) {
        final Peso peso = new Peso(1);
        if (this.configurador.getEstaciones().contains(receta.getEstacion())) {
            peso.aumentarValor();
        }
        if (receta.getDificultad().compareTo(this.configurador.getDificultad()) > 0) {
            peso.disminuirValor();
        }
        if (criterio.getTipoReceta().contains(receta.getTipo())) {
            peso.aumentarValor();
        }
        for (final ItemListaCompra item : receta.getIngredientes()) {
            final Ingrediente ingrediente = item.getIngrediente();
            if (criterio.getIngredientes().contains(ingrediente)) {
                peso.aumentarValor();
            }
            else {
                if (Boolean.TRUE.equals(item.getOpcional()) || ingrediente.getDisponibilidad().compareTo(this.configurador.getDisponibilidad()) <= 0) {
                    continue;
                }
                peso.disminuirValor();
            }
        }
        return peso;
    }
    
    private Receta getRecetaMenorFrecuencia(final List<Receta> recetasCandidatas) {
        Integer frecuenciaMinima = 0;
        Receta recetaMinimaFrecuencia = null;
        final Iterator<Receta> i = recetasCandidatas.iterator();
        if (i.hasNext()) {
            final Receta receta = i.next();
            frecuenciaMinima = receta.getFrecuencia();
            recetaMinimaFrecuencia = receta;
        }
        while (i.hasNext()) {
            final Receta receta = i.next();
            if (receta.getFrecuencia() <= frecuenciaMinima) {
                frecuenciaMinima = receta.getFrecuencia();
                recetaMinimaFrecuencia = receta;
            }
        }
        if(recetaMinimaFrecuencia != null) {
            recetaMinimaFrecuencia.aumentarFrecuencia();
        }
        return recetaMinimaFrecuencia;
    }
    
    public void print() {
        for (final Dia dia : this.dias) {
            for (final PeriodoDia periodo : dia.getPeriodos()) {
                for (final Receta receta : periodo.getRecetas()) {
                    LOGGER.log(Level.INFO, "{0}", periodo.getPeriodo() + " " + receta.getNombre());
                }
            }
        }
    }
    
    public List<Dia> getDias() {
        return this.dias;
    }
}
