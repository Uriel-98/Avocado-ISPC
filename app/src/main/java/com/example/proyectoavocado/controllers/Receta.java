package com.example.proyectoavocado.controllers;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

public class Receta implements Serializable {
    private Integer idReceta;
    private String titulo;
    private String imagen;
    private String descripcion;
    private String creadoPor;
    private String tiempoCoccion;
    private String dificultad;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private List<String> categorias;
    private List<Ingrediente> ingredientes;
    private List<Paso> pasos;




    // Constructor vacío necesario para deserialización
    public Receta() {
    }


    // Constructor
    public Receta(Integer idReceta, String titulo, String imagen,String descripcion, String creadoPor, String tiempoCoccion, String dificultad, Date fechaCreacion, Date fechaActualizacion, List<Paso> pasos, List<String> categorias, List<Ingrediente> ingredientes) {
        this.idReceta = idReceta;
        this.titulo = titulo;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.creadoPor = creadoPor;
        this.tiempoCoccion = tiempoCoccion;
        this.dificultad = dificultad;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.pasos = pasos;
        this.categorias = categorias;
        this.ingredientes = ingredientes;
    }

    public Receta(Integer idReceta, String nombre, String imagen) {
        this.titulo = nombre;
        this.imagen = imagen;
        this.idReceta = idReceta;

    }

    public Receta(Integer idReceta, String titulo, String creadoPor, String descripcion) {
        this.idReceta = idReceta;
        this.titulo = titulo;
        this.creadoPor = creadoPor;
        this.descripcion = descripcion;
    }

    public Receta(Integer idReceta, String titulo, String descripcion, String tiempoCoccion, String dificultad, List<Ingrediente> ingredientes, List<Paso> pasos) {
        this.idReceta = idReceta;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tiempoCoccion = tiempoCoccion;
        this.dificultad = dificultad;
        this.pasos = pasos;
        this.ingredientes = ingredientes;
    }

    // Getter y Setter para el id
    public Integer getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }


    // Getter y Setter para el nombre
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Getter y Setter para la imagen
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    // Getter y Setter para la descripcion
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getter y Setter para creadoPor
    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    // Getter y Setter para tiempoCoccion
    public String getTiempoCoccion() {
        return tiempoCoccion;
    }

    public void setTiempoCoccion(String tiempoCoccion) {
        this.tiempoCoccion = tiempoCoccion;
    }

    // Getter y Setter para la dificultad
    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    // Getter y Setter para fechaCreacion
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Getter y Setter para fechaActualizacion
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    // Getter y Setter para pasos
    public List<Paso> getPasos() {
        return pasos;
    }

    public void setPasos(List<Paso> pasos) {
        this.pasos = pasos;
    }

    // Getter y Setter para categorias
    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }


    // Getter y Setter para ingredientes
    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String toString() {
        return "Receta{" +
                "id=" + idReceta +
                ", titulo='" + titulo + '\'' +
                ", imagen='" + imagen + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", creadoPor='" + creadoPor + '\'' +
                ", tiempoCoccion='" + tiempoCoccion + '\'' +
                ", dificultad='" + dificultad + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                ", ingredientes=" + ingredientes +  '\'' +
                ", pasos=" + pasos +  '\'' +
                ", categorias=" + categorias +  '\'' +
                ", ingredientes=" + ingredientes +  '\'' +
                '}';
    }
}
