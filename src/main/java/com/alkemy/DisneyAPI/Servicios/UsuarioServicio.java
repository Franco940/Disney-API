package com.alkemy.DisneyAPI.Servicios;

import com.alkemy.DisneyAPI.Entidades.Usuario;
import com.alkemy.DisneyAPI.Repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    
    public UserDetails cargarUsuario(String nombre) throws UsernameNotFoundException {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorNombre(nombre);
        
        if(usuario.getNombre().equals("ADMIN")){
            return this.userBuilder(usuario.getNombre(), passwordEncoder.encode(usuario.getContrasenia()), usuario.getRol());
        }else{
            return this.userBuilder(usuario.getNombre(), passwordEncoder.encode(usuario.getContrasenia()), usuario.getRol());
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
    
    // Clase usuario de spring
    private User userBuilder(String username, String password, String... roles){
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        List<GrantedAuthority> authorities = new ArrayList();
        
        for(String role : roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        
        return new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
