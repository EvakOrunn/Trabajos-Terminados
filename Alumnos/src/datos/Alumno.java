package datos;

import entradaDatos.Consola;
import java.io.*;
import persistencia.*;
import excepciones.*;

public class Alumno implements Grabable {

    private int dni;
    private String nombreAlumno;
    private float saldo;
    private char estadoCuenta;
    private int codigoCurso;

    private static int TAMAREG = 114;
    private static int TAMARCHIVO = 100;

    public Alumno() {
        this.dni = -1;
        this.nombreAlumno = "";
        this.saldo = 0;
        this.estadoCuenta = 'A';
        this.codigoCurso = -1;
    }

    public Alumno(int dni, String nombreAlumno, float saldo, char estadoCuenta, int codigoCurso) {
        this.dni = dni;
        this.nombreAlumno = nombreAlumno;
        this.saldo = saldo;
        this.estadoCuenta = estadoCuenta;
        this.codigoCurso = codigoCurso;
    }

    public void setDNI(int dni) {
        this.dni = dni;
    }

    public void setNombreAlumno(String nombreAlumno) throws CadenaLargaExcepcion, CadenaVaciaExcepcion{
        if (nombreAlumno.trim().length() < 0) {
            throw new CadenaVaciaExcepcion();
        } else {
            if (nombreAlumno.trim().length() > 50) {
                throw new CadenaLargaExcepcion();
            }
        }
        this.nombreAlumno = nombreAlumno;
    }

    public void setSaldo(float saldo) throws NumeroNegativoExcepcion{
        if (saldo < 0) {
            throw new NumeroNegativoExcepcion();
        }
        this.saldo = saldo;
    }

    public void setEstadoCuenta(char estadoCuenta) {
        
        this.estadoCuenta = estadoCuenta;
    }

    public void setCodigoCurso(int codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public int getDNI() {
        return dni;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public float getSaldo() {
        return saldo;
    }

    public char getEstadoCuenta() {
        return estadoCuenta;
    }

    public int getCodigoCurso() {
        return codigoCurso;
    }

    private void cargarNombreAlumno() {
        boolean flag = false;
        while (!flag) {
            try {
                System.out.print("Apellido y Nombre:");
                String axNom = Consola.readLine();
                setNombreAlumno(axNom);
                flag = true;
            }catch (CadenaLargaExcepcion | CadenaVaciaExcepcion e) {
                System.out.println("Error al setear la razon social del Alumno:" + e.getMessage());
            }
        }
    }
    
    private void cargarSaldo() {
        boolean flag = false;
        while (!flag) {            
            try {
                System.out.print("Saldo:");
                float axPrec = Consola.readFloat();
                setSaldo(axPrec);
                flag = true;
            } catch (NumeroNegativoExcepcion | NumberFormatException e) {
                System.out.println("Error al setear el saldo del Alumno:" + e.getMessage());
            }
        }
    }
    
    private void actualizarEstadoCuenta() {
        if (this.saldo >= 0) {
            this.estadoCuenta = 'A';
            System.out.println("Estado Cuenta:" + this.estadoCuenta);
        } else {
            if (this.saldo < 0) {
                this.estadoCuenta = 'B';
                System.out.println("Estado Cuenta:" + this.estadoCuenta);
            }
        }
    }
    
    public void modificarDatos() {
        int op = -1;
        System.out.println("Datos del Alumno:\n" + toString());
        do {            
            menuModificacion();
            op = Consola.readInt();
            
            switch(op) {
                case 1:
                    cargarNombreAlumno();
                    break;
                case 2:
                    cargarSaldo();
                    actualizarEstadoCuenta();
                    break;
            }
        } while (op != 0);
    }
    
    private void menuModificacion() {
        System.out.println(Consola.repeat("-", 37));
        System.out.println("-" + Consola.repeat(" ", 9) + "MENU MODIFICACION" + Consola.repeat(" ", 9) + "-");
        System.out.println(Consola.repeat("-", 37));
        System.out.println("-1.Apellido y Nombre" + Consola.repeat(" ", 16) + "-");
        System.out.println("-2.Saldo" + Consola.repeat(" ", 28) + "-");
        System.out.println("-3.Actualizar Est. Cuenta" + Consola.repeat(" ", 11) + "-");
        System.out.println(Consola.repeat("-", 37));
        System.out.print("Operacion-->");
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DNI:").append(this.dni).append("\n");
        sb.append("Apellido y Nombre:").append(this.nombreAlumno).append("\n");
        sb.append("Saldo:").append(this.saldo).append("\n");
        sb.append("Est. de Cuenta:").append(this.estadoCuenta).append("\n");
        sb.append("Codigo de Curso:").append(this.codigoCurso).append("\n");
        return sb.toString();
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
     * @param file el archivo donde será grabado el objeto
     */
    @Override
    public void grabar(RandomAccessFile file) {
        try {
            file.writeInt(dni);
            file.writeInt(codigoCurso);
            Registro.writeString(file, nombreAlumno, 50);
            file.writeFloat(saldo);
            file.writeChar(estadoCuenta);
        } catch (IOException e) {
            System.out.println("Error al grabar el Alumno: " + e.getMessage());
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
            dni = file.readInt();
            codigoCurso = file.readInt();
            nombreAlumno = Registro.readString(file, 50).trim();
            saldo = file.readFloat();
            estadoCuenta = file.readChar();
        } catch (IOException e) {
            System.out.println("Error al leer el Alumno: " + e.getMessage());
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

        Alumno file = (Alumno) x;
        return (dni == file.dni);
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
        return dni;
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
        System.out.println(String.format("| %6d | %9d | %50s | %2c | $%8.2f |", this.dni, this.codigoCurso, this.nombreAlumno, this.estadoCuenta,this.saldo));
    }

    @Override
    public void cargarDatos() {
        
    }

}
