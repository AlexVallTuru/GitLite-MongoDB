/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author carlo
 */
public interface InterfaceLogica {

    void createRepository(String ruta);

    void dropRepository();

    void pushFile(String file,Boolean force);

    File pullFile(String file,Boolean force);

    void compareFiles(String file, boolean containsDetails);
    
    void cloneRepository(String date);
    
}
