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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.activities.MainActivity;
import com.dam2.reto_1_xeo.models.CartItem;
import com.dam2.reto_1_xeo.models.Console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleDetailsFragment extends Fragment {

    private Console console;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_console_details, container, false);

        if (getArguments() != null) {
            console = (Console) getArguments().getSerializable("console");
        }

        TextView nameTextView = rootView.findViewById(R.id.textViewName);
        TextView descriptionTextView = rootView.findViewById(R.id.textViewDescription);
        TextView priceTextView = rootView.findViewById(R.id.textViewPrice);
        TextView releaseDateTextView = rootView.findViewById(R.id.textViewReleaseDate);
        TextView developerTextView = rootView.findViewById(R.id.textViewDeveloper);
        TextView stockTextView = rootView.findViewById(R.id.textViewStock);
        ImageView consoleImageView = rootView.findViewById(R.id.imageViewConsole);
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
                CartItem cartItem = new CartItem(console.getId_producto(), console.getNombre(), console.getPrecio(), 1, true);
                ((MainActivity) getActivity()).addToCart(cartItem);
            }
        });

        if (console != null) {
            nameTextView.setText(console.getNombre());
            descriptionTextView.setText(console.getDescripcion());
            priceTextView.setText(String.format("Precio: €%.2f", console.getPrecio()));

            String formattedDate = formatDate(console.getFecha_lanzamiento());
            releaseDateTextView.setText("Fecha de lanzamiento: " + formattedDate);

            developerTextView.setText("Fabricante: " + console.getDesarrollador());
            stockTextView.setText("Stock: " + console.getStock());

            if (console.getFotos() != null && !console.getFotos().isEmpty()) {
                Glide.with(this)
                        .load(console.getFotos().get(0))
                        .into(consoleImageView);
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
