package com.alkemy.DisneyAPI.Repositorios;

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
public interface PeliculaRepositorio extends JpaRepository<Pelicula, Long>{
    
    @Query("SELECT p FROM Pelicula AS p WHERE p.titulo LIKE %:titulo%")
    public List<Pelicula> buscarPeliculasPorTitulo(@Param("titulo") String titulo);
    
    @Query("SELECT p FROM Pelicula AS p WHERE p.titulo = :titulo")
    public Pelicula buscarPeliculaPorTitulo(@Param("titulo") String titulo);
    
    @Query("SELECT p FROM Pelicula AS p ORDER BY p.titulo ASC")
    public List<Pelicula> buscarPeliculasPorOrdenASC();
    
    @Query("SELECT p FROM Pelicula AS p ORDER BY p.titulo DESC")
    public List<Pelicula> buscarPeliculasPorOrdenDESC();
}
