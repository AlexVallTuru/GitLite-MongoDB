/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Singleton;

import com.mongodb.MongoClient;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.nio.file.Path;
import org.bson.Document;

public class MongoConnection {

    private static MongoClient client;
    private static MongoConnection mongoClient;
    private static MongoDatabase dataBase;
    private String repositoryName;
    private Path repositoryPath;

    private static MongoCollection<Document> collection;

    private MongoConnection() {

        this.client = new MongoClient("localhost", 27017);
        this.dataBase = client.getDatabase("GETBD");

    }

    public static MongoConnection getInstance() {
        if (mongoClient == null) {
            mongoClient = new MongoConnection();

        }
        return mongoClient;

    }

    public MongoClient getDBClient() {
        return client;

    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public void setRepositoryPath(Path repositoryPath) {
        //Identificar el sistema operativo y en base a esto introducir
        //una barra inicial acompañada de otras que substituyan la barrabaja
        this.repositoryPath = repositoryPath;
    }

    public MongoDatabase getDataBase() {
        return dataBase;
    }

    public Path getRepositoryPath() {
        return repositoryPath;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public static MongoCollection<Document> getCollection() {
        return collection;
    }

}
