package datos;

/**
 * Representa un artículo file la venta en un comercio cualquiera, que podrá ser
 * usado dentro de un Registro para grabar en un Archivo
 *
 */
import entradaDatos.Consola;
import java.io.*;
import persistencia.*;

public class Destino implements Grabable {

    private int codigo; // 4 bytes
    private String descripcion; // 50 caracteres = 100 bytes

    private static int TAMARCHIVO = 100; // cantidad de registros que tendra el archivo
    private static int TAMAREG = 104; // cantidad de bytes que tendrá el registro

    public Destino() {
        this.codigo = 0;
        this.descripcion = "";
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
            Registro.writeString(file, descripcion, 50);
        } catch (IOException e) {
            System.out.println("Error al grabar el registro: " + e.getMessage());
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
            descripcion = Registro.readString(file, 50).trim();
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

        Destino file = (Destino) x;
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
        System.out.println(String.format("| %6d | %50s |", codigo, descripcion));
    }

    @Override
    public void cargarDatos() {
        do {
            System.out.print("Descripcion--> ");
            descripcion = Consola.readLine();
        } while (descripcion.length() == 0);
    }
}