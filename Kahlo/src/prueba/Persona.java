/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

/**
 *
 * @author JCORREA
 */
public class Persona implements Comparable{

    public String nombre;
    public int edad;
    public float altura; 
    public Sexo sexo;

    public Persona(String nombre, int edad, float altura, Sexo sexo) {
        this.nombre = nombre;
        this.edad = edad;
        this.altura = altura;
        this.sexo = sexo;
    }
    
    
    //ENUMS
    //SORT
    //BINARY SEARCH
    @Override
    public int compareTo(Object o) {
        
        if(!this.sexo.equals(((Persona)o).sexo))
            if(this.sexo.equals(Sexo.MASCULINO))
                return 1;
            else 
                return -1;
        
        if(this.edad < ((Persona)o).edad)
            return -1;
        else if(this.edad > ((Persona)o).edad)
            return 1;
        else 
            return 0;
    }
    
    public enum Sexo{
        MASCULINO,
        FEMENINO
    }
    
    @Override
    public String toString(){
        return ("Nombre: " + this.nombre + " - Edad: " + this.edad + " - Altura: " + this.altura + " - Sexo: " + this.sexo);
    }
}
