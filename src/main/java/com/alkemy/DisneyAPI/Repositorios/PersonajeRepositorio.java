package com.alkemy.DisneyAPI.Repositorios;

import com.alkemy.DisneyAPI.Entidades.Personaje;
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
public interface PersonajeRepositorio extends JpaRepository<Personaje, Long>{
    
    @Query("SELECT p FROM Personaje AS p WHERE p.id = :id")
    public Personaje buscarPersonajePorId(@Param("id") Long id);
    
    @Query("SELECT p FROM Personaje AS p WHERE p.pelicula = :idPelicula")
    public List<Personaje> buscarPersonajesEnPeliculas(@Param("idPelicula") Integer idPelicula);
    
    @Query("SELECT p FROM Personaje AS p WHERE p.peso = :peso")
    public List<Personaje> buscarPersonajePorPeso(@Param("peso") Integer peso);
    
    @Query("SELECT p FROM Personaje AS p WHERE p.edad = :edad")
    public List<Personaje> buscarPersonajePorEdad(@Param("edad") Integer edad);
    
    @Query("SELECT p FROM Personaje AS p WHERE p.nombre = :nombre")
    public List<Personaje> buscarPersonajePorNombre(@Param("nombre") String nombrer);
}
