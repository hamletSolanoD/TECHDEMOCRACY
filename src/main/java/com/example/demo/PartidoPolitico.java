package com.example.demo;

import jakarta.persistence.*;

@Entity
public class PartidoPolitico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String estatus;

    // Constructores (puedes agregar más constructores si es necesario)

    public PartidoPolitico() {
    }

    public PartidoPolitico(String nombre, String estatus) {
        this.nombre = nombre;
        this.estatus = estatus;
    }

    // Getters y Setters (puedes generarlos automáticamente en tu IDE)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    // toString (puedes generarlos automáticamente en tu IDE)

    @Override
    public String toString() {
        return "PartidoPolitico [id=" + id + ", nombre=" + nombre + ", estatus=" + estatus + "]";
    }
}
