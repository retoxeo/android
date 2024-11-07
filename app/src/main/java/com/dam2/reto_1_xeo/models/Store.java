package com.dam2.reto_1_xeo.models;

import java.util.List;

public class Store {
    private int id;
    private String pais;
    private String provincia;
    private int cp;
    private String ciudad;
    private String calle;
    private int numero;
    private String telefono;
    private String correo;
    private List<String> fotos;

    public int getId() {
        return id;
    }

    public String getPais() {
        return pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public int getCp() {
        return cp;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
