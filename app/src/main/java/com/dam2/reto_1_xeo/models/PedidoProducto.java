package com.dam2.reto_1_xeo.models;

public class PedidoProducto {
    private int cantidad;
    private double precioFinal;
    private double precioFinalAlquiler;
    private int idPedido;
    private int idProducto;

    // Getters y setters
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public double getPrecioFinalAlquiler() {
        return precioFinalAlquiler;
    }

    public void setPrecioFinalAlquiler(double precioFinalAlquiler) {
        this.precioFinalAlquiler = precioFinalAlquiler;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}