/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import org.bson.Document;
import org.bson.conversions.Bson;


/**
 *
 * @author carlo
 */
public class Ficheros {
    
    public static String llegir(File file) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));     
        StringBuilder sb = new StringBuilder();
        sb.append(br.readLine()).append("\n");
        
        while(br.readLine()!=null){
            sb.append(br.readLine()).append("\n");
        }
        
        return sb.toString();
            
        
    }
    
    public static String getExtensio(File file){
        String name  = file.getName();
        int limit = name.lastIndexOf(".");
        String extension = name.substring(limit+1,name.length());
        return extension;
        
    }

    public static void pushAllFiles(File file) {
        File [] files = file.listFiles();
        for(File fitxers : files){
            if(fitxers.isDirectory()){
                System.out.println("<Directorio> "+file.getAbsolutePath());
                //pushAllFiles(fitxers);
            }else{
                System.out.println("<Fichero> "+file.getAbsolutePath());
            }
            
        }
    }
    
    public static void compareModifiedDate(File file, MongoCollection collection){
        Date d = new Date(file.lastModified());
        Timestamp ts = Utils.convertToTimeStamp(d);
        System.out.println("Fichero local: "+ts);
        
        
        Bson filter = Filters.and(Filters.eq("path",file.getPath().substring(2)),Filters.exists("path",true));
        Document c = (Document) collection.find(filter).first();
        Timestamp ts2 =Utils.convertToTimeStamp(c.getDate("modificacio"));
        System.out.println(d.compareTo(ts2));
        if(d.after(Utils.convertToTimeStamp(c.getDate("modificacio")))){
            System.out.println("el fichero local esta actualizado");
        }else if(d.before(Utils.convertToTimeStamp(c.getDate("modificacio")))){
            System.out.println("el fichero local es mas viejo");
        }else{
            System.out.println("los ficheros son iguales");
        }
        //System.out.println(c.getDate("modificacio"));
        //System.out.println(d.equals(c.getDate("modificacio")));
        
        
    }
    
}
