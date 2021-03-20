package ara.planificadorrecetas.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaCompra {
    private static final Logger LOGGER = Logger.getLogger(ListaCompra.class.getName());

    List<ItemListaCompra> items;
    
    public ListaCompra() {
        this.items = new ArrayList<>();
    }
    
    public void addItem(final ItemListaCompra item) {
        final ItemListaCompra itemNuevo = new ItemListaCompra();
        itemNuevo.setIngrediente(item.getIngrediente());
        itemNuevo.getCantidad().setNumero(item.getCantidad().getNumero());
        itemNuevo.getCantidad().setUnidad(item.getCantidad().getUnidad());
        if (this.items.isEmpty()) {
            this.items.add(itemNuevo);
            return;
        }
        final Iterator<ItemListaCompra> i = this.items.iterator();
        if (!i.hasNext()) {
            return;
        }
        final ItemListaCompra itemLista = i.next();
        final int opcional = itemLista.getOpcional().compareTo(itemNuevo.getOpcional());
        if (itemLista.getIngrediente().getNombre().equals(itemNuevo.getIngrediente().getNombre()) && itemLista.getCantidad().getUnidad().getUnidad().equals(itemNuevo.getCantidad().getUnidad().getUnidad()) && opcional == 0) {
            itemLista.getCantidad().setNumero(itemLista.getCantidad().getNumero() + itemNuevo.getCantidad().getNumero());
            return;
        }
        this.items.add(itemNuevo);
    }
    
    public void setItems(final List<ItemListaCompra> items) {
        this.items = items;
    }
    
    public List<ItemListaCompra> getItems() {
        this.items.sort((ItemListaCompra i1, ItemListaCompra i2) -> i1.getIngrediente().getNombre().compareToIgnoreCase(i2.getIngrediente().getNombre()));
        this.items.sort((ItemListaCompra i1, ItemListaCompra i2) -> i1.getIngrediente().getSupermercado().compareToIgnoreCase(i2.getIngrediente().getSupermercado()));
        return this.items;
    }
    
    public String toString(final ItemListaCompra item) {
        String serialItem = item.getOpcional().toString() + ";";
        serialItem = serialItem + item.getIngrediente().getNombre() + ";";
        serialItem = serialItem + item.getCantidad().toString() + ";";
        serialItem += item.getIngrediente().getSupermercado();
        return serialItem;
    }
    
    public void print() {
        for (final ItemListaCompra item : this.items) {
            final String serialItem = this.toString(item);
            LOGGER.log(Level.INFO, serialItem);
        }
    }
}
