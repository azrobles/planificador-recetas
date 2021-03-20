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

public class TipoRecetaFileDAO implements TipoRecetaDAO {
    private static final Logger LOGGER = Logger.getLogger(TipoRecetaFileDAO.class.getName());

    private String fichero;
    private String directorioBBDD;
    private String ficheroBBDD;
    private String directorioBackup;
    private String extensionBU1;
    private String extensionBU2;
    private HashMap<String, TipoReceta> tiposRecetasBBDD;
    
    public TipoRecetaFileDAO() {
        this.fichero = null;
        this.directorioBBDD = "bbdd/";
        this.ficheroBBDD = "tiposRecetas.txt";
        this.directorioBackup = "backupBBDD/";
        this.extensionBU1 = ".backup";
        this.extensionBU2 = this.extensionBU1 + ".backup";
        this.tiposRecetasBBDD = new HashMap<>();
        this.fichero = this.directorioBBDD + this.ficheroBBDD;
        this.doBackup(this.fichero, this.ficheroBBDD);
        final Integer returnCode = this.readAllTiposRecetas();
        if (returnCode < -1) {
            LOGGER.log(Level.SEVERE, "Error en la lectura de la BBDD de tipos de recetas.");
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
    
    private Integer readAllTiposRecetas() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.fichero))) {
            String linea = null;
            while ((linea = br.readLine()) != null) {
                String serialTipoReceta = null;
                serialTipoReceta = linea;
                final TipoReceta tipo = new TipoReceta();
                tipo.setNombre(serialTipoReceta);
                this.tiposRecetasBBDD.put(tipo.getNombre(), tipo);
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
    public void createTipoReceta(final TipoReceta tipo) {
        if (this.tiposRecetasBBDD.containsKey(tipo.getNombre())) {
            return;
        }
        this.writeTipoReceta(tipo, true);
        this.tiposRecetasBBDD.put(tipo.getNombre(), tipo);
    }
    
    private void writeTipoReceta(final TipoReceta tipo, final Boolean append) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(this.fichero, append)))) {
            pw.println(tipo.toString());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }
    
    @Override
    public TipoReceta readTipoReceta(final String nombre) {
        if (this.tiposRecetasBBDD.containsKey(nombre)) {
            return this.tiposRecetasBBDD.get(nombre);
        }
        return null;
    }
    
    @Override
    public void updateTipoReceta(final TipoReceta tipo) {
        if (this.tiposRecetasBBDD.containsKey(tipo.getNombre())) {
            this.tiposRecetasBBDD.put(tipo.getNombre(), tipo);
            this.updateTiposRecetasBBDD();
        }
    }
    
    private void updateTiposRecetasBBDD() {
        final List<TipoReceta> listaTiposRecetas = this.tiposRecetasBBDD.values().stream().collect(Collectors.toList());
        final Iterator<TipoReceta> i = listaTiposRecetas.iterator();
        if (i.hasNext()) {
            this.writeTipoReceta(i.next(), false);
        }
        while (i.hasNext()) {
            this.writeTipoReceta(i.next(), true);
        }
    }
    
    @Override
    public void deleteTipoReceta(final String nombre) {
        if (this.tiposRecetasBBDD.containsKey(nombre)) {
            this.tiposRecetasBBDD.remove(nombre);
            this.updateTiposRecetasBBDD();
        }
    }
    
    @Override
    public List<TipoReceta> getAllTiposRecetas() {
        final List<TipoReceta> tiposRecetasOrdenados = this.tiposRecetasBBDD.values().stream().collect(Collectors.toList());
        tiposRecetasOrdenados.sort((final TipoReceta tipo1, final TipoReceta tipo2) -> tipo1.getNombre().compareToIgnoreCase(tipo2.getNombre()));
        return tiposRecetasOrdenados;
    }
    
    @Override
    public List<TipoReceta> getTiposRecetas(final TipoReceta tipo) {
        return Collections.emptyList();
    }
}
