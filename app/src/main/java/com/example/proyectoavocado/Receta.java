package com.example.proyectoavocado;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

public class Receta implements Serializable {
    private Integer id;
    private String nombre;
    private String imagen;
    private String descripcion;
    private Integer creadoPor;
    private String tiempoCoccion;
    private String dificultad;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private List<String> categorias;
    private List<String> pasos;



    // Constructor vacío necesario para deserialización
    public Receta() {
    }


    // Constructor
    public Receta(Integer id, String nombre, String imagen,String descripcion, Integer creadoPor, String tiempoCoccion, String dificultad, Date fechaCreacion, Date fechaActualizacion ) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.creadoPor = creadoPor;
        this.tiempoCoccion = tiempoCoccion;
        this.dificultad = dificultad;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.pasos = pasos;
        this.categorias = categorias;
    }

    public Receta(String nombre, String imagen,String descripcion) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public Receta(String imagen, String nombre) {
        this.imagen = imagen;
        this.nombre = nombre;
    }

    // Getter y Setter para el nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public Integer getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Integer creadoPor) {
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
    public List<String> getPasos() {
        return pasos;
    }

    public void setPasos(List<String> pasos) {
        this.pasos = pasos;
    }

    // Getter y Setter para categorias
    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public String toString() {
        return "Receta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", creadoPor='" + creadoPor + '\'' +
                ", tiempoCoccion='" + tiempoCoccion + '\'' +
                ", dificultad='" + dificultad + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                ", pasos=" + pasos +  '\'' +
                ", categorias=" + categorias +  '\'' +
                '}';
    }
}
