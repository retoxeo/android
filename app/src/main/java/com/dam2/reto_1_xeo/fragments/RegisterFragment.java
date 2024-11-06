package com.dam2.reto_1_xeo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.activities.MainActivity;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        ImageButton backButton = view.findViewById(R.id.backButton);
        Button registerButton = view.findViewById(R.id.registerButton);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        backButton.setOnClickListener(v -> navController.navigate(R.id.navigation_login));

        registerButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Registro en proceso...", Toast.LENGTH_SHORT).show();
            navController.navigate(R.id.navigation_shop);
            ((MainActivity) requireActivity()).showNavigation();
        });

        return view;
    }
}
