/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Interfaces.InterfaceDAO;
import Singleton.MongoConnection;
import Utils.Ficheros;
import Utils.Utils;

import static Utils.Ficheros.obtenerMD5ComoString;
import static Utils.Utils.*;

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

    /**
        System.out.print("\t\t\t\t C O M P A R A N D O\n");
        //Procés que compara els continguts dels fitxers locals amb els seus equivalents remots i informa a l’usuari
        // + FITXER de un fitxer en concret
        String carpetaConArchivos = aniadirBarraFinal(ruta.get(0));
        File carpeta = new File(aniadirBarraFinal(ruta.get(0)));
        File[] archivos = carpeta.listFiles();
        File file = new File(ruta.get(1));
        String archivoAComparar = ruta.get(1);
        for (File archivo : archivos) {
            if (archivo.isFile() && archivo.getName().endsWith(".txt")) {
                if (file.lastModified() == archivo.lastModified()) {
                    System.out.print(file.getName() + " & " + archivo.getName() +
                            "\t\t\t\t FECHA IGUAL");

                    String checksum = obtenerMD5ComoString(archivoAComparar);
                    String checksumAComparar = obtenerMD5ComoString(carpetaConArchivos + archivo.getName());
                    if (checksum.equals(checksumAComparar)) {
                        System.out.print(" CONTENIDO IGUAL\n");
                    } else {
                        System.out.print(" CONTENIDO DIFERENTE\n");
                    }
                }
                else{
                    System.out.print(file.getName() + " & " + archivo.getName() +
                            "\t\t\t\t FECHA DIFERENTE");
                    String checksum = obtenerMD5ComoString(archivoAComparar);
                    String checksumAComparar = obtenerMD5ComoString(carpetaConArchivos + archivo.getName());
                    if (checksum.equals(checksumAComparar)) {
                        System.out.print(" CONTENIDO IGUAL\n");
                    } else {
                        System.out.print(" CONTENIDO DIFERENTE\n");
                    }
                }
            }
        }
        System.out.print("\t\t\t\t - - - - - - - - - -\n");
    }
**/



    @Override
    public void cloneRepository(String file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void compareFiles(String file, boolean containsDetails) {
        
    }

}
