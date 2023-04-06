/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Interfaces.InterfaceDAO;
import Singleton.MongoConnection;
import Utils.Ficheros;
import Utils.Utils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import org.bson.Document;

/**
 *
 * @author carlo
 */
public class DocumentsDAO implements InterfaceDAO {

    File rem = null;
    String userHome = System.getProperty("user.home");
    String userFolder = "getRepo1";
    String repositoryName = null;
    MongoClient connection = MongoConnection.getInstance().getDBClient();
    MongoDatabase repository = null;
    String idRemot = null;

    @Override
    public void createRepository(String ruta) {


    }

    @Override
    public void dropRepository(String path) {


    }

    @Override
    public void pushFile(String file, Boolean force) {
        
        MongoDatabase repository = MongoConnection.getInstance().getDB();
        File v = new File(file);       
        try {
            if (repository != null) {
                MongoCollection<Document> doc = repository.getCollection(Utils.generateRepositoryName(v));
                File fichero = new File(file);
                Document don = Utils.fileToDocument(fichero);
                
                if (force) {

                    doc.insertOne(don);

                } else {
                    Ficheros.compareModifiedDate(fichero, doc);
                }
                

            }else{
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

    @Override
    public void compareFiles(String file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cloneRepository(String file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
