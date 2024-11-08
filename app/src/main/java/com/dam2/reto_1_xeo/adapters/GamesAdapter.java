package com.dam2.reto_1_xeo.adapters;

import android.annotation.SuppressLint;
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
    private final OnGameClickListener onGameClickListener;

    public interface OnGameClickListener {
        void onGameClick(Game game);
    }

    public GamesAdapter(List<Game> gameList, OnGameClickListener onGameClickListener) {
        this.gameList = gameList;
        this.onGameClickListener = onGameClickListener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.nameTextView.setText(game.getNombre());
        holder.priceTextView.setText(String.format("€%.2f", game.getPrecio()));

        if (game.getFotos() != null && !game.getFotos().isEmpty()) {
            String firstImageUrl = game.getFotos().get(0);
            Glide.with(holder.itemView.getContext())
                    .load(firstImageUrl)
                    .into(holder.imageView);
        }

        holder.itemView.setOnClickListener(v -> onGameClickListener.onGameClick(game));
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView priceTextView;
        ImageView imageView;

        GameViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            priceTextView = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageViewGame);
        }
    }
}
