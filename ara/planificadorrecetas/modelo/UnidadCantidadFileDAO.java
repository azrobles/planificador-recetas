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

public class UnidadCantidadFileDAO implements UnidadCantidadDAO {
    private static final Logger LOGGER = Logger.getLogger(UnidadCantidadFileDAO.class.getName());

    private String fichero;
    private String directorioBBDD;
    private String ficheroBBDD;
    private String directorioBackup;
    private String extensionBU1;
    private String extensionBU2;
    private HashMap<String, UnidadCantidad> unidadesCantidadesBBDD;
    
    public UnidadCantidadFileDAO() {
        this.fichero = null;
        this.directorioBBDD = "bbdd/";
        this.ficheroBBDD = "unidadesCantidades.txt";
        this.directorioBackup = "backupBBDD/";
        this.extensionBU1 = ".backup";
        this.extensionBU2 = this.extensionBU1 + ".backup";
        this.unidadesCantidadesBBDD = new HashMap<>();
        this.fichero = this.directorioBBDD + this.ficheroBBDD;
        this.doBackup(this.fichero, this.ficheroBBDD);
        final Integer returnCode = this.readAllUnidadesCantidades();
        if (returnCode < -1) {
            LOGGER.log(Level.SEVERE, "Error en la lectura de la BBDD de unidades de cantidades.");
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
    
    private Integer readAllUnidadesCantidades() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.fichero))) {
            String linea = null;
            while ((linea = br.readLine()) != null) {
                String serialUnidadCantidad = null;
                serialUnidadCantidad = linea;
                final UnidadCantidad unidad = new UnidadCantidad();
                unidad.setUnidad(serialUnidadCantidad);
                this.unidadesCantidadesBBDD.put(unidad.getUnidad(), unidad);
            }
        } catch (FileNotFoundException fnte) {
            LOGGER.log(Level.SEVERE, "No existe el fichero de la BBDD de unidades de cantidades.");
            return -1;
        } catch (Exception e3) {
            LOGGER.log(Level.SEVERE, "", e3);
            return -2;
        }
        return 0;
    }
    
    @Override
    public void createUnidadCantidad(final UnidadCantidad unidad) {
        if (this.unidadesCantidadesBBDD.containsKey(unidad.getUnidad())) {
            return;
        }
        this.writeUnidadCantidad(unidad, true);
        this.unidadesCantidadesBBDD.put(unidad.getUnidad(), unidad);
    }
    
    private void writeUnidadCantidad(final UnidadCantidad unidad, final Boolean append) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(this.fichero, append)))) {
            pw.println(unidad.toString());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }
    
    @Override
    public UnidadCantidad readUnidadCantidad(final String unidad) {
        if (this.unidadesCantidadesBBDD.containsKey(unidad)) {
            return this.unidadesCantidadesBBDD.get(unidad);
        }
        return null;
    }
    
    @Override
    public void updateUnidadCantidad(final UnidadCantidad unidad) {
        if (this.unidadesCantidadesBBDD.containsKey(unidad.getUnidad())) {
            this.unidadesCantidadesBBDD.put(unidad.getUnidad(), unidad);
            this.updateUnidadesCantidadesBBDD();
        }
    }
    
    private void updateUnidadesCantidadesBBDD() {
        final List<UnidadCantidad> listaUnidadesCantidades = this.unidadesCantidadesBBDD.values().stream().collect(Collectors.toList());
        final Iterator<UnidadCantidad> i = listaUnidadesCantidades.iterator();
        if (i.hasNext()) {
            this.writeUnidadCantidad(i.next(), false);
        }
        while (i.hasNext()) {
            this.writeUnidadCantidad(i.next(), true);
        }
    }
    
    @Override
    public void deleteUnidadCantidad(final String unidad) {
        if (this.unidadesCantidadesBBDD.containsKey(unidad)) {
            this.unidadesCantidadesBBDD.remove(unidad);
            this.updateUnidadesCantidadesBBDD();
        }
    }
    
    @Override
    public List<UnidadCantidad> getAllUnidadesCantidades() {
        final List<UnidadCantidad> unidadesCantidadesOrdenados = this.unidadesCantidadesBBDD.values().stream().collect(Collectors.toList());
        unidadesCantidadesOrdenados.sort((final UnidadCantidad unidad1, final UnidadCantidad unidad2) -> unidad1.getUnidad().compareToIgnoreCase(unidad2.getUnidad()));
        return unidadesCantidadesOrdenados;
    }
    
    @Override
    public List<UnidadCantidad> getUnidadesCantidades(final UnidadCantidad unidad) {
        return Collections.emptyList();
    }
}
