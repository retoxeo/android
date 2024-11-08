package com.dam2.reto_1_xeo.models;

import java.io.Serializable;
import java.util.List;

public class Console implements Serializable {
    private int id_producto;
    private int id_consola;
    private String nombre;
    private String descripcion;
    private double precio;
    private String almacenamiento;
    private String fecha_lanzamiento;
    private String desarrollador;
    private List<String> fotos;
    private int stock;

    public int getId_producto() {
        return id_producto;
    }

    public int getId_consola() {
        return id_consola;
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

    public String getAlmacenamiento() {
        return almacenamiento;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
