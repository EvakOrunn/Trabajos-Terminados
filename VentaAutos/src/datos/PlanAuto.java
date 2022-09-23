package datos;

import entradaDatos.Consola;
import excepciones.*;
import java.io.*;
import persistencia.*;
import principal.*;

/**
 *
 * @author Alumnos
 */
public class PlanAuto implements Grabable {

    private int codigoSuscripcion;
    private int codigoVend;
    private int grupo;
    private int orden;
    private String plan;

    private static int TAMAREG = 28;
    private static int TAMARCHIVO = 100;

    public PlanAuto() {
        this.codigoSuscripcion = -1;
        this.codigoVend = -1;
        this.grupo = -1;
        this.orden = -1;
        this.plan = "";
    }

    public PlanAuto(int codigoSub, int codigoVend, int grupo, int orden, String plan) {
        this.codigoSuscripcion = codigoSub;
        this.codigoVend = codigoVend;
        this.grupo = grupo;
        this.orden = orden;
        this.plan = plan;
    }

    public void setCodigoSuscripcion(int codigoSub) throws NumeroNegativoExcepcion {
        if (codigoSub < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigoSuscripcion = codigoSub;
    }

    public void setCodigoVend(int codigoVend) throws NumeroNegativoExcepcion {
        if (codigoVend < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigoVend = codigoVend;
    }

    public void setGrupo(int grupo) throws NumeroNegativoExcepcion {
        if (grupo < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.grupo = grupo;
    }

    public void setOrden(int orden) throws NumeroNegativoExcepcion {
        if (orden < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.orden = orden;
    }

    public void setPlan(String plan) throws CadenaLargaExcepcion, CadenaVaciaExcepcion {
        if (plan.trim().length() < 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (plan.trim().length() > 6) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.plan = plan;
    }

    public int getCodigoSuscripcion() {
        return codigoSuscripcion;
    }

    public int getCodigoVend() {
        return codigoVend;
    }

    public int getGrupo() {
        return grupo;
    }

    public int getOrden() {
        return orden;
    }

    public String getPlan() {
        return plan;
    }
    
    private void cargarCodigoSuscripcion() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Codigo de Suscripcion:");
                int axCod = Consola.readInt();
                setCodigoSuscripcion(axCod);
                flag = true;
            } catch (NumberFormatException | NumeroNegativoExcepcion e) {
                
            }
        }
    }
    
    private void cargarCodigoVendedor() {
        int axCod;
        do {            
            System.out.print("Codigo Vendedor:");
            axCod = Consola.readInt();
        } while (VendedorMenu.obtener(axCod) == null);
        this.codigoVend = axCod;
    }
    
    private void cargarGrupo() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Grupo:");
                int axGrup = Consola.readInt();
                setGrupo(axGrup);
                flag = true;
            } catch (NumeroNegativoExcepcion e) {
                System.out.println("Error el setear el grupo:" + e.getMessage());
            }
        }
    }
    
    private void cargarOrden() {
        boolean flag = true;
        while (!flag) {            
            try {
                System.out.print("Orden:");
                int axOr = Consola.readInt();
                setOrden(axOr);
                flag = true;
            } catch (NumeroNegativoExcepcion e) {
                System.out.println("Error al setear el Orden:" + e.getMessage());
            }
        }
    }
    
    private void cargarPlan() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Plan:");
                String axPl = Consola.readLine();
                setPlan(axPl);
                flag = true;
            } catch (CadenaLargaExcepcion | CadenaVaciaExcepcion e) {
                System.out.println("Error al setear el Plan:" + e.getMessage());
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
            a.writeInt(this.codigoSuscripcion);
            a.writeInt(this.codigoVend);
            a.writeInt(this.grupo);
            a.writeInt(this.orden);
            Registro.writeString(a, this.plan.trim(), 6);
        } catch (IOException e) {
            System.out.println("Error al grabar los datos de Plan Auto:" + e.getMessage());
        }
    }

    @Override
    public void leer(RandomAccessFile a) {
        try {
            this.codigoSuscripcion = a.readInt();
            this.codigoVend = a.readInt();
            this.grupo = a.readInt();
            this.orden = a.readInt();
            this.plan = Registro.readString(a, 6).trim();
        } catch (IOException e) {
            System.out.println("Error al leer los datos del Plan Auto");
        }
    }

    @Override
    public void mostrarRegistro() {
        System.out.println(String.format("| %9d | %10d | %5d | %5d | %6s |", this.codigoSuscripcion, this.codigoVend, this.grupo, this.orden, this.plan));
    }

    @Override
    public void cargarDatos() {
        cargarCodigoVendedor();
        cargarGrupo();
        cargarOrden();
        cargarPlan();
    }

}
