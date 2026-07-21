package com.univo.backend_app.controllers;

import com.univo.backend_app.models.DocumentoDTO;
import com.univo.backend_app.repositories.DocumentoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documentos")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentoController {

    private final DocumentoRepository repository;

    public DocumentoController(DocumentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<DocumentoDTO> listarDocumentos() {
        return repository.findAll();
    }

    @PostMapping
    public DocumentoDTO guardarDocumento(@RequestBody DocumentoDTO nuevoDocumento) {
        return repository.save(nuevoDocumento);
    }
}