package com.univo.backend_app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "documentos")
public class DocumentoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    private String materia;

    public DocumentoDTO() {
    }

    public DocumentoDTO(String titulo, String contenido, String materia) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.materia = materia;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }
}