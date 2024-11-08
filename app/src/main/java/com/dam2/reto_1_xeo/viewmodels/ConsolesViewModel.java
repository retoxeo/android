package com.dam2.reto_1_xeo.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dam2.reto_1_xeo.api.RetrofitClient;
import com.dam2.reto_1_xeo.models.Console;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsolesViewModel extends ViewModel {

    private final MutableLiveData<List<Console>> consoles = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<List<Console>> getConsoles() {
        return consoles;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadConsoles() {
        RetrofitClient.getApiService().getConsoles().enqueue(new Callback<List<Console>>() {
            @Override
            public void onResponse(@NonNull Call<List<Console>> call, @NonNull Response<List<Console>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    consoles.setValue(response.body());
                } else {
                    errorMessage.setValue("Error al cargar las consolas");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Console>> call, @NonNull Throwable t) {
                errorMessage.setValue("Error al cargar las consolas");
            }
        });
    }
}
