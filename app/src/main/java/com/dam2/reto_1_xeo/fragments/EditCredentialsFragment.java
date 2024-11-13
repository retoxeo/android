package com.dam2.reto_1_xeo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.activities.MainActivity;
import com.dam2.reto_1_xeo.api.ApiService;
import com.dam2.reto_1_xeo.api.RetrofitClient;
import com.dam2.reto_1_xeo.models.LoginResponse;
import com.dam2.reto_1_xeo.utils.SharedPreferencesHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCredentialsFragment extends Fragment {

    private EditText nombreEditText, apellido1EditText, apellido2EditText, ciudadEditText, provinciaEditText, paisEditText, telefonoEditText, newPasswordEditText, confirmNewPasswordEditText, calleEditText, numeroEditText, codigoPostalEditText;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_credentials, container, false);

        nombreEditText = view.findViewById(R.id.nombreEditText);
        apellido1EditText = view.findViewById(R.id.apellido1EditText);
        apellido2EditText = view.findViewById(R.id.apellido2EditText);
        ciudadEditText = view.findViewById(R.id.ciudadEditText);
        provinciaEditText = view.findViewById(R.id.provinciaEditText);
        paisEditText = view.findViewById(R.id.paisEditText);
        telefonoEditText = view.findViewById(R.id.telefonoEditText);
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText);
        confirmNewPasswordEditText = view.findViewById(R.id.confirmNewPasswordEditText);
        calleEditText = view.findViewById(R.id.calleEditText);
        numeroEditText = view.findViewById(R.id.numeroEditText);
        codigoPostalEditText = view.findViewById(R.id.codigoPostalEditText);
        saveButton = view.findViewById(R.id.saveButton);
        ImageButton backButton = view.findViewById(R.id.backButton);

        LoginResponse.UserData userData = SharedPreferencesHelper.getUserData(requireActivity());

        if (userData != null) {
            nombreEditText.setText(userData.getNombre());
            apellido1EditText.setText(userData.getApellido1());
            apellido2EditText.setText(userData.getApellido2());
            ciudadEditText.setText(userData.getCiudad());
            provinciaEditText.setText(userData.getProvincia());
            paisEditText.setText(userData.getPais());
            telefonoEditText.setText(userData.getTelefono());
            calleEditText.setText(userData.getCalle());
            numeroEditText.setText(String.valueOf(userData.getNumero()));
            codigoPostalEditText.setText(String.valueOf(userData.getCp()));
        }

        saveButton.setOnClickListener(v -> updateUserData());

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        backButton.setOnClickListener(v -> navController.navigate(R.id.navigation_profile));

        return view;
    }

    private void updateUserData() {
        String nombre = nombreEditText.getText().toString();
        String apellido1 = apellido1EditText.getText().toString();
        String apellido2 = apellido2EditText.getText().toString();
        String ciudad = ciudadEditText.getText().toString();
        String provincia = provinciaEditText.getText().toString();
        String pais = paisEditText.getText().toString();
        String telefono = telefonoEditText.getText().toString();
        String calle = calleEditText.getText().toString();
        String numero = numeroEditText.getText().toString();
        String codigoPostal = codigoPostalEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();
        String confirmNewPassword = confirmNewPasswordEditText.getText().toString();

        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginResponse.UserData currentUserData = SharedPreferencesHelper.getUserData(requireActivity());
        if (currentUserData == null) {
            Toast.makeText(requireContext(), "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }
        int userId = currentUserData.getId();

        LoginResponse.UserData updatedUserData = new LoginResponse.UserData();
        updatedUserData.setId(userId);
        updatedUserData.setNombre(nombre);
        updatedUserData.setApellido1(apellido1);
        updatedUserData.setApellido2(apellido2);
        updatedUserData.setCiudad(ciudad);
        updatedUserData.setProvincia(provincia);
        updatedUserData.setPais(pais);
        updatedUserData.setTelefono(telefono);
        updatedUserData.setCalle(calle);
        updatedUserData.setNumero(Integer.parseInt(numero));
        updatedUserData.setCp(Integer.parseInt(codigoPostal));

        if (!newPassword.isEmpty()) {
            updatedUserData.setNewPassword(newPassword);
            updatedUserData.setConfirmNewPassword(confirmNewPassword);
        }

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<Void> call = apiService.updateUser(userId, updatedUserData);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                    requireActivity().onBackPressed();
                } else {
                    Toast.makeText(requireContext(), "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(requireContext(), "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

