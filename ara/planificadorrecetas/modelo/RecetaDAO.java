package ara.planificadorrecetas.modelo;

import java.util.List;

public interface RecetaDAO {
    void createReceta(final Receta p0);
    
    Receta readReceta(final String p0);
    
    void updateReceta(final Receta p0);
    
    void deleteReceta(final String p0);
    
    List<Receta> getAllRecetas();
    
    List<Receta> getRecetas(final Receta p0);
}
