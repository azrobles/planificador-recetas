package ara.planificadorrecetas.vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import ara.planificadorrecetas.modelo.ItemListaCompra;
import ara.planificadorrecetas.modelo.ListaCompra;

public class PanelListaCompra extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PanelListaCompra.class.getName());
    
    private TableModelListaCompra modeloTabla;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonImprimir;
    private JPanel buttonPanel;
    
    public PanelListaCompra(final ListaCompra lista) {
        this.table = new JTable();
        this.scrollPane = new JScrollPane();
        this.buttonImprimir = new JButton();
        this.buttonPanel = new JPanel();
        try {
            this.jbInit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
        for (final ItemListaCompra item : lista.getItems()) {
            final Vector<Object> data = new Vector<>(this.table.getColumnCount());
            data.add(0, item.getOpcional());
            data.add(1, item.getIngrediente().getNombre());
            data.add(2, item.getCantidad().getNumero());
            data.add(3, item.getCantidad().getUnidad());
            data.add(4, item.getIngrediente().getSupermercado());
            this.modeloTabla.addRow(data);
        }
    }
    
    private void jbInit() {
        this.setLayout(new BorderLayout());
        this.modeloTabla = new TableModelListaCompra();
        this.table = new JTable(this.modeloTabla);
        this.table.setFillsViewportHeight(true);
        this.table.setPreferredScrollableViewportSize(new Dimension(300, 140));
        this.table.setRowSelectionAllowed(false);
        this.table.setColumnSelectionAllowed(false);
        this.table.setCellSelectionEnabled(false);
        this.scrollPane = new JScrollPane(this.table);
        this.initColumnSizes(this.table);
        this.add(this.scrollPane, BorderLayout.NORTH);
        this.buttonImprimir.setText("Imprimir");
        this.buttonPanel.setLayout(new FlowLayout(4));
        this.buttonPanel.add(this.buttonImprimir);
        this.add(this.buttonPanel, BorderLayout.SOUTH);
    }
    
    private void initColumnSizes(final JTable table) {
        final TableModelListaCompra model = (TableModelListaCompra)table.getModel();
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
    
    public void addImprimirListener(final ActionListener al) {
        this.buttonImprimir.addActionListener(al);
    }
    
    public JTable getTable() {
        return this.table;
    }
}
