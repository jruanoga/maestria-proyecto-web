package com.univo.backend_app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "resultados_quiz")
public class ResultadoQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String materia;
    private Integer aciertos;
    private Integer total;

    public ResultadoQuiz() {
    }

    public ResultadoQuiz(String materia, Integer aciertos, Integer total) {
        this.materia = materia;
        this.aciertos = aciertos;
        this.total = total;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }

    public Integer getAciertos() { return aciertos; }
    public void setAciertos(Integer aciertos) { this.aciertos = aciertos; }

    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }
}