/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Interfaces.InterfaceDAO;
import Singleton.MongoConnection;
import Utils.Ficheros;
import Utils.Utils;
import static Utils.Utils.containsDetails;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.bson.Document;

/**
 *
 * @author di carlo
 */
public class DocumentsDAO implements InterfaceDAO {

    File rem = null;
    String userHome = System.getProperty("user.home");
    String fileSeparator = System.getProperty("file.separator");
    String userFolder = "getRepo1";
    String repositoryName = null;
    MongoClient connection = MongoConnection.getInstance();
    MongoDatabase repository = null;
    String idRemot = null;

    /**
     * Crea un repositori a la BBDD remota amb una ruta indicada per l'usuari.
     * El nom de la ruta es la direcció del repositori localment, canviant els
     * separadors de fitxers per _. Per exemple:
     * "C:\Users\mole6\OneDrive\Documentos\NetBeansProjects" es converteix en
     * "Users_mole6_OneDrive_Documentos_NetBeansProjects".
     *
     * @param ruta
     */
    @Override
    public void createRepository(String ruta) {
        System.out.println("Creant repositori...");
        //Comproba si la ruta indicada per l'usuari existeix localment
        Path userPath = Paths.get(ruta);
        if (Files.exists(userPath)) {
            //Comproba si la ruta conté un identificador d'unitat. Si existeix, l'elimina
            if (ruta.matches("^[A-Za-z]:\\" + fileSeparator + ".*")) {
                ruta = ruta.substring(3);
            } else if (ruta.startsWith(fileSeparator) || ruta.startsWith("/")) {
                ruta = ruta.substring(1);
            }

            //Canvia els separadors de ruta per _
            ruta = ruta.replace(fileSeparator, "_");

            //Comprobem si la col·lecció ja existeix
            boolean collectionExists;
            try {
                /**
                 * TODO Eliminar conexióncuando la creacion de la BD este implementada
                 */
                repository = connection.getDatabase("GETDB");
                collectionExists = repository.listCollectionNames()
                        .into(new ArrayList<>()).contains(ruta);
                if (collectionExists) {
                    System.out.println("ERROR: Aquest repositori ja existeix.");
                } else {
                    //Crea el nou repositori amb el nom de la ruta processada
                    repository.getCollection(ruta);
                    System.out.println("Repositori creat correctament.");
                    /**
                     * TODO Codigo para que la coleccion sea creada
                     * forzadamente, eliminar antes de subir la practica
                     */
                    Document del = new Document();
                    del.append("1", "del");
                    repository.getCollection(ruta).insertOne(del);
                    repository.getCollection(ruta).deleteOne(del);
                }
            } catch (MongoException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            System.out.println("La ruta no existeix localment.");
        }
    }

    /**
     * Elimina un repositori indicat per l'usuari de la BBDD si aquest existeix.
     *
     * @param repositori
     */
    @Override
    public void dropRepository(String repositori) {
        try {
            /**
             * TODO Eliminar cuando la creacion de la BD este implementada
             */
            repository = connection.getDatabase("GETDB");

            //Obtenim el repositori si existeix
            boolean collectionExists = repository.listCollectionNames()
                    .into(new ArrayList<>()).contains(repositori);
            if (collectionExists) {
                MongoCollection<Document> repositoryCollection = repository
                        .getCollection(repositori);
                repositoryCollection.drop();
                System.out.println("Repositori eliminat correctament.");
            } else {
                System.out.println("El repositori "
                        + repositori + " no existeix.");
            }
        } catch (MongoException ex) {
            System.out.println("Excepció: " + ex.getMessage());
        }
    }

    @Override
    public void pushFile(String file, String force) {
        repositoryName = MongoConnection.getRepository();
        repository = connection.getDatabase(repositoryName);
        try {
            if (repository != null) {
                MongoCollection<Document> doc = repository.getCollection(repositoryName);
                File fichero = new File(file);
                Document don = Utils.fileToDocument(fichero);

                if (force.equals("1")) {

                    doc.insertOne(don);

                } else {
                    Ficheros.compareModifiedDate(fichero, doc);
                }

            } else {
                Ficheros.pushAllFiles(new File(String.join("\\", userHome, repositoryName)));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void pullFile(String file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void compareFiles(ArrayList<String> ruta) {

        boolean containsDetails = containsDetails(ruta);

        switch (ruta.size()) {
            case 1:
                compareAllFiles(ruta);
                break;
            case 2:
                if (containsDetails) {
                    compareAllFilesWithDetails(ruta);
                } else {
                    compareSingleFile(ruta);
                }
                break;
            case 3:
                compareSingleFileWithDetails(ruta);
                break;
            default:
                System.out.println("Número incorrecto de argumentos");
        }
    }

    private void compareAllFiles(ArrayList<String> ruta) {
        //Procés que compara els continguts dels fitxers locals amb els seus equivalents remots i informa a l’usuari
    }

    private void compareAllFilesWithDetails(ArrayList<String> ruta) {
        //Procés que compara els continguts dels fitxers locals amb els seus equivalents remots i informa a l’usuari 
        //+ Mostra DETALLS
    }

    private void compareSingleFile(ArrayList<String> ruta) {
        //Procés que compara els continguts dels fitxers locals amb els seus equivalents remots i informa a l’usuari 
        // + FITXER de un fitxer en concret
    }

    private void compareSingleFileWithDetails(ArrayList<String> ruta) {
        //Procés que compara els continguts dels fitxers locals amb els seus equivalents remots i informa a l’usuari 
        // + FITXER de un fitxer en concret
        // + Mostra DETALLS
    }

    @Override
    public void cloneRepository(String file, String date) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
