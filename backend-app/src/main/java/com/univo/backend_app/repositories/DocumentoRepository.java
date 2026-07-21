package com.univo.backend_app.repositories;
import com.univo.backend_app.models.DocumentoDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<DocumentoDTO, Long> {
    // Vacío — Spring genera el SQL automáticamente
}