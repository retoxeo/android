package com.dam2.reto_1_xeo.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.activities.MainActivity;
import com.dam2.reto_1_xeo.models.LoginResponse.UserData;
import com.dam2.reto_1_xeo.utils.SharedPreferencesHelper;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView usernameTextView = view.findViewById(R.id.usernameTextView);
        TextView emailTextView = view.findViewById(R.id.emailTextView);
        TextView addressTextView = view.findViewById(R.id.addressTextView);
        ImageView profileImageView = view.findViewById(R.id.profileImageView);
        View editCredentialsLinearLayout = view.findViewById(R.id.editCredentialsLinearLayout);

        editCredentialsLinearLayout.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.navigation_edit_credentials);
        });

        UserData userData = SharedPreferencesHelper.getUserData(requireActivity());

        if (userData != null) {
            usernameTextView.setText(userData.getNombre() + " " + userData.getApellido1() + " " + userData.getApellido2());
            emailTextView.setText(userData.getCorreo());

            String address = userData.getCalle() + " " + userData.getNumero();
            addressTextView.setText(address);

            Glide.with(this)
                    .load(userData.getFoto())
                    .error(R.drawable.obras)
                    .into(profileImageView);
        }
    }
}