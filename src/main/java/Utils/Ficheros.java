/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Singleton.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author carlo
 */
public class Ficheros {

    public static String llegir(File file) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        sb.append(br.readLine());

        while (br.readLine() != null) {
            sb.append(br.readLine()).append("\n");
        }

        return sb.toString();

    }

    public static String getExtensio(File file) {
        String name = file.getName();
        int limit = name.lastIndexOf(".");
        String extension = name.substring(limit + 1, name.length());
        return extension;

    }

    public static void pushAllFiles(File file) {
        File[] files = file.listFiles();
        for (File fitxers : files) {
            if (fitxers.isDirectory()) {
                pushAllFiles(fitxers);
            } else {

            }

        }
    }

    public static List<File> compareAllFiles(File file) {
        List<File> lista = new ArrayList<>();
        File[] files = file.listFiles();
        for (File fitxers : files) {
            if (fitxers.isDirectory()) {
                compareAllFiles(fitxers);
            } else {
                lista.add(fitxers);
            }
        }
        return lista;
    }

    public static void compareModifiedDate(File file, MongoCollection collection) {
        Date d = new Date(file.lastModified());
        Document c = (Document) collection.find(Filters.eq("path", file.getPath().substring(2))).first();
        System.out.println(c.getDate("modificacio"));
        System.out.println(d.equals(c.getDate("modificacio")));
    }

    public static void compareLines() {
        String line;
        String line1 = null;

        try {
            File documento;
            File documento1;
            documento = new File("C:\\Users\\Alex\\Desktop\\RF05.txt");
            documento1 = new File("C:\\Users\\Alex\\Desktop\\RF05 - copia.txt");
            FileReader lector = new FileReader(documento);
            BufferedReader lectura = new BufferedReader(lector);

            //Documento 2
            FileReader lector1 = new FileReader(documento1);
            BufferedReader lectura1 = new BufferedReader(lector1);

            int contadorDeLineas = 1; // Empezamos en la línea 1
            System.out.println("-------------------- Comparando los archivos -------------------------");
            while ((line = lectura.readLine()) != null) {
                line1 = lectura1.readLine();
                contadorDeLineas++;
                if (!line.equals(line1)) {
                    System.out.println("Linea " + contadorDeLineas + " diferente");
                } else {
                    System.out.println("Linea" + contadorDeLineas + " igual");
                }
            }
            System.err.println("----------------- No quedan mas lineas a analizar -----------------------\n");
        } catch (Exception exception) {
            exception.printStackTrace(System.out);
        }
    }

    public static boolean sonArchivosIgualesPorMD5(File archivo1, File archivo2) throws Exception {
        String md5Archivo1 = obtenerMD5ComoString(archivo1);
        String md5Archivo2 = obtenerMD5ComoString(archivo2);
        return md5Archivo1.equals(md5Archivo2);
    }
    public static String obtenerMD5ComoString(File archivo) throws Exception {
        // Convertir el arreglo de bytes a cadena
        byte[] b = obtenerChecksum(archivo);
        StringBuilder resultado = new StringBuilder();

        for (byte unByte : b) {
            resultado.append(Integer.toString((unByte & 0xff) + 0x100, 16).substring(1));
        }
        return resultado.toString();
    }

    public static byte[] obtenerChecksum(File archivo) throws Exception {
        InputStream fis = new FileInputStream(archivo);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        // Leer el archivo pedazo por pedazo
        do {
            // Leer datos y ponerlos dentro del búfer
            numRead = fis.read(buffer);
            // Si se leyó algo, se actualiza el MessageDigest
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        // Devolver el arreglo de bytes
        return complete.digest();
    }
}
