/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Singleton.MongoConnection;
import Utils.Utils;
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

    public static void repositoryList() {
        int i;
        Scanner in = new Scanner(System.in);

        MongoConnection mc = MongoConnection.getInstance();
        MongoDatabase bbdd = mc.getDataBase();
        MongoIterable<String> v = bbdd.listCollectionNames();
        List<String> f = v.into(new ArrayList<String>());
        for (i = 0; i < f.size(); i++) {
            System.out.println(String.format("%s - %s", i + 1, f.get(i)));
        }
        int opcion = in.nextInt();
        mc.setRepositoryName(f.get(opcion - 1));
        mc.setRepositoryPath(Utils.updateRepoPath(f.get(opcion - 1)));


    }

}
