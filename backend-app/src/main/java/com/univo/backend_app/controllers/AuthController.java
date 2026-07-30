package com.univo.backend_app.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String emailRecibido = credenciales.get("email");
        String passwordRecibido = credenciales.get("password");

        if ("admin@univo.edu.mx".equals(emailRecibido) && "12345".equals(passwordRecibido)) {
            String token = Jwts.builder()
                    .setSubject(emailRecibido)
                    .claim("rol", "ADMIN")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                    .signWith(secretKey)
                    .compact();

            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
        }
    }
}