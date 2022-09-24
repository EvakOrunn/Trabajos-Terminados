package datos;

import entradaDatos.Consola;
import excepciones.*;
import java.io.*;
import persistencia.*;
import principal.*;

public class Invento implements Grabable {
    
    private int codigoInvento;
    private int codigoInventor;
    private int patente;
    private float monto;
    private String denominacion;

    private static int TAMAREG = 76;
    private static int TAMARCHIVO = 100;

    public Invento() {
        this.codigoInvento = -1;
        this.codigoInventor = -1;
        this.patente = -1;
        this.monto = -1;
        this.denominacion = "";
    }

    public Invento(int codigoInvento, int codigoInventor, int patente, float monto, String denominacion) {
        this.codigoInvento = codigoInvento;
        this.codigoInventor = codigoInventor;
        this.patente = patente;
        this.monto = monto;
        this.denominacion = denominacion;
    }

    public int getCodigoInvento() {
        return codigoInvento;
    }

    public int getCodigoInventor() {
        return codigoInventor;
    }

    public int getPatente() {
        return patente;
    }

    public float getMonto() {
        return monto;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setCodigoInvento(int codigoInvento) throws NumeroNegativoExcepcion {
        if (codigoInvento < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigoInvento = codigoInvento;
    }

    public void setCodigoInventor(int codigoInventor) throws NumeroNegativoExcepcion {
        if (codigoInventor < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigoInventor = codigoInventor;
    }

    public void setPatente(int patente) throws NumeroNegativoExcepcion {
        if (patente < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.patente = patente;
    }

    public void setMonto(float monto) throws NumeroNegativoExcepcion {
        if (monto < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.monto = monto;
    }

    public void setDenominacion(String denominacion) throws CadenaLargaExcepcion, CadenaVaciaExcepcion {
        if (denominacion.trim().length() < 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (denominacion.trim().length() > 30) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.denominacion = denominacion;
    }
    
    private void cargarCodigoInventor() {
        int axDes;
        do {
            System.out.print("Codigo de Destino:");
            axDes = Consola.readInt();  
        } while (DestinosMenu.obtener(axDes) == null);
    }
    
    private void cargarPatente() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Patente:");
                int axPat = Consola.readInt();
                setPatente(axPat);
                flag = true;
            } catch (NumeroNegativoExcepcion | NumberFormatException e) {
                System.out.println("Error al setear la Patente:" + e.getMessage());
            }
        }
    }
    
    private void cargarMonto() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Monto:");
                float axMon = Consola.readFloat();
                setMonto(axMon);
                flag = true;
            } catch (NumeroNegativoExcepcion | NumberFormatException e) {
                System.out.println("Error al setear el Monto:" + e.getMessage());
            }
        }
    }
    
    private void cargarDenominacion() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Denominacion:");
                String axDenom = Consola.readLine();
                setDenominacion(axDenom);
                flag = true;
            } catch (CadenaLargaExcepcion | CadenaVaciaExcepcion e) {
                System.out.println("Error al setear la Denominacion:" + e.getMessage());
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
            a.writeInt(this.codigoInvento);
            a.writeInt(this.codigoInventor);
            a.writeInt(this.patente);
            a.writeFloat(this.monto);
            Registro.writeString(a, this.denominacion.trim(), 30);
        } catch (IOException e) {
            System.out.println("Error al grabar los datos de Invento:" + e.getMessage());
        }
    }

    @Override
    public void leer(RandomAccessFile a) {
        try {
            this.codigoInvento = a.readInt();
            this.codigoInventor = a.readInt();
            this.patente = a.readInt();
            this.monto = a.readFloat();
            this.denominacion = Registro.readString(a, 30).trim();
        } catch (IOException e) {
            System.out.println("Error al leer los datos de Invento:" + e.getMessage());
        }
    }

    @Override
    public void mostrarRegistro() {
        System.out.println(String.format("| %14d | %13d | %9d | %7.2f | %30s |", this.codigoInventor, this.codigoInvento, this.patente, this.monto, this.denominacion));
    }

    @Override
    public void cargarDatos() {
        cargarCodigoInventor();
        cargarPatente();
        cargarMonto();
        cargarDenominacion();
    }
    
}
