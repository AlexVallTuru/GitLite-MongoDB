/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Singleton;

import com.mongodb.MongoClient;

public class MongoConnection {

    private static String repositoryFolder;
    private static MongoClient client;
    private static MongoConnection mongoClient;

    private MongoConnection() {

        this.client = new MongoClient("localhost", 27017);

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

    public static String getRepository() {
        return repositoryFolder;
    }

}
