package com.alkemy.DisneyAPI.Servicios;

import com.alkemy.DisneyAPI.Entidades.Pelicula;
import com.alkemy.DisneyAPI.Entidades.PersonajePeliculas;
import com.alkemy.DisneyAPI.Entidades.Personaje;
import com.alkemy.DisneyAPI.Repositorios.PersonajePeliculasRepositorio;
import com.alkemy.DisneyAPI.Repositorios.PersonajeRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Franco Roman
 */

@Service
public class PersonajeServicio {
    
    @Autowired
    private PersonajeRepositorio personajeRepo;
    
    @Autowired
    private PersonajePeliculasRepositorio personajePeliculasRepo;
    
    @Autowired
    private PeliculaServicio peliculaServicio;
    
    
    @Transactional()
    public Personaje crearPersonaje(String nombre, Integer edad, Integer peso, String historia, 
            String [] peliculas, MultipartFile imagen) throws Exception{
        
        validaciones(nombre, edad, peso, historia);
        
        Personaje personaje = new Personaje();
        
        personaje.setNombre(nombre);
        personaje.setEdad(edad);
        personaje.setPeso(peso);
        personaje.setHistoria(historia);
        
        if(imagen != null){
            if(!imagen.isEmpty()){
                personaje.setImagen(imagen.getBytes());
            }else{
                throw new Exception("Imagen requerida");
            }
        }
        
        personajeRepo.save(personaje);
        return personaje;
    }
    
    @Transactional()
    public Personaje modificarPersonaje(Personaje personaje, Map<String, String> nuevosDatos, 
            String [] peliculas, MultipartFile imagen) throws Exception{
        
        personaje.setNombre(nuevosDatos.get("nombre"));
        personaje.setEdad(Integer.valueOf(nuevosDatos.get("edad")));
        personaje.setPeso(Integer.valueOf(nuevosDatos.get("peso")));
        personaje.setHistoria(nuevosDatos.get("historia"));
        
        if(imagen != null){
            if(!imagen.isEmpty()){
                personaje.setImagen(imagen.getBytes());
            }else{
                throw new Exception("No ha llegado correctamente la imagen");
            }
        }
        
        if(peliculas != null){
            List<PersonajePeliculas> peliculasEncontradas = new ArrayList();
            List<Long> idPeliculas = new ArrayList();
            for(String peliculaNombre : peliculas){
                Pelicula pelicula = peliculaServicio.buscarPeliculaPorTitulo(peliculaNombre);
                
                // Mapeo la pelicula encontrada
                PersonajePeliculas pr = new PersonajePeliculas();
                pr.setId(pelicula.getId());
                pr.setTitulo(pelicula.getTitulo());
                pr.setFechaCreacion(pelicula.getFechaCreacion());
                pr.setImgaen(pelicula.getImagen());
                
                personajePeliculasRepo.save(pr);
                idPeliculas.add(pelicula.getId());
                peliculasEncontradas.add(pr);
            }
            
            personaje.setPelicula(peliculasEncontradas);
        }
        
        personajeRepo.save(personaje);
        return personaje;
    }
    
    @Transactional()
    public void actualizarPeliculasPersonajes(Pelicula pelicula, List<Long> idPersonaje){
        List<PersonajePeliculas> peliculaList = new ArrayList();
        
        // Mapeo la pelicula a esta entidad
        PersonajePeliculas pr = new PersonajePeliculas();
        pr.setId(pelicula.getId());
        pr.setTitulo(pelicula.getTitulo());
        pr.setFechaCreacion(pelicula.getFechaCreacion());
        pr.setImgaen(pelicula.getImagen());
        peliculaList.add(pr);
        
        personajePeliculasRepo.save(pr);
        
        for(Long id : idPersonaje){
            Personaje personaje = buscarPersonajePorId(id);
            
            List<PersonajePeliculas> peliculasEnLasQueApareceElPersonaje= personaje.getPelicula(); // Agarro todas las peliculas en las que aparece el personaje
            peliculasEnLasQueApareceElPersonaje.add(pr); // le sumo la nueva pelicula en la que aparece
            
            personaje.setPelicula(peliculasEnLasQueApareceElPersonaje); // guardo nuevamente las peliculas
            personajeRepo.save(personaje);
        }
    }
    
    @Transactional()
    public void borrarPeliculaDelPersonaje(Personaje personaje, Long idPelicula){
        
    }
    
    @Transactional(readOnly = true)
    public List<Personaje> buscarTodosLosPersonajes(){
        return personajeRepo.findAll();
    }
    
    @Transactional(readOnly = true)
    public Personaje buscarPersonajePorId(Long id){
        return personajeRepo.buscarPersonajePorId(id);
    }
    
    @Transactional(readOnly = true)
    public List<Personaje> buscarPersonajesPorNombre(String nombre){
        return personajeRepo.buscarPersonajesPorNombre(nombre);
    }
    
    @Transactional(readOnly = true)
    public Personaje buscarPersonajePorNombre(String nombre){
        return personajeRepo.buscarPersonajePorNombre(nombre);
    }
    
    @Transactional(readOnly = true)
    public List<Personaje> buscarPersonajePorEdad(Integer edad){
        return personajeRepo.buscarPersonajePorEdad(edad);
    }
    
    @Transactional(readOnly = true)
    public List<Personaje> buscarPersonajePorPeso(Integer peso){
        return personajeRepo.buscarPersonajePorPeso(peso);
    }
    
    @Transactional(readOnly = true)
    public List<Personaje> buscarPersonajeEnPeliculas(Long idPelicula){
        return personajeRepo.buscarPersonajesEnPeliculas(idPelicula);
    }
    
    @Transactional()
    public void borrarPersonaje(Long id){
        personajeRepo.deleteById(id);
    }
    
    private void validaciones(String nombre, Integer edad, Integer peso, String historia) throws Exception{
        if(nombre == null || nombre.isEmpty()){
            throw new Exception("El nombre no puede estar vacío");
        }
        if(edad == null || edad == 0){
            throw new Exception("La edad no puede ser 0 o estar vacía");
        }
        if(peso == null || peso == 0){
            throw new Exception("El peso no puede ser 0 o estar vacía");
        }
        if(historia == null || historia.isEmpty()){
            throw new Exception("La historia no puede estar vacía");
        }
    }
}
