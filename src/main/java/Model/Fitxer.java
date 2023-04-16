/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.File;
import java.util.Date;
import org.bson.Document;

/**
 *
 * @author carlo
 */
public class Fitxer {

    private String filePath;
    private String nomFitxer;
    private String extensio;
    private Long tamany;
    private String contingut;
    private String parent;
    private Date dataModificacio;

    public Fitxer() {

    }

    public Fitxer(String filePath, String nomFitxer, String extensio, Long tamany, String contingut,String parent,Date dataModificacio) {
        this.filePath = filePath;
        this.nomFitxer = nomFitxer;
        this.extensio = extensio;
        this.tamany = tamany;
        this.contingut = contingut;
        this.parent=parent;
        this.dataModificacio = dataModificacio;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getNomFitxer() {
        return nomFitxer;
    }

    public void setNomFitxer(String nomFitxer) {
        this.nomFitxer = nomFitxer;
    }

    public String getExtensio() {
        return extensio;
    }

    public void setExtensio(String extensio) {
        this.extensio = extensio;
    }

    public Long getTamany() {
        return tamany;
    }

    public void setTamany(Long tamany) {
        this.tamany = tamany;
    }

    public String getContingut() {
        return contingut;
    }

    public void setContingut(String contingut) {
        this.contingut = contingut;
    }
    
    public String getParentPath() {
        return parent;
    }

    public void setParentPath(String contingut) {
        this.parent = contingut;
    }

    public Date getDataModificacio() {
        return dataModificacio;
    }

    public void setDataModificacio(Date dataModificacio) {
        this.dataModificacio = dataModificacio;
    }

    public Fitxer documentToObject(Document doc,String path) {

        Fitxer fitxer = new Fitxer();
        fitxer.setNomFitxer(doc.getString("nom"));
        fitxer.setTamany(doc.getLong("tamany"));
        fitxer.setFilePath(path);
        fitxer.setExtensio(doc.getString("extensio"));
        fitxer.setContingut(doc.getString("contingut"));
        fitxer.setParentPath(new File(path).getParent());
        fitxer.setDataModificacio(doc.getDate("modificacio"));
        return fitxer;
    }

    @Override
    public String toString() {
        return String.format("Nom: %s\nTamany: %s\nPath: %s\nExtensio: %s\nParent: %s\nContingut: \n%s",nomFitxer,tamany,filePath,extensio,parent,contingut);
    }

}
