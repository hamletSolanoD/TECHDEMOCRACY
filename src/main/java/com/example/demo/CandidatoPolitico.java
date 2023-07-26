package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CandidatoPolitico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String puesto;
    private String nombre;
    private String status;
    private Long votos;

    public CandidatoPolitico() {
        this.votos = 0L; // Valor predeterminado para votos
    }

    @ManyToOne
    @JoinColumn(name = "partido_id")
    private PartidoPolitico partidoPolitico;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PartidoPolitico getPartidoPolitico() {
        return this.partidoPolitico;
    }

    public Long getVotos() {
        return this.votos;
    }

    public void setVotos(Long votos) {
        this.votos = votos;
    }

    public void setPartidoPolitico(PartidoPolitico partidoPolitico) {
        this.partidoPolitico = partidoPolitico;
    }

    public String getPuesto() {
        return this.puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
