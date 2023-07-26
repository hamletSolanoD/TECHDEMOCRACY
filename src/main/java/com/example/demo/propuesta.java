package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class propuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String titulo;

    String propuesta;
    String fotoURL;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private CandidatoPolitico candidato;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPropuesta() {
        return this.propuesta;
    }

    public void setPropuesta(String propuesta) {
        this.propuesta = propuesta;
    }

    public CandidatoPolitico getCandidato() {
        return this.candidato;
    }

    public void setCandidato(CandidatoPolitico candidato) {
        this.candidato = candidato;
    }

    public String getFotoURL() {
        return this.fotoURL;
    }

    public void setFotoURL(String fotoURL) {
        this.fotoURL = fotoURL;
    }

}
