/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacio;

import DAO.MenuDAO;
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
                         1. DROP
                         2. PUSH
                         3. COMPARE
                         4. CLONE
                         5. SALIR
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
            Menus.menuPrincipal();
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
        System.out.println("Indicar ruta: ");
        String ruta = in.nextLine();

        logica.dropRepository(ruta);

    }

    public static void menuPush() {

        System.out.println("[PUJAR FITXER]");
        Scanner in = new Scanner(System.in);
        System.out.println("[Indicar ruta]: ");
        String ruta = in.nextLine();
        System.out.println("[Vols forçar?]: ");
        String force = in.nextLine();
        logica.pushFile(ruta, force);

    }

    public static void menuPull() {

    }

    public static void menuCompare() {

    }

    public static void menuClone() {

    }

}
