package ara.planificadorrecetas.modelo;

public class Peso {
    private int valor;
    
    public Peso() {
        this.valor = 0;
    }
    
    public Peso(final int valor) {
        this.valor = valor;
    }
    
    public void aumentarValor() {
        ++this.valor;
    }
    
    public void disminuirValor() {
        --this.valor;
    }
    
    public void setValor(final int valor) {
        this.valor = valor;
    }
    
    public int getValor() {
        return this.valor;
    }
    
    public Boolean mayorQue(final Peso otroPeso) {
        return (this.valor > otroPeso.getValor());
    }
    
    public Boolean igualQue(final Peso otroPeso) {
        return (this.valor == otroPeso.getValor());
    }
}
