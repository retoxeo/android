package com.dam2.reto_1_xeo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.activities.MainActivity;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        rootView.findViewById(R.id.backButton).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigateUp();
            ((MainActivity) requireActivity()).showNavigation();
        });

        return rootView;
    }
}
