/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Singleton.MongoConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.Date;
import org.bson.Document;

/**
 *
 * @author carlo
 */
public class Utils {

    public static String getAbsolutePath(File file) {
        Path path = file.toPath().toAbsolutePath();

        return path.toString().substring(2);

    }

    public static Document fileToDocument(File file) throws IOException {
        MongoConnection c = MongoConnection.getInstance();
        Path path = c.getRepositoryPath();
        String[] nom_i_extensio = file.getName().split("\\.");
        String filePath = Ficheros.getAbsolutePath(path, file);
        Document doc = new Document();
        doc
                .append("path", filePath)
                .append("nom", nom_i_extensio[0])
                .append("extensio", nom_i_extensio[1])
                .append("tamany", Files.size(file.toPath()))
                .append("modificacio", Utils.convertToTimeStamp(new Date(file.lastModified())))
                .append("contingut", Ficheros.llegir(file));

        return doc;
    }

    public static String generateRepositoryName(File file) {
        String path = file.toString().substring(3).replace("\\", "_");
        return path;

    }

    public static Boolean verificaOpcio(int opcio) {
        Scanner in = new Scanner(System.in);
        while (opcio != 1 && opcio != 2) {
            System.out.println("Opcio no valida");
            opcio = in.nextInt();

        }
        if (opcio == 1) {
            return true;
        } else {
            return false;
        }

    }

    public static Timestamp convertToTimeStamp(Date date) {
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    public static File documentToFile(Document document) throws IOException {
        String path = document.getString("path");
        String nom = document.getString("nom");
        String extensio = document.getString("extensio");
        long tamany = document.getLong("tamany");
        Date modificacio = document.getDate("modificacio");
        byte[] contingut = document.getString("contingut").getBytes();

        File file = new File(path);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(contingut);
        fos.close();

        // Establecer la fecha de modificación del archivo
        file.setLastModified(modificacio.getTime());

        return file;
    }

    public static void crearRuta(File destination, File fname,Boolean force) throws IOException {
        if (destination.mkdirs()) {
            System.out.println("Nueva ruta creada");
            fname.createNewFile();
        } else {
            System.out.println("La ruta ya existe");
            if (fname.exists()) {
                if(force){
                    fname.createNewFile();
                }
            }
            

        }
    }

}
