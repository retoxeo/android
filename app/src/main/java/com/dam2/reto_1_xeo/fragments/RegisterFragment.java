package com.dam2.reto_1_xeo.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.api.ApiService;
import com.dam2.reto_1_xeo.api.RetrofitClient;
import com.dam2.reto_1_xeo.models.RegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText nameEditText, lastName1EditText, lastName2EditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private ApiService apiService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        nameEditText = view.findViewById(R.id.nameEditText);
        lastName1EditText = view.findViewById(R.id.lastName1EditText);
        lastName2EditText = view.findViewById(R.id.lastName2EditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
        Button registerButton = view.findViewById(R.id.registerButton);
        ImageButton backButton = view.findViewById(R.id.backButton);

        apiService = RetrofitClient.getApiService();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        backButton.setOnClickListener(v -> navController.navigate(R.id.navigation_login));

        registerButton.setOnClickListener(v -> registerUser(navController));

        return view;
    }

    private void registerUser(NavController navController) {
        String nombre = nameEditText.getText().toString().trim();
        String apellido1 = lastName1EditText.getText().toString().trim();
        String apellido2 = lastName2EditText.getText().toString().trim();
        String correo = emailEditText.getText().toString().trim();
        String contrasena = passwordEditText.getText().toString().trim();
        String confirmContrasena = confirmPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido1) || TextUtils.isEmpty(apellido2) ||
                TextUtils.isEmpty(correo) || TextUtils.isEmpty(contrasena) || TextUtils.isEmpty(confirmContrasena)) {
            Toast.makeText(getActivity(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contrasena.equals(confirmContrasena)) {
            Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(nombre, apellido1, apellido2, correo, contrasena);
        Call<Void> call = apiService.registerUser(registerRequest);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.navigation_login);
                } else {
                    Toast.makeText(getActivity(), "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}