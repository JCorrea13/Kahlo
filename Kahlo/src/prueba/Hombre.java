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
public class Hombre extends Persona{

    public Hombre(String nombre, int edad, float altura, Sexo sexo) {
        super(nombre, edad, altura, sexo);
        assert sexo == Sexo.MASCULINO : "El Sexo no es Masculio";
    }
    
    
}
