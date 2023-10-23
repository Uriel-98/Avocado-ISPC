package com.example.proyectoavocado.controllers;

public class Categoria {
    private Integer idCategoria;
    private String nombre;
    private Boolean selected;  // Para manejar la selección en la interfaz de usuario

    public Categoria(Integer idCategoria, String nombre) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.selected = false;  // Por defecto, la categoría no está seleccionada
    }

    public Categoria() {

    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}

