/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacio;

import DAO.MenuDAO;
import Logica.DocumentsLogica;
import Logica.MenuLogica;
import Singleton.MongoConnection;
import Utils.Utils;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author carlo
 */
public class Menus {

    private static DocumentsLogica logica = new DocumentsLogica();
    private static MongoConnection connection = MongoConnection.getInstance();
    private static MongoDatabase repository = null;
    private static File rem = null;
    //private static String ruta = System.getProperty("user.home");
    //private static String userFolder = "getRepo1";
    //private static String repositoryName = "home_user_getrepo2";
    private static String idRemot = null;

    public static int menuPrincipal() {

        try {
            Scanner in = new Scanner(System.in);
            System.out.print("""
                         || MENU ||
                         1. DROP
                         2. PUSH
                         3. PULL
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
            
           int op =0; 
           while(op!= 5){
               op=Menus.menuPrincipal();
               MenuLogica.repositoryOptions(op);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int menuInicio() {
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("""
                           [INICIO]
                           1. CREAR
                           2. SELECCIONAR
                           
                           """);
            return in.nextInt();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    public static void selectRepository(){
        try {
            MenuDAO.repositoryList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void menuDrop() {
        Scanner in = new Scanner(System.in);
        System.out.println("Indicar repositori: ");
        String ruta = in.nextLine();

        logica.dropRepository(ruta);

    }

    public static void menuPush() {

        System.out.println("[PUJAR FITXER]");
        Scanner in = new Scanner(System.in);
        System.out.println("[Indicar ruta]: ");
        String ruta = in.nextLine();
        System.out.println("[Vols forçar?]: [S/N] ");
        Boolean force = Utils.verificaOpcio(in.nextInt());
        logica.pushFile(ruta, force);

    }

    public static void menuPull() {
        System.out.println("[DESCARREGA FITXER]");
        Scanner in = new Scanner(System.in);
        System.out.println("[Indicar ruta]: ");
        String ruta = in.nextLine();
        System.out.println("[Vols forçar?]: [S/N] ");
        Boolean force = Utils.verificaOpcio(in.nextInt());
        logica.pullFile(ruta, force);

    }

    public static void menuCompare() {
        Scanner in = new Scanner(System.in);
        System.out.println("Indicar ruta: ");
        String ruta = in.nextLine();
        System.out.println("Quieres visualizar los detalles? (s/n)");
        String resultado = in.nextLine();
        boolean containsDetails = false;

        if (resultado.equalsIgnoreCase("s")) {
            logica.compareFiles(ruta, true);
        } else if (resultado.equalsIgnoreCase("n")) {
            logica.compareFiles(ruta, false);
        } else {
            System.out.print("\nIntroduce una opcion valida\n");
        }

    }

    public static void menuClone() {
        Scanner in = new Scanner(System.in);
        System.out.println("Indicar repositori: ");
        String repo = in.nextLine();
        System.out.println("Indica una data (opcional): ");
        String date = in.nextLine();
        logica.cloneRepository(repo, date);
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
