package com.dam2.reto_1_xeo.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.adapters.CartAdapter;
import com.dam2.reto_1_xeo.databinding.ActivityMainBinding;
import com.dam2.reto_1_xeo.models.CartItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;
    private View topMenu;
    private LinearLayout cartDrawer;
    private List<CartItem> cartItems = new ArrayList<>();
    public CartAdapter cartAdapter;
    public TextView totalPriceTextView;
    public TextView cartCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        topMenu = findViewById(R.id.top_menu);
        cartDrawer = findViewById(R.id.cart_drawer);
        RecyclerView cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.total_price);
        Button orderButton = findViewById(R.id.btn_order);
        ImageButton backButton = findViewById(R.id.backButton);
        cartCountTextView = findViewById(R.id.cart_count);

        cartItems = new ArrayList<>();

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartItems, this);
        cartRecyclerView.setAdapter(cartAdapter);

        orderButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            if (cartItems.isEmpty()) {
                Toast.makeText(MainActivity.this, "¡No tienes productos en el carrito!", Toast.LENGTH_SHORT).show();
            } else if (!isLoggedIn) {
                Toast.makeText(MainActivity.this, "¡Inicia sesión para continuar!", Toast.LENGTH_SHORT).show();
            } else {
                toggleCart();
                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_order);
            }
        });

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_shop, R.id.navigation_location, R.id.navigation_gallery, R.id.navigation_info)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        ImageView profileIcon = findViewById(R.id.profile);
        profileIcon.setOnClickListener(this::showProfileMenu);

        ImageView cartIcon = findViewById(R.id.cart);
        cartIcon.setOnClickListener(v -> toggleCart());

        backButton.setOnClickListener(v -> toggleCart());
    }

    private void showProfileMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());
        } else {
            popupMenu.getMenuInflater().inflate(R.menu.profile_menu_logged_out, popupMenu.getMenu());
        }

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_login && !isLoggedIn) {
                hideNavigation();
                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_login);
                return true;
            } else if (item.getItemId() == R.id.action_profile && isLoggedIn) {
                hideNavigation();
                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_profile);
                return true;
            } else if (item.getItemId() == R.id.action_logout && isLoggedIn) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void hideNavigation() {
        navView.setVisibility(View.GONE);
        topMenu.setVisibility(View.GONE);
    }

    public void showNavigation() {
        navView.setVisibility(View.VISIBLE);
        topMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (cartDrawer.getVisibility() == View.VISIBLE) {
            cartDrawer.animate().translationX(300).setDuration(300).withEndAction(() -> cartDrawer.setVisibility(View.GONE)).start();
        } else {
            super.onBackPressed();
            if (!isReturningToExceptionalScreen()) {
                showNavigation();
            }
        }
    }

    private boolean isReturningToExceptionalScreen() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        int currentDestinationId = Objects.requireNonNull(navController.getCurrentDestination()).getId();
        return currentDestinationId == R.id.navigation_login || currentDestinationId == R.id.navigation_profile || currentDestinationId == R.id.navigation_register;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void addToCart(CartItem item) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getId() == item.getId() && cartItem.isEsCompra() == item.isEsCompra()) {
                cartItem.incrementarCantidad();
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice();
                updateCartCount();
                return;
            }
        }
        cartItems.add(item);
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
        updateCartCount();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    private void toggleCart() {
        if (cartDrawer.getVisibility() == View.GONE) {
            new CartAnimationTask(true).execute();
        } else {
            new CartAnimationTask(false).execute();
        }
    }

    @SuppressLint("SetTextI18n")
    public void updateTotalPrice() {
        double totalPriceCompra = 0;
        double totalPriceAlquiler = 0;

        for (CartItem cartItem : cartItems) {
            if (cartItem.isEsCompra()) {
                totalPriceCompra += cartItem.getPrecio() * cartItem.getCantidad();
            } else {
                totalPriceAlquiler += cartItem.getPrecio() * cartItem.getCantidad();
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        totalPriceTextView.setText("Total: €" + decimalFormat.format(totalPriceCompra) + " Alquiler: €" + decimalFormat.format(totalPriceAlquiler));
    }

    public void updateCartCount() {
        int totalItems = 0;
        for (CartItem cartItem : cartItems) {
            totalItems += cartItem.getCantidad();
        }
        cartCountTextView.setText(String.valueOf(totalItems));
    }

    @SuppressLint("StaticFieldLeak")
    private class CartAnimationTask extends AsyncTask<Void, Integer, Void> {
        private final boolean opening;
        private final int duration = 300;
        private final int stepDelay = 10;
        private final int steps = duration / stepDelay;
        private final int distance = 300;

        public CartAnimationTask(boolean opening) {
            this.opening = opening;
        }

        @Override
        protected void onPreExecute() {
            if (opening) {
                cartDrawer.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i <= steps; i++) {
                try {
                    Thread.sleep(stepDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            float progress = (float) values[0] / steps;
            float translation = opening ? distance * (1 - progress) : distance * progress;
            cartDrawer.setTranslationX(translation);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!opening) {
                cartDrawer.setVisibility(View.GONE);
            }
        }
    }
}