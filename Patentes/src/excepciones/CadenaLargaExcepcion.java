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
public class CadenaLargaExcepcion extends Exception{

    public CadenaLargaExcepcion() {
        super("La cadena supero el limite de caracteres");
    }

    public CadenaLargaExcepcion(String message) {
        super(message);
    }
    
    
    
    
}
