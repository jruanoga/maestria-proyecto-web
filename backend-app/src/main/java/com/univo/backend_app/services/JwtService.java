package com.univo.backend_app.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Llave fija (generada una sola vez), compartida por toda la app
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generarToken(String email, String nombre) {
        return Jwts.builder()
                .setSubject(email)
                .claim("nombre", nombre)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(secretKey)
                .compact();
    }

    public String extraerEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
