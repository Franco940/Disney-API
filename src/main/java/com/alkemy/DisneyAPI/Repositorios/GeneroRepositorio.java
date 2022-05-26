package com.alkemy.DisneyAPI.Repositorios;

import com.alkemy.DisneyAPI.Entidades.Genero;
import com.alkemy.DisneyAPI.Entidades.Pelicula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Franco Roman
 */

@Repository
public interface GeneroRepositorio extends JpaRepository<Genero, Long>{
    
    @Query("SELECT g.pelicula FROM Genero AS g WHERE g.genero = :genero")
    public List<Pelicula> buscarPeliculaPorGenero(@Param("genero") String genero);
}
