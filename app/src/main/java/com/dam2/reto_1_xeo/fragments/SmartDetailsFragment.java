package com.dam2.reto_1_xeo.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.activities.MainActivity;
import com.dam2.reto_1_xeo.models.CartItem;
import com.dam2.reto_1_xeo.models.Smarts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmartDetailsFragment extends Fragment {

    private Smarts smart;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_smart_details, container, false);

        if (getArguments() != null) {
            smart = (Smarts) getArguments().getSerializable("smart");
        }

        TextView nameTextView = rootView.findViewById(R.id.textViewName);
        TextView descriptionTextView = rootView.findViewById(R.id.textViewDescription);
        TextView priceTextView = rootView.findViewById(R.id.textViewPrice);
        TextView releaseDateTextView = rootView.findViewById(R.id.textViewReleaseDate);
        TextView developerTextView = rootView.findViewById(R.id.textViewDeveloper);
        TextView stockTextView = rootView.findViewById(R.id.textViewStock);
        TextView ramTextView = rootView.findViewById(R.id.textViewRam);
        TextView processorTextView = rootView.findViewById(R.id.textViewProcessor);
        TextView storageTextView = rootView.findViewById(R.id.textViewStorage);
        ImageView smartImageView = rootView.findViewById(R.id.imageViewSmart);
        ImageButton backButton = rootView.findViewById(R.id.backButton);
        Button buyButton = rootView.findViewById(R.id.buttonBuy);

        backButton.setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.onBackPressed();
            }
        });

        buyButton.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                CartItem cartItem = new CartItem(smart.getId_producto(), smart.getNombre(), smart.getPrecio(), 1, true);
                ((MainActivity) getActivity()).addToCart(cartItem);
            }
        });

        if (smart != null) {
            nameTextView.setText(smart.getNombre());
            descriptionTextView.setText(smart.getDescripcion());
            priceTextView.setText(String.format("Precio: €%.2f", smart.getPrecio()));

            String formattedDate = formatDate(smart.getFecha_lanzamiento());
            releaseDateTextView.setText("Fecha de lanzamiento: " + formattedDate);

            developerTextView.setText("Fabricante: " + smart.getDesarrollador());
            stockTextView.setText("Stock: " + smart.getStock());

            ramTextView.setText("RAM: " + smart.getRam());
            processorTextView.setText("Procesador: " + smart.getProcesador());
            storageTextView.setText("Almacenamiento: " + smart.getAlmacenamiento());

            if (smart.getFotos() != null && !smart.getFotos().isEmpty()) {
                Glide.with(this)
                        .load(smart.getFotos().get(0))
                        .into(smartImageView);
            }
        }

        return rootView;
    }

    private String formatDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }
}