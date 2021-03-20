package ara.planificadorrecetas.modelo;

import java.util.List;

public interface UnidadCantidadDAO {
    void createUnidadCantidad(final UnidadCantidad p0);
    
    UnidadCantidad readUnidadCantidad(final String p0);
    
    void updateUnidadCantidad(final UnidadCantidad p0);
    
    void deleteUnidadCantidad(final String p0);
    
    List<UnidadCantidad> getAllUnidadesCantidades();
    
    List<UnidadCantidad> getUnidadesCantidades(final UnidadCantidad p0);
}
