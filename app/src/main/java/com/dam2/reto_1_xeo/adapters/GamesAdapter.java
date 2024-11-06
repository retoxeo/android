package com.dam2.reto_1_xeo.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.models.Game;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {

    private final List<Game> gameList;

    public GamesAdapter(List<Game> gameList) {
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.nameTextView.setText(game.getNombre());

        if (game.getFotos() != null && !game.getFotos().isEmpty()) {
            String firstImageUrl = game.getFotos().get(0);
            Log.d("GamesAdapter", "Image URL: " + firstImageUrl);
            Glide.with(holder.itemView.getContext())
                    .load(firstImageUrl)
                    .placeholder(R.drawable.obras)  // Una imagen que muestre mientras carga
                    .error(R.drawable.home_selected_24px)       // Una imagen que muestre si ocurre un error
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.obras);
        }
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageView;

        GameViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            imageView = itemView.findViewById(R.id.imageViewGame);
        }
    }
}
