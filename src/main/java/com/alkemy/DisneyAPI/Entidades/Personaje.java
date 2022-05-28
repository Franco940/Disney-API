package com.alkemy.DisneyAPI.Entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Franco Roman
 */

@Entity
@Table(name = "personaje")
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private Integer edad;
    private Integer peso;
    private String historia;
    
    @Lob
    private byte[] imgaenPersonaje;
    
    @ManyToMany
    private List<PersonajePeliculas> pelicula;

    public Personaje() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String Historia) {
        this.historia = Historia;
    }

    public byte[] getImagen() {
        return imgaenPersonaje;
    }

    public void setImagen(byte[] imagen) {
        this.imgaenPersonaje = imagen;
    }

    public List<PersonajePeliculas> getPelicula() {
        return pelicula;
    }

    public void setPelicula(List<PersonajePeliculas> pelicula) {
        this.pelicula = pelicula;
    }
}
