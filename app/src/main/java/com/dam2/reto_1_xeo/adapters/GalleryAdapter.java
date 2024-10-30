package com.dam2.reto_1_xeo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.models.Store;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private final List<Store> stores;

    public GalleryAdapter(List<Store> stores) {
        this.stores = stores;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_store, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Store store = stores.get(position);
        holder.bind(store);
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {

        private final TextView storeName;
        private final RecyclerView photoRecyclerView;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.storeName);
            photoRecyclerView = itemView.findViewById(R.id.photoRecyclerView);
        }

        public void bind(Store store) {
            storeName.setText(store.getName());
            photoRecyclerView.setLayoutManager(
                    new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            photoRecyclerView.setAdapter(new GalleryPhotoAdapter(store.getPhotos()));
        }
    }
}

