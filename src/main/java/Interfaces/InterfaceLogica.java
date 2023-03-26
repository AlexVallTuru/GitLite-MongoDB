/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.io.File;

/**
 *
 * @author carlo
 */
public interface InterfaceLogica {

    void createRepository(String ruta);

    void dropRepository(String repository);

    void pushFile(String file,String force);

    File pullFile(String file);

    void compareFiles(String file);
    
    void cloneRepository(String file);
    
}
