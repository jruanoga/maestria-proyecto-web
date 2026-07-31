package com.univo.backend_app.controllers;

import com.univo.backend_app.models.ResultadoQuiz;
import com.univo.backend_app.repositories.ProgresoPorMateria;
import com.univo.backend_app.repositories.ResultadoQuizRepository;
import com.univo.backend_app.services.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resultados")
@CrossOrigin(origins = "http://localhost:4200")
public class ResultadoQuizController {

    private final ResultadoQuizRepository repository;
    private final JwtService jwtService;

    public ResultadoQuizController(ResultadoQuizRepository repository, JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<ResultadoQuiz> listar(@RequestHeader("Authorization") String authHeader) {
        String email = extraerEmailDeHeader(authHeader);
        return repository.findByUsuarioEmail(email);
    }

    @PostMapping
    public ResultadoQuiz guardar(@RequestBody ResultadoQuiz resultado, @RequestHeader("Authorization") String authHeader) {
        String email = extraerEmailDeHeader(authHeader);
        resultado.setUsuarioEmail(email);
        return repository.save(resultado);
    }

    @GetMapping("/progreso")
    public List<ProgresoPorMateria> obtenerProgreso(@RequestHeader("Authorization") String authHeader) {
        String email = extraerEmailDeHeader(authHeader);
        return repository.calcularProgresoPorMateriaYUsuario(email);
    }

    private String extraerEmailDeHeader(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtService.extraerEmail(token);
    }
}