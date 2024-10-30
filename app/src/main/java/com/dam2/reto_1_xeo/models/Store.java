package com.dam2.reto_1_xeo.models;

import java.util.List;

public class Store {
    private final String name;
    private final List<String> photos;

    public Store(String name, List<String> photos) {
        this.name = name;
        this.photos = photos;
    }

    public String getName() {
        return name;
    }

    public List<String> getPhotos() {
        return photos;
    }
}
