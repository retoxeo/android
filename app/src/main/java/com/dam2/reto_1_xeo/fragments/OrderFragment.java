package com.dam2.reto_1_xeo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.activities.MainActivity;
import com.dam2.reto_1_xeo.api.ApiService;
import com.dam2.reto_1_xeo.api.RetrofitClient;
import com.dam2.reto_1_xeo.models.CartItem;
import com.dam2.reto_1_xeo.models.Pedido;
import com.dam2.reto_1_xeo.models.PedidoProducto;
import com.dam2.reto_1_xeo.models.PedidoResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    private EditText descriptionEditText, countryEditText, provinceEditText, postalCodeEditText,
            cityEditText, streetEditText, numberEditText, userIdEditText;
    private Button confirmOrderButton;

    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        descriptionEditText = view.findViewById(R.id.editTextDescription);
        countryEditText = view.findViewById(R.id.editTextCountry);
        provinceEditText = view.findViewById(R.id.editTextProvince);
        postalCodeEditText = view.findViewById(R.id.editTextPostalCode);
        cityEditText = view.findViewById(R.id.editTextCity);
        streetEditText = view.findViewById(R.id.editTextStreet);
        numberEditText = view.findViewById(R.id.editTextNumber);
        userIdEditText = view.findViewById(R.id.editTextUserId);
        confirmOrderButton = view.findViewById(R.id.buttonConfirmOrder);

        apiService = RetrofitClient.getApiService();

        confirmOrderButton.setOnClickListener(v -> {
            createPedido();
        });
    }

    private void createPedido() {
        String description = descriptionEditText.getText().toString().trim();
        String country = countryEditText.getText().toString().trim();
        String province = provinceEditText.getText().toString().trim();
        String postalCode = postalCodeEditText.getText().toString().trim();
        String city = cityEditText.getText().toString().trim();
        String street = streetEditText.getText().toString().trim();
        String number = numberEditText.getText().toString().trim();
        String userId = userIdEditText.getText().toString().trim();

        if (country.isEmpty() || city.isEmpty() || userId.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, complete todos los campos requeridos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Pedido pedido = new Pedido(startDate, null, description, country, province, postalCode, city, street, number, Integer.parseInt(userId), 1);

        apiService.createPedido(pedido).enqueue(new Callback<PedidoResponse>() {
            @Override
            public void onResponse(Call<PedidoResponse> call, Response<PedidoResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            int pedidoId = response.body().getId_pedido();
                            MainActivity mainActivity = (MainActivity) getActivity();
                            if (mainActivity != null) {
                                for (CartItem cartItem : mainActivity.getCartItems()) {
                                    PedidoProducto pedidoProducto = new PedidoProducto();
                                    pedidoProducto.setCantidad(cartItem.getCantidad());
                                    pedidoProducto.setId_pedido(pedidoId);
                                    pedidoProducto.setId_producto(cartItem.getId());

                                    if (cartItem.isEsCompra()) {
                                        pedidoProducto.setPrecio_final(cartItem.getPrecio());
                                    } else {
                                        pedidoProducto.setPrecio_final_alquiler(cartItem.getPrecio());
                                    }

                                    addProductToPedido(pedidoProducto);
                                }
                            }

                            Toast.makeText(getContext(), "Pedido realizado", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "No se pudo obtener el ID del pedido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error al crear el pedido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PedidoResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addProductToPedido(PedidoProducto pedidoProducto) {
        apiService.createPedidoProducto(pedidoProducto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                } else {
                    Toast.makeText(getContext(), "Error al agregar el producto al pedido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}