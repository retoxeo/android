package com.dam2.reto_1_xeo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.activities.MainActivity;
import com.dam2.reto_1_xeo.api.ApiService;
import com.dam2.reto_1_xeo.api.RetrofitClient;
import com.dam2.reto_1_xeo.models.LoginRequest;
import com.dam2.reto_1_xeo.models.LoginResponse;
import com.dam2.reto_1_xeo.models.SharedPreferencesHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ImageButton backButton = view.findViewById(R.id.backButton);
        TextView registerTextView = view.findViewById(R.id.registerTextView);
        Button loginButton = view.findViewById(R.id.loginButton);
        EditText emailEditText = view.findViewById(R.id.emailEditText);
        EditText passwordEditText = view.findViewById(R.id.passwordEditText);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        registerTextView.setOnClickListener(v -> navController.navigate(R.id.navigation_register));

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity(), "Por favor, ingrese el correo y la contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(email, password, navController);
        });

        return view;
    }

    private void loginUser(String email, String password, NavController navController) {
        LoginRequest loginRequest = new LoginRequest(email, password);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<LoginResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    assert loginResponse != null;
                    if ("success".equals(loginResponse.getStatus())) {
                        SharedPreferencesHelper.saveUserData(requireActivity(), loginResponse.getData());

                        Toast.makeText(getActivity(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                        navController.navigate(R.id.navigation_shop);
                        ((MainActivity) requireActivity()).showNavigation();
                    } else {
                        Toast.makeText(getActivity(), "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}