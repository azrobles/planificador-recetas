package ara.planificadorrecetas.modelo;

public class Tiempo {
    private Integer cantidad;
    private UnidadDeMedidaTiempo unidadDeMedida;
    
    public void setCantidad(final Integer cantidad, final UnidadDeMedidaTiempo unidadDeMedida) {
        this.cantidad = cantidad;
        this.unidadDeMedida = unidadDeMedida;
    }
    
    public void setCantidad(final Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public Integer getCantidad() {
        return this.cantidad;
    }
    
    public void setUnidadDeMedida(final UnidadDeMedidaTiempo unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }
    
    public UnidadDeMedidaTiempo getUnidadDeMedida() {
        return this.unidadDeMedida;
    }
    
    public enum UnidadDeMedidaTiempo {
        SEGUNDOS, 
        MINUTOS, 
        HORAS;
    }
}
