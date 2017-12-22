package hipermercado;

public class Caja extends Thread {
    private Cola miCola;
    private Contabilidad miConta;
    private static int count = 0;
    private static int numero = 0;
    private int id;
    private boolean abierta;
    private Cliente cliente;
    private double saldo;

    public Caja(Cola miCola, Contabilidad miConta) {
        this.miConta = miConta;
        this.miCola = miCola;
        abierta = true;
        count++;
        id = count;
        saldo = 0;
        numero++;
    }   

    public static int getNumero() {
        return numero;
    }
  
    @Override
    public void run() {
        try {
            while(abierta){
                cliente = miCola.sacar();
                if (cliente == null){
                    abierta = false;
                    System.out.println("---* Se ha cerrado la caja "+id+" *---");
                    numero--;
                    miConta.añadeSaldo(saldo);
                    System.out.println("---* Se han añadido "+ saldo + " euros a la contabilidad *---");
                    return;
                } else {
                    System.out.println("----> La caja " + id + " llama a "+cliente.dameNombre() +" para atenderle.");
                    Thread.sleep((long) (cliente.damePrecioCarro()/ 10) * 1000);
                    saldo += cliente.damePrecioCarro();
                    System.out.println("DONE: La caja " + id + " ha terminado de atender a "+cliente.dameNombre());
                    cliente = null;
                }
            }
        } catch (InterruptedException ex) {
            abierta = false;
            System.out.println("---* Se ha averiado la caja "+id+" *---");
            numero--;
            if(cliente!=null){
                miCola.añadirPrincipio(cliente);
            }
            miConta.añadeSaldo(saldo);
            System.out.println("---* Se han añadido "+ saldo + " euros a la contabilidad *---");
            return;
        }
    }
}