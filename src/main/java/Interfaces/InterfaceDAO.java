/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author carlo
 */
public interface InterfaceDAO {
    
    void createRepository(String ruta);
    void dropRepository();
    void pushFile(String file,Boolean force);
    void pullFile(String file,Boolean force);
    void compareFiles(String inputPathfile, boolean containsDetails, boolean detailLocalORemoto);
    void cloneRepository(String date);
    
}
