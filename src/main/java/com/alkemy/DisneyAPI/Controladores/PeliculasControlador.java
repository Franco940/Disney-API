package com.alkemy.DisneyAPI.Controladores;

import com.alkemy.DisneyAPI.Entidades.Pelicula;
import com.alkemy.DisneyAPI.Entidades.PeliculaResponse;
import com.alkemy.DisneyAPI.Entidades.Personaje;
import com.alkemy.DisneyAPI.Servicios.PeliculaServicio;
import com.alkemy.DisneyAPI.Servicios.PersonajeServicio;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Franco Roman
 */

@RestController
@RequestMapping("/peliculas")
public class PeliculasControlador {
    
    @Autowired
    private PeliculaServicio servicioPelicula;
    
    @Autowired
    private PersonajeServicio servicioPersonaje;
    
    
    @GetMapping("")
    public List<PeliculaResponse> todosLosPeliculasYFiltros(@RequestParam(required = false, name = "titulo") String titulo, 
            @RequestParam(required = false, name = "genero") String genero, 
            @RequestParam(required = false, name = "orden") String orden){
        
        List<Pelicula> peliculas = servicioPelicula.buscarTodasLasPeliculas();
        
        // TODO: HACER UN METODO PARA EL FILTRO
        // Filtros: si se buscó con un filtro, se sobre escribe personajes con los datos del filtro
        if(titulo != null){
            peliculas = servicioPelicula.buscarPeliculasPorTitulo(titulo);
        }
        if(genero != null){
            peliculas = servicioPelicula.buscarPeliculaPorGenero(genero);
        }
        if(orden != null){
            // logica para mostrar de forma ascendente o descendente
            if(orden.equals("ASC")){
                
            }
            if(orden.endsWith("DESC")){
                
            }
        }
        
        // Se adapta la información a lo solicitado
        List<PeliculaResponse> response = new ArrayList();
        for(Pelicula pelicula : peliculas){
            PeliculaResponse peliculaResponse = new PeliculaResponse();

            peliculaResponse.setTitulo(pelicula.getTitulo());
            peliculaResponse.setFechaCreacion(pelicula.getFechaCreacion());
            peliculaResponse.setImgaen(pelicula.getImagen());
            
            response.add(peliculaResponse);
        }
        
        return response;
    }
    
    @PostMapping("/crear")
    public Pelicula crearPelicula(@RequestParam(required = true, name = "titulo") String titulo, 
            @RequestParam(required = true, name = "calificacion") Integer calificacion, 
            @RequestParam(required = true, name = "fechaDeCreacion") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaCreacion, 
            @RequestParam(required = true, name = "imagen") MultipartFile imagen, 
            @RequestParam(required = true, name = "personajes") String[] personajes) throws JsonProcessingException, Exception{
        
        return servicioPelicula.crearPelicula(titulo, calificacion, fechaCreacion, imagen, personajes);
    }
}
