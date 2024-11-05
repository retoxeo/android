package com.dam2.reto_1_xeo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.dam2.reto_1_xeo.R;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ImageButton backButton = view.findViewById(R.id.backButton);
        TextView registerTextView = view.findViewById(R.id.registerTextView);
        Button loginButton = view.findViewById(R.id.loginButton);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.navigation_register);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Iniciando sesión...", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}