package ara.planificadorrecetas.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RecetaFileDAO implements RecetaDAO {
    private static final Logger LOGGER = Logger.getLogger(RecetaFileDAO.class.getName());

    private String ficheroReceta;
    private String directorioBBDD;
    private String ficheroRecetaBBDD;
    private String directorioBackup;
    private String extensionBU1;
    private String extensionBU2;
    private String ficheroAsociacionTipoReceta;
    private String ficheroAsociacionTipoRecetaBBDD;
    private String ficheroAsociacionIngrediente;
    private String ficheroAsociacionIngredienteBBDD;
    private TipoRecetaDAO tiposRecetasBBDD;
    private HashMap<String, TipoReceta> asociacionRecetaTipoReceta;
    private IngredienteDAO ingredientesBBDD;
    private HashMap<String, Set<ItemListaCompra>> asociacionRecetaIngrediente;
    private HashMap<String, Receta> recetasBBDD;
    
    public RecetaFileDAO(final TipoRecetaDAO tiposRecetasBBDD, final IngredienteDAO ingredientesBBDD) {
        this.ficheroReceta = null;
        this.directorioBBDD = "bbdd/";
        this.ficheroRecetaBBDD = "recetas.txt";
        this.directorioBackup = "backupBBDD/";
        this.extensionBU1 = ".backup";
        this.extensionBU2 = this.extensionBU1 + ".backup";
        this.ficheroAsociacionTipoReceta = null;
        this.ficheroAsociacionTipoRecetaBBDD = "receta_tipoReceta.txt";
        this.ficheroAsociacionIngrediente = null;
        this.ficheroAsociacionIngredienteBBDD = "receta_ingrediente.txt";
        this.tiposRecetasBBDD = null;
        this.ingredientesBBDD = null;
        this.tiposRecetasBBDD = tiposRecetasBBDD;
        this.asociacionRecetaTipoReceta = new HashMap<>();
        this.ficheroAsociacionTipoReceta = this.directorioBBDD + this.ficheroAsociacionTipoRecetaBBDD;
        this.ingredientesBBDD = ingredientesBBDD;
        this.asociacionRecetaIngrediente = new HashMap<>();
        this.ficheroAsociacionIngrediente = this.directorioBBDD + this.ficheroAsociacionIngredienteBBDD;
        this.ficheroReceta = this.directorioBBDD + this.ficheroRecetaBBDD;
        this.recetasBBDD = new HashMap<>();
        this.doBackup(this.ficheroAsociacionTipoReceta, this.ficheroAsociacionTipoRecetaBBDD);
        this.doBackup(this.ficheroAsociacionIngrediente, this.ficheroAsociacionIngredienteBBDD);
        this.doBackup(this.ficheroReceta, this.ficheroRecetaBBDD);
        Integer returnCode = this.readAsociacionesTipoReceta();
        if (returnCode < -1) {
            LOGGER.log(Level.SEVERE, "Error en la lectura de las asociaciones entre recetas y tipos de recetas.");
        } else {
            returnCode = this.readAsociacionesIngrediente();
            if (returnCode < -1) {
                LOGGER.log(Level.SEVERE, "Error en la lectura de las asociaciones entre recetas e ingredientes.");
            }
            if (returnCode == 0) {
                returnCode = this.readAllRecetas();
                if (returnCode < -1) {
                    LOGGER.log(Level.SEVERE, "Error en la lectura de la BBDD de recetas.");
                }
            }
        }
    }
    
    private void doBackup(final String file, final String backup) {
        File directorio = new File(this.directorioBBDD);
        if (!directorio.exists()) {
            directorio.mkdir();
            directorio = new File(this.directorioBackup);
            directorio.mkdir();
        }
        final String backup2 = this.directorioBackup + backup + this.extensionBU1;
        final File ficheroBackup1 = new File(backup2);
        final String backup3 = this.directorioBackup + backup + this.extensionBU2;
        final File ficheroBackup2 = new File(backup3);
        if (ficheroBackup1.exists()) {
            if (ficheroBackup2.exists()) {
                if (ficheroBackup1.lastModified() < ficheroBackup2.lastModified()) {
                    FileCopy.copy(file, backup2);
                } else {
                    FileCopy.copy(file, backup3);
                }
            } else {
                FileCopy.copy(file, backup3);
            }
        } else {
            FileCopy.copy(file, backup2);
        }
    }
    
    private Integer readAsociacionesTipoReceta() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.ficheroAsociacionTipoReceta))) {
            String linea = null;
            while ((linea = br.readLine()) != null) {
                String[] serialAsociacionTipoReceta = null;
                serialAsociacionTipoReceta = linea.split(";");
                final TipoReceta tipo = this.tiposRecetasBBDD.readTipoReceta(serialAsociacionTipoReceta[1]);
                this.asociacionRecetaTipoReceta.put(serialAsociacionTipoReceta[0], tipo);
            }
        } catch (FileNotFoundException fnte) {
            LOGGER.log(Level.SEVERE, "No existe el fichero con las asociaciones entre recetas y tipos de recetas.");
            return -1;
        } catch (Exception e3) {
            LOGGER.log(Level.SEVERE, "", e3);
            return -2;
        }
        return 0;
    }
    
    private Integer readAsociacionesIngrediente() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.ficheroAsociacionIngrediente))) {
            String linea = null;
            while ((linea = br.readLine()) != null) {
                String[] serialAsociacionIngrediente = null;
                final Cantidad cantidad = new Cantidad();
                final UnidadCantidad unidad = new UnidadCantidad();
                final ItemListaCompra item = new ItemListaCompra();
                serialAsociacionIngrediente = linea.split(";");
                final Ingrediente ingrediente = this.ingredientesBBDD.readIngrediente(serialAsociacionIngrediente[1]);
                item.setIngrediente(ingrediente);
                cantidad.setNumero(Integer.valueOf(serialAsociacionIngrediente[2]));
                unidad.setUnidad(serialAsociacionIngrediente[3]);
                cantidad.setUnidad(unidad);
                item.setCantidad(cantidad);
                item.setOpcional(Boolean.valueOf(serialAsociacionIngrediente[4]));
                if (this.asociacionRecetaIngrediente.containsKey(serialAsociacionIngrediente[0])) {
                    this.asociacionRecetaIngrediente.get(serialAsociacionIngrediente[0]).add(item);
                } else {
                    final HashSet<ItemListaCompra> setIngrediente = new HashSet<>();
                    setIngrediente.add(item);
                    this.asociacionRecetaIngrediente.put(serialAsociacionIngrediente[0], setIngrediente);
                }
            }
        } catch (FileNotFoundException fnte) {
            LOGGER.log(Level.SEVERE, "No existe el fichero con las asociaciones entre recetas e ingredientes.");
            return -1;
        } catch (Exception e3) {
            LOGGER.log(Level.SEVERE, "", e3);
            return -2;
        }
        return 0;
    }
    
    private Integer readAllRecetas() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.ficheroReceta))) {
            String linea = null;
            while ((linea = br.readLine()) != null) {
                String[] serialReceta = null;
                serialReceta = linea.split(";");
                final Receta receta = new Receta();
                receta.setNombre(serialReceta[0]);
                receta.setLocalizacion(serialReceta[1]);
                receta.setDificultad(Receta.Dificultad.valueOf(serialReceta[2]));
                final Tiempo tiempo = new Tiempo();
                tiempo.setCantidad(Integer.valueOf(serialReceta[3]), Tiempo.UnidadDeMedidaTiempo.valueOf(serialReceta[4]));
                receta.setTiempoPreparacion(tiempo);
                receta.setEstacion(Receta.Estacion.valueOf(serialReceta[5]));
                receta.setFrecuencia(Integer.valueOf(serialReceta[6]));
                receta.setTipo(this.asociacionRecetaTipoReceta.get(receta.getNombre()));
                receta.setIngredientes(this.asociacionRecetaIngrediente.get(receta.getNombre()));
                this.recetasBBDD.put(receta.getNombre(), receta);
            }
        } catch (FileNotFoundException fnte) {
            LOGGER.log(Level.SEVERE, "No existe el fichero de la BBDD de tipos de recetas.");
            return -1;
        } catch (Exception e3) {
            LOGGER.log(Level.SEVERE, "", e3);
            return -2;
        }
        return 0;
    }
    
    @Override
    public void createReceta(final Receta receta) {
        if (this.recetasBBDD.containsKey(receta.getNombre())) {
            return;
        }
        this.writeReceta(receta, true);
        this.recetasBBDD.put(receta.getNombre(), receta);
    }
    
    private void writeReceta(final Receta receta, final Boolean append) {
        try (PrintWriter pwReceta = new PrintWriter(new BufferedWriter(new FileWriter(this.ficheroReceta, append)))) {
            pwReceta.println(receta.toString());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
        this.createAsociacionesTipoReceta(receta, append);
        this.createAsociacionesIngrediente(receta, append);
    }
    
    private void createAsociacionesTipoReceta(final Receta receta, final Boolean append) {
        String asociacion = null;
        asociacion = receta.getNombre() + ";";
        asociacion += receta.getTipo().getNombre();
        try (PrintWriter pwTipoReceta = new PrintWriter(new BufferedWriter(new FileWriter(this.ficheroAsociacionTipoReceta, append)))) {
            pwTipoReceta.println(asociacion);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }
    
    private void createAsociacionesIngrediente(final Receta receta, final Boolean append) {
        final Iterator<ItemListaCompra> i = receta.getIngredientes().iterator();
        while (i.hasNext()) {
            String asociacion = null;
            final ItemListaCompra item = i.next();
            asociacion = receta.getNombre() + ";";
            asociacion += item.toString();
            try (PrintWriter pwIngrediente = new PrintWriter(new BufferedWriter(new FileWriter(this.ficheroAsociacionIngrediente, append)))) {
                pwIngrediente.println(asociacion);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "", e);
            }
        }
    }
    
    @Override
    public Receta readReceta(final String nombre) {
        if (this.recetasBBDD.containsKey(nombre)) {
            return this.recetasBBDD.get(nombre);
        }
        return null;
    }
    
    @Override
    public void updateReceta(final Receta receta) {
        if (this.recetasBBDD.containsKey(receta.getNombre())) {
            this.recetasBBDD.put(receta.getNombre(), receta);
            this.updateRecetasBBDD();
        }
    }
    
    private void updateRecetasBBDD() {
        final List<Receta> listaRecetas = this.recetasBBDD.values().stream().collect(Collectors.toList());
        final Iterator<Receta> i = listaRecetas.iterator();
        if (i.hasNext()) {
            this.writeReceta(i.next(), false);
        }
        while (i.hasNext()) {
            this.writeReceta(i.next(), true);
        }
    }
    
    @Override
    public void deleteReceta(final String nombre) {
        if (this.recetasBBDD.containsKey(nombre)) {
            this.recetasBBDD.remove(nombre);
            this.updateRecetasBBDD();
        }
    }
    
    @Override
    public List<Receta> getAllRecetas() {
        final List<Receta> recetasOrdenadas = this.recetasBBDD.values().stream().collect(Collectors.toList());
        recetasOrdenadas.sort((final Receta rece1, final Receta rece2) -> rece1.getNombre().compareToIgnoreCase(rece2.getNombre()));
        return recetasOrdenadas;
    }
    
    @Override
    public List<Receta> getRecetas(final Receta receta) {
        return Collections.emptyList();
    }
}
