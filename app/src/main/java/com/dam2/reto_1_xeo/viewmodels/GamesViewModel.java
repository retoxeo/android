package com.dam2.reto_1_xeo.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dam2.reto_1_xeo.api.RetrofitClient;
import com.dam2.reto_1_xeo.models.Game;
import com.dam2.reto_1_xeo.models.Genre;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesViewModel extends ViewModel {

    private final MutableLiveData<List<Game>> games = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Genre>> genres = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(true);

    public LiveData<List<Game>> getGames() {
        return games;
    }

    public LiveData<List<Genre>> getGenres() {
        return genres;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public void loadGames() {
        loading.setValue(true);
        RetrofitClient.getApiService().getGames().enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(@NonNull Call<List<Game>> call, @NonNull Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    games.setValue(response.body());
                }
                loading.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<Game>> call, @NonNull Throwable t) {
                loading.setValue(false);
            }
        });
    }

    public void loadGenres() {
        RetrofitClient.getApiService().getGenres().enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(@NonNull Call<List<Genre>> call, @NonNull Response<List<Genre>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    genres.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Genre>> call, @NonNull Throwable t) {}
        });
    }
}