package com.dam2.reto_1_xeo.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.adapters.GamesAdapter;
import com.dam2.reto_1_xeo.api.RetrofitClient;
import com.dam2.reto_1_xeo.models.Game;
import com.dam2.reto_1_xeo.models.Genre;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesTabFragment extends Fragment {

    private EditText editTextSearch;
    private Spinner spinnerGenres;
    private GamesAdapter gamesAdapter;
    private final List<Game> gameList = new ArrayList<>();
    private final List<Game> filteredGameList = new ArrayList<>();
    private final List<Genre> genreList = new ArrayList<>();
    private final List<String> genreNames = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_games_tab, container, false);

        RecyclerView recyclerViewGames = rootView.findViewById(R.id.recyclerViewGames);
        recyclerViewGames.setLayoutManager(new LinearLayoutManager(getContext()));
        gamesAdapter = new GamesAdapter(filteredGameList);
        recyclerViewGames.setAdapter(gamesAdapter);

        editTextSearch = rootView.findViewById(R.id.editTextSearch);
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

        spinnerGenres = rootView.findViewById(R.id.spinnerGenres);
        loadGenres();

        spinnerGenres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                filterGames();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        loadGames();

        return rootView;
    }

    private void loadGames() {
        Call<List<Game>> call = RetrofitClient.getApiService().getGames();
        call.enqueue(new Callback<List<Game>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Game>> call, @NonNull Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    gameList.clear();
                    gameList.addAll(response.body());
                    filteredGameList.clear();
                    filteredGameList.addAll(gameList);
                    gamesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Game>> call, @NonNull Throwable t) {
            }
        });
    }

    private void loadGenres() {
        Call<List<Genre>> call = RetrofitClient.getApiService().getGenres();
        call.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(@NonNull Call<List<Genre>> call, @NonNull Response<List<Genre>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    genreList.clear();
                    genreNames.clear();
                    genreList.addAll(response.body());
                    genreNames.add("Todos");

                    for (Genre genre : genreList) {
                        genreNames.add(genre.getNombre());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                            android.R.layout.simple_spinner_item, genreNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerGenres.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Genre>> call, @NonNull Throwable t) {
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filterGames() {
        String searchQuery = editTextSearch.getText().toString().toLowerCase();
        String selectedGenre = spinnerGenres.getSelectedItem().toString();

        filteredGameList.clear();

        for (Game game : gameList) {
            boolean matchesSearch = game.getNombre().toLowerCase().contains(searchQuery);
            boolean matchesGenre = selectedGenre.equals("Todos") || game.getDescripcion().toLowerCase().contains(selectedGenre.toLowerCase());

            if (matchesSearch && matchesGenre) {
                filteredGameList.add(game);
            }
        }

        gamesAdapter.notifyDataSetChanged();
    }
}