package com.dam2.reto_1_xeo.models;

public class PedidoProducto {
    private int cantidad;
    private double precio_final;
    private double precio_final_alquiler;
    private int id_pedido;
    private int id_producto;

    // Getters y setters
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_final() {
        return precio_final;
    }

    public void setPrecio_final(double precio_final) {
        this.precio_final = precio_final;
    }

    public double getPrecio_final_alquiler() {
        return precio_final_alquiler;
    }

    public void setPrecio_final_alquiler(double precio_final_alquiler) {
        this.precio_final_alquiler = precio_final_alquiler;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }
}