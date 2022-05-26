package com.alkemy.DisneyAPI.Servicios;

import com.alkemy.DisneyAPI.Entidades.Genero;
import com.alkemy.DisneyAPI.Entidades.Pelicula;
import com.alkemy.DisneyAPI.Repositorios.GeneroRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Franco Roman
 */

@Service
public class GeneroServicio {
    
    @Autowired
    private GeneroRepositorio repositorioGenero;
    
    public Genero crearGenero(String nombre, MultipartFile imagen){
        Genero genero = new Genero();
        
        genero.setNombre(nombre);
        
        return genero;
    }
    
    public List<Pelicula> buscarPeliculaPorGenero(String genero){
        return repositorioGenero.buscarPeliculaPorGenero(genero);
    }
}
