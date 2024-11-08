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
import com.dam2.reto_1_xeo.models.Console;

import java.util.List;

public class ConsolesAdapter extends RecyclerView.Adapter<ConsolesAdapter.ConsoleViewHolder> {

    private final List<Console> consoleList;
    private final OnConsoleClickListener onConsoleClickListener;

    public interface OnConsoleClickListener {
        void onConsoleClick(Console console);
    }

    public ConsolesAdapter(List<Console> consoleList, OnConsoleClickListener onConsoleClickListener) {
        this.consoleList = consoleList;
        this.onConsoleClickListener = onConsoleClickListener;
    }

    @NonNull
    @Override
    public ConsoleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_console, parent, false);
        return new ConsoleViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ConsoleViewHolder holder, int position) {
        Console console = consoleList.get(position);
        holder.nameTextView.setText(console.getNombre());
        holder.priceTextView.setText(String.format("€%.2f", console.getPrecio()));

        if (console.getFotos() != null && !console.getFotos().isEmpty()) {
            String firstImageUrl = console.getFotos().get(0);
            Glide.with(holder.itemView.getContext())
                    .load(firstImageUrl)
                    .into(holder.imageView);
        }

        holder.itemView.setOnClickListener(v -> onConsoleClickListener.onConsoleClick(console));
    }

    @Override
    public int getItemCount() {
        return consoleList.size();
    }

    public static class ConsoleViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView priceTextView;
        ImageView imageView;

        ConsoleViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            priceTextView = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageViewConsole);
        }
    }
}