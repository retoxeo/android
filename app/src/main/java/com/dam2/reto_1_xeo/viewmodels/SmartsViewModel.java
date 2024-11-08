package com.dam2.reto_1_xeo.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dam2.reto_1_xeo.api.RetrofitClient;
import com.dam2.reto_1_xeo.models.Smarts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmartsViewModel extends ViewModel {

    private final MutableLiveData<List<Smarts>> smarts = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<List<Smarts>> getSmarts() {
        return smarts;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadSmarts() {
        RetrofitClient.getApiService().getSmarts().enqueue(new Callback<List<Smarts>>() {
            @Override
            public void onResponse(@NonNull Call<List<Smarts>> call, @NonNull Response<List<Smarts>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    smarts.setValue(response.body());
                } else {
                    errorMessage.setValue("Error al cargar los smartphones");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Smarts>> call, @NonNull Throwable t) {
                errorMessage.setValue("Error al cargar los smartphones");
            }
        });
    }
}
