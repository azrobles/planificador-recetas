package ara.planificadorrecetas.modelo;

import java.util.List;

public interface TipoRecetaDAO {
    void createTipoReceta(final TipoReceta p0);
    
    TipoReceta readTipoReceta(final String p0);
    
    void updateTipoReceta(final TipoReceta p0);
    
    void deleteTipoReceta(final String p0);
    
    List<TipoReceta> getAllTiposRecetas();
    
    List<TipoReceta> getTiposRecetas(final TipoReceta p0);
}
