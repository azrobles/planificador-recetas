package ara.planificadorrecetas;

import ara.planificadorrecetas.modelo.ConfiguradorDAO;
import ara.planificadorrecetas.modelo.ConfiguradorFileDAO;
import ara.planificadorrecetas.modelo.IngredienteDAO;
import ara.planificadorrecetas.modelo.IngredienteFileDAO;
import ara.planificadorrecetas.modelo.RecetaDAO;
import ara.planificadorrecetas.modelo.RecetaFileDAO;
import ara.planificadorrecetas.modelo.TipoRecetaDAO;
import ara.planificadorrecetas.modelo.TipoRecetaFileDAO;
import ara.planificadorrecetas.modelo.UnidadCantidadDAO;
import ara.planificadorrecetas.modelo.UnidadCantidadFileDAO;

public class ModeloPlanificadorRecetas {
    private RecetaDAO recetaDAO;
    private TipoRecetaDAO tipoRecetaDAO;
    private IngredienteDAO ingredienteDAO;
    private ConfiguradorDAO configuradorDAO;
    private UnidadCantidadDAO unidadCantidadDAO;
    
    public ModeloPlanificadorRecetas() {
        this.recetaDAO = null;
        this.tipoRecetaDAO = null;
        this.ingredienteDAO = null;
        this.configuradorDAO = null;
        this.unidadCantidadDAO = null;
        this.tipoRecetaDAO = new TipoRecetaFileDAO();
        this.ingredienteDAO = new IngredienteFileDAO();
        this.recetaDAO = new RecetaFileDAO(this.tipoRecetaDAO, this.ingredienteDAO);
        this.configuradorDAO = new ConfiguradorFileDAO(this.tipoRecetaDAO, this.ingredienteDAO);
        this.unidadCantidadDAO = new UnidadCantidadFileDAO();
    }
    
    public RecetaDAO getRecetaDAO() {
        return this.recetaDAO;
    }
    
    public TipoRecetaDAO getTipoRecetaDAO() {
        return this.tipoRecetaDAO;
    }
    
    public IngredienteDAO getIngredienteDAO() {
        return this.ingredienteDAO;
    }
    
    public ConfiguradorDAO getConfiguradorDAO() {
        return this.configuradorDAO;
    }
    
    public UnidadCantidadDAO getUnidadCantidadDAO() {
        return this.unidadCantidadDAO;
    }
}
