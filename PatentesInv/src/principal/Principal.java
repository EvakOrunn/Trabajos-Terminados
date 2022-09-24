/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;

import entradaDatos.Consola;

/**
 *
 * @author mayocca
 */
public class Principal {

    public static void main(String[] args) {
        int op;

        // Inicializar los archivos
        InventorMenu.inicializarArchivo();
        InventoMenu.inicializarArchivo();

        do {
            System.out.println(Consola.repeat("-", 34));
            System.out.println("-" + Consola.repeat(" ", 9) + "MENU PRINCIPAL" + Consola.repeat(" ", 9) + "-");
            System.out.println(Consola.repeat("-", 34));
            System.out.println("1. Menu de Inventores");
            System.out.println("2. Menu de Inventos");
            System.out.println("0. Salir");
            System.out.println(Consola.repeat("-", 34));
            System.out.print("--> ");
            op = Consola.readInt();
            switch (op) {
                case 1:
                    InventorMenu.menu();
                    break;
                case 2:
                    InventoMenu.menu();
                    break;
            }
        } while (op != 0);
    }
}
