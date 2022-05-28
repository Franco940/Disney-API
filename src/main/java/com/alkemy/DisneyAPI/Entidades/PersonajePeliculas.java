package com.alkemy.DisneyAPI.Entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Franco Roman
 */

@Entity
@Table(name = "personaje_peliculas")
public class PersonajePeliculas {
    @Id
    private Long id;
    private String titulo;
    
    @Lob
    private byte[] imagenPelicula;
    
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    public PersonajePeliculas() {
    }

    public Long getId() {
        return id;
    }
    
    

    public void setId(Long id) {
        this.id = id;
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
    
    public void setImagenPelicula(byte[] imagenPelicula) {
        this.imagenPelicula = imagenPelicula;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public byte[] getImagenPelicula() {
        return imagenPelicula;
    }
}
