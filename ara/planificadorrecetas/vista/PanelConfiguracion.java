package ara.planificadorrecetas.vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import ara.planificadorrecetas.ModeloPlanificadorRecetas;
import ara.planificadorrecetas.modelo.Configurador;
import ara.planificadorrecetas.modelo.Criterio;
import ara.planificadorrecetas.modelo.Ingrediente;
import ara.planificadorrecetas.modelo.PeriodoDia;
import ara.planificadorrecetas.modelo.Receta;
import ara.planificadorrecetas.modelo.TipoReceta;

public class PanelConfiguracion extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PanelConfiguracion.class.getName());

    ModeloPlanificadorRecetas modelo;
    private TableModelConfiguradorCriterios modeloTabla;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonAddDia;
    private JButton buttonAddComida;
    private JButton buttonAddCena;
    private JPanel addPanel;
    private JButton buttonGuardar;
    private JButton buttonEjecutar;
    private JPanel buttonPanel;
    private JCheckBox[] estacion;
    private JPanel panelEstacion;
    private JLabel labelDificultad;
    private JRadioButton[] radioDificultad;
    private ButtonGroup groupDificultad;
    private JPanel panelDificultad;
    private JLabel labelDisponibilidad;
    private JRadioButton[] radioDisponibilidad;
    private ButtonGroup groupDisponibilidad;
    private JPanel panelDisponibilidad;
    private JPanel selectionPanel;
    private JPanel southPanel;
    
    public PanelConfiguracion(final ModeloPlanificadorRecetas modelo) {
        this.table = new JTable();
        this.scrollPane = new JScrollPane();
        this.buttonAddDia = new JButton();
        this.buttonAddComida = new JButton();
        this.buttonAddCena = new JButton();
        this.addPanel = new JPanel();
        this.buttonGuardar = new JButton();
        this.buttonEjecutar = new JButton();
        this.buttonPanel = new JPanel();
        this.panelEstacion = new JPanel();
        this.labelDificultad = new JLabel();
        this.groupDificultad = new ButtonGroup();
        this.panelDificultad = new JPanel();
        this.labelDisponibilidad = new JLabel();
        this.groupDisponibilidad = new ButtonGroup();
        this.panelDisponibilidad = new JPanel();
        this.selectionPanel = new JPanel();
        this.southPanel = new JPanel();
        this.modelo = modelo;
        try {
            this.jbInit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
    }
    
    private void jbInit() {
        this.setLayout(new BorderLayout());
        this.modeloTabla = new TableModelConfiguradorCriterios(0);
        this.table = new JTable(this.modeloTabla);
        this.table.setFillsViewportHeight(true);
        this.table.setPreferredScrollableViewportSize(new Dimension(300, 140));
        this.table.setRowSelectionAllowed(false);
        this.table.setColumnSelectionAllowed(false);
        this.table.setCellSelectionEnabled(true);
        this.scrollPane = new JScrollPane(this.table);
        this.initColumnSizes(this.table);
        this.setUpColumnPeriodo();
        this.setUpColumnTipoReceta(this.modelo.getTipoRecetaDAO().getAllTiposRecetas());
        this.setUpColumnIngrediente(this.modelo.getIngredienteDAO().getAllIngredientes());
        this.add(this.scrollPane, BorderLayout.NORTH);
        this.buttonAddDia.setText("A\u00f1adir d\u00eda");
        this.buttonAddComida.setText("A\u00f1adir comida");
        this.buttonAddCena.setText("A\u00f1adir cena");
        this.addPanel.setLayout(new FlowLayout(1));
        this.addPanel.add(this.buttonAddDia);
        this.addPanel.add(this.buttonAddComida);
        this.addPanel.add(this.buttonAddCena);
        this.buttonGuardar.setText("Guardar criterios");
        this.buttonEjecutar.setText("Planificar");
        this.buttonPanel.setLayout(new FlowLayout(4));
        this.buttonPanel.add(this.buttonGuardar);
        this.buttonPanel.add(this.buttonEjecutar);
        this.selectionPanel.setLayout(new BorderLayout());
        this.panelEstacion.setLayout(new FlowLayout(3));
        this.panelDificultad.setLayout(new FlowLayout(3));
        this.panelDisponibilidad.setLayout(new FlowLayout(3));
        this.setUpButtonsEstacion();
        this.setUpButtonsDificultad();
        this.setUpButtonsDisponibilidad();
        this.selectionPanel.add(this.panelEstacion, BorderLayout.EAST);
        this.selectionPanel.add(this.panelDificultad, BorderLayout.NORTH);
        this.selectionPanel.add(this.panelDisponibilidad, BorderLayout.WEST);
        this.southPanel.setLayout(new BorderLayout());
        this.southPanel.add(this.selectionPanel, BorderLayout.NORTH);
        this.southPanel.add(this.addPanel, BorderLayout.CENTER);
        this.southPanel.add(this.buttonPanel, BorderLayout.SOUTH);
        this.add(this.southPanel, BorderLayout.SOUTH);
    }
    
    private void initColumnSizes(final JTable table) {
        final TableModelConfiguradorCriterios model = (TableModelConfiguradorCriterios)table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        final TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        for (int i = 0; i < table.getColumnCount(); ++i) {
            column = table.getColumnModel().getColumn(i);
            comp = headerRenderer.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;
            comp = table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(table, model.longValues[i], false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }
    
    private void setUpColumnPeriodo() {
        final JComboBox<String> comboBox = new JComboBox<>();
        final PeriodoDia.NombrePeriodo[] periodos = PeriodoDia.NombrePeriodo.values();
        for (int i = 0; i < periodos.length; ++i) {
            comboBox.addItem(periodos[i].toString());
        }
        this.table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));
        final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Selecciona un periodo");
        this.table.getColumnModel().getColumn(1).setCellRenderer(renderer);
    }
    
    private void setUpColumnTipoReceta(final List<TipoReceta> tipos) {
        final JComboBox<String> comboBox = new JComboBox<>();
        final Iterator<TipoReceta> i = tipos.iterator();
        while (i.hasNext()) {
            comboBox.addItem(i.next().getNombre());
        }
        comboBox.addItem(null);
        this.table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox));
        final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Selecciona un tipo de receta");
        this.table.getColumnModel().getColumn(2).setCellRenderer(renderer);
    }
    
    private void setUpColumnIngrediente(final List<Ingrediente> ingredientes) {
        final JComboBox<String> comboBox = new JComboBox<>();
        final Iterator<Ingrediente> i = ingredientes.iterator();
        while (i.hasNext()) {
            comboBox.addItem(i.next().getNombre());
        }
        comboBox.addItem(null);
        this.table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboBox));
        final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Selecciona un ingrediente");
        this.table.getColumnModel().getColumn(3).setCellRenderer(renderer);
    }
    
    private void setUpButtonsEstacion() {
        final Receta.Estacion[] estaciones = Receta.Estacion.values();
        this.estacion = new JCheckBox[estaciones.length - 1];
        int j = 0;
        for (int i = 0; i < estaciones.length; ++i) {
            if (estaciones[i].compareTo(Receta.Estacion.CUALQUIERA) != 0) {
                this.estacion[j] = new JCheckBox();
                this.estacion[j].setText(estaciones[i].toString());
                this.estacion[j].setActionCommand(estaciones[i].toString());
                this.panelEstacion.add(this.estacion[j]);
                ++j;
            }
        }
    }
    
    private void setUpButtonsDificultad() {
        this.labelDificultad.setText("Dificultad de preparaci\u00f3n: ");
        this.panelDificultad.add(this.labelDificultad);
        final Receta.Dificultad[] dificultades = Receta.Dificultad.values();
        this.radioDificultad = new JRadioButton[dificultades.length];
        for (int i = 0; i < dificultades.length; ++i) {
            this.radioDificultad[i] = new JRadioButton(dificultades[i].toString());
            this.radioDificultad[i].setActionCommand(dificultades[i].toString());
            if (dificultades[i].compareTo(Receta.Dificultad.MEDIA) == 0) {
                this.radioDificultad[i].setSelected(true);
            }
            this.groupDificultad.add(this.radioDificultad[i]);
            this.panelDificultad.add(this.radioDificultad[i]);
        }
    }
    
    private void setUpButtonsDisponibilidad() {
        this.labelDisponibilidad.setText("Disponibilidad de ingredientes: ");
        this.panelDisponibilidad.add(this.labelDisponibilidad);
        final Ingrediente.Disponibilidad[] disponibilidades = Ingrediente.Disponibilidad.values();
        this.radioDisponibilidad = new JRadioButton[disponibilidades.length];
        for (int i = 0; i < disponibilidades.length; ++i) {
            this.radioDisponibilidad[i] = new JRadioButton(disponibilidades[i].toString());
            this.radioDisponibilidad[i].setActionCommand(disponibilidades[i].toString());
            if (disponibilidades[i].compareTo(Ingrediente.Disponibilidad.FACIL) == 0) {
                this.radioDisponibilidad[i].setSelected(true);
            }
            this.groupDisponibilidad.add(this.radioDisponibilidad[i]);
            this.panelDisponibilidad.add(this.radioDisponibilidad[i]);
        }
    }
    
    public void addDiaListener(final ActionListener al) {
        this.buttonAddDia.addActionListener(al);
    }
    
    public void addComidaListener(final ActionListener al) {
        this.buttonAddComida.addActionListener(al);
    }
    
    public void addCenaListener(final ActionListener al) {
        this.buttonAddCena.addActionListener(al);
    }
    
    public void addGuardarListener(final ActionListener al) {
        this.buttonGuardar.addActionListener(al);
    }
    
    public void addPlanificarListener(final ActionListener al) {
        this.buttonEjecutar.addActionListener(al);
    }
    
    public JTable getTable() {
        return this.table;
    }
    
    public JCheckBox[] getButtonsEstacion() {
        return this.estacion;
    }
    
    public ButtonGroup getGroupDificultad() {
        return this.groupDificultad;
    }
    
    public ButtonGroup getGroupDisponibilidad() {
        return this.groupDisponibilidad;
    }
    
    public void reset() {
        this.modeloTabla = new TableModelConfiguradorCriterios(0);
        this.table.setModel(this.modeloTabla);
        this.initColumnSizes(this.table);
        this.setUpColumnPeriodo();
        this.setUpColumnTipoReceta(this.modelo.getTipoRecetaDAO().getAllTiposRecetas());
        this.setUpColumnIngrediente(this.modelo.getIngredienteDAO().getAllIngredientes());
        for (int i = 0; i < this.estacion.length; ++i) {
            this.estacion[i].setSelected(false);
        }
        for (int i = 0; i < this.radioDificultad.length; ++i) {
            if (Receta.Dificultad.valueOf(this.radioDificultad[i].getText()).compareTo(Receta.Dificultad.MEDIA) == 0) {
                this.radioDificultad[i].setSelected(true);
            }
        }
        for (int i = 0; i < this.radioDisponibilidad.length; ++i) {
            if (Ingrediente.Disponibilidad.valueOf(this.radioDisponibilidad[i].getText()).compareTo(Ingrediente.Disponibilidad.FACIL) == 0) {
                this.radioDisponibilidad[i].setSelected(true);
            }
        }
        this.updateUI();
        this.repaint();
    }

    private void cargarCriterios(List<Criterio> criterios) {
        for (final Criterio criterio : criterios) {
            final Vector<String> data = new Vector<>();
            data.add(0, null);
            data.add(1, criterio.getPeriodo().toString());
            final Set<TipoReceta> tipos = criterio.getTipoReceta();
            final Iterator<TipoReceta> jt = tipos.iterator();
            if (jt.hasNext()) {
                data.add(2, jt.next().getNombre());
            }
            final Set<Ingrediente> ingredientes = criterio.getIngredientes();
            final Iterator<Ingrediente> kt = ingredientes.iterator();
            if (kt.hasNext()) {
                data.add(3, kt.next().getNombre());
            }
            this.modeloTabla.addRow(data);
        }
    }
    
    public void cargarConfiguracion(final Configurador configurador) {
        this.reset();
        final Set<Receta.Estacion> estaciones = configurador.getEstaciones();
        for (final Receta.Estacion estacionConfig : estaciones) {
            for (int i = 0; i < this.estacion.length; ++i) {
                if (Receta.Estacion.valueOf(this.estacion[i].getText()).compareTo(estacionConfig) == 0) {
                    this.estacion[i].setSelected(true);
                }
            }
        }
        for (int i = 0; i < this.radioDificultad.length; ++i) {
            if (Receta.Dificultad.valueOf(this.radioDificultad[i].getText()).compareTo(configurador.getDificultad()) == 0) {
                this.radioDificultad[i].setSelected(true);
            }
        }
        for (int i = 0; i < this.radioDisponibilidad.length; ++i) {
            if (Ingrediente.Disponibilidad.valueOf(this.radioDisponibilidad[i].getText()).compareTo(configurador.getDisponibilidad()) == 0) {
                this.radioDisponibilidad[i].setSelected(true);
            }
        }

        final List<Criterio> criterios = configurador.getCriterios();
        this.cargarCriterios(criterios);

        this.updateUI();
        this.repaint();
    }
}
