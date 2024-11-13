package com.dam2.reto_1_xeo.api;

import com.dam2.reto_1_xeo.models.Console;
import com.dam2.reto_1_xeo.models.Game;
import com.dam2.reto_1_xeo.models.Genre;
import com.dam2.reto_1_xeo.models.LoginRequest;
import com.dam2.reto_1_xeo.models.LoginResponse;
import com.dam2.reto_1_xeo.models.PedidoProducto;
import com.dam2.reto_1_xeo.models.PedidoResponse;
import com.dam2.reto_1_xeo.models.Smarts;
import com.dam2.reto_1_xeo.models.Store;
import com.dam2.reto_1_xeo.models.Pedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @POST("crearPedido")
    Call<PedidoResponse> createPedido(@Body Pedido pedido);

    @POST("crearPedidoProducto")
    Call<Void> createPedidoProducto(@Body PedidoProducto pedidoProducto);

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @PUT("usuario/{id}")
    Call<Void> updateUser(@Path("id") int id, @Body LoginResponse.UserData userData);

}