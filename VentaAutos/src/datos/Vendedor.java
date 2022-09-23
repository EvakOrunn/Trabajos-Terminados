package datos;

import entradaDatos.Consola;
import java.io.*;
import persistencia.*;
import excepciones.*;

/**
 *
 * @author Alumnos
 */
public class Vendedor implements Grabable{
    
    private int codigoVendedor;
    private String apellidoNombre;
    private long numeroTelefono;
    
    private static int TAMAREG = 72;
    private static int TAMARCHIVO = 100;

    public Vendedor() {
        this.codigoVendedor = -1;
        this.apellidoNombre = "";
        this.numeroTelefono = -1;
    }

    public Vendedor(int codigoVendedor, String apellidoNombre, long numeroTelefono) {
        this.codigoVendedor = codigoVendedor;
        this.apellidoNombre = apellidoNombre;
        this.numeroTelefono = numeroTelefono;
    }

    public void setCodigoVendedor(int codigoVendedor) throws NumeroNegativoExcepcion {
        if (codigoVendedor < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigoVendedor = codigoVendedor;
    }

    public void setApellidoNombre(String apellidoNombre) throws CadenaLargaExcepcion, CadenaVaciaExcepcion {
        if (apellidoNombre.trim().length() < 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (apellidoNombre.trim().length() > 30) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.apellidoNombre = apellidoNombre;
    }

    public void setNumeroTelefono(long numeroTelefono) throws NumeroNegativoExcepcion {
        if (numeroTelefono < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.numeroTelefono = numeroTelefono;
    }

    public int getCodigoVendedor() {
        return codigoVendedor;
    }

    public String getApellidoNombre() {
        return apellidoNombre;
    }

    public long getNumeroTelefono() {
        return numeroTelefono;
    }
    
    private void cargarCodigoVendedor() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Codigo de Vendedor:");
                int axCodV = Consola.readInt();
                setCodigoVendedor(axCodV);
                flag = true;
            } catch (NumeroNegativoExcepcion | NumberFormatException e) {
                System.out.println("Error al setear el Codigo de Vendedor:" + e.getMessage());
            }
        }
    }
    
    private void cargarApellidoNombre() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Apellido y Nombre:");
                String axApe = Consola.readLine();
                setApellidoNombre(axApe);
                flag = true;
            } catch (CadenaLargaExcepcion | CadenaVaciaExcepcion e) {
                System.out.println("Error al setear el Apellido y Nombre:" + e.getMessage());
            }
        }
    }
    
    private void cargarNumeroTelefono() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Numero de Telefono:");
                long axNumT = Consola.readInt();
                setNumeroTelefono(axNumT);
                flag = true;
            } catch (NumeroNegativoExcepcion e) {
                System.out.println("Error al setear el Numero de Telefono" + e.getMessage());
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
            a.writeInt(this.codigoVendedor);
            Registro.writeString(a, this.apellidoNombre.trim(), 30);
            a.writeLong(this.numeroTelefono);
        } catch (IOException e) {
            System.out.println("Error al grabar los datos del Vendedor:" + e.getMessage());
        }
    }

    @Override
    public void leer(RandomAccessFile a) {
        try {
            this.codigoVendedor = a.readInt();
            this.apellidoNombre = Registro.readString(a, 30).trim();
            this.numeroTelefono = a.readLong();
        } catch (IOException e) {
            System.out.println("Error al leer los datos del Vendedor:" + e.getMessage());
        }
    }

    @Override
    public void mostrarRegistro() {
        System.out.println(String.format("| %6d | %31s | %15d |", this.codigoVendedor, this.apellidoNombre, this.numeroTelefono));
    }

    @Override
    public void cargarDatos() {
        cargarCodigoVendedor();
        cargarApellidoNombre();
        cargarNumeroTelefono();
    }
    
}
