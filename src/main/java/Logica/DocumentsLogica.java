/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import DAO.DocumentsDAO;
import Interfaces.InterfaceLogica;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author carlo
 */
public class DocumentsLogica implements InterfaceLogica {
    
    DocumentsDAO dao = new DocumentsDAO();
    

    @Override
    public void createRepository(String ruta) {
        try {
            dao.createRepository(ruta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropRepository(String file) {
        try {
            dao.dropRepository(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pushFile(String file,String force) {
        try {
            dao.pushFile(file,force);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public File pullFile(String file) {
        try {
            //return dao.pullFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void compareFiles(ArrayList file) {
        try {
            dao.compareFiles(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cloneRepository(String file) {
        try {
            dao.cloneRepository(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
}
