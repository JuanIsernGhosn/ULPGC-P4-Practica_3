package hipermercado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
      
    private static Timer crono = new Timer(60000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cola.cerrar();
            System.out.println("Se ha cerrado la cola");
            control = false; 
            crono.stop();
        }
    });
    private static boolean control = true;
    private static Cola cola = new Cola();
    private static Contabilidad contabilidad = new Contabilidad();
            
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Número de clientes: ");
        int numeroClientes = scanner.nextInt();
        System.out.print("Número de cajas: ");
        int numeroCajas = scanner.nextInt();
        
        if(numeroCajas > 0){
            
            ArrayList<Caja> listaCajas = new ArrayList<>();
            for (int i = 0; i < numeroCajas; i++) {
                listaCajas.add(new Caja(cola, contabilidad));
            }

            for (Caja caja : listaCajas) {
                caja.start();
            }
            
            crono.start();
            
            DuendeAveria duende = new DuendeAveria(listaCajas);
                       
            int countClientes = 0;
            
            while(control){
                if(countClientes < numeroClientes && Caja.getNumero()> 0){
                    sleep((long) new Random().nextInt(6) * 1000);
                    cola.añadirFinal();
                    countClientes++;
                } else {
                    crono.stop();
                    control = false; 
                    break;
                }
            }

            for (Caja caja : listaCajas) {
                try {
                    caja.join();
                } catch (Exception e) {
                }
            }
            duende.interrupt();
        }
    }
}