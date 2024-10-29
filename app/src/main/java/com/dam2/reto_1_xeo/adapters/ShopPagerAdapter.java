package com.dam2.reto_1_xeo.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dam2.reto_1_xeo.fragments.ConsolesTabFragment;
import com.dam2.reto_1_xeo.fragments.GamesTabFragment;
import com.dam2.reto_1_xeo.fragments.SmartsTabFragment;

public class ShopPagerAdapter extends FragmentStateAdapter {

    public ShopPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new ConsolesTabFragment();
            case 2:
                return new SmartsTabFragment();
            default:
                return new GamesTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}