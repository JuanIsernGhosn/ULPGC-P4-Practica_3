package hipermercado;

import java.util.ArrayList;

public class Cola {
    
    private ArrayList<Cliente> clientes;
    private int actualClientes;
    private int maxClientes;
    private boolean abierta;
    
    public Cola(){
        clientes = new ArrayList<>();
        abierta = true;
        maxClientes = 0;
        actualClientes = 0;
    }
    
    int tamanoMaximo(){
        return maxClientes;
    }
    
    int tamanoActual(){
        return actualClientes;
    }
    
    public void cerrar() {
        abierta = false;
    }
    
    public boolean abierta(){
        if(abierta){
            return true;
        }
        return false;
    }
    
    public synchronized void añadirFinal() {
        if (abierta) {
            actualClientes++;
            if (actualClientes > maxClientes) maxClientes = actualClientes;
            Cliente nuevoCliente = new Cliente();
            clientes.add(nuevoCliente);
            System.out.println("-> El cliente: " + nuevoCliente.dameNombre() + " se ha unido a la cola ");
            notify();
        } else {
            System.out.println("Cola cerrada, no se añade");
        }
    }
    
     public synchronized Cliente sacar() throws InterruptedException{
        if(actualClientes>0 && abierta) {
            Cliente cliente = clientes.get(0);
            clientes.remove(0);
            actualClientes--;
            System.out.println("<- El cliente: " + cliente.dameNombre() + " ha salido de la cola ");
            return cliente;
        } else if (actualClientes==0 && abierta){
            wait(10000);
            if(actualClientes==0){
                return null;
            } else {
                Cliente cliente = clientes.get(0);
                clientes.remove(0);
                actualClientes--;
                System.out.println("<- El cliente: " + cliente.dameNombre() + " ha salido de la cola ");
                return cliente;
            }

        }         
        return null;
    }
    
    public synchronized void añadirPrincipio(Cliente cliente) {
        actualClientes++;
        if (actualClientes > maxClientes) maxClientes = actualClientes;
        clientes.add(0, cliente);
        System.out.println("-> El cliente: " + cliente.dameNombre() + " ha vuelto a la cola");
        notify();
    }
}