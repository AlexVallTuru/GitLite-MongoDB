/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacio;

import Logica.DocumentsLogica;
import Singleton.MongoConnection;
import static Utils.Utils.separarParametros;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
        System.out.println("Indicar repositori: ");
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
        Scanner in = new Scanner(System.in);
        System.out.println("Indicar ruta: ");
        String input = in.nextLine();
        List<String> partes = separarParametros(input);
        logica.compareFiles((ArrayList) partes);
    }

    public static void menuClone() {
        Scanner in = new Scanner(System.in);
        System.out.println("Indicar repositori: ");
        String repo = in.nextLine();
        logica.cloneRepository(repo);
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

    public static void menuCreateAyuda() {
        System.out.println("""
        WORK IN PROGRESS                   """);
    }

    public static void menuDropAyuda() {
        System.out.println("""
              WORK IN PROGRESS             """);

    }

    public static void menuPushAyuda() {
        System.out.println("""
              WORK IN PROGRESS             """);

    }

    public static void menuPullAyuda() {
        System.out.println("""
                WORK IN PROGRESS           """);

    }

    public static void menuCompareAyuda() {
        System.out.println("""
                 WORK IN PROGRESS          """);

    }

    public static void menuCloneAyuda() {
        System.out.println("""
                WORK IN PROGRESS           """);

    }
}
