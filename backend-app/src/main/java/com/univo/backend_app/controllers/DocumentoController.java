package com.univo.backend_app.controllers;

import com.univo.backend_app.models.DocumentoDTO;
import com.univo.backend_app.repositories.DocumentoRepository;
import com.univo.backend_app.services.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documentos")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentoController {

    private final DocumentoRepository repository;
    private final JwtService jwtService;

    public DocumentoController(DocumentoRepository repository, JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<DocumentoDTO> listarDocumentos(@RequestHeader("Authorization") String authHeader) {
        String email = extraerEmailDeHeader(authHeader);
        return repository.findByUsuarioEmail(email);
    }

    @PostMapping
    public DocumentoDTO guardarDocumento(@RequestBody DocumentoDTO nuevoDocumento, @RequestHeader("Authorization") String authHeader) {
        String email = extraerEmailDeHeader(authHeader);
        nuevoDocumento.setUsuarioEmail(email);
        return repository.save(nuevoDocumento);
    }

    private String extraerEmailDeHeader(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtService.extraerEmail(token);
    }
}