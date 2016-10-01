 /*choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proyectoHeliox.helper.entidades;

import java.awt.Image;

/**
 * Representa los atributos de un botón.
 * @author Mariana García
 */
public class Boton {
    
    private int id;
    private Image icono;
    private String rutaIcono;
    private String descripcion;
    private String rutaEjecutable;
    private String rutaAudio;

    /**
     * @return the icono
     */
    public Image getIcono() {
        return icono;
    }

    /**
     * @param icono the icono to set
     */
    public void setIcono(Image icono) {
        this.icono = icono;
    }

    /**
     * @return the rutaIcono
     */
    public String getRutaIcono() {
        return rutaIcono;
    }

    /**
     * @param rutaIcono the rutaIcono to set
     */
    public void setRutaIcono(String rutaIcono) {
        this.rutaIcono = rutaIcono;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the rutaEjecutable
     */
    public String getRutaEjecutable() {
        return rutaEjecutable;
    }

    /**
     * @param rutaEjecutable the rutaEjecutable to set
     */
    public void setRutaEjecutable(String rutaEjecutable) {
        this.rutaEjecutable = rutaEjecutable;
    }

    /**
     * @return the rutaAudio
     */
    public String getRutaAudio() {
        return rutaAudio;
    }

    /**
     * @param rutaAudio the rutaAudio to set
     */
    public void setRutaAudio(String rutaAudio) {
        this.rutaAudio = rutaAudio;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
