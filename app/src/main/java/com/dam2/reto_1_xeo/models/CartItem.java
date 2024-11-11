package com.dam2.reto_1_xeo.models;

import java.io.Serializable;

public class CartItem implements Serializable {
    private int id;
    private String nombre;
    private double precio;
    private int cantidad;

    public CartItem(int id, String nombre, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void incrementarCantidad() {
        this.cantidad++;
    }

    public void decrementarCantidad() {
        if (this.cantidad > 1) {
            this.cantidad--;
        }
    }
}