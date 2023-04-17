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
                         || MENU || Conectat a : %s
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
            System.out.println("El valor introducido no es valido");
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
        try {
            System.out.println("[SUBIR FICHERO]");
            Scanner in = new Scanner(System.in);
            System.out.println("[Indicar ruta]: ");
            String ruta = in.nextLine();
            System.out.println("[¿Quieres forzar?]: [1-Si | 2-No] ");
            Boolean force = Utils.verificaOpcio(in.nextInt());
            logica.pushFile(ruta, force);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void menuPull() {
        try {
            System.out.println("[DESCARGAR FICHERO]");
            Scanner in = new Scanner(System.in);
            System.out.println("[Indicar ruta]: ");
            String ruta = in.nextLine();
            System.out.println("[¿Quieres forzar?]: [1-Si | 2-No] ");
            Boolean force = Utils.verificaOpcio(in.nextInt());
            logica.pullFile(ruta, force);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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
            La función PUSH nos permite subir archivos al repositorio remoto.
                    Los parámetros que utilizara son [nombre_repositorio] [ruta_del archivo_local] [force]
                    
                    - [nombre_repositorio] : Se crea automáticamente al generar un repositorio nuevo
                    - [ruta_del_archivo_local] : Pasando una ruta absoluta del archivo se crea automáticamente una ruta relativa
                      que se guardara en el remoto
                    - [force] : Esta opción permite subir el archivo sin comparar las fechas de modificación de los archivos y será de tipo boolean
                      En caso de que force sea TRUE:
                            - El archivo se subirá y se actualizará
                      En caso de que force sea FALSE:
                            - Se revisará si existe en el repositorio y sólo se subirá si el archivo remoto está desactualizado
                            - Si no existe en el remoto, se subirá directamente
                           
        """);

    }

    public static void menuPullAyuda() {
        System.out.println("""
        La función PULL nos permite descargar archivos del repositorio remoto.
        Los parámetros que utilizara son [nombre_repositorio] [ruta_del_archivo_local] [force]
                                        
            - [nombre_repositorio] : Se crea automáticamente al generar un repositorio nuevo
            - [ruta_del_archivo_local] : Pasando una ruta absoluta del archivo se crea automáticamente una ruta relativa
              que se guardará en el remoto
            - [force] : Esta opción permite descargar el archivo sin comparar las fechas de modificación de los archivos y será de tipo boolean
              En caso de que force sea TRUE:
                 - El archivo se descargará y actualizará
              En caso de que force sea FALSE:
                 - Se revisará si existe en el repositorio y sólo se descargará si el archivo local está desactualizado
                 - Si no existe en el local, se descargará directamente
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
