/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacio;

import Logica.DocumentsLogica;
import Logica.MenuLogica;
import Singleton.MongoConnection;
import Utils.Utils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author carlo
 */
public class Menus {

    private static MongoConnection connection = MongoConnection.getInstance();
    private static MongoDatabase repository = null;
    private static File rem = null;
    private static String idRemot = null;
    public static MongoConnection f = MongoConnection.getInstance();
    public static MongoDatabase db = f.getDataBase();
    public static Path repoPath = f.getRepositoryPath();
    public static String repoName = f.getRepositoryName();
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
            boolean pathExists = false;
            String ruta = null;

            //Comproba si existeix la ruta
            while (!pathExists) {
                System.out.println("Indicar ruta: ");
                ruta = in.nextLine();
                if (new File(ruta).exists() && new File(ruta).isDirectory()) {
                    pathExists = true;
                } else {
                    System.out.println("La ruta no existeix o apunta a un fitxer, "
                            + "introdueix-la de nou");
                }
            }

            logica.createRepository(ruta);

            int op = 0;
            while (op != 7) {
                op = Menus.menuPrincipal();
                MenuLogica.repositoryOptions(op);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
        System.out.println("Indicar ruta del archivo:\n- En caso de que se encuentra en el repositorio actual, introduce el nombre unicamente.\n "
                + "- En caso de dejarlo vacio mostrara el contenido del repositorio: " + f.getRepositoryName() + ".");
        String ruta = in.nextLine();
        System.out.println("Quieres visualizar los detalles? (s/n)");
        String resultado = in.nextLine();
        String detailsLocalORemot;

        if (resultado.equalsIgnoreCase("s")) {
            System.out.println("Quieres comparar de local a remoto o viceversa? (l/r)");
            detailsLocalORemot = in.nextLine();
        } else if (resultado.equalsIgnoreCase("n")) {
            detailsLocalORemot = "n"; // Establecer una opción por defecto
        } else {
            System.out.println("Introduce una opción válida, por favor.");
            return;
        }

        if (detailsLocalORemot.equalsIgnoreCase("l")) {
            logica.compareFiles(ruta, true, true);
        } else if (detailsLocalORemot.equalsIgnoreCase("r")) {
            logica.compareFiles(ruta, true, false);
        } else if (detailsLocalORemot.equalsIgnoreCase("n")) {
            logica.compareFiles(ruta, false, true);
        } else {
            System.out.println("Introduce una opción válida.");
        }
    }

    public static void menuClone() {
        Scanner in = new Scanner(System.in);
        System.out.println("Indica una data en dd-MM-yyyy (opcional): ");
        String date = in.nextLine();
        // Si l'usuari introdueix una data, comproba que tingui un format valid
        if (!date.isBlank()) {
            while (!Utils.verificaData(date) && !date.isBlank()) {
                System.out.println("Format de la data incorrecte, introdueix-ho"
                        + " de nou o no introdueixis res per continuar:");
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
        System.out.println("3. Documentación de la opción PULL");
        System.out.println("4. Documentación de la opción COMPARE");
        System.out.println("5. Documentación de la opción CLONE");
        System.out.println("6. Volver al menú principal");
        System.out.println("Selecciona una opción: ");
        return in.nextInt();
    }

    public static void menuCreateAyuda() {
        System.out.println("""
                           La funció de CREATE ens permet crear repositoris remotament a la base de dades a partir d'un repositori local. 
                           És necessari que aquest repositori existeixi localment, i no és necessari que hi tingui cap contingut, 
                           ja que es pujarà més endavant amb la funció de PULL.
                           
                           Instruccions:
                           Introdueix la ruta al repositori, el programa crearà un repositori a partir d'aquesta ruta i 
                           proporcionarà un nom a aquest a partir de la ruta.
                           """);
    }

    public static void menuDropAyuda() {
        System.out.println("""
                           La funció DROP elimina el repositori seleccionat a l'inici del programa de la base de dades, 
                           després d'obtenir la confirmació de l'usuari. No l'elimina localment.
                           """);

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
                           La funció CLONE clonarà el repositori seleccionat a l'inici del programa al sistema local, 
                           si és que aquest encara no existeix localment. També permet clonar només els arxius creats abans 
                           d'una data indicada per l'usuari.
                           
                           Instruccions:
                           El programa demana a l'usuari una data en format dd-MM-yyyy, si no s'introdueix cap data, 
                           el programa clonarà tots els arxius al repositori remot.
                           """);

    }
}
