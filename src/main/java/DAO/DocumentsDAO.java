/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Interfaces.InterfaceDAO;
import Model.Fitxer;
import Singleton.MongoConnection;

import Utils.Ficheros;
import Utils.Utils;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static Singleton.MongoConnection.*;
import static Utils.Ficheros.*;
import static Utils.Utils.documentToFile;
import static Utils.Utils.fileToDocument;
import java.nio.file.NoSuchFileException;
import java.util.List;

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
    MongoDatabase db = MongoConnection.getDataBase();

    Path repoPath = getRepositoryPath();
    String repoName = MongoConnection.getRepositoryName();

    MongoCollection<Document> collection = MongoConnection.getCollection();

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
            if (ruta.matches("^[A-Za-z]:[/\\\\].*")) {
                ruta = ruta.substring(3);
            } else if (ruta.startsWith(fileSeparator) || ruta.startsWith("/")) {
                ruta = ruta.substring(1);
            }

            //Canvia els separadors de ruta per _
            ruta = ruta.replaceAll("[/\\\\]", "_");
            setRepositoryName(ruta);
            //Comprobem si la col·lecció ja existeix
            boolean collectionExists;
            try {
                /**
                 * TODO Eliminar conexióncuando la creacion de la BD este implementada
                 */
                collectionExists = db.listCollectionNames()
                        .into(new ArrayList<>()).contains(ruta);
                if (collectionExists) {
                    System.out.println("ERROR: Aquest repositori ja existeix.");
                } else {
                    //Crea el nou repositori amb el nom de la ruta processada
                    db.getCollection(ruta);
                    System.out.println("Repositori creat correctament.");
                    /**
                     * TODO Codigo para que la coleccion sea creada
                     * forzadamente, eliminar antes de subir la practica
                     */
                    Document del = new Document();
                    del.append("1", "del");
                    db.getCollection(ruta).insertOne(del);
                    db.getCollection(ruta).deleteOne(del);
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

    @Override
    public void cloneRepository(String file, String date) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void compareFiles(String inputPathfile, boolean containsDetails) throws Exception {

        if (inputPathfile.isEmpty()) {

            MongoCursor<Document> allFilesDb = collection.find().iterator();
            ArrayList<Document> documentsDb = new ArrayList<>();
            while (allFilesDb.hasNext()) {
                documentsDb.add(allFilesDb.next());
            }
            File directoryLocal = new File(getRepositoryPath().toUri());
            //TODO FILTRAR DOCUMENTOS QUE SEAN TXT XML ..... (Actualmente solo se filtran *.txt)
            List<File> fileList = compareAllFiles(directoryLocal);

            for (Document documentoDb : documentsDb) {
                for (File Localfile : fileList) {
                    if (documentoDb.getString("nom").equals(Localfile.getName())) {
                        System.out.print(Localfile.getName() + " " + documentoDb.getString("nom") + "\n");
                        compareTwoFiles(fileToDocument(Localfile),documentoDb,containsDetails);
                    }
                }
            }

        } else {
            Path secondPath = Paths.get(inputPathfile);
            Path resolvedPath = repoPath.resolve(secondPath);

            File localFile = new File(resolvedPath.toString());

            if (!localFile.exists()) {
                System.out.println("ERROR: El archivo local no existe.\n");
                return;
            }

            Document documentLocal = fileToDocument(localFile);

            Document query = new Document("path", resolvedPath.toString());
            Document documentoDb = collection.find(query).first();

            compareTwoFiles(documentLocal,documentoDb,containsDetails);
        }
    }
    
    @Override
    public void cloneRepository(String file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
