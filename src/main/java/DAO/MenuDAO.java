/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Singleton.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class MenuDAO {
    
    public static void repositoryList(){
        MongoClient mc = MongoConnection.getInstance();
        MongoDatabase bbdd = mc.getDatabase("GETDB");
        MongoIterable<String> v = bbdd.listCollectionNames();
        List<String> f = v.into(new ArrayList<String>());
        f.forEach(n -> System.out.println(n));

    }
    
}
