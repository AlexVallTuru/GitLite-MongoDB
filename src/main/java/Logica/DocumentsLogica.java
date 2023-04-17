/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import DAO.DocumentsDAO;
import Interfaces.InterfaceLogica;
import java.io.File;

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
    public void dropRepository() {
        try {
            dao.dropRepository();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pushFile(String file,Boolean force) {
        try {
            dao.pushFile(file,force);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public File pullFile(String file,Boolean force) {
        try {
            dao.pullFile(file,force);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void compareFiles(String file, boolean containsDetails, boolean detailLocalORemoto) {
        try {
            dao.compareFiles(file,containsDetails,detailLocalORemoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cloneRepository(String date) {
        try {
            dao.cloneRepository(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
}
