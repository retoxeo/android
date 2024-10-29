package com.dam2.reto_1_xeo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.dam2.reto_1_xeo.databinding.FragmentShopBinding;
import com.dam2.reto_1_xeo.viewmodels.ShopViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.dam2.reto_1_xeo.adapters.ShopPagerAdapter;


public class ShopFragment extends Fragment {

    private FragmentShopBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShopViewModel shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ViewPager2 viewPager = binding.viewPager;
        TabLayout tabLayout = binding.tabLayout;

        ShopPagerAdapter adapter = new ShopPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Juegos");
                            break;
                        case 1:
                            tab.setText("Consolas");
                            break;
                        case 2:
                            tab.setText("Smarts");
                            break;
                    }
                }).attach();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}