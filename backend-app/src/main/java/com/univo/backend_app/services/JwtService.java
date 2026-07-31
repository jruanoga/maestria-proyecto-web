package com.univo.backend_app.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key obtenerLlave() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generarToken(String email, String nombre) {
        return Jwts.builder()
                .setSubject(email)
                .claim("nombre", nombre)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(obtenerLlave())
                .compact();
    }

    public String extraerEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(obtenerLlave())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
