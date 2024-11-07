package com.dam2.reto_1_xeo.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dam2.reto_1_xeo.api.RetrofitClient;
import com.dam2.reto_1_xeo.models.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Store>> stores;

    public GalleryViewModel(Application application) {
        super(application);
        stores = new MutableLiveData<>();
        loadStores();
    }

    public LiveData<List<Store>> getStores() {
        return stores;
    }

    private void loadStores() {
        RetrofitClient.getApiService().getStores().enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(@NonNull Call<List<Store>> call, @NonNull Response<List<Store>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stores.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Store>> call, @NonNull Throwable t) {
            }
        });
    }
}
