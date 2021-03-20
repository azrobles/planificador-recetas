package ara.planificadorrecetas.vista;

import javax.swing.table.DefaultTableModel;

public class TableModelMostrarRecetas extends DefaultTableModel {
    private static final long serialVersionUID = 1L;
    private static final String NOMBRE = "Nombre";
    private static final String LOCALIZACION = "Localizaci\u00f3n";
    private static final String DIFICULTAD = "Dificultad";
    private static final String TIEMPO = "Tiempo Preparaci\u00f3n";
    private static final String ESTACION = "Estaci\u00f3n";
    private static final String TIPO = "Tipo";
    private static final String INGREDIENTES = "Ingredientes";
    private static final String FRECUENCIA = "Frecuencia";
    private static final String EDITAR = "Editar";
    private static final String MEDIA = "MEDIA";

    private String[] columnNames;
    public final transient Object[] longValues;
    
    public TableModelMostrarRecetas(final Object[][] objects, final Object[] objects1) {
        super(objects, objects1);
        this.columnNames = new String[] { NOMBRE, LOCALIZACION, DIFICULTAD, TIEMPO, ESTACION, TIPO, INGREDIENTES, FRECUENCIA, EDITAR };
        this.longValues = new Object[] { new String[50], new String[25], MEDIA, new String[5], new String[10], new String[10], new String[10], new String[2], Boolean.TRUE };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelMostrarRecetas(final Object[] objects, final int i) {
        super(objects, i);
        this.columnNames = new String[] { NOMBRE, LOCALIZACION, DIFICULTAD, TIEMPO, ESTACION, TIPO, INGREDIENTES, FRECUENCIA, EDITAR };
        this.longValues = new Object[] { new String[50], new String[25], MEDIA, new String[5], new String[10], new String[10], new String[10], new String[2], Boolean.TRUE };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelMostrarRecetas(final int i, final int i1) {
        super(i, i1);
        this.columnNames = new String[] { NOMBRE, LOCALIZACION, DIFICULTAD, TIEMPO, ESTACION, TIPO, INGREDIENTES, FRECUENCIA, EDITAR };
        this.longValues = new Object[] { new String[50], new String[25], MEDIA, new String[5], new String[10], new String[10], new String[10], new String[2], Boolean.TRUE };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelMostrarRecetas() {
        this.columnNames = new String[] { NOMBRE, LOCALIZACION, DIFICULTAD, TIEMPO, ESTACION, TIPO, INGREDIENTES, FRECUENCIA, EDITAR };
        this.longValues = new Object[] { new String[50], new String[25], MEDIA, new String[5], new String[10], new String[10], new String[10], new String[2], Boolean.TRUE };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelMostrarRecetas(final int numRecetas) {
        this.columnNames = new String[] { NOMBRE, LOCALIZACION, DIFICULTAD, TIEMPO, ESTACION, TIPO, INGREDIENTES, FRECUENCIA, EDITAR };
        this.longValues = new Object[] { new String[50], new String[25], MEDIA, new String[5], new String[10], new String[10], new String[10], new String[2], Boolean.TRUE };
        super.setColumnIdentifiers(this.columnNames);
        super.setRowCount(numRecetas);
    }
    
    @Override
    public Class<?> getColumnClass(final int c) {
        return this.longValues[c].getClass();
    }
    
    @Override
    public boolean isCellEditable(final int row, final int col) {
        return col == 6 || col == 8;
    }
}
