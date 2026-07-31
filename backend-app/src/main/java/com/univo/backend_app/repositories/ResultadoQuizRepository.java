package com.univo.backend_app.repositories;

import com.univo.backend_app.models.ResultadoQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ResultadoQuizRepository extends JpaRepository<ResultadoQuiz, Long> {

    List<ResultadoQuiz> findByMateria(String materia);

    @Query("SELECT r.materia as materia, " +
            "SUM(r.aciertos) as totalAciertos, " +
            "SUM(r.total) as totalPreguntas " +
            "FROM ResultadoQuiz r " +
            "GROUP BY r.materia")
    List<ProgresoPorMateria> calcularProgresoPorMateria();
}