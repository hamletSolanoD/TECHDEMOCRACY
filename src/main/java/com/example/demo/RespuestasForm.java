package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class RespuestasForm {
    private Map<String, CandidatoPolitico> respuestas;

    // Agregar constructor, getters y setters según sea necesario.

    // Constructor
    public RespuestasForm() {
        respuestas = new HashMap<>();
    }

    public Map<String, CandidatoPolitico> getRespuestas() {
        return this.respuestas;
    }

    public void setRespuestas(Map<String, CandidatoPolitico> respuestas) {
        this.respuestas = respuestas;
    }

    @Override
    public String toString() {
        return "{" +
                " respuestas='" + getRespuestas() + "'" +
                "}";
    }

}