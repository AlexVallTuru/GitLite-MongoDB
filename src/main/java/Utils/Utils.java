/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        String[] nom_i_extensio = file.getName().split("\\.");

        Document doc = new Document();
        doc
                .append("path", file.getAbsolutePath().substring(2))
                .append("nom", nom_i_extensio[0])
                .append("extensio", nom_i_extensio[1])
                .append("tamany", Files.size(file.toPath()))
                .append("modificacio", new Date(file.lastModified()))
                .append("contingut", Ficheros.llegir(file));

        return doc;

    }

    public static File documentToFile(Document document) throws IOException {
        String path = document.getString("path");
        String nom = document.getString("nom");
        String extensio = document.getString("extensio");
        int tamany = document.getInteger("tamany");
        Date modificacio = document.getDate("modificacio");
        byte[] contingut = document.getString("contingut").getBytes();

        File file = new File(path);
        Files.write(file.toPath(), contingut);

        // Establecer la fecha de modificación del archivo
        file.setLastModified(modificacio.getTime());

        return file;
    }

    public static String generateRepositoryName(File file) {
        String path = file.toString().substring(3).replace("\\", "_");
        return path;

    }
}
