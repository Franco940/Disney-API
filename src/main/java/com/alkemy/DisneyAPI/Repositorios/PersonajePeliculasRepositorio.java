package com.alkemy.DisneyAPI.Repositorios;

import com.alkemy.DisneyAPI.Entidades.PersonajePeliculas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Franco Roman
 */

@Repository
public interface PersonajePeliculasRepositorio extends JpaRepository<PersonajePeliculas, Long>{
    
}
