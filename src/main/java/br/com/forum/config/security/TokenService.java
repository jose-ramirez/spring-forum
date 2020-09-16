package br.com.forum.config.security;

import br.com.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private Long expirationTime;

    @Value("${forum.jwt.secret}")
    private String jwtSecret;

    public String generateToken(Authentication auth) {
        Usuario loggedUser = (Usuario) auth.getPrincipal();
        Date today = new Date();
        Date expiration = new Date(today.getTime() + expirationTime);
        return Jwts.builder()
                .setIssuer("Forum API")
                .setSubject(loggedUser.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}
