package com.alkemy.DisneyAPI.Servicios;

import com.alkemy.DisneyAPI.Entidades.Genero;
import com.alkemy.DisneyAPI.Entidades.Pelicula;
import com.alkemy.DisneyAPI.Entidades.PeliculasPersonajes;
import com.alkemy.DisneyAPI.Entidades.Personaje;
import com.alkemy.DisneyAPI.Repositorios.PeliculaRepositorio;
import com.alkemy.DisneyAPI.Repositorios.PeliculasPersonajesRepositorio;
import java.util.ArrayList;
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
    private PersonajeServicio personajeServicio;
    
    @Autowired
    private PeliculasPersonajesRepositorio peliculasPersonajesRepo;
    
    @Autowired
    private GeneroServicio servicioGenero;
    
    
    @Transactional()
    public Pelicula crearPelicula(String titulo, Integer calificacion, Date fechaCreacion, MultipartFile imagen, String[] personajes) throws Exception{
        validaciones(titulo, calificacion, fechaCreacion, imagen, personajes);
        
        Pelicula pelicula = new Pelicula();
        
        pelicula.setTitulo(titulo);
        pelicula.setCalificacion(calificacion);
        pelicula.setFechaCreacion(fechaCreacion);
        
        List<PeliculasPersonajes> personajesQueAparecenEnLaPelicula = new ArrayList();
        List<Long> idPersonajes = new ArrayList(); // Guardo los id de los personajes para luego actualizar su información
        for(String nombre : personajes){
            Personaje personaje = personajeServicio.buscarPersonajePorNombre(nombre);
            
            PeliculasPersonajes pr = new PeliculasPersonajes();
            pr.setId(personaje.getId());
            pr.setNombre(personaje.getNombre());
            pr.setImgaenPersonaje(personaje.getImagen());
            
            peliculasPersonajesRepo.save(pr);
            
            personajesQueAparecenEnLaPelicula.add(pr);
            idPersonajes.add(personaje.getId());
        }
        pelicula.setPersonajes(personajesQueAparecenEnLaPelicula);
        
        pelicula.setImagen(imagen.getBytes());
        
        repositorioPelicula.save(pelicula);
        personajeServicio.actualizarPeliculasPersonajes(pelicula, idPersonajes); // agrego la pelicula a los personajes que aparecen
        return pelicula;
    }
    
    @Transactional(readOnly = true)
    public List<Pelicula> buscarTodasLasPeliculas(){
        return repositorioPelicula.findAll();
    }
    
    @Transactional(readOnly = true)
    public Pelicula buscarPeliculaPorId(Long id){
        return repositorioPelicula.findById(id).get();
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
    public List<Pelicula> buscarPeliculasPorGenero(String genero){
        Genero peliculasPorGenero = servicioGenero.buscarPorGenero(genero);
        
        List<Pelicula> peliculas = peliculasPorGenero.getPeliculas();
        return peliculas;
    }
    
    @Transactional(readOnly = true)
    public List<Pelicula> buscarPeliculaPorOrden(String orden) throws Exception{
        if(orden.equals("ASC")){
            return repositorioPelicula.buscarPeliculasPorOrdenASC();
        }else if(orden.equals("DESC")){
            return repositorioPelicula.buscarPeliculasPorOrdenDESC();
        }else{
            throw new Exception("Parámetro de orden inválido. Parametros permitidos: ASC o DESC");
        }
    }
    
    @Transactional()
    public void actualizarPersonajeEnPeliculas(Personaje personaje, Long[] idPelicula){
        List<PeliculasPersonajes> personajeList = new ArrayList();
        
        // Mapeo la pelicula a esta entidad
        PeliculasPersonajes pr = new PeliculasPersonajes();
        pr.setNombre(personaje.getNombre());
        pr.setImgaenPersonaje(personaje.getImagen());
        personajeList.add(pr);
        
        peliculasPersonajesRepo.save(pr);
        
        for(Long id: idPelicula){
            Pelicula pelicula = buscarPeliculaPorId(id);
            
            List<PeliculasPersonajes> personajesQueAparecenEnLaPelicula = pelicula.getPersonajes();
            personajesQueAparecenEnLaPelicula.add(pr);
            
            pelicula.setPersonajes(personajesQueAparecenEnLaPelicula);
            repositorioPelicula.save(pelicula);
        }
    }
    
    @Transactional()
    public void borrarPelicula(Long id){
        repositorioPelicula.deleteById(id);
    }
            
    private void validaciones(String titulo, Integer calificacion, Date fechaCreacion, MultipartFile imagen, String[] personajes) throws Exception{
        if(titulo == null || titulo.isEmpty()){
            throw new Exception("El titulo no puede estar vacío");
        }
        if(calificacion == null || calificacion == 0){
            throw new Exception("La calificacion no puede ser 0");
        }
        if(calificacion <= 0 && calificacion > 5){
            throw new Exception("La caliifacion tiene que ser entre 1 y 5");
        }
        if(fechaCreacion == null){
            throw new Exception("Debe de ingrese una fecha");
        }
        if(imagen == null || imagen.isEmpty()){
            throw new Exception("Imagen requerida");
        }
        if(personajes == null || personajes.length == 0){
            throw new Exception("Por favor, ingrese los personajes");
        }
    }
}
