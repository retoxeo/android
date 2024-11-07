package com.dam2.reto_1_xeo.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.adapters.GamesAdapter;
import com.dam2.reto_1_xeo.models.Game;
import com.dam2.reto_1_xeo.models.Genre;
import com.dam2.reto_1_xeo.viewmodels.GamesViewModel;

import java.util.ArrayList;
import java.util.List;

public class GamesTabFragment extends Fragment {

    private EditText editTextSearch;
    private Spinner spinnerGenres;
    private GamesAdapter gamesAdapter;
    private ProgressBar progressBar;
    private GamesViewModel gamesViewModel;
    private final List<Game> filteredGameList = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_games_tab, container, false);

        RecyclerView recyclerViewGames = rootView.findViewById(R.id.recyclerViewGames);
        recyclerViewGames.setLayoutManager(new GridLayoutManager(getContext(), 2));
        gamesAdapter = new GamesAdapter(filteredGameList);
        recyclerViewGames.setAdapter(gamesAdapter);

        editTextSearch = rootView.findViewById(R.id.editTextSearch);
        spinnerGenres = rootView.findViewById(R.id.spinnerGenres);
        progressBar = rootView.findViewById(R.id.progressBar);

        gamesViewModel = new ViewModelProvider(this).get(GamesViewModel.class);

        gamesViewModel.getGames().observe(getViewLifecycleOwner(), games -> {
            filteredGameList.clear();
            filteredGameList.addAll(games);
            gamesAdapter.notifyDataSetChanged();
        });

        gamesViewModel.getGenres().observe(getViewLifecycleOwner(), genres -> {
            List<String> genreNames = new ArrayList<>();
            genreNames.add("Todos");
            for (Genre genre : genres) {
                genreNames.add(genre.getNombre());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, genreNames);
            spinnerGenres.setAdapter(adapter);
        });

        gamesViewModel.isLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        editTextSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filterGames();
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });

        spinnerGenres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                filterGames();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        gamesViewModel.loadGames();
        gamesViewModel.loadGenres();

        return rootView;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filterGames() {
        String searchQuery = editTextSearch.getText().toString().toLowerCase();
        String selectedGenre = (spinnerGenres.getSelectedItem() != null) ? spinnerGenres.getSelectedItem().toString() : "Todos";

        List<Game> games = gamesViewModel.getGames().getValue();
        if (games != null) {
            filteredGameList.clear();
            for (Game game : games) {
                boolean matchesSearch = game.getNombre().toLowerCase().contains(searchQuery);
                boolean matchesGenre = selectedGenre.equals("Todos");

                if (!selectedGenre.equals("Todos")) {
                    for (Genre genre : game.getGeneros()) {
                        if (genre.getNombre().equalsIgnoreCase(selectedGenre)) {
                            matchesGenre = true;
                            break;
                        }
                    }
                }

                if (matchesSearch && matchesGenre) {
                    filteredGameList.add(game);
                }
            }
            gamesAdapter.notifyDataSetChanged();
        }
    }
}