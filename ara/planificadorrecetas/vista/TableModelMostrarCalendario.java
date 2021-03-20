package ara.planificadorrecetas.vista;

import javax.swing.table.DefaultTableModel;

public class TableModelMostrarCalendario extends DefaultTableModel {
    private static final long serialVersionUID = 1L;
    private static final String DIA = "D\u00eda";
    private static final String PERIODO = "Periodo";
    private static final String TIPO = "Tipo";
    private static final String RECETA = "Receta";
    private static final String LOCALIZACION = "Localizaci\u00f3n";
    private static final String DIA_VALUE = "Dia";
    private static final String PERIODO_VALUE = "Comida";
    private static final String TIPO_VALUE = "Pescado";
    private static final String LOCALIZACION_VALUE = "Archivador Sirena";

    private String[] columnNames;
    public final transient Object[] longValues;
    
    public TableModelMostrarCalendario(final Object[][] objects, final Object[] objects1) {
        super(objects, objects1);
        this.columnNames = new String[] { DIA, PERIODO, TIPO, RECETA, LOCALIZACION };
        this.longValues = new Object[] { DIA_VALUE, PERIODO_VALUE, TIPO_VALUE, new String[50], LOCALIZACION_VALUE };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelMostrarCalendario(final Object[] objects, final int i) {
        super(objects, i);
        this.columnNames = new String[] { DIA, PERIODO, TIPO, RECETA, LOCALIZACION };
        this.longValues = new Object[] { DIA_VALUE, PERIODO_VALUE, TIPO_VALUE, new String[50], LOCALIZACION_VALUE };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelMostrarCalendario(final int i, final int i1) {
        super(i, i1);
        this.columnNames = new String[] { DIA, PERIODO, TIPO, RECETA, LOCALIZACION };
        this.longValues = new Object[] { DIA_VALUE, PERIODO_VALUE, TIPO_VALUE, new String[50], LOCALIZACION_VALUE };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelMostrarCalendario() {
        this.columnNames = new String[] { DIA, PERIODO, TIPO, RECETA, LOCALIZACION };
        this.longValues = new Object[] { DIA_VALUE, PERIODO_VALUE, TIPO_VALUE, new String[50], LOCALIZACION_VALUE };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelMostrarCalendario(final int days) {
        this.columnNames = new String[] { DIA, PERIODO, TIPO, RECETA, LOCALIZACION };
        this.longValues = new Object[] { DIA_VALUE, PERIODO_VALUE, TIPO_VALUE, new String[50], LOCALIZACION_VALUE };
        super.setColumnIdentifiers(this.columnNames);
        super.setRowCount(days);
    }
    
    @Override
    public Class<?> getColumnClass(final int c) {
        return this.longValues[c].getClass();
    }
}
