/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import prueba.Hombre;
import prueba.Mujer;
import prueba.Persona;
import prueba.Subprocesos;
import kahlo.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author JCORREA
 */
public class Kahlo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Kahlo");
        Persona juan = new Persona("Juan", 13, 1.2f, Persona.Sexo.MASCULINO);
        Persona[] personas = new Persona []{
            new Persona("Ana", 48, 1.2f, Persona.Sexo.FEMENINO),
            new Persona("Pablo", 38, 1.8f, Persona.Sexo.MASCULINO),
            new Persona("Ramiro", 37, 1.75f, Persona.Sexo.MASCULINO),
            new Persona("Berenice", 10, 1.75f, Persona.Sexo.FEMENINO),
            new Persona("Carla", 7, 1.75f, Persona.Sexo.FEMENINO),
        };
        
        new Kahlo().imprimePersonas(personas);
        System.out.println("-------------------------------------");
        Arrays.sort(personas);
        new Kahlo().imprimePersonas(personas);
        //COMO CREAR UN EJECUTABLE DE MI ARCHIVO JAR
        System.out.println(Arrays.binarySearch(personas, juan));
        System.out.println("ARGS [] lenght :" + args.length);
        
        new Hombre("Manuel", 20, 1.83f, Persona.Sexo.FEMENINO);
        new Mujer("Maria", 20, 1.75f, Persona.Sexo.FEMENINO);
        
        Thread sub1 = new Subprocesos.Subproceso1();
        Thread sub2 = new Subprocesos.Subproceso2();
        
        System.out.println("Inicio prueba Hilos");
        sub1.start();
        sub2.start();
        
        try{
            Thread.yield();
            System.out.println("Esperando a que el hilo 2 termine");
            sub2.join();
            System.out.println("El hilo 2 termino");
            System.out.println("Activamos el hilo 1");
            synchronized(sub1){
                sub1.notify();
            }
            System.out.println("Esperamos a que el hilo 1 termine");
            sub1.join();
            System.out.println("Fin de todos los hilos incluyendome :)");
            
        }catch(InterruptedException ex){}
        
        TimerTask hiloDemonio = new Subprocesos.Subproceso3();
        new Timer(false).schedule(hiloDemonio, (long)500, (long)500);
    }
    
    private void imprimePersonas(Persona [] personas){
        for(Persona p: personas)
            System.out.println(p.toString());
    }
}
