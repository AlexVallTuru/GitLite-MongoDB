/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Utils.Ficheros;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    public Fitxer() {

    }

    public Fitxer(String filePath, String nomFitxer, String extensio, Long tamany, String contingut) {
        this.filePath = filePath;
        this.nomFitxer = nomFitxer;
        this.extensio = extensio;
        this.tamany = tamany;
        this.contingut = contingut;
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

    public Fitxer documentToObject(Document doc) {

        Fitxer fitxer = new Fitxer();

        fitxer.setNomFitxer(nomFitxer);
        fitxer.setTamany(tamany);
        fitxer.setFilePath(filePath);
        fitxer.setExtensio(extensio);
        fitxer.setContingut(contingut);

        return fitxer;
    }

    @Override
    public String toString() {
        return "Fitxer{" + "filePath=" + filePath + ", nomFitxer=" + nomFitxer + ", extensio=" + extensio + ", tamany=" + tamany + ", contingut=" + contingut + '}';
    }

}
