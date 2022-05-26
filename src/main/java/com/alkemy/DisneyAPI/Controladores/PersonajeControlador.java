package com.alkemy.DisneyAPI.Controladores;

import com.alkemy.DisneyAPI.Entidades.Personaje;
import com.alkemy.DisneyAPI.Entidades.PersonajeResponse;
import com.alkemy.DisneyAPI.Servicios.PersonajeServicio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Franco Roman
 */

@RestController
@RequestMapping("/personajes")
public class PersonajeControlador {
    
    @Autowired
    private PersonajeServicio personajeServicio;
    
    
    @GetMapping("")
    public List<PersonajeResponse> todosLosPersonajesYFiltros(@RequestParam(required = false, name = "nombre") String nombre, 
            @RequestParam(required = false, name = "edad") String edad, @RequestParam(required = false, name = "peso") String peso, 
            @RequestParam(required = false, name = "idPelicula") String idPelicula, 
            @RequestParam(required = false, name = "idPersonaje") String idPersonaje) throws Exception{
        
        List<Personaje> personajes = personajeServicio.buscarTodosLosPersonajes();;
        
        // Filtros: si se buscó con un filtro, se sobre escribe personajes con los datos del filtro
        if(idPersonaje != null){
            personajes = (List<Personaje>) personajeServicio.buscarPersonajePorId(Long.valueOf(idPersonaje));
        }
        if(nombre != null){
            personajes = personajeServicio.buscarPersonajePorNombre(nombre);
        }
        if(edad != null){
            personajes = personajeServicio.buscarPersonajePorEdad(Integer.valueOf(edad));
        }
        if(peso != null){
            personajes = personajeServicio.buscarPersonajePorPeso(Integer.valueOf(peso));
        }
        if(idPelicula != null){
            personajes = personajeServicio.buscarPersonajeEnPeliculas(Integer.valueOf(idPelicula));
        }
        
        // Se adapta la información a lo solicitado
        List<PersonajeResponse> response = new ArrayList();
        for (Personaje personaje : personajes) {
            PersonajeResponse personajeResponse = new PersonajeResponse();
            
            personajeResponse.setNombre(personaje.getNombre());
            personajeResponse.setImgaen(personaje.getImagen());
            
            response.add(personajeResponse);
        }
        
        return response;
    }
    
    @PostMapping("/crear")
    public Personaje crearPersonaje(@RequestParam(required = true, name = "nombre") String nombre, 
            @RequestParam(required = true, name = "edad") Integer edad, @RequestParam(required = true, name = "peso") Integer peso, 
            @RequestParam(required = true, name = "historia") String historia, @RequestParam(required = true, name = "imagen") MultipartFile imagen, 
            @RequestParam(required = false, name = "peliculas") String [] peliculas) throws Exception{
        
        return personajeServicio.crearPersonaje(nombre, edad, peso, historia, peliculas, imagen);
    }
    
    @PutMapping("/editar")
    public Personaje modificarPersonaje(@RequestParam(required = true, name = "id") String id, @RequestParam(required = false, name = "nombre") String nombre, 
            @RequestParam(required = false, name = "edad") Integer edad, @RequestParam(required = false, name = "peso") Integer peso, 
            @RequestParam(required = false, name = "historia") String historia, @RequestParam(required = false, name = "imagen") MultipartFile imagen, 
            @RequestParam(required = false, name = "peliculas") String[] peliculas) throws Exception{
        
        Personaje personajeAModificar = personajeServicio.buscarPersonajePorId(Long.valueOf(id));
        Map<String, String> datosAModificar = new HashMap();
        
        if(nombre != null){
            datosAModificar.put("nombre", nombre);
        }else{
            datosAModificar.put("nombre", personajeAModificar.getNombre());
        }
        
        if(edad != null){
            datosAModificar.put("edad", String.valueOf(edad));
        }else{
            datosAModificar.put("edad", String.valueOf(personajeAModificar.getEdad()));
        }
        
        if(peso != null){
            datosAModificar.put("peso", String.valueOf(peso));
        }else{
            datosAModificar.put("peso", String.valueOf(personajeAModificar.getPeso()));
        }
        
        if(historia != null){
            datosAModificar.put("historia", historia);
        }else{
            datosAModificar.put("historia", personajeAModificar.getHistoria());
        }
        
        return personajeServicio.modificarPersonaje(personajeAModificar, datosAModificar, peliculas, imagen);
    }
    
    @DeleteMapping("/borrar")
    public ResponseEntity<String> borrarPersonaje(@RequestParam(required = true, name = "id") String id){
        personajeServicio.borrarPersonaje(Long.valueOf(id));
        
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
