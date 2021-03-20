package ara.planificadorrecetas.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileCopy {
    private static final Logger LOGGER = Logger.getLogger(FileCopy.class.getName());

    private FileCopy() {
        throw new IllegalStateException("Utility class");
    }

    public static void copy(final String sourceFile, final String destinationFile) {
        try (final FileInputStream in = new FileInputStream(new File(sourceFile));
                final FileOutputStream out = new FileOutputStream(new File(destinationFile));) {
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Hubo un error de entrada/salida!!!");
        }
    }
}
