/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Interfaces.InterfaceDAO;
import Model.Fitxer;
import Singleton.MongoConnection;

import Utils.Ficheros;
import static Utils.Ficheros.compareAllFiles;
import static Utils.Ficheros.compareTwoFiles;
import Utils.Utils;
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

import static Utils.Utils.fileToDocument;
import com.mongodb.client.model.Filters;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

import java.util.List;
import org.bson.conversions.Bson;

/**
 *
 * @author di carlo
 */
public class DocumentsDAO implements InterfaceDAO {

    //File rem = null;
    //String userHome = System.getProperty("user.home");
    //String userFolder = "getRepo1";
    //String repositoryName = null;
    //MongoClient connection = MongoConnection.getInstance().getDBClient();
    //MongoDatabase repository = null;
    //String idRemot = null;

    MongoConnection mongoConnection = MongoConnection.getInstance();
    MongoDatabase database = mongoConnection.getDataBase();
    Path repositoryPath = mongoConnection.getRepositoryPath();
    //String repoName = mongoConnection.getRepositoryName();

    MongoCollection<Document> collection = MongoConnection.getCollection();

    /**
     * Crea un repositori a la BBDD remota amb una ruta indicada per l'usuari.
     * El nom del repositori es la direcció del repositori localment formatat.
     *
     * @param ruta
     */
    @Override
    public void createRepository(String ruta) {
        System.out.println("Creant repositori...");
        Path userPath = Paths.get(ruta);
        // Obtenim el nom del repositori a partir de la ruta
        String nomRepo = Utils.pathToRepoName(ruta);

        //Emmagatzemar nom i ruta del repositori al singleton
        mongoConnection.setRepositoryName(nomRepo);
        mongoConnection.setRepositoryPath(userPath);

        //Comprobem si la col·lecció ja existeix
        boolean collectionExists;
        try {
            collectionExists = database.listCollectionNames()
                    .into(new ArrayList<>()).contains(nomRepo);

            if (collectionExists) {
                // Si ja existeix, guardem el nom i ruta del repositori i continua
                // al següent menú.
                System.out.println("Aquest repositori ja existeix. Accedint...");

            } else {
                //Crea el nou repositori amb el nom de la ruta processada
                database.getCollection(nomRepo);
                System.out.println("Repositori creat correctament.");

                //Inserta un document amb la ruta del repositori
                Document del = new Document();
                del.append("ruta", mongoConnection.getRepositoryPath().toString());
                database.getCollection(nomRepo).insertOne(del);
                database.getCollection(nomRepo).deleteOne(del);
            }
        } catch (MongoException ex) {
            System.out.println("Error: " + ex.getMessage());

        }

    }

    /**
     * Elimina el repositori actual de la BBDD.
     */
    @Override
    public void dropRepository() {
        try {
            MongoCollection<Document> repositoryCollection = database
                    .getCollection(mongoConnection.getRepositoryName());
            repositoryCollection.drop();
            System.out.println("Repositori eliminat correctament.");

        } catch (MongoException ex) {
            System.out.println("Excepció: " + ex.getMessage());
        }
    }

    /**
     * Funcion para subir ficheros al repositorio remoto
     *
     * @param file
     * @param force
     */
    @Override
    public void pushFile(String file, Boolean force) {

        MongoDatabase repository = mongoConnection.getDataBase();
        File v = new File(file);
        try {

            if (!file.isEmpty() && v.exists()) {
                MongoCollection<Document> doc = repository.getCollection(mongoConnection.getRepositoryName());
                File fichero = new File(file);
                Document don = Utils.fileToDocument(fichero);

                if (force) {

                    doc.insertOne(don);

                } else {
                    Ficheros.compareModifiedDate(mongoConnection.getRepositoryPath(), fichero, doc);
                }

            } else {

                Ficheros.pushAllFiles(new File(mongoConnection.getRepositoryPath().toString()), force);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Funcion para descargar los ficheros del repositorio remoto
     *
     * @param file
     * @param force
     */

    @Override
    public void pullFile(String file, Boolean force) {

        try {
            MongoDatabase repository = mongoConnection.getDataBase();
            MongoCollection<Document> col = repository.getCollection(mongoConnection.getRepositoryName());
            String destinationFolder = String.join("", mongoConnection.getRepositoryPath().toString(), file);

            if (!file.isEmpty()) {
                //Comprobar que no esta vacio
                //String path = Ficheros.getAbsolutePath(mongoConnection.getRepositoryPath(),pa);

                Bson filter = Filters.eq("path", file);
                Document documento = col.find(filter).first();
                Fitxer fit = new Fitxer();
                fit = fit.documentToObject(documento, destinationFolder);
                String parent = new File(fit.getFilePath()).getParent();
                File destination = new File(parent);
                File fname = new File(destination, String.join(".", fit.getNomFitxer(), fit.getExtensio()));

                //Forzar el pull
                if (force) {

                    Utils.crearRuta(destination, fname, force);
                    Ficheros.addContent(fname, fit.getContingut());

                } else {
                    //Si no se fuerza se comprueba la fecha
                    if (Ficheros.checkDateForPull(fit, file)) {
                        Utils.crearRuta(destination, fname, force);
                        Ficheros.addContent(fname, fit.getContingut());
                    }

                }

            } else {
                Ficheros.recursivePull(force);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    /**
     * Clona el repositori actual de la BBDD al sistema, si aquest no existeix
     * localment.
     *
     * @param date
     */
    @Override
    public void cloneRepository(String date) {
        //Comproba si el directori existeix localment per crear-lo
        if (Files.notExists(mongoConnection.getRepositoryPath())) {
            try {
                Files.createDirectories(mongoConnection.getRepositoryPath());
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Clonant repositori a " + mongoConnection.getRepositoryPath());

            // Obtenim un cursor amb tots els documents de la col·leccio
            MongoCursor<Document> cursor = database.getCollection(mongoConnection.getRepositoryName()).find().iterator();

            while (cursor.hasNext()) {
                // Iterem la col·leccio i afegim els documents a la llista
                Document documento = cursor.next();

                // Saltem el primer document amb clau "ruta"
                if (documento.containsKey("ruta")) {
                    continue;
                }

                // Comprovar si el fitxer es anterior a la data indicada per
                // l'usuari, si aquest n'ha indicat una
                if (!date.isBlank()) {
                    Date newDate = Utils.dateFormat(date);
                    Date docDate = documento.getDate("modificacio");
                    // Si la data del document es mes nova que la indicada
                    // no l'afegeix
                    if (docDate.after(newDate)) {
                        continue;
                    }
                }

                // Comprovar si el document està en un subdirectori
                Path filePath = mongoConnection.getRepositoryPath().resolve(documento.getString("path").substring(1));
                if (!Ficheros.checkSubdirectory(filePath)) {
                    continue;
                }

                // Escribim el document en un nou fitxer
                try ( BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                    writer.write(documento.getString("contingut"));
                    System.out.println("Clonat fitxer " + filePath);
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

        } else {
            System.out.println("El repositori a clonar ja existeix localment,"
                    + " eliminal i intenta-ho de nou.");
        }
    }

    @Override
    public void compareFiles(String inputPathfile, boolean containsDetails) throws Exception {

        if (inputPathfile.isEmpty()) {

            MongoCursor<Document> allFilesDb = collection.find().iterator();
            ArrayList<Document> documentsDb = new ArrayList<>();
            while (allFilesDb.hasNext()) {
                documentsDb.add(allFilesDb.next());
            }
            File directoryLocal = new File(mongoConnection.getRepositoryPath().toUri());
            //TODO FILTRAR DOCUMENTOS QUE SEAN TXT XML ..... (Actualmente solo se filtran *.txt)
            List<File> fileList = compareAllFiles(directoryLocal);

            for (Document documentoDb : documentsDb) {
                for (File Localfile : fileList) {
                    if (documentoDb.getString("nom").equals(Localfile.getName())) {
                        System.out.print(Localfile.getName() + " " + documentoDb.getString("nom") + "\n");
                        compareTwoFiles(fileToDocument(Localfile), documentoDb, containsDetails);
                    }
                }
            }

        } else {
            Path secondPath = Paths.get(inputPathfile);
            Path resolvedPath = repositoryPath.resolve(secondPath);

            File localFile = new File(resolvedPath.toString());

            if (!localFile.exists()) {
                System.out.println("ERROR: El archivo local no existe.\n");
                return;
            }

            Document documentLocal = fileToDocument(localFile);

            Document query = new Document("path", resolvedPath.toString());
            Document documentoDb = collection.find(query).first();

            compareTwoFiles(documentLocal, documentoDb, containsDetails);
        }
    }
}
