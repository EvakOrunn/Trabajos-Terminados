package datos;

/**
 * Representa un artículo file la venta en un comercio cualquiera, que podrá ser
 * usado dentro de un Registro para grabar en un Archivo
 *
 */
import entradaDatos.Consola;
import excepciones.*;
import java.io.*;
import persistencia.*;
import principal.DestinosMenu;

public class Vuelo implements Grabable {

    private int codigo; // 4 bytes
    private int codigoDestino; // 4 bytes
    private String fecha; // 8 caracteres = 16 bytes
    private int hora; // 4 bytes
    private float tarifa; // 4 bytes
    private int cantidadDias; // 4 bytes

    private static int TAMARCHIVO = 100; // cantidad de registros que tendra el archivo
    private static int TAMAREG = 36; // cantidad de bytes que tendrá el registro

    public Vuelo() {
        this.codigo = 0;
        this.codigoDestino = 0;
        this.fecha = "";
        this.hora = 0;
        this.tarifa = 0.0f;
        this.cantidadDias = 0;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) throws NumeroNegativoExcepcion{
        if (codigo < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigo = codigo;
    }

    public int getCodigoDestino() {
        return codigoDestino;
    }

    public void setCodigoDestino(int codigoDestino) throws NumeroNegativoExcepcion{
        if (codigoDestino < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.codigoDestino = codigoDestino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) throws CadenaLargaExcepcion, CadenaVaciaExcepcion{
        if (fecha.length() > 8) {
            throw new CadenaLargaExcepcion();
        } else {
            if (fecha.length() < 0) {
                throw new CadenaVaciaExcepcion();
            }
        }
        this.fecha = fecha;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) throws HoraInvalidaExcepcion{
        if (hora < 0 || hora > 23) {
            throw new HoraInvalidaExcepcion();
        }
        this.hora = hora;
    }

    public float getTarifa() {
        return tarifa;
    }

    public void setTarifa(float tarifa) throws NumeroNegativoExcepcion{
        if (tarifa < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.tarifa = tarifa;
    }

    public int getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(int cantidadDias) throws NumeroNegativoExcepcion{
        if (cantidadDias < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.cantidadDias = cantidadDias;
    }
    
    private void cargarCodigoDestino(){
        int axDes;
        do {
            System.out.print("Codigo de Destino:");
            axDes = Consola.readInt();  
        } while (DestinosMenu.obtener(axDes) == null);
    }
    
    private void cargarFecha(){
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Fecha del Vuelo:");
                String axVu = Consola.readLine();
                setFecha(axVu.trim());
                flag = true;
            } catch (CadenaLargaExcepcion | CadenaVaciaExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }
    
    private void cargarHora(){
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Hora:");
                int axHo = Consola.readInt();
                setHora(axHo);
                flag = true;
            } catch (HoraInvalidaExcepcion | NumberFormatException e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }
    
    private void cargarTarifa(){
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Tarifa:");
                float axTar = Consola.readFloat();
                setTarifa(axTar);
                flag = true;
            } catch (NumeroNegativoExcepcion | NumberFormatException e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }
    
    private void cargarCantidadDias(){
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Cantidad de Dias:");
                int axCD = Consola.readInt();
                setCantidadDias(axCD);
                flag = true;
            } catch (NumeroNegativoExcepcion | NumberFormatException e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }
    
    /**
     * Calcula el tamaño en bytes del objeto, tal como será grabado. Pedido por
     * Grabable
     *
     * @return el tamaño en bytes del objeto
     */
    @Override
    public int tamRegistro() {
        return TAMAREG;
    }

    /**
     * Devuelve la cantidad de registros que debe haber en el archivo. Pedido por
     * Grabable
     *
     * @return cantidad de registros
     */
    @Override
    public int tamArchivo() {
        return TAMARCHIVO;
    }

    /**
     * Indica cómo grabar un objeto. Pedido por Grabable.
     *
     * @param file el archivo donde será grabado el objeto
     */
    @Override
    public void grabar(RandomAccessFile file) {
        try {
            file.writeInt(codigo);
            file.writeInt(codigoDestino);
            Registro.writeString(file, fecha, 8);
            file.writeInt(hora);
            file.writeFloat(tarifa);
            file.writeInt(cantidadDias);
        } catch (IOException e) {
            System.out.println("Error al grabar el vuelo: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Indica cómo leer un objeto. Pedido por Grabable.
     *
     * @param file el archivo donde se hará la lectura
     */
    @Override
    public void leer(RandomAccessFile file) {
        try {
            codigo = file.readInt();
            codigoDestino = file.readInt();
            fecha = Registro.readString(file, 8).trim();
            hora = file.readInt();
            tarifa = file.readFloat();
            cantidadDias = file.readInt();
        } catch (IOException e) {
            System.out.println("Error al leer el registro: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Redefinición del heredado desde Object. Considera que dos Articulos son
     * iguales si sus códigos lo son
     *
     * @param x el objeto contra el cual se compara
     * @return true si los códigos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object x) {
        if (x == null) {
            return false;
        }

        Vuelo file = (Vuelo) x;
        return (codigo == file.codigo);
    }

    /**
     * Redefinición del heredado desde Object. La convención es si equals() dice
     * que dos objetos son iguales, entonces hashCode() debería retornar el
     * mismo valor para ambos.
     *
     * @return el hashCode del Vuelo. Se eligió el código para ese valor.
     */
    @Override
    public int hashCode() {
        return codigo;
    }

    /**
     * Prints the attributes in a table format using String.format()
     */
    @Override
    public void mostrarRegistro() {
        // https://www.javatpoint.com/java-string-format
        // Los numeros entre el % y la mascara de tipo indican los caracteres que ocupa
        // el dato en la salida
        // En el caso del float, el que esta antes del punto indica la cantidad total de
        // digitos del float
        // y el que esta despues del punto, cuantos de esos digitos van a ser decimales
        System.out.println(String.format("| %6d | %9d | %8s | %6d | $%8.2f | %10d |", codigo, codigoDestino, fecha, hora,
                tarifa, cantidadDias));
    }
    
    @Override
    public void cargarDatos() {
        cargarCodigoDestino();
        cargarFecha();
        cargarHora();
        cargarTarifa();
        cargarCantidadDias();
    }
}