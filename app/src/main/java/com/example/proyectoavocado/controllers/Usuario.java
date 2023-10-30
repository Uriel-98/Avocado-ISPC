package com.example.proyectoavocado.controllers;

import java.io.Serializable;

public class Usuario implements Serializable {
    private Integer idUsuario;
    private String nombreCompleto;
    private String imagen;
    private String usuario;
    private String email;
    private String contraseña;



    // Constructor vacío necesario para deserialización
    public Usuario() {
    }


    // Constructor
    public void Receta(Integer idUsuario, String nombreCompleto, String usuario, String email, String contraseña, String imagen) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.imagen = imagen;
        this.usuario = usuario;
        this.email = email;
        this.contraseña = contraseña;
    }


    // Getter para el id
    public Integer getIdUsuario() {
        return idUsuario;
    }

    // Getter para email
    public String getEmail() {
        return email;
    }

    // Getter y Setter para el nombre
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String titulo) {
        this.nombreCompleto = nombreCompleto;
    }

    // Getter y Setter para la imagen
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    // Getter y Setter para el nombre de usuario
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String nuevoUsuario) {
        this.usuario = nuevoUsuario;
    }

    public void setContraseña(String nuevaContraseña) {this.contraseña = nuevaContraseña;}

    public String getContraseña(){return contraseña;}

    public String toString() {
        return "Receta{" +
                "idUsuario=" + idUsuario +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", imagen='" + imagen + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    }
