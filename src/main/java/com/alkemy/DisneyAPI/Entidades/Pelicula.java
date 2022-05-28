package com.alkemy.DisneyAPI.Entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Franco Roman
 */

@Entity
@Table(name = "pelicula")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titulo;
    private Integer calificacion;
    
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    
    @Lob
    private byte[] imagenPelicula;
    
    @ManyToMany
    private List<PeliculasPersonajes> personajes;
    

    public Pelicula() {
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public byte[] getImagen() {
        return imagenPelicula;
    }

    public void setImagen(byte[] imagen) {
        this.imagenPelicula = imagen;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<PeliculasPersonajes> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<PeliculasPersonajes> personajes) {
        this.personajes = personajes;
    }
}
