package ara.planificadorrecetas.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfiguradorFileDAO implements ConfiguradorDAO {
    private static final Logger LOGGER = Logger.getLogger(ConfiguradorFileDAO.class.getName());
    private static final String EXTENSION_CONFIGURADOR = "conf";

    String directorioConfiguradores;
    String extensionConfigurador;
    List<String> ficherosConfiguradores;
    private TipoRecetaDAO tiposRecetasBBDD;
    private IngredienteDAO ingredientesBBDD;
    
    public ConfiguradorFileDAO(final TipoRecetaDAO tiposRecetasBBDD, final IngredienteDAO ingredientesBBDD) {
        this.directorioConfiguradores = "config/";
        this.extensionConfigurador = "." + EXTENSION_CONFIGURADOR;
        this.tiposRecetasBBDD = null;
        this.ingredientesBBDD = null;
        final File directorio = new File(this.directorioConfiguradores);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
        this.tiposRecetasBBDD = tiposRecetasBBDD;
        this.ingredientesBBDD = ingredientesBBDD;
        this.ficherosConfiguradores = new ArrayList<>();
    }
    
    @Override
    public void createConfigurador(final Configurador configurador, final String fichero) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(this.directorioConfiguradores + fichero + this.extensionConfigurador, false)))) {
            final List<String> serialConfigurador = configurador.toStrings();
            final Iterator<String> i = serialConfigurador.iterator();
            while (i.hasNext()) {
                pw.println(i.next());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }
    
    @Override
    public Configurador readConfigurador(final String fichero) {
        final Configurador configurador = new Configurador();
        int i = 0;
        int j = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(this.directorioConfiguradores + fichero))) {
            String linea = null;
            if ((linea = br.readLine()) != null) {
                j = Integer.valueOf(linea);
            }
            for (i = 0; i < j; ++i) {
                if ((linea = br.readLine()) == null) {
                    return null;
                }
                configurador.addEstacion(Receta.Estacion.valueOf(linea));
            }
            if ((linea = br.readLine()) != null) {
                configurador.setDificultad(Receta.Dificultad.valueOf(linea));
            }
            if ((linea = br.readLine()) != null) {
                configurador.setDisponibilidad(Ingrediente.Disponibilidad.valueOf(linea));
            }
            if ((linea = br.readLine()) != null) {
                j = Integer.valueOf(linea);
            }
            for (i = 0; i < j; ++i) {
                if ((linea = br.readLine()) == null) {
                    return null;
                }
                final Criterio criterio = this.readCriterio(linea);
                configurador.addCriterios(criterio);
            }
        } catch (FileNotFoundException fnte) {
            LOGGER.log(Level.WARNING, "No existe el fichero del configurador.");
            return null;
        } catch (Exception e3) {
            LOGGER.log(Level.SEVERE, "", e3);
            return null;
        }
        return configurador;
    }
    
    private Criterio readCriterio(final String linea) {
        final String[] serialCriterio = linea.split(";");
        final Criterio criterio = new Criterio();
        int i = 0;
        int j = 0;
        int k = 0;
        criterio.setPeriodo(PeriodoDia.NombrePeriodo.valueOf(serialCriterio[0]));
        j = Integer.valueOf(serialCriterio[1]);
        k = 2;
        for (i = 0; i < j; ++i) {
            final TipoReceta tipo = this.tiposRecetasBBDD.readTipoReceta(serialCriterio[k]);
            criterio.addTipoReceta(tipo);
            ++k;
        }
        j = Integer.valueOf(serialCriterio[k]);
        ++k;
        for (i = 0; i < j; ++i) {
            final Ingrediente ingrediente = this.ingredientesBBDD.readIngrediente(serialCriterio[k]);
            criterio.addIngrediente(ingrediente);
        }
        return criterio;
    }
    
    @Override
    public void updateConfigurador(final Configurador configurador, final String fichero) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void deleteConfigurador(final String fichero) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public List<String> getAllConfiguradores() {
        return Collections.emptyList();
    }
    
    public String getDirectorioConfiguradores() {
        return this.directorioConfiguradores;
    }
    
    public static String getExtensionConfigurador() {
        return EXTENSION_CONFIGURADOR;
    }
}
