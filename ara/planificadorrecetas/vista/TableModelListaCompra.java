package ara.planificadorrecetas.vista;

import javax.swing.table.DefaultTableModel;

public class TableModelListaCompra extends DefaultTableModel {
    private static final long serialVersionUID = 1L;
    private static final String OPCIONAL = "Opcional";
    private static final String INGREDIENTE = "Ingrediente";
    private static final String CANTIDAD = "Cantidad";
    private static final String UNIDAD = "Unidad";
    private static final String SUPERMERCADO = "Supermercado";

    private String[] columnNames;
    public final transient Object[] longValues;
    
    public TableModelListaCompra(final Object[][] objects, final Object[] objects1) {
        super(objects, objects1);
        this.columnNames = new String[] { OPCIONAL, INGREDIENTE, CANTIDAD, UNIDAD, SUPERMERCADO };
        this.longValues = new Object[] { false, new String[15], new String[5], new String[10], new String[20] };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelListaCompra(final Object[] objects, final int i) {
        super(objects, i);
        this.columnNames = new String[] { OPCIONAL, INGREDIENTE, CANTIDAD, UNIDAD, SUPERMERCADO };
        this.longValues = new Object[] { false, new String[15], new String[5], new String[10], new String[20] };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelListaCompra(final int i, final int i1) {
        super(i, i1);
        this.columnNames = new String[] { OPCIONAL, INGREDIENTE, CANTIDAD, UNIDAD, SUPERMERCADO };
        this.longValues = new Object[] { false, new String[15], new String[5], new String[10], new String[20] };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelListaCompra() {
        this.columnNames = new String[] { OPCIONAL, INGREDIENTE, CANTIDAD, UNIDAD, SUPERMERCADO };
        this.longValues = new Object[] { false, new String[15], new String[5], new String[10], new String[20] };
        super.setColumnIdentifiers(this.columnNames);
    }
    
    public TableModelListaCompra(final int items) {
        this.columnNames = new String[] { OPCIONAL, INGREDIENTE, CANTIDAD, UNIDAD, SUPERMERCADO };
        this.longValues = new Object[] { false, new String[15], new String[5], new String[10], new String[20] };
        super.setColumnIdentifiers(this.columnNames);
        super.setRowCount(items);
    }
    
    @Override
    public Class<?> getColumnClass(final int c) {
        return this.longValues[c].getClass();
    }
}
