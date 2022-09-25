package datos;

import entradaDatos.Consola;
import java.io.*;
import persistencia.*;
import principal.InventosMenu;
import excepciones.*;
import principal.PersonalMenu;

/**
 *
 * @author luis
 */
public class Invento implements Grabable{

    private int codigoInvento;
    private String denominacion;
    private int codigoPersonal;
    private float monto;
    private int patente;
    
    private static int TAMAREG = 76;
    private static int TAMARCHIVO = 100;

    public Invento() {
        this.codigoInvento = -1;
        this.denominacion = "";
        this.codigoPersonal = -1;
        this.monto = -1;
        this.patente = -1;
    }

    public Invento(int codigoInvento, String denominacion, int codigoPersonal, float monto, int patente) {
        this.codigoInvento = codigoInvento;
        this.denominacion = denominacion;
        this.codigoPersonal = codigoPersonal;
        this.monto = monto;
        this.patente = patente;
    }

    public int getCodigoInvento() {
        return codigoInvento;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public int getCodigoPersonal() {
        return codigoPersonal;
    }

    public float getMonto() {
        return monto;
    }

    public int getPatente() {
        return patente;
    }
    
    public static int getTAMAREG() {
        return TAMAREG;
    }

    public static int getTAMARCHIVO() {
        return TAMARCHIVO;
    }

    public void setCodigoInvento(int codigoInvento) {
        this.codigoInvento = codigoInvento;
    }

    public void setDenominacion(String denominacion) throws CadenaLargaExcepcion, CadenaVaciaExcepcion{
        if (denominacion.length() < 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (denominacion.length() > 30) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.denominacion = denominacion;
    }

    public void setCodigoPersonal(int codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }

    public void setMonto(float monto) throws NumeroNegativoExcepcion {
        if (monto < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.monto = monto;
    }

    public void setPatente(int patente) throws NumeroNegativoExcepcion {
        if (patente < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.patente = patente;
    }
    
    private void cargarDenominacion() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Denominacion.");
                String axDenom = Consola.readLine();
                setDenominacion(axDenom.trim());
                flag = true;
            } catch (CadenaLargaExcepcion | CadenaVaciaExcepcion e) {
                System.out.println("Error:" + e.getMessage());
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
                System.out.println("Error:" + e.getMessage());
            }
        }
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
                System.out.println("Error:" + e.getMessage());
            }
        }
    }
    
    private void cargarCodigoPersonal() {
        int axCod;
        do {            
            System.out.print("Codigo Personal:");
            axCod = Consola.readInt();
        } while (PersonalMenu.obtener(axCod) == null);
        this.codigoPersonal = axCod;
    }

    public void modificarDatosInvento() {
        int op = -1;
        
        do {
            menuModificacion();
            op = Consola.readInt();
            
            switch (op) {
                case 1:
                    cargarCodigoPersonal();
                    break;
                case 2:
                    cargarDenominacion();
                    break;
                case 3:
                    cargarMonto();
                    break;
                case 4:
                    cargarPatente();
                    break;
                case 0:
                    System.out.println("Menu de modificacioon finalizado");
                    break;
                default:
                    System.out.println("Operacion Invalida");
                    break;
            }
        } while (op != 0);
    }

    private void menuModificacion() {
        System.out.println("---------------------------------------------");
        System.out.println("          MENU MODIFICACION INVENTO          ");
        System.out.println("---------------------------------------------");
        System.out.println("- 1.Codigo Personal                         -");
        System.out.println("- 2.Denominacion                            -");
        System.out.println("- 3.Monto                                   -");
        System.out.println("- 4.Patente                                 -");
        System.out.println("- 0.Salir                                   -");
        System.out.println("---------------------------------------------");
        System.out.println("--->");
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
            Registro.writeString(a, this.denominacion, 30);
            a.writeInt(this.codigoPersonal);
            a.writeFloat(this.monto);
            a.writeInt(this.patente);
        } catch (IOException e) {
            System.out.println("Error al grabar los datos del Invento:" + e.getMessage());
        }
    }

    @Override
    public void leer(RandomAccessFile a) {
            try {
                this.codigoInvento = a.readInt();
                this.denominacion = Registro.readString(a, 30).trim();
                this.codigoPersonal = a.readInt();
                this.monto = a.readFloat();
                this.patente = a.readInt();
            } catch(IOException e) {
                System.out.println("Error al leer los datos del Invento");
            }
    }

    @Override
    public void mostrarRegistro() {
        System.out.println(String.format("| %6d | %9d | %30s | $%8.2f | %10d |", codigoInvento, codigoPersonal, denominacion, monto, patente));    
    }

    @Override
    public void cargarDatos() {
        cargarCodigoPersonal();
        cargarDenominacion();
        cargarMonto();
        cargarPatente();
    }
    
}
