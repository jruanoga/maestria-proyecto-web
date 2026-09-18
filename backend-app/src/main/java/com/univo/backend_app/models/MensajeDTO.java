package com.univo.backend_app.models;
import jakarta.persistence.*;
@Entity // Le dice a Spring que esto será una tabla en PostgreSQL
@Table(name = "mensajes")

public class MensajeDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Llave primaria autoincrementable
    private String texto;
    private String remitente;
    // Constructores
    public MensajeDTO() {}
    public MensajeDTO(String texto, String remitente) {
        this.texto = texto;
        this.remitente = remitente;
    }
    // Getters y Setters (Necesarios para que Spring Boot los convierta a JSON)
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public String getRemitente() { return remitente; }
    public void setRemitente(String remitente) { this.remitente = remitente; }
}