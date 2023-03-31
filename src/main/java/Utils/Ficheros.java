/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

import static Utils.Ficheros.sonArchivosIgualesPorMD5;
import static Utils.Utils.documentToFile;
import static Utils.Utils.fileToDocument;

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

    public static ArrayList<File> compareAllFiles(File file) {
        List<File> lista = new ArrayList<>();
        File[] files = file.listFiles();
        for (File fitxers : files) {
            if (fitxers.isDirectory()) {
                List<File> listaRecursiva = new ArrayList<>();
                listaRecursiva = compareAllFiles(fitxers);
                lista.addAll(listaRecursiva);
            } else if(fitxers.getName().endsWith(".txt")) {
                    lista.add(fitxers);
            }
        }
        return (ArrayList<File>) lista;
    }

    public static void compareTwoFiles(Document localDoc, Document dbDoc, Boolean containsDetails){

    try{
        long localTimeStamp = localDoc.getDate("modificacio").getTime();
        String contenidoLocal = localDoc.getString("contingut");
        long dbTimeStamp = dbDoc.getDate("modificacio").getTime();
        String contenidoDb = dbDoc.getString("contingut");

        if (dbTimeStamp == localTimeStamp) {
            System.out.print("\nSon iguales!\n\n");
        } else if (sonArchivosIgualesPorMD5(contenidoDb, contenidoLocal)) {
            System.out.print("\nSon iguales!! \t Pero la ultimas fechas de modificación son diferente\n\n");
        } else {
            if (containsDetails){
                compareLines(localDoc,dbDoc);
            }else {
                System.out.println("Son distintos\n\n");
            }

        }

    } catch (NoSuchFileException | ArrayIndexOutOfBoundsException e) {
        System.out.println("ERROR:\tDocumento local no encontrado\t\n");
    } catch (NullPointerException e){
        System.out.println("ERROR:\tDocumento remoto no encontrado a la base de datos\t\n");
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    }

    public static void compareModifiedDate(File file, MongoCollection collection) {
        Date d = new Date(file.lastModified());
        Document c = (Document) collection.find(Filters.eq("path", file.getPath().substring(2))).first();
        System.out.println(c.getDate("modificacio"));
        System.out.println(d.equals(c.getDate("modificacio")));
    }

    public static void compareLines(Document doc1, Document doc2) {
        String line;
        String line1;

        try {
            // Extraer el contenido de los documentos
            String content1 = doc1.getString("contingut");
            String content2 = doc2.getString("contingut");

            // Crear un lector de cadenas para cada documento
            StringReader reader1 = new StringReader(content1);
            BufferedReader lectura = new BufferedReader(reader1);

            StringReader reader2 = new StringReader(content2);
            BufferedReader lectura1 = new BufferedReader(reader2);

            int contadorDeLineas = 1; // Empezamos en la línea 1

            while ((line = lectura.readLine()) != null) {
                line1 = lectura1.readLine();
                contadorDeLineas++;
                if (!line.equals(line1)) {
                    Boolean contadorDeLineasEliminadas = true;
                    //TODO REALIZAR EL CONTAR DE LINEAS DEL DOC2 BIEN REALIZADO, YA QUE ACTUALMENTE VA MAL
                    for (int i=0; i >= lectura1.lines().count();i++){
                        if(line.equals(line1)){
                            System.out.print("La linea " +contadorDeLineas+ " s'ha modificat\n");
                            contadorDeLineasEliminadas = false;
                            break;
                        }
                    }
                    if(!contadorDeLineasEliminadas){
                        System.out.print("La linea " +contadorDeLineas+" s'ha eliminat\n");
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace(System.out);
        }
    }

    public static boolean sonArchivosIgualesPorMD5(String contenido1, String contenido2) throws Exception {
        byte[] bytesContenido1 = contenido1.getBytes(StandardCharsets.UTF_8);
        byte[] bytesContenido2 = contenido2.getBytes(StandardCharsets.UTF_8);
        String md5Contenido1 = obtenerMD5ComoString(bytesContenido1);
        String md5Contenido2 = obtenerMD5ComoString(bytesContenido2);
        return md5Contenido1.equals(md5Contenido2);
    }

    public static String obtenerMD5ComoString(byte[] contenido) throws Exception {
        MessageDigest complete = MessageDigest.getInstance("MD5");
        complete.update(contenido);
        byte[] b = complete.digest();
        StringBuilder resultado = new StringBuilder();
        for (byte unByte : b) {
            resultado.append(Integer.toString((unByte & 0xff) + 0x100, 16).substring(1));
        }
        return resultado.toString();
    }
}
