/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.util.ArrayList;
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

    public static String generateRepositoryName(File file) {
        String path = file.toString().substring(3).replace("\\", "_");
        return path;

    }

    public static List<String> separarParametros(String input) {
        List<String> parametros = new ArrayList<>();
        String[] partes = input.split(":");
        for (String parte : partes) {
            parte = parte.trim();
            if (!parte.isEmpty()) {
                parametros.add(parte);
            }
        }
        return parametros;
    }

    public static boolean containsDetails(ArrayList<String> list) {
        for (String s : list) {
            if (s.contains("-d")) {
                return true;
            }
        }
        return false;
    }
}
