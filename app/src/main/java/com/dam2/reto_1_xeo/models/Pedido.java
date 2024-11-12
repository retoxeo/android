package com.dam2.reto_1_xeo.models;

public class Pedido {
    private String fecha_inicio;
    private String fecha_fin;
    private String descripcion;
    private String pais;
    private String provincia;
    private String cp;
    private String ciudad;
    private String calle;
    private String numero;
    private int id_usuario;
    private int id_estado;

    public Pedido(String fecha_inicio, String fecha_fin, String descripcion, String pais, String provincia, String cp, String ciudad, String calle, String numero, int id_usuario, int id_estado) {
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.descripcion = descripcion;
        this.pais = pais;
        this.provincia = provincia;
        this.cp = cp;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
        this.id_usuario = id_usuario;
        this.id_estado = id_estado;
    }


    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }
}
