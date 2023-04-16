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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    
    /**
     * Canvia una ruta canviant els separadors de fitxers per _. Per exemple:
     * "C:\Users\mole6\OneDrive\Documentos\NetBeansProjects" es converteix en
     * "Users_mole6_OneDrive_Documentos_NetBeansProjects".
     * 
     * @param ruta
     * @return 
     */
    public static String pathToRepoName(String ruta) {
        // Comproba si la ruta conté un identificador d'unitat. Si existeix, l'elimina
        if (ruta.matches("^[A-Za-z]:[/\\\\].*")) {
            ruta = ruta.substring(3);

        // Si no conté cap identificador d'unitat, només comproba si comença
        // amb un separador i l'elimina
        } else if (ruta.startsWith(System.getProperty("file.separator")) 
                || ruta.startsWith("/")) {
            ruta = ruta.substring(1);
        }

        // Canvia els separadors de ruta per _
        ruta = ruta.replaceAll("[/\\\\]", "_");
        return ruta;
    }

    /**
     * Dona un format a un string amb una data si aquest es válid i retorna el
     * valor Date.
     *
     * @param date
     * @return
     */
    public static Date dateFormat(String date) {
        Date newDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            newDate = dateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return newDate;
    }

    public static Timestamp convertToTimeStamp(Date date) {
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    /**
     * Comproba si una data introduïda per text de l'usuari te un format válid.
     *
     * @param data
     * @return
     */
    public static Boolean verificaData(String data) {
        boolean check;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(data);
            check = true;
        } catch (ParseException e) {
            check = false;
        }
        return check;
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

    public static void crearRuta(File destination, File fname, Boolean force) throws IOException {
        if (destination.mkdirs()) {
            System.out.println("Nueva ruta creada");
            fname.createNewFile();
        } else {
            System.out.println("La ruta ya existe");
            if (fname.exists()) {
                if (force) {
                    fname.createNewFile();
                }
            }

        }
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
