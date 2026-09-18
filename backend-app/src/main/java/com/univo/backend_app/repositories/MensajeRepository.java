package com.univo.backend_app.repositories;

import com.univo.backend_app.models.MensajeDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRepository extends JpaRepository<MensajeDTO, Long> {
    // Vacío — Spring genera el SQL automáticamente
}