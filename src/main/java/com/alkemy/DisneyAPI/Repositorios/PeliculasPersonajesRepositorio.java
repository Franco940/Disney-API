/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alkemy.DisneyAPI.Repositorios;

import com.alkemy.DisneyAPI.Entidades.PeliculasPersonajes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Franco Roman
 */

@Repository
public interface PeliculasPersonajesRepositorio extends JpaRepository<PeliculasPersonajes, Long>{
    
}
