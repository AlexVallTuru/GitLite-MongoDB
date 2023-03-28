/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Singleton;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.bson.Document;

public class MongoConnection {

    private static String repositoryFolder;
    private static MongoClient client;
    private static MongoConnection mongoClient;
    private static String repositoryName;
    private static Path repositoryPath;
    private static MongoDatabase dataBase;

    private MongoConnection() {

        this.client = new MongoClient("localhost", 27017);
        this.dataBase = client.getDatabase("GETBD");
        this.repositoryName = "Users_Alex_Desktop";
        this.repositoryPath = Paths.get("\\Users\\Alex\\Desktop");
    }

    public static MongoClient getInstance() {
        if (mongoClient == null) {
            mongoClient = new MongoConnection();
        }
        return client;
    }

    public static void setNewRepository(String repository) {
        repositoryFolder = repository;
    }

    public static void setRepositoryName(String repositoryName) {
        repositoryName = repositoryName;
    }

    public static void setRepositoryPath(Path repositoryPath) {
        //Identificar el sistema operativo y en base a esto introducir
        //una barra inicial acompañada de otras que substituyan la barrabaja
        repositoryPath = repositoryPath;
    }

    public static String getRepository() {
        return repositoryFolder;
    }

    public static MongoDatabase getDataBase() {
        return dataBase;
    }
}
