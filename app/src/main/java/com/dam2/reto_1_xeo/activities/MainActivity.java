package com.dam2.reto_1_xeo.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.databinding.ActivityMainBinding;
import com.dam2.reto_1_xeo.fragments.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;
    private View topMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        topMenu = findViewById(R.id.top_menu);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_shop, R.id.navigation_location, R.id.navigation_gallery, R.id.navigation_info)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        ImageView profileIcon = findViewById(R.id.profile);
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfileMenu(view);
            }
        });
    }

    private void showProfileMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_login) {
                    hideNavigation();
                    LoginFragment loginFragment = new LoginFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment_activity_main, loginFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                } else if (item.getItemId() == R.id.action_profile) {
                    Toast.makeText(MainActivity.this, "Perfil seleccionado", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.action_logout) {
                    Toast.makeText(MainActivity.this, "Cerrar sesión seleccionado", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void hideNavigation() {
        navView.setVisibility(View.GONE);
        topMenu.setVisibility(View.GONE);
    }

    private void showNavigation() {
        navView.setVisibility(View.VISIBLE);
        topMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showNavigation();
    }
}