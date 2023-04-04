/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static String generateRepositoryName(File file) {
        String path = file.toString().substring(3).replace("\\", "_");
        return path;

    }

    public static List<String> retornarExtension(){
        List<String> extensions = Arrays.asList("java", "txt", "xml", "html");
        return extensions;
    }
    public static String formatPath(String ruta) {
        String fileSeparator = System.getProperty("file.separator");
        if (ruta.matches("^[A-Za-z]:" + fileSeparator + ".*")) {
            ruta = ruta.substring(3);
        } else if (ruta.startsWith(fileSeparator) || ruta.startsWith("/")) {
            ruta = ruta.substring(1);
        }

        // Eliminar el disco del camino y el primer separador de archivos
        int index = ruta.indexOf(fileSeparator);
        if (index > -1) {
            ruta = ruta.substring(index);
        }

        return ruta;
    }

}
