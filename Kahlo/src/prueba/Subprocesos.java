/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JCORREA
 */
public class Subprocesos {

    public static class Subproceso1 extends Thread {
        @Override
        public void run(){
            System.out.println("Inicio Proceso 1");
            try {
                synchronized(this){
                    wait();
                }
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException en Subproceso 1");
            }
            System.out.println("Fin Subproceso 1");
        }
    }
    
    public static class Subproceso2 extends Thread {
        @Override
        public void run(){
            System.out.println("Inicio Proceso 2");
            try {
                sleep(10000);
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException en Subproceso 2");
            }
            System.out.println("Fin Subproceso 2");
        }
    }
    public static class Subproceso3 extends TimerTask {
        @Override
        public void run(){
            System.out.println("Hilo demonio y TimerTask");
        }
    }

}
