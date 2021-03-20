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

import ara.planificadorrecetas.modelo.Dia;
import ara.planificadorrecetas.modelo.PeriodoDia;
import ara.planificadorrecetas.modelo.Planificador;
import ara.planificadorrecetas.modelo.Receta;

public class PanelMostrarCalendario extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PanelMostrarCalendario.class.getName());
    
    private TableModelMostrarCalendario modeloTabla;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonListaCompra;
    private JButton buttonImprimir;
    private JPanel buttonPanel;
    
    public PanelMostrarCalendario(final Planificador plan) {
        this.table = new JTable();
        this.scrollPane = new JScrollPane();
        this.buttonListaCompra = new JButton();
        this.buttonImprimir = new JButton();
        this.buttonPanel = new JPanel();
        try {
            this.jbInit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
        for (final Dia dia : plan.getDias()) {
            for (final PeriodoDia periodo : dia.getPeriodos()) {
                if (periodo.getRecetas().isEmpty()) {
                    final Vector<String> data = new Vector<>(this.table.getColumnCount());
                    data.add(0, String.valueOf(dia.getNumero()));
                    data.add(1, periodo.getPeriodo().toString());
                    data.add(2, "-");
                    data.add(3, "NINGUNA DISPONIBLE");
                    data.add(4, "-");
                    this.modeloTabla.addRow(data);
                }
                else {
                    for (final Receta receta : periodo.getRecetas()) {
                        final Vector<String> data2 = new Vector<>(this.table.getColumnCount());
                        data2.add(0, String.valueOf(dia.getNumero()));
                        data2.add(1, periodo.getPeriodo().toString());
                        data2.add(2, receta.getTipo().getNombre());
                        data2.add(3, receta.getNombre());
                        data2.add(4, receta.getLocalizacion());
                        this.modeloTabla.addRow(data2);
                    }
                }
            }
        }
    }
    
    private void jbInit() {
        this.setLayout(new BorderLayout());
        this.modeloTabla = new TableModelMostrarCalendario();
        this.table = new JTable(this.modeloTabla);
        this.table.setFillsViewportHeight(true);
        this.table.setPreferredScrollableViewportSize(new Dimension(300, 140));
        this.table.setRowSelectionAllowed(false);
        this.table.setColumnSelectionAllowed(false);
        this.table.setCellSelectionEnabled(false);
        this.scrollPane = new JScrollPane(this.table);
        this.initColumnSizes(this.table);
        this.add(this.scrollPane, "North");
        this.buttonListaCompra.setText("Generar Lista Compra");
        this.buttonImprimir.setText("Imprimir");
        this.buttonPanel.setLayout(new FlowLayout(4));
        this.buttonPanel.add(this.buttonListaCompra);
        this.buttonPanel.add(this.buttonImprimir);
        this.add(this.buttonPanel, "South");
    }
    
    private void initColumnSizes(final JTable table) {
        final TableModelMostrarCalendario model = (TableModelMostrarCalendario)table.getModel();
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
    
    public void addListaCompraListener(final ActionListener al) {
        this.buttonListaCompra.addActionListener(al);
    }
    
    public void addImprimirListener(final ActionListener al) {
        this.buttonImprimir.addActionListener(al);
    }
    
    public JTable getTable() {
        return this.table;
    }
}
