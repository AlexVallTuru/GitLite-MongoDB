/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Model.Fitxer;
import Singleton.MongoConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.security.MessageDigest;
import java.util.Date;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import static Utils.Ficheros.sonArchivosIgualesPorMD5;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import java.nio.file.Path;

/**
 *
 * @author carlo
 */
public class Ficheros {

    public static String llegir(File file) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);

        }
        reader.close();

        String content;
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            content = stringBuilder.toString();
        } else {
            content = "";
        }
        return content;
    }

    public static String getExtensio(File file) {
        String name = file.getName();
        int limit = name.lastIndexOf(".");
        String extension = name.substring(limit + 1, name.length());
        return extension;

    }

    public static void pushAllFiles(File file, boolean force) throws IOException {
        MongoConnection c = MongoConnection.getInstance();
        MongoCollection<Document> col = c.getDataBase().getCollection(c.getRepositoryName());
        File[] files = file.listFiles();

        for (File fitxers : files) {

            if (fitxers.isDirectory()) {
                //System.out.println("<Directorio> " + fitxers.getAbsolutePath());
                pushAllFiles(fitxers, force);

            } else {
                //System.out.println("<Fichero> " + fitxers.getAbsolutePath());
                if (!force) {

                    if (Ficheros.compareModifiedDate(c.getRepositoryPath(), fitxers, col)) {

                        Document update = Utils.fileToDocument(fitxers);
                        String filter = Ficheros.getAbsolutePath(c.getRepositoryPath(), fitxers);
                        col.replaceOne(Filters.eq("path", filter), update);

                    }

                } else {
                    col.insertOne(Utils.fileToDocument(fitxers));
                }

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
            } else if (fitxers.getName().endsWith(".txt")) {
                lista.add(fitxers);
            }
        }
        return (ArrayList<File>) lista;
    }

    public static void compareTwoFiles(Document localDoc, Document dbDoc, Boolean containsDetails) {

        try {
            if (compareTwoTimeStamp(localDoc, dbDoc)) {
                System.out.print("\nSon iguales!\n\n");
            } else if (sonArchivosIgualesPorMD5(localDoc, dbDoc)) {
                System.out.print("\nSon iguales!! \t Pero la ultimas fechas de modificación son diferente\n\n");
            } else {
                if (containsDetails) {
                    compareLines(localDoc, dbDoc);
                } else {
                    System.out.println("Son distintos\n\n");
                }
            }

        } catch (NoSuchFileException | ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR:\tDocumento local no encontrado\t\n");
        } catch (NullPointerException e) {
            System.out.println("ERROR:\tDocumento remoto no encontrado a la base de datos\t\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean compareTwoTimeStamp(Document doc1, Document doc2) {
        long localTimeStamp = doc1.getDate("modificacio").getTime();
        long dbTimeStamp = doc2.getDate("modificacio").getTime();
        return dbTimeStamp == localTimeStamp;
    }

    public static boolean compareModifiedDate(Path path, File file, MongoCollection collection) throws IOException {
        Date d = new Date(file.lastModified());
        String pathRepositorio = Ficheros.getAbsolutePath(path, file);
        Timestamp ts = Utils.convertToTimeStamp(d);
        Bson filter = Filters.and(Filters.eq("path", pathRepositorio), Filters.exists("path", true));
        Document c = (Document) collection.find(filter).first();
        if (c == null) {
            System.out.println("No s'ha trovat el fitxer al repositori. Es pujara automaticament");
            collection.insertOne(Utils.fileToDocument(file));
        } else {
            Timestamp ts2 = Utils.convertToTimeStamp(c.getDate("modificacio"));

            if (d.after(Utils.convertToTimeStamp(c.getDate("modificacio")))) {
                System.out.println(file.getName() + " se ha actualizado");

                return true;
            } else if (d.before(Utils.convertToTimeStamp(c.getDate("modificacio")))) {
                System.out.println("El fichero remoto es posterior, actualiza el local");
                return false;
            } else {
                System.out.println("los ficheros son iguales");
            }

        }

        return false;

    }

    public static void compareLines(Document doc1, Document doc2) throws IOException {

        int contadorDeLineas1 = 0;
        ArrayList<String> lineas1 = docToArrayListLines(doc1);
        ArrayList<String> lineas2 = docToArrayListLines(doc2);
        int contadorLineasIguales = 0;
        for (String linea1 : lineas1) {

            contadorDeLineas1++;
            int contadorDeLineas2 = 0;
            for (String linea2 : lineas2) {
                contadorDeLineas2++;
                if (contadorDeLineas1 - 1 >= lineas2.size()) {
                    if (linea1.equals(linea2)) {
                        System.out.println("La linea " + contadorDeLineas1 + " s'ha modificat.");
                        break;
                    }
                } else if (linea1.equals(lineas2.get(contadorDeLineas1 - 1))) {
                    contadorLineasIguales++;
                    break;
                } else if (linea1.equals(linea2)) {
                    System.out.println("La linea " + contadorDeLineas1 + " s'ha modificat.");
                    break;
                }
                if (contadorDeLineas2 == lineas2.size()) {
                    System.out.println("La linea " + contadorDeLineas1 + " s'ha eliminat.");
                }
            }
        }
        System.out.println("S'ha trobat " + contadorLineasIguales + " líneas iguals.\n");
    }

    public static int countLines(String str) {
        int count = 0;
        int total = str.length();
        for (int i = 0; i < total; ++i) {
            char letter = str.charAt(i);
            if (letter == '\n') {
                ++count;
            }
        }
        return count;
    }

    public static boolean sonArchivosIgualesPorMD5(Document doc1, Document doc2) throws Exception {
        ArrayList<String> localLines = docToArrayListLines(doc1);
        ArrayList<String> dbLines = docToArrayListLines(doc2);

        StringBuilder builder = new StringBuilder();
        for (String line : localLines) {
            builder.append(line);
        }
        String contenido1 = builder.toString();

        builder.setLength(0); // Reset builder

        for (String line : dbLines) {
            builder.append(line);
        }
        String contenido2 = builder.toString();

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

    public static ArrayList<String> docToArrayListLines(Document doc) throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        String content1 = doc.getString("contingut");
        BufferedReader reader = new BufferedReader(new StringReader(content1));
        String line;
        while ((line = reader.readLine()) != null) {
            lineas.add(line);
        }
        return lineas;
    }

    public static String getAbsolutePath(Path directory, File file) {
        String path = file.getAbsolutePath();
        return path.replace(directory.toString(), "");

    }

    public static void addContent(File fname, String content) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fname));
        bw.write(content);
        bw.close();

    }

    public static boolean checkDateForPull(Fitxer fitxer, String rutaRemota) {
        //Obtener conexion
        MongoConnection mc = MongoConnection.getInstance();

        //Obtener coleccion de singleton
        MongoCollection mmc = mc.getDataBase().getCollection(mc.getRepositoryName());

        //Obtener fecha de modificacion del fichero local
        Date d = fitxer.getDataModificacio();

        //Convertirlo a timestamp
        Timestamp ts = Utils.convertToTimeStamp(d);

        //Aplicar filtro para encontrar si existe en remoto
        Bson filter = Filters.and(Filters.eq("path", rutaRemota), Filters.exists("path", true));
        //Recoger el resultado de la busqueda
        Document c = (Document) mmc.find(filter).first();

        //Verificar que existe en el repositorio
        if (c == null) {
            System.out.println("No s'ha trovat el fitxer al repositori. Es pujara automaticament");

        } else {
            //Si se encuentra un resultado convertir la fecha a timestamp
            Timestamp ts2 = Utils.convertToTimeStamp(c.getDate("modificacio"));

            //Comparar fecha local con la fecha del fichero remoto
            if (ts.before(ts2)) {
                System.out.println(fitxer + " se ha actualizado");

                return true;
            } else if (ts.after(ts2)) {
                System.out.println(fitxer + " ya está actualizado");

            } else {
                System.out.println("los ficheros son iguales");

            }

        }

        return false;

    }

    public static void recursivePull() {
        MongoConnection con = MongoConnection.getInstance();
        String repositorio = con.getRepositoryPath().toString();
        MongoCollection col = con.getDataBase().getCollection(con.getRepositoryName());
        MongoCursor<Document> doc = col.find().iterator();

        while (doc.hasNext()) {
            Document g = doc.next();
            System.out.println(g.toJson());

        }

    }

}
