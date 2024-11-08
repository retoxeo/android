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
import com.dam2.reto_1_xeo.models.Smarts;

import java.util.List;

public class SmartsAdapter extends RecyclerView.Adapter<SmartsAdapter.SmartViewHolder> {

    private final List<Smarts> smartList;
    private final OnSmartClickListener onSmartClickListener;

    public interface OnSmartClickListener {
        void onSmartClick(Smarts smart);
    }

    public SmartsAdapter(List<Smarts> smartList, OnSmartClickListener onSmartClickListener) {
        this.smartList = smartList;
        this.onSmartClickListener = onSmartClickListener;
    }

    @NonNull
    @Override
    public SmartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_smart, parent, false);
        return new SmartViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(SmartViewHolder holder, int position) {
        Smarts smart = smartList.get(position);
        holder.nameTextView.setText(smart.getNombre());
        holder.priceTextView.setText(String.format("€%.2f", smart.getPrecio()));

        if (smart.getFotos() != null && !smart.getFotos().isEmpty()) {
            String firstImageUrl = smart.getFotos().get(0);
            Glide.with(holder.itemView.getContext())
                    .load(firstImageUrl)
                    .into(holder.imageView);
        }

        holder.itemView.setOnClickListener(v -> onSmartClickListener.onSmartClick(smart));
    }

    @Override
    public int getItemCount() {
        return smartList.size();
    }

    public static class SmartViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView priceTextView;
        ImageView imageView;

        SmartViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            priceTextView = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageViewSmart);
        }
    }
}
