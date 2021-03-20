package ara.planificadorrecetas.vista;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import ara.planificadorrecetas.modelo.PeriodoDia;

public class TableModelConfiguradorCriterios extends DefaultTableModel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(TableModelConfiguradorCriterios.class.getName());

    private String[] columnNames;
    public final transient Object[] longValues;
    
    public TableModelConfiguradorCriterios(final int days) {
        this.columnNames = new String[] { "D\u00eda", "Periodo", "Tipo Receta", "Ingredientes", "Borrar" };
        this.longValues = new Object[] { Integer.valueOf(30), "Comida", "Tipo Receta", "Ingredientes", Boolean.TRUE };
        super.setColumnIdentifiers(this.columnNames);
        super.setRowCount(days);
    }
    
    public void addPeriodo(final PeriodoDia.NombrePeriodo periodo) {
        final Vector<String> data = new Vector<>();
        data.add(0, null);
        data.add(1, periodo.toString());
        super.addRow(data);
    }
    
    public void addDia() {
        final Vector<String> periodo1 = new Vector<>();
        periodo1.add(0, null);
        periodo1.add(1, PeriodoDia.NombrePeriodo.COMIDA.toString());
        super.addRow(periodo1);
        final Vector<String> periodo2 = new Vector<>();
        periodo2.add(0, null);
        periodo2.add(1, PeriodoDia.NombrePeriodo.CENA.toString());
        super.addRow(periodo2);
    }
    
    @Override
    public Class<?> getColumnClass(final int c) {
        return this.longValues[c].getClass();
    }
    
    @Override
    public boolean isCellEditable(final int row, final int col) {
        return col >= 1;
    }
    
    @Override
    public void setValueAt(final Object value, final int row, final int col) {
        if (col == this.getColumnCount() - 1) {
            this.removeRow(row);
        }
        else {
            super.setValueAt(value, row, col);
            this.fireTableCellUpdated(row, col);
        }
        LOGGER.log(Level.INFO, "New value of data:");
        this.printDebugData();
    }
    
    private void printDebugData() {
        StringBuilder stringBuilder = new StringBuilder();
        final int numRows = this.getRowCount();
        final int numCols = this.getColumnCount();
        for (int i = 0; i < numRows; ++i) {
            stringBuilder.append("    row " + i + ":");
            for (int j = 0; j < numCols; ++j) {
                stringBuilder.append("  " + this.getValueAt(i, j));
            }
            stringBuilder.append(System.lineSeparator());
        }
        stringBuilder.append("--------------------------");
        LOGGER.log(Level.INFO, "{0}", stringBuilder);
    }
}
