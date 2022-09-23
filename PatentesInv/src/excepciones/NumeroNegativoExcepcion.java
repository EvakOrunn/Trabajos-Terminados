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
public class NumeroNegativoExcepcion extends Exception{
    
    public NumeroNegativoExcepcion() {
        super("Numero negativo ingresado");
    }
    
    public NumeroNegativoExcepcion(String message){
        super(message);
    }
    
}
