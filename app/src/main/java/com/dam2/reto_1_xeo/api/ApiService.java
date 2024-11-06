package com.dam2.reto_1_xeo.api;

import com.dam2.reto_1_xeo.models.Game;
import com.dam2.reto_1_xeo.models.Genre;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("videojuegos")
    Call<List<Game>> getGames();

    @GET("generos")
    Call<List<Genre>> getGenres();
}