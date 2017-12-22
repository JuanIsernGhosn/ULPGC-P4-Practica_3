package hipermercado;

public class Contabilidad {
    
    private Double saldo;
    
    public Contabilidad() {
        saldo = 0.0;
    }

    public synchronized void añadeSaldo (Double cantidad){
        saldo += cantidad;
    }

    public synchronized Double dameSaldo (){
        return saldo;
    }
    
}