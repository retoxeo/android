package com.dam2.reto_1_xeo.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.activities.MainActivity;
import com.dam2.reto_1_xeo.models.CartItem;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private MainActivity mainActivity;

    public CartAdapter(List<CartItem> cartItems, MainActivity mainActivity) {
        this.cartItems = cartItems;
        this.mainActivity = mainActivity;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        String formattedPrice = "€" + decimalFormat.format(cartItem.getPrecio());
        holder.nameTextView.setText(cartItem.getNombre());
        holder.quantityTextView.setText("Cantidad: " + cartItem.getCantidad());
        holder.priceTextView.setText("Precio: " + formattedPrice);

        holder.increaseButton.setOnClickListener(v -> {
            cartItem.incrementarCantidad();
            holder.quantityTextView.setText("Cantidad: " + cartItem.getCantidad());
            mainActivity.updateTotalPrice();
            mainActivity.updateCartCount();
        });

        holder.decreaseButton.setOnClickListener(v -> {
            if (cartItem.getCantidad() > 1) {
                cartItem.decrementarCantidad();
                holder.quantityTextView.setText("Cantidad: " + cartItem.getCantidad());
                mainActivity.updateTotalPrice();
                mainActivity.updateCartCount();
            } else {
                cartItems.remove(position);
                notifyItemRemoved(position);
                mainActivity.updateTotalPrice();
                mainActivity.updateCartCount();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView quantityTextView;
        public TextView priceTextView;
        public ImageButton increaseButton;
        public ImageButton decreaseButton;

        public CartViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.cartItemName);
            quantityTextView = itemView.findViewById(R.id.cartItemQuantity);
            priceTextView = itemView.findViewById(R.id.cartItemPrice);
            increaseButton = itemView.findViewById(R.id.cartItemIncreaseButton);
            decreaseButton = itemView.findViewById(R.id.cartItemDecreaseButton);
        }
    }
}