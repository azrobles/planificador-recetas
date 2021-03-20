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

import ara.planificadorrecetas.modelo.UnidadCantidad;

public class PanelUnidadCantidad extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PanelUnidadCantidad.class.getName());

    private GridBagLayout layoutDatos;
    private JLabel labelTitulo;
    private JButton buttonOK;
    private JPanel panelTitulo;
    private JPanel panelBoton;
    private JPanel panelDatos;
    private JLabel labelUnidad;
    private JTextField fieldUnidad;
    
    public PanelUnidadCantidad() {
        this.layoutDatos = new GridBagLayout();
        this.labelTitulo = new JLabel();
        this.buttonOK = new JButton();
        this.panelTitulo = new JPanel();
        this.panelBoton = new JPanel();
        this.panelDatos = new JPanel();
        this.labelUnidad = new JLabel();
        this.fieldUnidad = new JTextField();
        try {
            this.jbInit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }
    
    private void jbInit() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(250, 125));
        this.labelTitulo.setText("A\u00d1ADIR UNIDAD");
        this.buttonOK.setText("Guardar");
        this.labelUnidad.setText("Unidad: ");
        this.fieldUnidad.setColumns(20);
        this.panelTitulo.setLayout(new FlowLayout(1));
        this.panelTitulo.add(this.labelTitulo);
        this.panelDatos.setLayout(this.layoutDatos);
        this.panelDatos.add(this.labelUnidad, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 17, 0, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.fieldUnidad, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 13, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelBoton.setLayout(new FlowLayout(4));
        this.panelBoton.add(this.buttonOK);
        this.add(this.panelTitulo, BorderLayout.NORTH);
        this.add(this.panelDatos, BorderLayout.CENTER);
        this.add(this.panelBoton, BorderLayout.SOUTH);
    }
    
    public void addUnidadBotonOKListener(final ActionListener al) {
        this.buttonOK.addActionListener(al);
    }
    
    public JTextField getFieldUnidad() {
        return this.fieldUnidad;
    }
    
    public void reset() {
        this.add(this.panelDatos, BorderLayout.CENTER);
        this.fieldUnidad.setText("");
        this.updateUI();
        this.repaint();
    }
    
    public void cargarUnidad(final UnidadCantidad unidad) {
        this.reset();
        this.fieldUnidad.setText(unidad.getUnidad());
        this.updateUI();
        this.repaint();
    }
    
    public JPanel getPanelDatos() {
        return this.panelDatos;
    }
}
