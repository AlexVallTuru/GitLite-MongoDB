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
    private String nombreFichero;
    private String extension;
    private Long fileSize;
    private String contenido;
    private String parentPath;
    private Date fechaModificacion;

    public Fitxer() {

    }

    public Fitxer(String filePath, String nombreFichero, String extension, Long fileSize, String contenido,String parentPath,Date fechaModificacion) {
        this.filePath = filePath;
        this.nombreFichero = nombreFichero;
        this.extension = extension;
        this.fileSize = fileSize;
        this.contenido = contenido;
        this.parentPath=parentPath;
        this.fechaModificacion = fechaModificacion;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getNomFitxer() {
        return nombreFichero;
    }

    public void setNombreFichero(String nomFitxer) {
        this.nombreFichero = nomFitxer;
    }

    public String getExtensio() {
        return extension;
    }

    public void setExtension(String extensio) {
        this.extension = extensio;
    }

    public Long getTamany() {
        return fileSize;
    }

    public void setFileSize(Long tamany) {
        this.fileSize = tamany;
    }

    public String getContingut() {
        return contenido;
    }

    public void setContenido(String contingut) {
        this.contenido = contingut;
    }
    
    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String contingut) {
        this.parentPath = contingut;
    }

    public Date getDataModificacio() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date dataModificacio) {
        this.fechaModificacion = dataModificacio;
    }

    public Fitxer documentToObject(Document doc,String path) {

        Fitxer fitxer = new Fitxer();
        fitxer.setNombreFichero(doc.getString("nom"));
        fitxer.setFileSize(doc.getLong("tamany"));
        fitxer.setFilePath(path);
        fitxer.setExtension(doc.getString("extensio"));
        fitxer.setContenido(doc.getString("contingut"));
        fitxer.setParentPath(new File(path).getParent());
        fitxer.setFechaModificacion(doc.getDate("modificacio"));
        return fitxer;
    }

    @Override
    public String toString() {
        return String.format("Nombre: %s\nTamaño: %s\nPath: %s\nExtension: %s\nParent: %s\nContenido: \n%s",nombreFichero,fileSize,filePath,extension,parentPath,contenido);
    }

}
