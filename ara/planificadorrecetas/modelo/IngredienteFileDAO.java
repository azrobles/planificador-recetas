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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class IngredienteFileDAO implements IngredienteDAO {
    private static final Logger LOGGER = Logger.getLogger(IngredienteFileDAO.class.getName());

    private String fichero;
    private String directorioBBDD;
    private String ficheroBBDD;
    private String directorioBackup;
    private String extensionBU1;
    private String extensionBU2;
    private HashMap<String, Ingrediente> ingredientesBBDD;
    
    public IngredienteFileDAO() {
        this.fichero = null;
        this.directorioBBDD = "bbdd/";
        this.ficheroBBDD = "ingredientes.txt";
        this.directorioBackup = "backupBBDD/";
        this.extensionBU1 = ".backup";
        this.extensionBU2 = this.extensionBU1 + ".backup";
        this.ingredientesBBDD = new HashMap<>();
        this.fichero = this.directorioBBDD + this.ficheroBBDD;
        this.doBackup(this.fichero, this.ficheroBBDD);
        final Integer returnCode = this.readAllIngredientes();
        if (returnCode < -1) {
            LOGGER.log(Level.SEVERE, "Error en la lectura de la BBDD de ingredientes.");
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
    
    private Integer readAllIngredientes() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.fichero))) {
            String linea = null;
            while ((linea = br.readLine()) != null) {
                String[] serialIngrediente = null;
                serialIngrediente = linea.split(";");
                final Ingrediente ingrediente = new Ingrediente();
                ingrediente.setNombre(serialIngrediente[0]);
                ingrediente.setDisponibilidad(Ingrediente.Disponibilidad.valueOf(serialIngrediente[1]));
                ingrediente.setSupermercado(serialIngrediente[2]);
                this.ingredientesBBDD.put(ingrediente.getNombre(), ingrediente);
            }
        } catch (FileNotFoundException fnte) {
            LOGGER.log(Level.SEVERE, "No existe el fichero de la BBDD de ingredientes.");
            return -1;
        } catch (Exception e3) {
            LOGGER.log(Level.SEVERE, "", e3);
            return -2;
        }
        return 0;
    }
    
    @Override
    public void createIngrediente(final Ingrediente ingrediente) {
        if (this.ingredientesBBDD.containsKey(ingrediente.getNombre())) {
            return;
        }
        this.writeIngrediente(ingrediente, true);
        this.ingredientesBBDD.put(ingrediente.getNombre(), ingrediente);
    }
    
    private void writeIngrediente(final Ingrediente ingrediente, final Boolean append) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(this.fichero, append)))) {
            pw.println(ingrediente.toString());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }
    
    @Override
    public Ingrediente readIngrediente(final String nombre) {
        if (this.ingredientesBBDD.containsKey(nombre)) {
            return this.ingredientesBBDD.get(nombre);
        }
        return null;
    }
    
    @Override
    public void updateIngrediente(final Ingrediente ingrediente) {
        if (this.ingredientesBBDD.containsKey(ingrediente.getNombre())) {
            this.ingredientesBBDD.put(ingrediente.getNombre(), ingrediente);
            this.updateIngredientesBBDD();
        }
    }
    
    private void updateIngredientesBBDD() {
        final List<Ingrediente> listaIngredientes = this.ingredientesBBDD.values().stream().collect(Collectors.toList());
        final Iterator<Ingrediente> i = listaIngredientes.iterator();
        if (i.hasNext()) {
            this.writeIngrediente(i.next(), false);
        }
        while (i.hasNext()) {
            this.writeIngrediente(i.next(), true);
        }
    }
    
    @Override
    public void deleteIngrediente(final String nombre) {
        if (this.ingredientesBBDD.containsKey(nombre)) {
            this.ingredientesBBDD.remove(nombre);
            this.updateIngredientesBBDD();
        }
    }
    
    @Override
    public List<Ingrediente> getAllIngredientes() {
        final List<Ingrediente> ingredientesOrdenados = this.ingredientesBBDD.values().stream().collect(Collectors.toList());
        ingredientesOrdenados.sort((final Ingrediente ingr1, final Ingrediente ingr2) -> ingr1.getNombre().compareToIgnoreCase(ingr2.getNombre()));
        return ingredientesOrdenados;
    }
    
    @Override
    public List<Ingrediente> getIngredientes(final Ingrediente ingrediente) {
        return Collections.emptyList();
    }
}
