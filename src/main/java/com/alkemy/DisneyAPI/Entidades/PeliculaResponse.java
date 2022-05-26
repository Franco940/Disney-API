package com.alkemy.DisneyAPI.Entidades;

import java.util.Date;

/**
 *
 * @author Franco Roman
 */

public class PeliculaResponse {
    private String titulo;
    private byte[] imagenPelicula;
    private Date fechaCreacion;

    public PeliculaResponse() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public byte[] getImgaen() {
        return imagenPelicula;
    }

    public void setImgaen(byte[] imgaen) {
        this.imagenPelicula = imgaen;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
