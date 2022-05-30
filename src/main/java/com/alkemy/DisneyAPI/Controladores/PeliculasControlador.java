package com.alkemy.DisneyAPI.Controladores;

import com.alkemy.DisneyAPI.Entidades.Pelicula;
import com.alkemy.DisneyAPI.Entidades.PersonajePeliculas;
import com.alkemy.DisneyAPI.Servicios.PeliculaServicio;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    
    
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("")
    public List<PersonajePeliculas> todosLosPeliculasYFiltros(@RequestParam(required = false, name = "titulo") String titulo, 
            @RequestParam(required = false, name = "genero") String genero, 
            @RequestParam(required = false, name = "orden") String orden) throws Exception{
        
        List<Pelicula> peliculas = peliculasAcordeAlFiltro(titulo, genero, orden);
        
        // Se adapta la información a lo solicitado
        List<PersonajePeliculas> response = new ArrayList();
        for(Pelicula pelicula : peliculas){
            PersonajePeliculas peliculaResponse = new PersonajePeliculas();

            peliculaResponse.setTitulo(pelicula.getTitulo());
            peliculaResponse.setFechaCreacion(pelicula.getFechaCreacion());
            peliculaResponse.setImgaen(pelicula.getImagen());
            
            response.add(peliculaResponse);
        }
        
        return response;
    }
    
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("/detalle")
    public Pelicula peliculaConDetalle(@RequestParam(required = true, name = "titulo") String titulo){
        return servicioPelicula.buscarPeliculaPorTitulo(titulo);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public Pelicula crearPelicula(@RequestParam(required = true, name = "titulo") String titulo, 
            @RequestParam(required = true, name = "calificacion") Integer calificacion, 
            @RequestParam(required = true, name = "fechaDeCreacion") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaCreacion, 
            @RequestParam(required = true, name = "imagen") MultipartFile imagen, 
            @RequestParam(required = true, name = "personajes") String[] personajes) throws JsonProcessingException, Exception{
        
        return servicioPelicula.crearPelicula(titulo, calificacion, fechaCreacion, imagen, personajes);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/borrar")
    public ResponseEntity<String> borrarPelicula(@RequestParam(required = true, name = "id") String id){
        servicioPelicula.borrarPelicula(Long.valueOf(id));
        
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    private List<Pelicula> peliculasAcordeAlFiltro(String titulo, String genero, String orden) throws Exception{
        List<Pelicula> peliculas = servicioPelicula.buscarTodasLasPeliculas();
        
        // Si hay algún filtro, se sobre escribe la lista de peliculas
        if(titulo != null){
            peliculas = servicioPelicula.buscarPeliculasPorTitulo(titulo);
        }
        if(genero != null){
            peliculas = servicioPelicula.buscarPeliculasPorGenero(genero);
        }
        if(orden != null){
            peliculas = servicioPelicula.buscarPeliculaPorOrden(orden);
        }
        
        return peliculas;
    }
}
