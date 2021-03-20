package ara.planificadorrecetas.modelo;

import java.util.List;

public interface ConfiguradorDAO {
    void createConfigurador(final Configurador p0, final String p1);
    
    Configurador readConfigurador(final String p0);
    
    void updateConfigurador(final Configurador p0, final String p1);
    
    void deleteConfigurador(final String p0);
    
    List<String> getAllConfiguradores();
}
