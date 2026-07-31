package com.univo.backend_app.repositories;

import com.univo.backend_app.models.ResultadoQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ResultadoQuizRepository extends JpaRepository<ResultadoQuiz, Long> {

    List<ResultadoQuiz> findByMateria(String materia);
    List<ResultadoQuiz> findByUsuarioEmail(String usuarioEmail);

    @Query("SELECT r.materia as materia, " +
            "SUM(r.aciertos) as totalAciertos, " +
            "SUM(r.total) as totalPreguntas " +
            "FROM ResultadoQuiz r " +
            "WHERE r.usuarioEmail = :email " +
            "GROUP BY r.materia")
    List<ProgresoPorMateria> calcularProgresoPorMateriaYUsuario(@Param("email") String email);
}