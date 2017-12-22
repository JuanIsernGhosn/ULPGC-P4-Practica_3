/* NO MODIFIQUE ESTE CÓDIGO */
package hipermercado;
import java.util.Random;
import static java.lang.Math.log;
import java.util.concurrent.*;
public final class DuendeAveria extends Thread {
    private Random rnd=new Random();
    private Caja[] cajas;
    private long[] intervalos;
    private int[] cajaAveriada;
    private void println(String s) { 
        System.out.println("\033[7m"+s+"\033[0m"); // video inverso
    }   
    private static long tini = 0;
    public static long getT() { return System.currentTimeMillis()-tini; }
    
    public DuendeAveria(Caja[] c) {
        println("--DuendeAveria INVOCADO--");
        tini= System.currentTimeMillis();
        if(c.length == 0) return;
        rnd=new Random();
        cajas = c.clone();
        //Calculo de intervalos de fallos
        intervalos=new long[cajas.length];
        cajaAveriada= new int[cajas.length];
        for(int i=0;i<cajaAveriada.length;i++){
            cajaAveriada[i]=i;
        }
        for(int i=0;i<intervalos.length;i++){
            double ran=rnd.nextDouble();
            intervalos[i]= (long)(-30*1000*log(1-ran));
        }
        for(int i=0;i<intervalos.length-1;i++){
            //Desordena las averiadas i <=> averiada
            int averiada = rnd.nextInt(cajaAveriada.length-i)+i;
            int aux=cajaAveriada[i];
            cajaAveriada[i]=cajaAveriada[averiada];
            cajaAveriada[averiada]=aux;
        }
        //Ordenación
        java.util.Arrays.sort(intervalos);
        //Diferencia=espera para siguiente avería;
        long anterior=intervalos[0];
        for(int i=1;i<intervalos.length;i++){
            intervalos[i]-=anterior;
            anterior += intervalos[i];
        }
        start();
    }
    public DuendeAveria(java.util.Collection<Caja> c) {
        this(c.toArray(new Caja[0]));
    }
    
    public void run() {
        Thread segundero= new Thread(new Runnable() {
            int segundos = -1;
            public void run() {
                try{
                    for(int i=1; i<80; i++){
                        Thread.sleep(1000);
                        println(i+"s");
                        if(isInterrupted()) break;
                    }
                }catch(InterruptedException e){
                    return;
                }
            }
        });
        segundero.start();
        try {
            for(int i=0; i<intervalos.length && !isInterrupted(); i++){
                if(getT() > 60000) break;
                Caja caja=cajas[cajaAveriada[i]];
                if(caja==null){
                    println("Error: El Duende avería ha encontrado una caja a null");
                    return;
                }
                if(caja.getState() == Thread.State.NEW) {
                    println("ERROR: inicie los hilos antes de crear el Duende Avería");
                    System.exit(0);
                    continue;
                }
                if(!caja.isAlive()) {
                    continue;
                }
                sleep(intervalos[i]);
                if(!caja.isAlive()) {
                    continue;
                }
                caja.interrupt();
                println("AVERÍA hilo:"+caja.getId());
                for(int j=0; j<i; j++) {
                    Caja averiada=cajas[cajaAveriada[j]];
                    if(averiada.isAlive()){
                       println("ERROR:La caja "+averiada.getId()+" sigue en ejecución después de ser averiada"); 
                    }
                }
            }
            println("--TODAS LAS CAJAS AVERIADAS--");
        } catch(InterruptedException e) {
            println("Duende avería: interrupción recibida");
        } finally { 
            segundero.interrupt();
        }
    }
}
