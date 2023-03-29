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
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static Singleton.MongoConnection.setRepositoryName;
import static Utils.Ficheros.sonArchivosIgualesPorMD5;
import static Utils.Utils.documentToFile;
import static Utils.Utils.fileToDocument;
import java.nio.file.NoSuchFileException;

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

    Path repoPath = MongoConnection.getRepositoryPath();
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
        /**
         * TODO Eliminar cuando la creacion de la BD este implementada
         */
        repository = connection.getDatabase("GETBD");

        System.out.println("Creant repositori...");
        //Comproba si la ruta indicada per l'usuari existeix localment
        Path userPath = Paths.get(ruta);
        if (Files.exists(userPath)) {
            //Comproba si la ruta conté un identificador d'unitat. Si existeix, l'elimina
            if (ruta.matches("^[A-Za-z]:\\" + fileSeparator + ".*")) {
                ruta = ruta.substring(3);
            } else if (ruta.startsWith(fileSeparator)) {
                ruta = ruta.substring(1);
            }

            //Canvia els separadors de ruta per _
            ruta = ruta.replace(fileSeparator, "_");
            setRepositoryName(ruta);
            //Comprobem si la col·lecció ja existeix
            boolean collectionExists = repository.listCollectionNames()
                    .into(new ArrayList<>()).contains(ruta);
            if (collectionExists) {
                System.out.println("ERROR: Aquest repositori ja existeix.");
            } else {
                //Crea el nou repositori amb el nom de la ruta processada
                repository.getCollection(ruta);
                System.out.println("Repositori creat correctament.");
                /**
                 * TODO Codigo para que la coleccion sea creada forzadamente,
                 * eliminar antes de subir la practica
                 */
                Document del = new Document();
                del.append("1", "del");
                repository.getCollection(ruta).insertOne(del);
                repository.getCollection(ruta).deleteOne(del);
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
                MongoCollection<Document> coleccio = repository.getCollection(repositori);
                coleccio.drop();
            } else {
                System.out.println("El repositori "
                        + repositori + " no existeix.");
            }
        } catch (MongoException ex) {
            System.out.println("Excepció: " + ex);
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

    /**
     * System.out.print("\t\t\t\t C O M P A R A N D O\n"); //Procés que compara
     * els continguts dels fitxers locals amb els seus equivalents remots i
     * informa a l’usuari // + FITXER de un fitxer en concret String
     * carpetaConArchivos = aniadirBarraFinal(ruta.get(0)); File carpeta = new
     * File(aniadirBarraFinal(ruta.get(0))); File[] archivos =
     * carpeta.listFiles(); File file = new File(ruta.get(1)); String
     * archivoAComparar = ruta.get(1); for (File archivo : archivos) { if
     * (archivo.isFile() && archivo.getName().endsWith(".txt")) { if
     * (file.lastModified() == archivo.lastModified()) {
     * System.out.print(file.getName() + " & " + archivo.getName() + "\t\t\t\t
     * FECHA IGUAL");
     *
     * String checksum = obtenerMD5ComoString(archivoAComparar); String
     * checksumAComparar = obtenerMD5ComoString(carpetaConArchivos +
     * archivo.getName()); if (checksum.equals(checksumAComparar)) {
     * System.out.print(" CONTENIDO IGUAL\n"); } else { System.out.print("
     * CONTENIDO DIFERENTE\n"); } } else{ System.out.print(file.getName() + " &
     * " + archivo.getName() + "\t\t\t\t FECHA DIFERENTE"); String checksum =
     * obtenerMD5ComoString(archivoAComparar); String checksumAComparar =
     * obtenerMD5ComoString(carpetaConArchivos + archivo.getName()); if
     * (checksum.equals(checksumAComparar)) { System.out.print(" CONTENIDO
     * IGUAL\n"); } else { System.out.print(" CONTENIDO DIFERENTE\n"); } } } }
     * System.out.print("\t\t\t\t - - - - - - - - - -\n"); }
     *
     */
    @Override
    public void cloneRepository(String file, String date) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void compareFiles(String inputPathfile, boolean containsDetails) throws Exception {

        if (inputPathfile.equals(null)) {

        } else {
            //GENERAMOS UN PATH CON EL FILE ADJUNTO
            Path secondPath = Paths.get(inputPathfile);
            Path resolvedPath = repoPath.resolve(secondPath);

            try {
                File localFile = new File(resolvedPath.toString());
                long localTimeStamp = fileToDocument(localFile).getDate("modificacio").getTime();
                String contenidoLocal = fileToDocument(localFile).getString("contingut");
                Document query = new Document("path", resolvedPath.toString());
                Document documento = collection.find(query).first();
                String contenidoDb = documento.getString("contingut");
                long dbTimeStamp = documento.getDate("modificacio").getTime();

                if (dbTimeStamp == localTimeStamp) {
                    System.out.print("\nSon iguales!\n");
                } else if (sonArchivosIgualesPorMD5(contenidoDb, contenidoLocal)) {
                    System.out.print("\nSon iguales!! \t Pero la ultimas fechas de modificación son diferente\n");
                } else {
                    System.out.println("Son distintos");
                }
            } catch (NoSuchFileException e) {
                System.out.println("ERROR:\tDocumento local no encontrado:\t" + e.getMessage() + "\n");
            } catch (NullPointerException e){
                System.out.println("ERROR:\tDocumento remoto no encontrado a la base de datos:\t" + e.getMessage() + "\n");
            }
            
             

        }
    }

    @Override
    public void cloneRepository(String file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
