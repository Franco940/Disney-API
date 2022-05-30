package com.alkemy.DisneyAPI.Entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Franco Roman
 */

@Entity
@Table(name = "peliculas_personaje")
public class PeliculasPersonajes {
    @Id
    private Long id;
    private String nombre;
    
    @Lob
    private byte[] imgaenPersonaje;

    public PeliculasPersonajes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImgaenPersonaje() {
        return imgaenPersonaje;
    }

    public void setImgaenPersonaje(byte[] imgaenPersonaje) {
        this.imgaenPersonaje = imgaenPersonaje;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
