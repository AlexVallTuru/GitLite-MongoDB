/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacio;

import Logica.DocumentsLogica;
import Logica.MenuLogica;
import Singleton.MongoConnection;
import Utils.Utils;
import java.io.File;
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

    /**
     * Funcio que recull una ruta local per crear un nou repositori remot.
     */
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
                    System.out.println("La ruta no existe o apunta a un "
                            + "fichero, introducela de nuevo.");
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

    /**
     * Funció que confirma si l'usuari vol eliminar el repositori actual.
     */
    public static void menuDrop() {
        Scanner in = new Scanner(System.in);
        System.out.println("Estás seguro de que quieres eliminar el repositorio "
                + "actual? [Sí = 1, No = 2]");
        Boolean check = Utils.verificaOpcio(in.nextInt());

        // Si l'usuari indica que no s'aborta l'operació, si no s'esborra el
        // repositori i s'en torna a l'inici del programa
        if (check) {
            logica.dropRepository();

            int opc = 0;
            while (opc != 7) {
                opc = Menus.menuInicio();
                MenuLogica.getInitialOption(opc);
            }
        } else {
            System.out.println("Operación cancelada.");
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

    /**
     * Menu que recoge una fecha (opciona) i la envía al metodo de clonación de
     * repositorios.
     */
    public static void menuClone() {
        Scanner in = new Scanner(System.in);
        System.out.println("Indica una fecha en dd-MM-yyyy (opcional): ");
        String date = in.nextLine();
        // Si l'usuari introdueix una data, comproba que tingui un format valid
        if (!date.isBlank()) {
            while (!Utils.verificaData(date) && !date.isBlank()) {
                System.out.println("Formato de la fecha incorrecto, introducela"
                        + " de nuevo o no introduzcas nada para continuar:");
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
                           La función CREATE nos permite crear repositorios remotamente en la base de datos a partir de un repositorio local.
                           Es necesario que este repositorio exista localmente, y no es necesario que tenga contenido, ya que
                           este se podrá subir con la función PULL.
                           
                           Instrucciones:
                           Introduce la ruta al repositorio, el programa creará un repositorio a partir de esta ruta i proporcionará
                           un nombre a este a partir de la ruta.
                           """);
    }

    public static void menuDropAyuda() {
        System.out.println("""
                           La función DROP elimina el repositorio seleccionado en el inicio del programa de la base de datos,
                           después de obtener la confirmación del usuario. No eliminará los archivos locales.
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
        System.out.println("Muestra este menú de ayuda.");

    }

    public static void menuCloneAyuda() {
        System.out.println("""
                           La fucnión CLONE clonará el repositorio seleccionado al inicio del programa en el sistema local,
                           si es que este no existe localmente. También permite clonar solo los archivos creados antes
                           de una fecha indicada por el usuario.
                           
                           Instrucciones:
                           El programa pedirá al usuario una fecha en formato dd-MM-yyyy. Si no se introduce ninguna fecha,
                           el programa clonará todos los archivos del repositorio remoto al sistema.
                           """);

    }
}
