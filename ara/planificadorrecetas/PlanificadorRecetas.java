package ara.planificadorrecetas;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

public class PlanificadorRecetas {
    private static final Logger LOGGER = Logger.getLogger(PlanificadorRecetas.class.getName());

    public PlanificadorRecetas() {
        final ModeloPlanificadorRecetas modelo = new ModeloPlanificadorRecetas();
        final VistaPlanificadorRecetas vista = new VistaPlanificadorRecetas();
        new ControladorPlanificadorRecetas(modelo, vista);
    }
    
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }

        new PlanificadorRecetas();
    }
}
