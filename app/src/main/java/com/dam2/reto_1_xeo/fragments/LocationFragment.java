package com.dam2.reto_1_xeo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dam2.reto_1_xeo.databinding.FragmentLocationBinding;
import com.dam2.reto_1_xeo.viewmodels.LocationViewModel;

public class LocationFragment extends Fragment {

    private FragmentLocationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LocationViewModel dashboardViewModel =
                new ViewModelProvider(this).get(LocationViewModel.class);

        binding = FragmentLocationBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}