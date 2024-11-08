package com.dam2.reto_1_xeo.api;

import com.dam2.reto_1_xeo.models.Console;
import com.dam2.reto_1_xeo.models.Game;
import com.dam2.reto_1_xeo.models.Genre;
import com.dam2.reto_1_xeo.models.Smarts;
import com.dam2.reto_1_xeo.models.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("videojuegos")
    Call<List<Game>> getGames();

    @GET("generos")
    Call<List<Genre>> getGenres();

    @GET("tiendas")
    Call<List<Store>> getStores();

    @GET("consolas")
    Call<List<Console>> getConsoles();

    @GET("dispositivos_movil")
    Call<List<Smarts>> getSmarts();
}