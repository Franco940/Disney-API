package com.alkemy.DisneyAPI.Servicios;

import com.alkemy.DisneyAPI.Entidades.Usuario;
import com.alkemy.DisneyAPI.Repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Franco Roman
 */

@Service
public class UsuarioServicio{
    
    @Autowired
    private UsuarioRepositorio repositorioUsuario;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @Transactional()
    public void crearUsuario(Usuario usuario) throws Exception{
        validaciones(usuario.getNombre(), usuario.getContrasenia(), usuario.getEmail());
        
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        repositorioUsuario.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorNombre(String nombre){
        return repositorioUsuario.buscarUsuarioPorNombre(nombre);
    }
    
    public boolean verificarLogin(String contraseniaPlana, String contraseniaEncriptada) throws Exception{
        if(passwordEncoder.matches(contraseniaPlana, contraseniaEncriptada)){
            return true;
        }else{
            throw new Exception("Nombre o contrasenia invalidos");
        }
    }
    
    private void validaciones(String nombre, String contrasenia, String email) throws Exception{
        if(nombre == null || nombre.isEmpty()){
            throw new Exception("El nombre no puede estar vacío");
        }
        if(contrasenia == null || contrasenia.isEmpty()){
            throw new Exception("La contrasenia no puede estar vacía");
        }
        if(email == null || email.isEmpty()){
            throw new Exception("El email no puede estar vacio");
        }
    }
}
