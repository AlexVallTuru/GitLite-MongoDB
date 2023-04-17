/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Singleton.MongoConnection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Carlos
 */
public class MenuDAO {
    
    /**
     * Funcion para listar los repositorios remotos disponibles
     */
    public static void repositoryList(){
        int  i;
        Scanner in = new Scanner(System.in);
        
        MongoConnection mongoConnection = MongoConnection.getInstance();
        MongoDatabase database = mongoConnection.getDataBase();
        MongoIterable<String> documentsListIterable = database.listCollectionNames();
        List<String> documentsList = documentsListIterable.into(new ArrayList<String>());
        for (i = 0; i <documentsList.size(); i++) {
            System.out.println(String.format("%s - %s",i+1,documentsList.get(i)));
        }
        int opcion = in.nextInt();
        mongoConnection.setRepositoryName(documentsList.get(opcion-1));
        

    }
    
}
