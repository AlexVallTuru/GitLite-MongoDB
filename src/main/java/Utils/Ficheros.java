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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import org.bson.Document;


/**
 *
 * @author carlo
 */
public class Ficheros {
    
    public static String llegir(File file) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));     
        StringBuilder sb = new StringBuilder();
        sb.append(br.readLine());
        
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
                pushAllFiles(fitxers);
            }else{
                
            }
            
        }
    }
    
    public static void compareModifiedDate(File file, MongoCollection collection){
        Date d = new Date(file.lastModified());
        Document c = (Document) collection.find(Filters.eq("path",file.getPath().substring(2))).first();
        System.out.println(c.getDate("modificacio"));
        System.out.println(d.equals(c.getDate("modificacio")));
        
        
    }
    
}
