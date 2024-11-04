package com.dam2.reto_1_xeo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.databinding.FragmentInfoBinding;
import com.dam2.reto_1_xeo.viewmodels.InfoViewModel;

public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InfoViewModel infoViewModel =
                new ViewModelProvider(this).get(InfoViewModel.class);

        binding = FragmentInfoBinding.inflate(inflater, container, false);

        binding.logoImage.setImageResource(R.drawable.obras);

        binding.textHistoria.setText(getString(R.string.historia_text));
        binding.textDescripcion.setText(getString(R.string.descripcion_text));
        binding.textContacto.setText(getString(R.string.contacto_text));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}