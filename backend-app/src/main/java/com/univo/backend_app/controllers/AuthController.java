package com.univo.backend_app.controllers;

import com.univo.backend_app.models.Usuario;
import com.univo.backend_app.repositories.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String emailRecibido = credenciales.get("email");
        String passwordRecibido = credenciales.get("password");

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(emailRecibido);

        if (usuarioEncontrado.isPresent() && usuarioEncontrado.get().getPassword().equals(passwordRecibido)) {
            String token = Jwts.builder()
                    .setSubject(emailRecibido)
                    .claim("nombre", usuarioEncontrado.get().getNombre())
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