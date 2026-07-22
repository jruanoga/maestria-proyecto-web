package com.univo.backend_app.controllers;

import com.univo.backend_app.services.InteligenciaArtificialService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ia")
@CrossOrigin(origins = "http://localhost:4200")
public class AiController {

    private final InteligenciaArtificialService iaService;

    public AiController(InteligenciaArtificialService iaService) {
        this.iaService = iaService;
    }

    @GetMapping("/consulta")
    public Map<String, String> preguntarIa(@RequestParam String pregunta) {
        String respuesta = iaService.generarRespuestaSimple(pregunta);
        return Map.of("respuesta", respuesta);
    }

    @PostMapping("/resumen")
    public Map<String, String> generarResumen(@RequestBody Map<String, String> body) {
        String contenido = body.get("contenido");
        String resumen = iaService.generarResumen(contenido);
        return Map.of("resumen", resumen);
    }
}