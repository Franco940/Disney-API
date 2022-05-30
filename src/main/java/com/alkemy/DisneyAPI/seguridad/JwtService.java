package com.alkemy.DisneyAPI.seguridad;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Franco Roman
 */

@Service
public class JwtService {
    public static final String BEARER = "Bearer";
    private static final String SECRET = "clavesecreta1234";
    private static final int EXPIRA = 1800000; // 30 minutos
    private static final String EMISOR = "DisneyAPI";
    
    
    public String createToken(String user, List<String> roles) throws IllegalArgumentException, UnsupportedEncodingException{
        return JWT.create().withIssuer(EMISOR)
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRA))
                .withClaim("usuario", user)
                .withArrayClaim("roles", roles.toArray(new String[0]))
                .sign(Algorithm.HMAC512(SECRET));
    }
    
    public boolean isBearer(String authorization){
        return authorization != null && authorization.startsWith(BEARER) && authorization.split("\\.").length == 3;
    }
    
    public String user(String authorization) throws Exception{
        return this.verify(authorization).getClaim("usuario").asString();
    }
    
    private DecodedJWT verify(String authorization) throws Exception{
        if(!this.isBearer(authorization)){
            throw new Exception("No es un bearer");
        }
        try{
            return JWT.require(Algorithm.HMAC512(SECRET))
                    .withIssuer(EMISOR).build()
                    .verify(authorization.substring(BEARER.length() + 1));
            
        }catch(Exception ex){
            throw new Exception("El JWT esta mal. " + ex.getMessage());
        }
    }
    
    public List<String> roles(String authorization) throws Exception{
        return Arrays.asList(this.verify(authorization).getClaim("roles").asArray(String.class));
    }
}
