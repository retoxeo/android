package com.dam2.reto_1_xeo.models;

import java.util.List;

public class Game {
    private int id_producto;
    private int id_videojuego;
    private String nombre;
    private String descripcion;
    private double precio;
    private double precio_alquiler;
    private int pegi;
    private String fecha_lanzamiento;
    private String desarrollador;
    private List<String> fotos;
    private List<Genre> generos;

    public int getId_producto() {
        return id_producto;
    }

    public int getId_videojuego() {
        return id_videojuego;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public double getPrecio_alquiler() {
        return precio_alquiler;
    }

    public int getPegi() {
        return pegi;
    }

    public String getFecha_lanzamiento() {
        return fecha_lanzamiento;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public List<Genre> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genre> generos) {
        this.generos = generos;
    }
}
