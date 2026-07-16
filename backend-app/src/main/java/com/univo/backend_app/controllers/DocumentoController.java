package com.univo.backend_app.controllers;

import com.univo.backend_app.models.DocumentoDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentoController {

    @GetMapping
    public List<DocumentoDTO> obtenerDocumentos() {
        List<DocumentoDTO> documentos = new ArrayList<>();
        documentos.add(new DocumentoDTO(
                "Apuntes de Redes",
                "El modelo OSI tiene 7 capas...",
                "Redes de Computadoras"
        ));
        documentos.add(new DocumentoDTO(
                "Resumen de Bases de Datos",
                "Una base de datos relacional organiza los datos en tablas...",
                "Bases de Datos"
        ));
        return documentos;
    }

    @PostMapping
    public String recibirDocumento(@RequestBody DocumentoDTO documento) {
        System.out.println("Documento recibido: " + documento.getTitulo());
        return "Documento '" + documento.getTitulo() + "' recibido correctamente";
    }
}