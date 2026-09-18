package com.univo.backend_app.repositories;

import com.univo.backend_app.models.DocumentoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentoRepository extends JpaRepository<DocumentoDTO, Long> {
    List<DocumentoDTO> findByUsuarioEmail(String usuarioEmail);
}