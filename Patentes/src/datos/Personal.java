package datos;

import entradaDatos.Consola;
import java.io.*;
import persistencia.Grabable;
import excepciones.*;
import persistencia.Registro;

/**
 *
 * @author Luis
 */
public class Personal implements Grabable{
    
    private int codigoPersonal;
    private String apellidoNombre;

    private static int TAMAREG = 44;
    private static int TAMARCHIVO = 100;
    
    public Personal() {
        this.codigoPersonal = -1;
        this.apellidoNombre = "";
    }

    public Personal(int codigoPersonal, String apellidoNombre) {
        this.codigoPersonal = codigoPersonal;
        this.apellidoNombre = apellidoNombre;
    }

    public int getCodigoPersonal() {
        return codigoPersonal;
    }

    public void setCodigoPersonal(int codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }

    public String getApellidoNombre() {
        return apellidoNombre;
    }

    public void setApellidoNombre(String apellidoNombre) throws CadenaLargaExcepcion, CadenaVaciaExcepcion {
        if (apellidoNombre.length() < 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (apellidoNombre.length() > 20) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.apellidoNombre = apellidoNombre;
    }
    
    public static int getTAMAREG() {
        return TAMAREG;
    }

    public static int getTAMARCHIVO() {
        return TAMARCHIVO;
    }
    
    private void cargarApellidoNombre() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Apellido y Nombre:");
                String axAp = Consola.readLine();
                setApellidoNombre(axAp);
                flag = true;
            } catch (CadenaLargaExcepcion | CadenaVaciaExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    @Override
    public int tamRegistro() {
        return TAMAREG;
    }

    @Override
    public int tamArchivo() {
        return TAMARCHIVO;
    }

    @Override
    public void grabar(RandomAccessFile a) {
        try {
            a.writeInt(codigoPersonal);
            Registro.writeString(a, apellidoNombre, 20);
        } catch (IOException e) {
            System.out.println("Error al grabar el Personal: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void leer(RandomAccessFile a) {
        try {
            codigoPersonal = a.readInt();
            apellidoNombre = Registro.readString(a, 20).trim();
        } catch (IOException e) {
            System.out.println("Error al leer el Personal: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void mostrarRegistro() {
        System.out.println(String.format("| %6d | %50s |", codigoPersonal, apellidoNombre));
    }

    @Override
    public void cargarDatos() {
        cargarApellidoNombre();
    }
    
}
