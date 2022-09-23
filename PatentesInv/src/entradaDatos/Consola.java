package entradaDatos;

import java.util.Scanner;

/**
 * Clase para facilitar operaciones de carga por teclado en consola estándar
 */
public class Consola {
    /*
     * Todos los métodos de esta clase son estáticos, y por lo tanto pueden ser
     * invocados sin tener que
     * crear objetos de la clase. Es suficiente con nombrar la clase al invocar el
     * método: int x = Consola.readInt();
     */

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Reads a line from the console until the user presses ENTER. The line is
     * trimmed and returned as a String.
     * 
     * @return the line read from the console
     */
    public static String readLine() {
        return scanner.nextLine();
    }

    /**
     * Reads an int from the console until the user presses ENTER. The int is
     * trimmed and returned as a int. If the line read is not a valid int, the
     * method will keep asking until a valid int is read.
     * 
     * @return the int read from the console
     */
    public static int readInt() {
        int x = 0;
        while (true) {
            try {
                x = Integer.parseInt(readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return x;
    }

    /**
     * Reads a double from the console until the user presses ENTER. The double
     * is trimmed and returned as a double. If the line read is not a valid
     * double, the method will keep asking until a valid double is read.
     * 
     * @return the double read from the console
     */
    public static double readDouble() {
        double x = 0;
        while (true) {
            try {
                x = Double.parseDouble(readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return x;
    }

    /**
     * Reads a float from the console until the user presses ENTER. The float
     * is trimmed and returned as a float. If the line read is not a valid
     * float, the method will keep asking until a valid float is read.
     * 
     * @return the float read from the console
     */
    public static float readFloat() {
        float x = 0;
        while (true) {
            try {
                x = Float.parseFloat(readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return x;
    }

    /**
     * Displays a message on the console asking the user if he/she wants to
     * continue. The user must press 's' or 'n'. If the user presses any other
     * key, a loop is repeated until the user presses 's' or 'n'.
     *
     * @return true if the user wants to continue, false otherwise
     */
    public static boolean deseaContinuar() {
        boolean continuar = false;
        char respuesta = ' ';
        do {
            System.out.print("¿Desea continuar? (s/n)> ");
            respuesta = Character.toLowerCase(readLine().charAt(0));
        } while (respuesta != 's' && respuesta != 'n');
        continuar = respuesta == 's';
        return continuar;
    }

    /**
     * Shows a message on the console and waits for the user to press ENTER.
     */
    public static void pausa() {
        System.out.println("Pulse ENTER para continuar...");
        readLine();
    }
    public static String repeat(String car, int tam){
        String s="";
        for(int i=0;i<tam;i++){
            s+=car;
        }
        return s;
    }
}