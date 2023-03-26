/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacio;

import Logica.DocumentsLogica;
import Singleton.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author carlo
 */
public class Menus {

    private static DocumentsLogica logica = new DocumentsLogica();
    private static MongoClient connection = MongoConnection.getInstance();
    private static MongoDatabase repository = null;
    private static File rem = null;
    private static String ruta = System.getProperty("user.home");
    private static String userFolder = "getRepo1";
    private static String repositoryName = "home_user_getrepo2";
    private static String idRemot = null;

    public static int menuPrincipal() {

        try {
            Scanner in = new Scanner(System.in);
            System.out.print("""
                         || MENU ||
                         1. CREATE
                         2. DROP
                         3. PUSH
                         4. COMPARE
                         5. CLONE
                         6. AYUDA
                         7. SALIR
                         """);
            return in.nextInt();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static void menuCreate() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Indicar ruta: ");
            String ruta = in.nextLine();
            logica.createRepository(ruta);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void menuDrop() {
        Scanner in = new Scanner(System.in);
        System.out.println("Indicar ruta: ");
        String ruta = in.nextLine();

        logica.dropRepository(ruta);

    }

    public static void menuPush() {
        Scanner in = new Scanner(System.in);
        System.out.println("Indicar ruta: ");
        String ruta = in.nextLine();
        System.out.println("Indicar ruta: ");
        String force = in.nextLine();
        logica.pushFile(ruta, force);

    }

    public static void menuPull() {

    }

    public static void menuCompare() {
        System.out.println("");
        Scanner in = new Scanner(System.in);
        System.out.println("Indicar ruta: ");
        String ruta = in.nextLine();
        System.out.println("Indicar ruta: ");
        String force = in.nextLine();
        logica.pushFile(ruta, force);
    }

    public static void menuClone() {

    }

    public static int menuAyuda() {
        Scanner in = new Scanner(System.in);
        System.out.println("|| MENU DE AYUDA ||");
        System.out.println("1. Documentación de la opción CREATE");
        System.out.println("2. Documentación de la opción DROP");
        System.out.println("3. Documentación de la opción PUSH");
        System.out.println("4. Documentación de la opción COMPARE");
        System.out.println("5. Documentación de la opción CLONE");
        System.out.println("6. Volver al menú principal");
        System.out.println("Selecciona una opción: ");
        return in.nextInt();

    }
}
