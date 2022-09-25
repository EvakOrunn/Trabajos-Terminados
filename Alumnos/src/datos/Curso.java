package datos;

import entradaDatos.Consola;
import java.io.*;
import persistencia.*;
import excepciones.*;

public class Curso implements Grabable {

    private int codigoCurso;
    private String nombreCurso;

    private static int TAMAREG = 64;
    private static int TAMARCHIVO = 100;

    public Curso() {
        this.codigoCurso = -1;
        this.nombreCurso = "";
    }

    public Curso(int codigoCurso, String nombreCurso) {
        this.codigoCurso = codigoCurso;
        this.nombreCurso = nombreCurso;
    }

    public void setCodigoCurso(int codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public void setNombreCurso(String nombreCurso) throws CadenaLargaExcepcion, CadenaVaciaExcepcion {
        if (nombreCurso.trim().length() == 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (nombreCurso.trim().length() > 30) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.nombreCurso = nombreCurso;
    }

    public int getCodigoCurso() {
        return codigoCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
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
     * Devuelve la cantidad de registros que debe haber en el archivo. Pedido
     * por Grabable
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
     * @param a el archivo donde será grabado el objeto
     */
    @Override
    public void grabar(RandomAccessFile a) {
        try {
            a.writeInt(codigoCurso);
            Registro.writeString(a, nombreCurso, 30);
        } catch (IOException e) {
            System.out.println("Error al grabar el registro Curso: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Indica cómo leer un objeto. Pedido por Grabable.
     *
     * @param a el archivo donde se hará la lectura
     */
    @Override
    public void leer(RandomAccessFile a) {
        try {
            codigoCurso = a.readInt();
            nombreCurso = Registro.readString(a, 30).trim();
        } catch (IOException e) {
            System.out.println("Error al leer el registro: Curso " + e.getMessage());
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

        Curso file = (Curso) x;
        return (codigoCurso == file.codigoCurso);
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
        return codigoCurso;
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
        System.out.println(String.format("| %6d | %29s |", codigoCurso, nombreCurso));
    }

    private void cargarNombreCurso() {
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print("Nombre del Curso:");
                String axNom = Consola.readLine();
                setNombreCurso(axNom);
                flag = true;
            } catch (CadenaLargaExcepcion | CadenaVaciaExcepcion e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    @Override
    public void cargarDatos() {
        cargarNombreCurso();
    }

}
