package com.univo.backend_app.controllers;

import com.univo.backend_app.models.Usuario;
import com.univo.backend_app.repositories.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String emailRecibido = credenciales.get("email");
        String passwordRecibido = credenciales.get("password");

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(emailRecibido);

        if (usuarioEncontrado.isPresent()
                && passwordEncoder.matches(passwordRecibido, usuarioEncontrado.get().getPassword())) {

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

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Map<String, String> datos) {
        String email = datos.get("email");

        if (usuarioRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("error", "El correo ya está registrado"));
        }

        String passwordEncriptado = passwordEncoder.encode(datos.get("password"));
        Usuario nuevoUsuario = new Usuario(email, passwordEncriptado, datos.get("nombre"));
        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado correctamente"));
    }
}