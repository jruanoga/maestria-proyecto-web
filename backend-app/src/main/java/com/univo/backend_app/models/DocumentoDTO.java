package com.univo.backend_app.models;

public class DocumentoDTO {

    private String titulo;
    private String contenido;
    private String materia;

    // Constructor vacío (necesario para que Spring convierta JSON -> objeto)
    public DocumentoDTO() {
    }

    // Constructor con parámetros
    public DocumentoDTO(String titulo, String contenido, String materia) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.materia = materia;
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
}