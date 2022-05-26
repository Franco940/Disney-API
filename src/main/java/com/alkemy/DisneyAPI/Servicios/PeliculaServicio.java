package com.alkemy.DisneyAPI.Servicios;

import com.alkemy.DisneyAPI.Entidades.Pelicula;
import com.alkemy.DisneyAPI.Entidades.Personaje;
import com.alkemy.DisneyAPI.Repositorios.PeliculaRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Franco Roman
 */

@Service
public class PeliculaServicio {
    
    @Autowired
    private PeliculaRepositorio repositorioPelicula;
    
    @Autowired
    private GeneroServicio servicioGenero;
    
    
    @Transactional()
    public Pelicula crearPelicula(String titulo, Integer calificacion, Date fechaCreacion, MultipartFile imagen, String[] personajes) throws Exception{
        validaciones(titulo, calificacion, fechaCreacion, imagen, personajes);
        
        Pelicula pelicula = new Pelicula();
        
        pelicula.setTitulo(titulo);
        pelicula.setCalificacion(calificacion);
        
        Date fecha = new Date();
        
        pelicula.setImagen(imagen.getBytes());
        
        return pelicula;
    }
    
    @Transactional(readOnly = true)
    public List<Pelicula> buscarTodasLasPeliculas(){
        return repositorioPelicula.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Pelicula> buscarPeliculasPorTitulo(String titulo){
        return repositorioPelicula.buscarPeliculasPorTitulo(titulo);
    }
    
    @Transactional(readOnly = true)
    public Pelicula buscarPeliculaPorTitulo(String titulo){
        return repositorioPelicula.buscarPeliculaPorTitulo(titulo);
    }
    
    @Transactional(readOnly = true)
    public List<Pelicula> buscarPeliculaPorGenero(String genero){
        return servicioGenero.buscarPeliculaPorGenero(genero);
    }
    
    private void validaciones(String titulo, Integer calificacion, Date fechaCreacion, MultipartFile imagen, String[] personajes) throws Exception{
        if(titulo == null || titulo.isEmpty()){
            throw new Exception("El titulo no puede estar vacío");
        }
        if(calificacion == null || calificacion == 0){
            throw new Exception("La calificacion no puede ser 0");
        }
        if(calificacion > 0 || calificacion < 5){
            throw new Exception("La caliifacion tiene que ser entre 1 y 5");
        }
        if(fechaCreacion == null){
            throw new Exception("Debe de ingrese una fecha");
        }
        if(imagen == null || imagen.isEmpty()){
            throw new Exception("Imagen requerida");
        }
        if(personajes.length == 0){
            throw new Exception("Por favor, ingrese los personajes");
        }
    }
}
