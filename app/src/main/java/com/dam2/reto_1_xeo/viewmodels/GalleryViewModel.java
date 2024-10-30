package com.dam2.reto_1_xeo.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.models.Store;

import java.util.Arrays;
import java.util.List;

public class GalleryViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Store>> stores;

    public GalleryViewModel(Application application) {
        super(application);
        stores = new MutableLiveData<>();
        loadSampleData();
    }

    public LiveData<List<Store>> getStores() {
        return stores;
    }

    private void loadSampleData() {
        String packageName = getApplication().getPackageName();
        List<Store> sampleStores = Arrays.asList(
                new Store("Tienda A", Arrays.asList(
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras)),
                new Store("Tienda B", Arrays.asList(
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras)),
                new Store("Tienda C", Arrays.asList(
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras)),
                new Store("Tienda C", Arrays.asList(
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras)),
                new Store("Tienda D", Arrays.asList(
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras)),
                new Store("Tienda E", Arrays.asList(
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras,
                        "android.resource://" + packageName + "/" + R.drawable.obras))
                );
        stores.setValue(sampleStores);
    }
}