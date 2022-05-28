package com.alkemy.DisneyAPI.Repositorios;

import com.alkemy.DisneyAPI.Entidades.Genero;
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
    
    @Query("SELECT g FROM Genero AS g WHERE g.nombre = :genero")
    public Genero buscarPeliculaPorGenero(@Param("genero") String genero);
}
