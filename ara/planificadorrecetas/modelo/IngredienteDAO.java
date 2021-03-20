package ara.planificadorrecetas.modelo;

import java.util.List;

public interface IngredienteDAO {
    void createIngrediente(final Ingrediente p0);
    
    Ingrediente readIngrediente(final String p0);
    
    void updateIngrediente(final Ingrediente p0);
    
    void deleteIngrediente(final String p0);
    
    List<Ingrediente> getAllIngredientes();
    
    List<Ingrediente> getIngredientes(final Ingrediente p0);
}
