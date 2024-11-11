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
import com.dam2.reto_1_xeo.models.Game;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameDetailsFragment extends Fragment {

    private Game game;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_details, container, false);

        if (getArguments() != null) {
            game = (Game) getArguments().getSerializable("game");
        }

        TextView nameTextView = rootView.findViewById(R.id.textViewName);
        TextView descriptionTextView = rootView.findViewById(R.id.textViewDescription);
        TextView priceTextView = rootView.findViewById(R.id.textViewPrice);
        TextView rentalPriceTextView = rootView.findViewById(R.id.textViewRentalPrice);
        TextView pegiTextView = rootView.findViewById(R.id.textViewPegi);
        TextView releaseDateTextView = rootView.findViewById(R.id.textViewReleaseDate);
        TextView developerTextView = rootView.findViewById(R.id.textViewDeveloper);
        TextView genresTextView = rootView.findViewById(R.id.textViewGenres);
        ImageView gameImageView = rootView.findViewById(R.id.imageViewGame);
        ImageButton backButton = rootView.findViewById(R.id.backButton);
        TextView stockTextView = rootView.findViewById(R.id.textViewStock);
        Button buyButton = rootView.findViewById(R.id.buttonBuy);
        Button rentButton = rootView.findViewById(R.id.buttonRent);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.onBackPressed();
                }
            }
        });

        buyButton.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                CartItem cartItem = new CartItem(game.getId_producto(), game.getNombre(), game.getPrecio(), 1, true);
                ((MainActivity) getActivity()).addToCart(cartItem); // Agregar al carrito
            }
        });

        rentButton.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                CartItem cartItem = new CartItem(game.getId_producto(), game.getNombre(), game.getPrecio_alquiler(), 1, false);
                ((MainActivity) getActivity()).addToCart(cartItem); // Agregar al carrito
            }
        });

        if (game != null) {
            nameTextView.setText(game.getNombre());
            descriptionTextView.setText(game.getDescripcion());
            priceTextView.setText(String.format("Precio: €%.2f", game.getPrecio()));
            rentalPriceTextView.setText(String.format("Alquiler: €%.2f", game.getPrecio_alquiler()));
            pegiTextView.setText(String.format("PEGI: %d", game.getPegi()));

            String formattedDate = formatDate(game.getFecha_lanzamiento());
            releaseDateTextView.setText("Fecha de lanzamiento: "+formattedDate);

            developerTextView.setText("Desarrollador: "+game.getDesarrollador());

            StringBuilder genres = new StringBuilder();
            for (int i = 0; i < game.getGeneros().size(); i++) {
                genres.append(game.getGeneros().get(i).getNombre());
                if (i < game.getGeneros().size() - 1) {
                    genres.append(", ");
                }
            }
            genresTextView.setText("Generos: "+ genres);

            stockTextView.setText("Stock: " + game.getStock());

            if (game.getFotos() != null && !game.getFotos().isEmpty()) {
                Glide.with(this)
                        .load(game.getFotos().get(0))
                        .into(gameImageView);
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
