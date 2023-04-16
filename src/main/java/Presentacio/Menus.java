/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacio;

import Logica.DocumentsLogica;
import Logica.MenuLogica;
import Singleton.MongoConnection;
import Utils.Utils;
import java.util.Scanner;

/**
 *
 * @author carlo
 */
public class Menus {

    private static final DocumentsLogica logica = new DocumentsLogica();

    public static int menuPrincipal() {
        MongoConnection c = MongoConnection.getInstance();

        try {
            Scanner in = new Scanner(System.in);
            System.out.print(String.format("""
                         || MENU || Connectat a : %s
                         1. DROP
                         2. PUSH
                         3. PULL
                         4. COMPARE
                         5. CLONE
                         6. AYUDA
                         7. SALIR
                         """, c.getRepositoryName()));
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

            int op = 0;
            while (op != 7) {
                op = Menus.menuPrincipal();
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
        System.out.println("[Vols forçar?]: [1-Si | 2-No] ");
        Boolean force = Utils.verificaOpcio(in.nextInt());
        logica.pushFile(ruta, force);

    }

    public static void menuPull() {
        System.out.println("[DESCARREGA FITXER]");
        Scanner in = new Scanner(System.in);
        System.out.println("[Indicar ruta]: ");
        String ruta = in.nextLine();
        System.out.println("[Vols forçar?]: [1-Si | 2-No] ");
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
        System.out.println("3. Documentación de la opción PULL");
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
        La funció PUSH ens permet pujar fitxers al repositori remot.
        Els parametres que utilitzara son [nom_repositori] [ruta_del_fitxer_local] [force]
        
        - [nom_repositori] : Es crea automaticament al generar un repositori nou
        - [ruta_del_fitxer_local] : Pasant una ruta absoluta del fitxer es crea automaticament una ruta relativa
          que es guardara al remot
        - [force] : Aquesta opció permet pujar el fitxer sense comparar les dates de modificacio dels fitxers i sera de tipus boolean
          En el cas de que force sigui TRUE:
                - El fitxer es pujarà i s'actualitzara
          En el cas de que force sigui FALSE:
                - Es revisara si existeix al repositori i només es pujara si el fitxer remot está desactualitzat
                - Si no existeix al remot, es pujarà directament
                           
        """);

    }

    public static void menuPullAyuda() {
        System.out.println("""
        La funció PULL ens permet descarregar fitxers del repositori remot.
        Els parametres que utilitzara son [nom_repositori] [ruta_del_fitxer_local] [force]
                
        - [nom_repositori] : Es crea automaticament al generar un repositori nou
        - [ruta_del_fitxer_local] : Pasant una ruta absoluta del fitxer es crea automaticament una ruta relativa
          que es guardarà al remot
        - [force] : Aquesta opció permet descarregar el fitxer sense comparar les dates de modificació dels fitxers i serà de tipus boolean
          En el cas de que force sigui TRUE:
                - El fitxer es descarregarà i s'actualitzarà
          En el cas de que force sigui FALSE:
                - Es revisarà si existeix al repositori i només es descarregarà si el fitxer local está desactualitzat
                - Si no existeix al local, es descarregarà directament
                           """);

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
