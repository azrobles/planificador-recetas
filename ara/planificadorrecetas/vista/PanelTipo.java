package ara.planificadorrecetas.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ara.planificadorrecetas.modelo.TipoReceta;

public class PanelTipo extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PanelTipo.class.getName());

    private GridBagLayout layoutDatos;
    private JLabel labelTitulo;
    private JButton buttonOK;
    private JPanel panelTitulo;
    private JPanel panelBoton;
    private JPanel panelDatos;
    private JLabel labelNombre;
    private JTextField fieldNombre;
    
    public PanelTipo() {
        this.layoutDatos = new GridBagLayout();
        this.labelTitulo = new JLabel();
        this.buttonOK = new JButton();
        this.panelTitulo = new JPanel();
        this.panelBoton = new JPanel();
        this.panelDatos = new JPanel();
        this.labelNombre = new JLabel();
        this.fieldNombre = new JTextField();
        try {
            this.jbInit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }
    
    private void jbInit() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(250, 125));
        this.labelTitulo.setText("A\u00d1ADIR TIPO");
        this.buttonOK.setText("Guardar");
        this.labelNombre.setText("Nombre: ");
        this.fieldNombre.setColumns(20);
        this.panelTitulo.setLayout(new FlowLayout(1));
        this.panelTitulo.add(this.labelTitulo);
        this.panelDatos.setLayout(this.layoutDatos);
        this.panelDatos.add(this.labelNombre, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 17, 0, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.fieldNombre, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 13, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelBoton.setLayout(new FlowLayout(4));
        this.panelBoton.add(this.buttonOK);
        this.add(this.panelTitulo, BorderLayout.NORTH);
        this.add(this.panelDatos, BorderLayout.CENTER);
        this.add(this.panelBoton, BorderLayout.SOUTH);
    }
    
    public void addTipoBotonOKListener(final ActionListener al) {
        this.buttonOK.addActionListener(al);
    }
    
    public JTextField getFieldNombre() {
        return this.fieldNombre;
    }
    
    public void reset() {
        this.add(this.panelDatos, BorderLayout.CENTER);
        this.fieldNombre.setText("");
        this.updateUI();
        this.repaint();
    }
    
    public void cargarTipo(final TipoReceta tipo) {
        this.reset();
        this.fieldNombre.setText(tipo.getNombre());
        this.updateUI();
        this.repaint();
    }
    
    public JPanel getPanelDatos() {
        return this.panelDatos;
    }
}
