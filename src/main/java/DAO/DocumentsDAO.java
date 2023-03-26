/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Interfaces.InterfaceDAO;
import Singleton.MongoConnection;
import Utils.Ficheros;
import static Utils.Ficheros.obtenerMD5ComoString;
import Utils.Utils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @Override
    public void compareFiles(String file1) {
        try {
            String archivoAComparar = "C:\\Users\\avall\\Desktop\\primero.txt";
            File file = new File("C:\\Users\\avall\\Desktop\\primero.txt");
            String carpetaConArchivos = "C:\\Users\\avall\\Desktop\\";
            File carpeta = new File(carpetaConArchivos);
            File[] archivos = carpeta.listFiles();
            for (File archivo : archivos) {
                if (archivo.isFile() && archivo.getName().endsWith(".txt")) {
                    if (file.lastModified() == archivo.lastModified()) {
                        System.out.print("\n \t\t C 0 M P A R A N D 0 \n\n \t" + file.getName().toUpperCase() + " & & & " + archivo.getName().toUpperCase()
                                + "\n\t- TIMESTPAMP IGUALES (FECHA MODIFICACION) \n");

                        String checksum = obtenerMD5ComoString(archivoAComparar);
                        String checksumAComparar = obtenerMD5ComoString(carpetaConArchivos + archivo.getName());
                        if (checksum.equals(checksumAComparar)) {
                            System.out.print("\t- CONTENIDO INTERNO IGUAL (HASH MD5)");
                        } else {
                            System.out.print("\t- CONTENIDO INTERNO DIFERENTE");
                        }
                        System.out.print("\n\n\t\t - - - - - - - - - -\n\n");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }

        File file = new File("C:\\Users\\avall\\Desktop\\primero.txt");
        long timestamp = file.lastModified();
        Date date = new Date(timestamp);
        System.out.println("El timestamp del archivo es: " + timestamp);
        System.out.println("La fecha y hora del archivo es: " + date);

        File file2 = new File("C:\\Users\\avall\\Desktop\\segundo.txt");
        long timestamp2 = file2.lastModified();
        System.out.println("El timestamp del segundo archivo es: " + timestamp2);
        if (timestamp == timestamp2) {
            System.out.print("Els TIMESTAMPS son iguals");
            String checksum = null;
            try {
                checksum = obtenerMD5ComoString("C:\\Users\\avall\\Desktop\\primero.txt");
            } catch (Exception ex) {
                Logger.getLogger(DocumentsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.print("\nChecksum del primer archivo" + checksum);
            String checksum1 = null;
            try {
                checksum1 = obtenerMD5ComoString("C:\\Users\\avall\\Desktop\\segundo.txt");
            } catch (Exception ex) {
                Logger.getLogger(DocumentsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.print("\nChecksum del segundo archivo" + checksum1);

        } else {
            String checksum = null;
            try {
                checksum = obtenerMD5ComoString("C:\\Users\\avall\\Desktop\\primero.txt");
            } catch (Exception ex) {
                Logger.getLogger(DocumentsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            String checksum1 = null;
            try {
                checksum1 = obtenerMD5ComoString("C:\\Users\\avall\\Desktop\\segundo.txt");
            } catch (Exception ex) {
                Logger.getLogger(DocumentsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (checksum.equals(checksum1)) {
                System.out.print("\nSon asquerosamente iguales madafaka ");
            }
        }
    }

    @Override
    public void cloneRepository(String file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
