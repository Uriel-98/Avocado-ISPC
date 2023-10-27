package com.example.proyectoavocado.controllers;

public class Paso {
    private Integer idPaso;
    private Integer idReceta;
    private String titulo;
    private String descripcion;

    public Paso(int idPaso, int idReceta, String titulo, String descripcion) {
        this.idPaso = idPaso;
        this.idReceta = idReceta;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public Paso(String titulo, String descripcion ) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public int getIdPaso() {
        return idPaso;
    }

    public void setIdPaso(int idPaso) {
        this.idPaso = idPaso;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

