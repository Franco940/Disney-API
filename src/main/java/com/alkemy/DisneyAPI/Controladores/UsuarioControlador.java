package com.alkemy.DisneyAPI.Controladores;

import com.alkemy.DisneyAPI.Entidades.Usuario;
import com.alkemy.DisneyAPI.Servicios.UsuarioServicio;
import com.alkemy.DisneyAPI.seguridad.JwtService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Franco Roman
 */

@RestController
@RequestMapping("/auth")
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio servicioUsuario;
    
    @Autowired
    private JwtService jwtService;
    
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam(required = true, name = "nombre") String nombre, 
            @RequestParam(required = true, name = "contrasenia") String contrasenia) throws Exception{

        if(nombre == null){
            return new ResponseEntity<>("El nombre no puede estar vacío", HttpStatus.BAD_REQUEST);
        }else if(contrasenia == null){
            return new ResponseEntity<>("La contraseña no puede estar vacía", HttpStatus.BAD_REQUEST);
        }else{
            try{
                Usuario usuario = servicioUsuario.buscarUsuarioPorNombre(nombre);

                if(usuario != null){
                    if(servicioUsuario.verificarLogin(contrasenia, usuario.getContrasenia())){
                        List<String> roles = new ArrayList();
                        roles.add(usuario.getRol());

                        String token = jwtService.createToken(usuario.getNombre(), roles);

                        return new ResponseEntity<>("Logeo exitoso.\nToken: " + token, HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>("Nombre o contraseña inválidos", HttpStatus.BAD_REQUEST);
                    }
                }else{
                    return new ResponseEntity<>("Nombre o contraseña inválidos", HttpStatus.BAD_REQUEST);
                }
            }catch(Exception ex){
                throw new Exception(ex.getMessage());
            }
        }
    }
    
    @PostMapping("/registro")
    public String registro(@RequestBody() Usuario usuario) throws Exception{
        
        servicioUsuario.crearUsuario(usuario);
        
        return "Registrado correctamente";
    }
}
