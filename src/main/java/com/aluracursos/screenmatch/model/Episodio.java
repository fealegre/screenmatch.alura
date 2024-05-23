package com.aluracursos.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    public Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double evaluaciones;
    private LocalDate fechaLanzamiento;

    public Episodio(Integer temporada, DatosEpisodio d) {
        this.temporada = temporada;
        this.titulo = d.titulo();
        this.numeroEpisodio = d.numeroEpisodio();
        try {
            this.evaluaciones = Double.valueOf(d.evaluacion());
        } catch (NumberFormatException e){
            this.evaluaciones = 0.0;
        }
        try {
            this.fechaLanzamiento = LocalDate.parse(d.fechaLanzamiento());
        }catch (DateTimeParseException e){
            this.fechaLanzamiento = null;
        }
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(Double evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    @Override
    public String toString() {
        return
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", evaluaciones=" + evaluaciones +
                ", fechaLanzamiento=" + fechaLanzamiento;

    }
}
