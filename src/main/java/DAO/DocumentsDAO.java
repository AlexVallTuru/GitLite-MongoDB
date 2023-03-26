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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.util.ArrayList;
import org.bson.Document;

/**
 *
 * @author di carlo
 */
public class DocumentsDAO implements InterfaceDAO {

    File rem = null;
    String userHome = System.getProperty("user.home");
    String userFolder = "getRepo1";
    String repositoryName = null;
    MongoClient connection = MongoConnection.getInstance();
    MongoDatabase repository = null;
    String idRemot = null;

    @Override
    public void createRepository(String ruta) {

    }

    @Override
    public void dropRepository(String path) {

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
    public void cloneRepository(String file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
