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
public class HoraInvalidaExcepcion extends Exception{

    public HoraInvalidaExcepcion() {
        super("Hora invalida");
    }
    
    public HoraInvalidaExcepcion(String message){
        super(message);
    }
    
}
