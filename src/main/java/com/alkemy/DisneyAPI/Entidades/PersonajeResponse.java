package com.alkemy.DisneyAPI.Entidades;

/**
 *
 * @author Franco Roman
 */

public class PersonajeResponse {
    private String nombre;
    private byte[] imgaenPersonaje;

    public PersonajeResponse() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImgaen() {
        return imgaenPersonaje;
    }

    public void setImgaen(byte[] imgaen) {
        this.imgaenPersonaje = imgaen;
    }
}
