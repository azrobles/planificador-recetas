package ara.planificadorrecetas.vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import ara.planificadorrecetas.ModeloPlanificadorRecetas;
import ara.planificadorrecetas.modelo.Ingrediente;
import ara.planificadorrecetas.modelo.ItemListaCompra;
import ara.planificadorrecetas.modelo.Receta;

public class PanelMostrarRecetas extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PanelMostrarRecetas.class.getName());

    ModeloPlanificadorRecetas modelo;
    private TableModelMostrarRecetas modeloTabla;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonAumentarFrecuencia;
    private JButton buttonDisminuirFrecuencia;
    private JButton buttonReiniciar;
    private JButton buttonBorrar;
    private JPanel buttonPanel;
    
    public PanelMostrarRecetas(final ModeloPlanificadorRecetas modelo) {
        this.table = new JTable();
        this.scrollPane = new JScrollPane();
        this.buttonAumentarFrecuencia = new JButton();
        this.buttonDisminuirFrecuencia = new JButton();
        this.buttonReiniciar = new JButton();
        this.buttonBorrar = new JButton();
        this.buttonPanel = new JPanel();
        this.modelo = modelo;
        try {
            this.jbInit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
        for (final Receta receta : modelo.getRecetaDAO().getAllRecetas()) {
            final Vector<Object> data = new Vector<>(this.table.getColumnCount());
            data.add(0, receta.getNombre());
            data.add(1, receta.getLocalizacion());
            data.add(2, receta.getDificultad().toString());
            String medidaTiempo = receta.getTiempoPreparacion().getUnidadDeMedida().toString();
            medidaTiempo = medidaTiempo.substring(0, 3);
            data.add(3, receta.getTiempoPreparacion().getCantidad() + " " + medidaTiempo);
            data.add(4, receta.getEstacion());
            data.add(5, receta.getTipo().getNombre());
            final List<String> ingredientes = new ArrayList<>();
            final Iterator<ItemListaCompra> j = receta.getIngredientes().iterator();
            while (j.hasNext()) {
                final Ingrediente ingrediente = j.next().getIngrediente();
                ingredientes.add(ingrediente.getNombre());
            }
            data.add(6, ingredientes);
            data.add(7, receta.getFrecuencia());
            this.modeloTabla.addRow(data);
        }
        final TableCellRenderer renderCombo = (final JTable jTable, 
                final Object value, final boolean isSelected, 
                final boolean hasFocus, final int row, final int column) -> {
                    final JComboBox<String> combo = new JComboBox<>();
                    final List<?> ingredientes = (List<?>)jTable.getModel().getValueAt(row, column);
                    final Iterator<?> i = ingredientes.iterator();
                    while (i.hasNext()) {
                        combo.addItem((String)i.next());
                    }
                    return combo;
                };
        this.table.getColumnModel().getColumn(6).setCellRenderer(renderCombo);
        this.table.getColumnModel().getColumn(6).setCellEditor(new TableRecetasCellEditorCombo());
        this.table.setSelectionMode(0);
    }
    
    private void jbInit() {
        this.setLayout(new BorderLayout());
        this.modeloTabla = new TableModelMostrarRecetas();
        this.table = new JTable(this.modeloTabla);
        this.table.setFillsViewportHeight(true);
        this.table.setPreferredScrollableViewportSize(new Dimension(300, 140));
        this.table.setRowSelectionAllowed(false);
        this.table.setColumnSelectionAllowed(false);
        this.table.setCellSelectionEnabled(false);
        this.scrollPane = new JScrollPane(this.table);
        this.initColumnSizes(this.table);
        this.add(this.scrollPane, BorderLayout.NORTH);
        this.buttonAumentarFrecuencia.setText("Aumentar Frecuencia");
        this.buttonAumentarFrecuencia.setActionCommand("Aumentar");
        this.buttonDisminuirFrecuencia.setText("Disminuir Frecuencia");
        this.buttonDisminuirFrecuencia.setActionCommand("Disminuir");
        this.buttonReiniciar.setText("Reiniciar");
        this.buttonReiniciar.setActionCommand("Reiniciar");
        this.buttonBorrar.setText("Borrar Receta");
        this.buttonBorrar.setActionCommand("Borrar");
        this.buttonPanel.setLayout(new FlowLayout(4));
        this.buttonPanel.add(this.buttonAumentarFrecuencia);
        this.buttonPanel.add(this.buttonDisminuirFrecuencia);
        this.buttonPanel.add(this.buttonReiniciar);
        this.buttonPanel.add(this.buttonBorrar);
        this.add(this.buttonPanel, BorderLayout.SOUTH);
    }
    
    private void initColumnSizes(final JTable table) {
        final TableModelMostrarRecetas model = (TableModelMostrarRecetas)table.getModel();
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
    
    public JTable getTable() {
        return this.table;
    }
    
    public void addEditarListener(final TableModelListener al) {
        this.table.getModel().addTableModelListener(al);
    }
    
    public void addFrecuenciaListener(final ActionListener al) {
        this.buttonReiniciar.addActionListener(al);
        this.buttonAumentarFrecuencia.addActionListener(al);
        this.buttonDisminuirFrecuencia.addActionListener(al);
    }
    
    public void addBorrarListener(final ActionListener al) {
        this.buttonBorrar.addActionListener(al);
    }
    
    public class TableRecetasCellEditorCombo extends AbstractCellEditor implements TableCellEditor {
        private static final long serialVersionUID = 1L;

        transient List<?> ingredientes;
        JComboBox<String> combo;
        
        public TableRecetasCellEditorCombo() {
            this.combo = new JComboBox<>();
        }
        
        @Override
        public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int rowIndex, final int vColIndex) {
            this.combo = new JComboBox<>();
            this.ingredientes = (List<?>)table.getModel().getValueAt(rowIndex, vColIndex);
            final Iterator<?> i = this.ingredientes.iterator();
            while (i.hasNext()) {
                this.combo.addItem((String)i.next());
            }
            return this.combo;
        }
        
        @Override
        public Object getCellEditorValue() {
            return this.ingredientes;
        }
    }
}
