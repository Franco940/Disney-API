package com.alkemy.DisneyAPI.Controladores;

import com.alkemy.DisneyAPI.Entidades.Personaje;
import com.alkemy.DisneyAPI.Entidades.PeliculasPersonajes;
import com.alkemy.DisneyAPI.Servicios.PersonajeServicio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/personajes")
public class PersonajeControlador {
    
    @Autowired
    private PersonajeServicio personajeServicio;
    
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<PeliculasPersonajes>> todosLosPersonajesYFiltros(@RequestParam(required = false, name = "nombre") String nombre, 
            @RequestParam(required = false, name = "edad") String edad, @RequestParam(required = false, name = "peso") String peso, 
            @RequestParam(required = false, name = "idPelicula") String idPelicula, 
            @RequestParam(required = false, name = "idPersonaje") String idPersonaje) throws Exception{
        
        // Se busca la información acorde a la petición
        List<Personaje> personajes = personajesAcordeAlFiltro(idPersonaje, nombre, edad, peso, idPelicula);
        
        // Se adapta la información a lo solicitado
        List<PeliculasPersonajes> response = new ArrayList();
        for (Personaje personaje : personajes) {
            PeliculasPersonajes personajeResponse = new PeliculasPersonajes();
            
            personajeResponse.setNombre(personaje.getNombre());
            personajeResponse.setImgaenPersonaje(personaje.getImagen());
            
            response.add(personajeResponse);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("/detalle")
    public ResponseEntity<Personaje> personajeConDetalle(@RequestParam(required = true, name = "nombre") String nombre){
        Personaje personaje = personajeServicio.buscarPersonajePorNombre(nombre);
        
        return new ResponseEntity<> (personaje, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<Personaje> crearPersonaje(@RequestParam(required = true, name = "nombre") String nombre, 
            @RequestParam(required = true, name = "edad") Integer edad, @RequestParam(required = true, name = "peso") Integer peso, 
            @RequestParam(required = true, name = "historia") String historia, @RequestParam(required = true, name = "imagen") MultipartFile imagen, 
            @RequestParam(required = false, name = "peliculas") String [] peliculas) throws Exception{
        
        Personaje personaje = personajeServicio.crearPersonaje(nombre, edad, peso, historia, peliculas, imagen);
        
        return new ResponseEntity<>(personaje, HttpStatus.ACCEPTED);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/editar")
    public ResponseEntity<Personaje> modificarPersonaje(@RequestParam(required = true, name = "id") String id, @RequestParam(required = false, name = "nombre") String nombre, 
            @RequestParam(required = false, name = "edad") Integer edad, @RequestParam(required = false, name = "peso") Integer peso, 
            @RequestParam(required = false, name = "historia") String historia, @RequestParam(required = false, name = "imagen") MultipartFile imagen, 
            @RequestParam(required = false, name = "peliculas") String[] peliculas) throws Exception{
        
        Personaje personajeAModificar = personajeServicio.buscarPersonajePorId(Long.valueOf(id));
        Map<String, String> datosAModificar = guardarDatosAModificar(personajeAModificar, nombre, edad, peso, historia);
        
        Personaje personaje = personajeServicio.modificarPersonaje(personajeAModificar, datosAModificar, peliculas, imagen);
        
        return new ResponseEntity<>(personaje, HttpStatus.ACCEPTED);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/borrar")
    public ResponseEntity<String> borrarPersonaje(@RequestParam(required = true, name = "id") String id){
        personajeServicio.borrarPersonaje(Long.valueOf(id));
        
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    private List<Personaje> personajesAcordeAlFiltro(String idPersonaje, String nombre, String edad, String peso, String idPelicula){
        List<Personaje> personajes = personajeServicio.buscarTodosLosPersonajes();
        
        // Si hay algún filtro, se sobre escribe la lista de personajes
        if(idPersonaje != null){
            personajes = (List<Personaje>) personajeServicio.buscarPersonajePorId(Long.valueOf(idPersonaje));
        }
        if(nombre != null){
            personajes = personajeServicio.buscarPersonajesPorNombre(nombre);
        }
        if(edad != null){
            personajes = personajeServicio.buscarPersonajePorEdad(Integer.valueOf(edad));
        }
        if(peso != null){
            personajes = personajeServicio.buscarPersonajePorPeso(Integer.valueOf(peso));
        }
        if(idPelicula != null){
            personajes = personajeServicio.buscarPersonajeEnPeliculas(Long.valueOf(idPelicula));
        }
        
        return personajes;
    }
    
    private Map<String, String> guardarDatosAModificar(Personaje personajeSinModificar, String nombre, Integer edad, Integer peso, String historia){
        Map<String, String> datosAModificar = new HashMap();
        
        if(nombre != null){
            datosAModificar.put("nombre", nombre);
        }else{
            datosAModificar.put("nombre", personajeSinModificar.getNombre());
        }
        
        if(edad != null){
            datosAModificar.put("edad", String.valueOf(edad));
        }else{
            datosAModificar.put("edad", String.valueOf(personajeSinModificar.getEdad()));
        }
        
        if(peso != null){
            datosAModificar.put("peso", String.valueOf(peso));
        }else{
            datosAModificar.put("peso", String.valueOf(personajeSinModificar.getPeso()));
        }
        
        if(historia != null){
            datosAModificar.put("historia", historia);
        }else{
            datosAModificar.put("historia", personajeSinModificar.getHistoria());
        }
        
        return datosAModificar;
    }
}
