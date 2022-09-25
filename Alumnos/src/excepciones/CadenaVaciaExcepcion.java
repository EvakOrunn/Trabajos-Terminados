/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *
 * @author Alumnos
 */
public class CadenaVaciaExcepcion extends Exception{

    public CadenaVaciaExcepcion() {
        super("La cadena esta vacia");
    }

    public CadenaVaciaExcepcion(String message) {
        super(message);
    }
    
}
