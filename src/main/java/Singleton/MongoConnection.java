/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Singleton;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

    private static MongoClient client;
    private static MongoDatabase bbdd;
    private static MongoConnection mongoClient;

    private MongoConnection() {

        this.client = new MongoClient("localhost", 27017);
        this.bbdd = client.getDatabase("GETDB");

    }

    public static MongoClient getInstance() {
        if (mongoClient == null) {
            mongoClient = new MongoConnection();

        }
        return client;

    }

    public static MongoDatabase getDB() {
        return bbdd;
    }

}
