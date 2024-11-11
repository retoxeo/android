package com.dam2.reto_1_xeo.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.adapters.SmartsAdapter;
import com.dam2.reto_1_xeo.models.Smarts;
import com.dam2.reto_1_xeo.viewmodels.SmartsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SmartsTabFragment extends Fragment implements SmartsAdapter.OnSmartClickListener {

    private EditText editTextSearch;
    private SmartsAdapter smartsAdapter;
    private SmartsViewModel smartsViewModel;
    private final List<Smarts> filteredSmartList = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_smarts_tab, container, false);

        RecyclerView recyclerViewSmarts = rootView.findViewById(R.id.recyclerViewSmarts);
        recyclerViewSmarts.setLayoutManager(new GridLayoutManager(getContext(), 2));

        smartsAdapter = new SmartsAdapter(filteredSmartList, this);
        recyclerViewSmarts.setAdapter(smartsAdapter);

        editTextSearch = rootView.findViewById(R.id.editTextSearch);

        smartsViewModel = new ViewModelProvider(this).get(SmartsViewModel.class);

        smartsViewModel.getSmarts().observe(getViewLifecycleOwner(), smarts -> {
            filteredSmartList.clear();
            filteredSmartList.addAll(smarts);
            smartsAdapter.notifyDataSetChanged();
        });

        smartsViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        smartsViewModel.loadSmarts();

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filterSmarts();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return rootView;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filterSmarts() {
        String searchQuery = editTextSearch.getText().toString().toLowerCase();

        List<Smarts> smarts = smartsViewModel.getSmarts().getValue();
        if (smarts != null) {
            filteredSmartList.clear();
            for (Smarts smart : smarts) {
                if (smart.getNombre().toLowerCase().contains(searchQuery)) {
                    filteredSmartList.add(smart);
                }
            }
            smartsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSmartClick(Smarts smart) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("smart", smart);
        navController.navigate(R.id.navigation_smart_details, bundle);
    }
}