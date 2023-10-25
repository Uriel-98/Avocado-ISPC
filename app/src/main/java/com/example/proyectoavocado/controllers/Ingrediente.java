package com.example.proyectoavocado.controllers;

import java.util.Date;
import java.io.Serializable;

public class Ingrediente {
    private Integer id;
    private String nombre;

    // Constructor vacío necesario para deserialización
    public Ingrediente() {
    }

    public Ingrediente(Integer id, String nombre ) {
        this.id = id;
        this.nombre = nombre;
    }

    public Ingrediente(String nombre) {
        this.nombre = nombre;
    }

    // Getter y Setter para el nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return "Receta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
