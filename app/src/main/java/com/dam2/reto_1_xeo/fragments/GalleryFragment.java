package com.dam2.reto_1_xeo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dam2.reto_1_xeo.adapters.GalleryAdapter;
import com.dam2.reto_1_xeo.databinding.FragmentGalleryBinding;
import com.dam2.reto_1_xeo.viewmodels.GalleryViewModel;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        GalleryViewModel galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.storeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        galleryViewModel.getStores().observe(getViewLifecycleOwner(), stores -> {
            GalleryAdapter adapter = new GalleryAdapter(stores);
            binding.storeRecyclerView.setAdapter(adapter);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}