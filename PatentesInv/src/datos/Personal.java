package datos;

import entradaDatos.Consola;
import java.io.*;
import persistencia.*;
import excepciones.*;

/**
 * Personal
 */
public class Personal implements Grabable{

    private int codigoPersonal;
    private String apellidoNombre;

    private static int TAMAREG = 44;
    private static int TAMARCHIVO = 100;

    public void setCodigoPersonal(int codigoPersonal) throws NumeroNegativoExcepcion {
        if (codigoPersonal < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigoPersonal = codigoPersonal;
    }

    public void setApellidoNombre(String apellidoNombre) throws CadenaLargaExcepcion, CadenaVaciaExcepcion {
        if (apellidoNombre.trim().length() < 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (apellidoNombre.trim().length() > 20) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.apellidoNombre = apellidoNombre;
    }

    public int getCodigoPersonal() {
        return this.codigoPersonal;
    }

    public String getApellidoNombre() {
        return this.apellidoNombre;
    }

    private void cargarApellidoNombre() {
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print("Apellido y Nombre:");
                String axApeNom = Consola.readLine();
                setApellidoNombre(axApeNom);
                flag = true;
            } catch (CadenaVaciaExcepcion | CadenaLargaExcepcion e) {
                System.out.println("Error al setear el Codigo Personal:" + e.getMessage());
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
            a.writeInt(this.codigoPersonal);
            Registro.writeString(a, this.apellidoNombre.trim(), 40);
        } catch (IOException e) {
            System.out.println("Error al grabar los datos de Personal:" + e.getMessage());
        }
    }

    @Override
    public void leer(RandomAccessFile a) {
        try {
            this.codigoPersonal = a.readInt();
            this.apellidoNombre = Registro.readString(a, 20);
        } catch (IOException e) {
            System.out.println("Error al leer los datos de Personal" + e.getMessage());
        }
    }

    @Override
    public void mostrarRegistro() {
        System.out.println(String.format("| %6d | %20 |", this.codigoPersonal, this.apellidoNombre));
    }

    @Override
    public void cargarDatos() {
        cargarApellidoNombre();
    }
    
}