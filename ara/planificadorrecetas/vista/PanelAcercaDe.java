package ara.planificadorrecetas.vista;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class PanelAcercaDe extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PanelAcercaDe.class.getName());

    private JLabel labelTitle;
    private JLabel labelAuthor;
    private JLabel labelCopyright;
    private JLabel labelVersion;
    private GridBagLayout layoutMain;
    private EtchedBorder border;
    
    public PanelAcercaDe() {
        this.labelTitle = new JLabel();
        this.labelAuthor = new JLabel();
        this.labelCopyright = new JLabel();
        this.labelVersion = new JLabel();
        this.layoutMain = new GridBagLayout();
        this.border = new EtchedBorder();
        try {
            this.jbInit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }
    
    private void jbInit() {
        this.setLayout(this.layoutMain);
        this.setBorder(this.border);
        this.labelTitle.setText("Planificador de Recetas");
        this.labelAuthor.setText("Azucena Robles Alonso");
        this.labelCopyright.setText("Copyright 2020");
        this.labelVersion.setText("Versi\u00f3n 2.7");
        this.add(this.labelTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 0, new Insets(5, 15, 0, 15), 0, 0));
        this.add(this.labelAuthor, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, 10, 0, new Insets(10, 15, 0, 15), 0, 0));
        this.add(this.labelCopyright, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, 10, 0, new Insets(0, 15, 0, 15), 0, 0));
        this.add(this.labelVersion, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, 10, 0, new Insets(10, 15, 5, 15), 0, 0));
    }
}
