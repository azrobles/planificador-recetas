package ara.planificadorrecetas.vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ara.planificadorrecetas.ModeloPlanificadorRecetas;
import ara.planificadorrecetas.modelo.Ingrediente;
import ara.planificadorrecetas.modelo.ItemListaCompra;
import ara.planificadorrecetas.modelo.Receta;
import ara.planificadorrecetas.modelo.Tiempo;
import ara.planificadorrecetas.modelo.TipoReceta;
import ara.planificadorrecetas.modelo.UnidadCantidad;

public class PanelReceta extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PanelReceta.class.getName());

    ModeloPlanificadorRecetas modelo;
    private GridBagLayout layoutDatos;
    private JLabel labelTitulo;
    private JButton buttonOK;
    private JPanel panelTitulo;
    private JPanel panelBoton;
    private JPanel panelDatos;
    private JLabel labelNombre;
    private JTextField fieldNombre;
    private JLabel labelLocalizacion;
    private JTextField fieldLocalizacion;
    private JLabel labelDificultad;
    private JComboBox<String> comboDificultad;
    private JLabel labelTiempo;
    private JTextField fieldTiempo;
    private JComboBox<String> comboTiempo;
    private JLabel labelEstacion;
    private JComboBox<String> comboEstacion;
    private JLabel labelTipo;
    private JComboBox<String> comboTipo;
    private JButton buttonNuevoTipo;
    private JLabel labelListaIngredientes;
    private JLabel labelListaSeleccionados;
    private JComboBox<String> comboIngredientes;
    private JTextField fieldCantidad;
    private JComboBox<String> comboUnidad;
    private JButton buttonNuevaUnidad;
    private JLabel labelOpcional;
    private JCheckBox checkOpcional;
    private DefaultListModel<String> listModelSeleccionados;
    private JList<String> listSeleccionados;
    private JScrollPane scrollListaSeleccionados;
    private JButton buttonDerecha;
    private JButton buttonIzquierda;
    private JPanel panelBotonLista;
    private JButton buttonNuevoIngrediente;
    
    public PanelReceta(final ModeloPlanificadorRecetas modelo) {
        this.layoutDatos = new GridBagLayout();
        this.labelTitulo = new JLabel();
        this.buttonOK = new JButton();
        this.panelTitulo = new JPanel();
        this.panelBoton = new JPanel();
        this.panelDatos = new JPanel();
        this.labelNombre = new JLabel();
        this.fieldNombre = new JTextField();
        this.labelLocalizacion = new JLabel();
        this.fieldLocalizacion = new JTextField();
        this.labelDificultad = new JLabel();
        this.comboDificultad = new JComboBox<>();
        this.labelTiempo = new JLabel();
        this.fieldTiempo = new JTextField();
        this.comboTiempo = new JComboBox<>();
        this.labelEstacion = new JLabel();
        this.comboEstacion = new JComboBox<>();
        this.labelTipo = new JLabel();
        this.comboTipo = new JComboBox<>();
        this.buttonNuevoTipo = new JButton();
        this.labelListaIngredientes = new JLabel();
        this.labelListaSeleccionados = new JLabel();
        this.comboIngredientes = new JComboBox<>();
        this.fieldCantidad = new JTextField();
        this.comboUnidad = new JComboBox<>();
        this.buttonNuevaUnidad = new JButton();
        this.labelOpcional = new JLabel();
        this.checkOpcional = new JCheckBox();
        this.listModelSeleccionados = new DefaultListModel<>();
        this.listSeleccionados = new JList<>();
        this.scrollListaSeleccionados = new JScrollPane();
        this.buttonDerecha = new JButton();
        this.buttonIzquierda = new JButton();
        this.panelBotonLista = new JPanel();
        this.buttonNuevoIngrediente = new JButton();
        this.modelo = modelo;
        try {
            this.jbInit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }
    
    private void jbInit() {
        this.setLayout(new BorderLayout());
        this.labelTitulo.setText("A\u00d1ADIR RECETA");
        this.buttonOK.setText("Guardar");
        this.labelNombre.setText("Nombre: ");
        this.fieldNombre.setColumns(30);
        this.labelLocalizacion.setText("Localizaci\u00f3n: ");
        this.fieldLocalizacion.setColumns(30);
        this.labelDificultad.setText("Dificultad: ");
        this.labelTiempo.setText("Tiempo preparaci\u00f3n: ");
        this.fieldTiempo.setColumns(15);
        this.labelEstacion.setText("Estaci\u00f3n: ");
        this.labelTipo.setText("Tipo: ");
        this.buttonNuevoTipo.setText("Nuevo Tipo");
        this.labelListaIngredientes.setText("Ingredientes: ");
        this.labelListaSeleccionados.setText("Seleccionados: ");
        this.fieldCantidad.setColumns(4);
        this.labelOpcional.setText("Opcional: ");
        this.buttonNuevaUnidad.setText("Nueva Unidad");
        this.buttonDerecha.setText("Derecha");
        this.buttonDerecha.setActionCommand("Derecha");
        this.buttonIzquierda.setText("Izquierda");
        this.buttonIzquierda.setActionCommand("Izquierda");
        this.buttonNuevoIngrediente.setText("Nuevo Ingrediente");
        final Receta.Dificultad[] dificultades = Receta.Dificultad.values();
        for (int i = 0; i < dificultades.length; ++i) {
            this.comboDificultad.addItem(dificultades[i].toString());
        }
        final Tiempo.UnidadDeMedidaTiempo[] unidades = Tiempo.UnidadDeMedidaTiempo.values();
        for (int i = 0; i < unidades.length; ++i) {
            this.comboTiempo.addItem(unidades[i].toString());
        }
        final Receta.Estacion[] estaciones = Receta.Estacion.values();
        for (int i = 0; i < estaciones.length; ++i) {
            this.comboEstacion.addItem(estaciones[i].toString());
        }
        final List<TipoReceta> tipos = this.modelo.getTipoRecetaDAO().getAllTiposRecetas();
        Iterator<TipoReceta> itTipoReceta = tipos.iterator();
        while (itTipoReceta.hasNext()) {
            final TipoReceta tipoReceta = itTipoReceta.next();
            this.comboTipo.addItem(tipoReceta.getNombre());
        }
        final List<Ingrediente> ingredientes = this.modelo.getIngredienteDAO().getAllIngredientes();
        Iterator<Ingrediente> itIngrediente = ingredientes.iterator();
        while (itIngrediente.hasNext()) {
            final Ingrediente ingrediente = itIngrediente.next();
            this.comboIngredientes.addItem(ingrediente.getNombre());
        }
        final List<UnidadCantidad> unidadesMedida = this.modelo.getUnidadCantidadDAO().getAllUnidadesCantidades();
        Iterator<UnidadCantidad> itUnidadCantidad = unidadesMedida.iterator();
        while (itUnidadCantidad.hasNext()) {
            final UnidadCantidad unidad = itUnidadCantidad.next();
            this.comboUnidad.addItem(unidad.getUnidad());
        }
        this.checkOpcional.setSelected(false);
        this.listSeleccionados = new JList<>(this.listModelSeleccionados);
        this.listSeleccionados.setSelectionMode(2);
        this.listSeleccionados.setVisibleRowCount(5);
        this.scrollListaSeleccionados = new JScrollPane(this.listSeleccionados);
        this.panelBotonLista.setLayout(new BoxLayout(this.panelBotonLista, 1));
        this.panelBotonLista.add(this.buttonDerecha);
        this.panelBotonLista.add(this.buttonIzquierda);
        this.panelTitulo.setLayout(new FlowLayout(1));
        this.panelTitulo.add(this.labelTitulo);
        this.panelDatos.setLayout(this.layoutDatos);
        this.panelDatos.add(this.labelNombre, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, 17, 0, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.fieldNombre, new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0, 13, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.labelLocalizacion, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, 17, 0, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.fieldLocalizacion, new GridBagConstraints(2, 1, 2, 1, 0.0, 0.0, 13, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.labelDificultad, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, 17, 0, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.comboDificultad, new GridBagConstraints(2, 2, 2, 1, 0.0, 0.0, 13, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.labelTiempo, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, 17, 0, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.fieldTiempo, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, 10, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.comboTiempo, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, 13, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.labelEstacion, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, 17, 0, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.comboEstacion, new GridBagConstraints(2, 4, 2, 1, 0.0, 0.0, 13, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.labelTipo, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0, 17, 0, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.comboTipo, new GridBagConstraints(2, 5, 2, 1, 0.0, 0.0, 13, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.labelListaIngredientes, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0, 17, 2, new Insets(10, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.labelListaSeleccionados, new GridBagConstraints(3, 6, 1, 1, 0.0, 0.0, 13, 2, new Insets(10, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.comboIngredientes, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0, 512, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.panelBotonLista, new GridBagConstraints(2, 7, 1, 5, 0.0, 0.0, 10, 0, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.scrollListaSeleccionados, new GridBagConstraints(3, 7, 1, 5, 0.0, 0.0, 768, 2, new Insets(2, 0, 20, 0), 0, 0));
        this.panelDatos.add(this.fieldCantidad, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0, 512, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.comboUnidad, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0, 512, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.labelOpcional, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0, 512, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelDatos.add(this.checkOpcional, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0, 512, 2, new Insets(2, 0, 2, 0), 0, 0));
        this.panelBoton.setLayout(new FlowLayout(4));
        this.panelBoton.add(this.buttonNuevoTipo);
        this.panelBoton.add(this.buttonNuevoIngrediente);
        this.panelBoton.add(this.buttonNuevaUnidad);
        this.panelBoton.add(this.buttonOK);
        this.add(this.panelTitulo, BorderLayout.NORTH);
        this.add(this.panelDatos, BorderLayout.CENTER);
        this.add(this.panelBoton, BorderLayout.SOUTH);
    }
    
    public void addRecetaBotonNuevoTipoListener(final ActionListener al) {
        this.buttonNuevoTipo.addActionListener(al);
    }
    
    public void addRecetaBotonNuevoIngredienteListener(final ActionListener al) {
        this.buttonNuevoIngrediente.addActionListener(al);
    }
    
    public void addRecetaBotonNuevaUnidadListener(final ActionListener al) {
        this.buttonNuevaUnidad.addActionListener(al);
    }
    
    public void addRecetaBotonListaListener(final ActionListener al) {
        this.buttonDerecha.addActionListener(al);
        this.buttonIzquierda.addActionListener(al);
    }
    
    public void addRecetaBotonOKListener(final ActionListener al) {
        this.buttonOK.addActionListener(al);
    }
    
    public JTextField getFieldNombre() {
        return this.fieldNombre;
    }
    
    public JTextField getFieldLocalizacion() {
        return this.fieldLocalizacion;
    }
    
    public JComboBox<String> getComboDificultad() {
        return this.comboDificultad;
    }
    
    public JTextField getFieldTiempo() {
        return this.fieldTiempo;
    }
    
    public JComboBox<String> getComboTiempo() {
        return this.comboTiempo;
    }
    
    public JComboBox<String> getComboEstacion() {
        return this.comboEstacion;
    }
    
    public JComboBox<String> getComboTipo() {
        return this.comboTipo;
    }
    
    public JComboBox<String> getComboIngredientes() {
        return this.comboIngredientes;
    }
    
    public void addIngredienteComboIngredientes(final String ingrediente) {
        this.comboIngredientes.addItem(ingrediente);
        final List<String> values = new ArrayList<>();
        for (int i = 0; i < this.comboIngredientes.getItemCount(); ++i) {
            values.add(this.comboIngredientes.getItemAt(i));
        }
        Collections.sort(values);
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(values.toArray(new String[values.size()]));
        this.comboIngredientes.setModel(model);
    }
    
    public void removeIngredienteComboIngredientes(final String ingrediente) {
        this.comboIngredientes.removeItem(ingrediente);
        final List<String> values = new ArrayList<>();
        for (int i = 0; i < this.comboIngredientes.getItemCount(); ++i) {
            values.add(this.comboIngredientes.getItemAt(i));
        }
        Collections.sort(values);
        final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(values.toArray(new String[values.size()]));
        this.comboIngredientes.setModel(model);
    }
    
    public JTextField getFieldCantidad() {
        return this.fieldCantidad;
    }
    
    public JComboBox<String> getComboUnidad() {
        return this.comboUnidad;
    }
    
    public JCheckBox getCheckOpcional() {
        return this.checkOpcional;
    }
    
    public JList<String> getListSeleccionados() {
        return this.listSeleccionados;
    }
    
    public void reset() {
        this.add(this.panelDatos, BorderLayout.CENTER);
        this.fieldNombre.setText("");
        this.fieldLocalizacion.setText("");
        this.comboDificultad.setSelectedIndex(0);
        this.fieldTiempo.setText("");
        this.comboTiempo.setSelectedIndex(0);
        this.comboEstacion.setSelectedIndex(0);
        this.comboTipo.removeAllItems();
        final List<TipoReceta> tipos = this.modelo.getTipoRecetaDAO().getAllTiposRecetas();
        Iterator<TipoReceta> itTipo = tipos.iterator();
        while (itTipo.hasNext()) {
            this.comboTipo.addItem(itTipo.next().getNombre());
        }
        this.comboTipo.setSelectedIndex(0);
        this.comboIngredientes.removeAllItems();
        final List<Ingrediente> ingredientes = this.modelo.getIngredienteDAO().getAllIngredientes();
        Iterator<Ingrediente> itIngrediente = ingredientes.iterator();
        while (itIngrediente.hasNext()) {
            final Ingrediente ingrediente = itIngrediente.next();
            this.comboIngredientes.addItem(ingrediente.getNombre());
        }
        this.comboIngredientes.setSelectedIndex(0);
        this.comboUnidad.removeAllItems();
        final List<UnidadCantidad> unidadesMedida = this.modelo.getUnidadCantidadDAO().getAllUnidadesCantidades();
        Iterator<UnidadCantidad> it = unidadesMedida.iterator();
        while (it.hasNext()) {
            final UnidadCantidad unidad = it.next();
            this.comboUnidad.addItem(unidad.getUnidad());
        }
        this.comboUnidad.setSelectedIndex(0);
        this.checkOpcional.setSelected(false);
        this.listModelSeleccionados.clear();
        this.listSeleccionados.setFixedCellWidth(200);
        this.listSeleccionados.setAutoscrolls(true);
        this.updateUI();
        this.repaint();
    }
    
    public void cargarReceta(final Receta receta) {
        this.reset();
        this.fieldNombre.setText(receta.getNombre());
        this.fieldLocalizacion.setText(receta.getLocalizacion());
        this.comboDificultad.setSelectedItem(receta.getDificultad().toString());
        this.fieldTiempo.setText(receta.getTiempoPreparacion().getCantidad().toString());
        this.comboTiempo.setSelectedItem(receta.getTiempoPreparacion().getUnidadDeMedida().toString());
        this.comboEstacion.setSelectedItem(receta.getEstacion().toString());
        this.comboTipo.setSelectedItem(receta.getTipo().getNombre());
        this.listModelSeleccionados.clear();
        for (final ItemListaCompra item : receta.getIngredientes()) {
            this.listModelSeleccionados.addElement(item.getIngrediente().getNombre() + ";" + item.getCantidad().getNumero() + ";" + item.getCantidad().getUnidad().toString() + ";" + item.getOpcional().toString());
            this.removeIngredienteComboIngredientes(item.getIngrediente().getNombre());
        }
        this.updateUI();
        this.repaint();
    }
    
    public JPanel getPanelDatos() {
        return this.panelDatos;
    }
}
