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
public class Mujer extends Persona{

    public Mujer(String nombre, int edad, float altura, Sexo sexo) {
        super(nombre, edad, altura, sexo);
        assert sexo == Sexo.FEMENINO: "El Sexo no es Femenino"; 
    }
    
}
