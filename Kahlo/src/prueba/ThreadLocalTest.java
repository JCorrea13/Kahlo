/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;
import java.util.Random;
/**
 * @author JCORREA
 */
public class ThreadLocalTest {
    //crear el thread clase local
    //El valor inicial es un numero aleatorio entre 0 y 999
    private static class MyThreadLocal extends ThreadLocal{
        Random random = new Random();
        
        @Override
        protected Object initialValue(){
            return random.nextInt(1000);
        }
    }
    
    //Define/ crea un thread variable local
    static ThreadLocal threadLocal = new MyThreadLocal();
    //crea una variable de clase
    static int counter = 0;
    
    //incrementa la variable counter
    private ThreadLocalTest(){
        counter++;
    }
    
    //Muestra el Thread variable local, counter y el conmbre de thread
    private void displayValues(){
        System.out.println(threadLocal.get() + "\t" + counter + "\t" + Thread.currentThread().getName());
    }
    
    public static void main(String args []){
        //Cada thread crea una nueva instacia de clase 
        //Muestra informacion de variable
        //Y descansa durante una canidad de tiempo aleatoria
        
        Runnable runner = new Runnable() {

            @Override
            public void run() {
                ThreadLocalTest tlt = new ThreadLocalTest();
                tlt.displayValues();
                try{
                    Thread.sleep(((Integer)threadLocal.get()).intValue());
                    tlt.displayValues();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        
        //Se crea otra instacia de clase y se muestran los valores
        ThreadLocalTest tlt = new ThreadLocalTest();
        tlt.displayValues();

        //Aque es donde se crean realmente los thread
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(runner);
            t.start();
        }
    }
}
