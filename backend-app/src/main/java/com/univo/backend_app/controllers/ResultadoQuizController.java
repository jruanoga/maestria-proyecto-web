package com.univo.backend_app.controllers;

import com.univo.backend_app.models.ResultadoQuiz;
import com.univo.backend_app.repositories.ResultadoQuizRepository;
import org.springframework.web.bind.annotation.*;
import com.univo.backend_app.repositories.ProgresoPorMateria;
import java.util.List;

@RestController
@RequestMapping("/api/v1/resultados")
@CrossOrigin(origins = "http://localhost:4200")
public class ResultadoQuizController {

    private final ResultadoQuizRepository repository;

    public ResultadoQuizController(ResultadoQuizRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ResultadoQuiz> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ResultadoQuiz guardar(@RequestBody ResultadoQuiz resultado) {
        return repository.save(resultado);
    }

    @GetMapping("/progreso")
    public List<ProgresoPorMateria> obtenerProgreso() {
        return repository.calcularProgresoPorMateria();
    }
}