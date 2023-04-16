/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

/**
 *
 * @author carlo
 */
public interface InterfaceDAO {
    
    void createRepository(String ruta);
    void dropRepository();
    void pushFile(String file,Boolean force);
    void pullFile(String file,Boolean force);
    void compareFiles(String file, boolean containsDetails) throws Exception;
    void cloneRepository(String date);
    
}
