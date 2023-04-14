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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
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
            boolean pathExists = false;
            String ruta = null;
            
            //Comproba si existeix la ruta
            while (!pathExists) {
                System.out.println("Indicar ruta: ");
                ruta = in.nextLine();
                if (Files.exists(Paths.get(ruta))) {
                    pathExists = true;
                } else {
                    System.out.println("La ruta no existeix, introdueix-la de nou");
                }
            }
            
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
        System.out.println("Estás segur de que vols eliminar el repositori "
                + "actual? [Si = 1, No = 2]");
        Boolean check = Utils.verificaOpcio(in.nextInt());
        
        // Si l'usuari indica que no s'aborta l'operació
        if (check) {
            logica.dropRepository();
        } else {
            System.out.println("Operació cancel·lada");
        }
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
        System.out.println("Indica una data en dd-MM-yyyy (opcional): ");
        String date = in.nextLine();
        // Si l'usuari introdueix una data, comproba que tingui un format valid
        if (!date.equals("")) {
            while (!Utils.verificaData(date)) {
                System.out.println("Format de la data incorrecte, introdueix-ho"
                        + " de nou:");
                date = in.nextLine();
            }
        }
        logica.cloneRepository(date);
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
