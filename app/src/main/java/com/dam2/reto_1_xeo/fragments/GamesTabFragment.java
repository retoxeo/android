package com.dam2.reto_1_xeo.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.adapters.GamesAdapter;
import com.dam2.reto_1_xeo.models.Game;
import com.dam2.reto_1_xeo.models.Genre;
import com.dam2.reto_1_xeo.viewmodels.GamesViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class GamesTabFragment extends Fragment implements GamesAdapter.OnGameClickListener {

    private EditText editTextSearch;
    private Spinner spinnerGenres;
    private GamesAdapter gamesAdapter;
    private GamesViewModel gamesViewModel;
    private final List<Game> filteredGameList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean hasErrorBeenShown = false;
    private static final int REQUEST_VOICE_SEARCH = 100;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_games_tab, container, false);

        RecyclerView recyclerViewGames = rootView.findViewById(R.id.recyclerViewGames);
        recyclerViewGames.setLayoutManager(new GridLayoutManager(getContext(), 2));

        gamesAdapter = new GamesAdapter(filteredGameList, this);
        recyclerViewGames.setAdapter(gamesAdapter);

        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            hasErrorBeenShown = false;
            gamesViewModel.loadGames();
            gamesViewModel.loadGenres();
        });

        editTextSearch = rootView.findViewById(R.id.editTextSearch);
        spinnerGenres = rootView.findViewById(R.id.spinnerGenres);

        gamesViewModel = new ViewModelProvider(this).get(GamesViewModel.class);

        gamesViewModel.getGames().observe(getViewLifecycleOwner(), games -> {
            filteredGameList.clear();
            filteredGameList.addAll(games);
            gamesAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
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

        gamesViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty() && !hasErrorBeenShown) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                hasErrorBeenShown = true;
            }
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

        AppCompatImageButton btnVoiceSearch = rootView.findViewById(R.id.btnVoiceSearchGame);
        btnVoiceSearch.setOnClickListener(v -> startVoiceSearch());

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

    @Override
    public void onGameClick(Game game) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("game", game);

        navController.navigate(R.id.navigation_game_details, bundle);
    }

    private void startVoiceSearch() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.diga_algo));

        try {
            startActivityForResult(intent, REQUEST_VOICE_SEARCH);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Tu dispositivo no soporta la búsqueda por voz", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VOICE_SEARCH && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null && !result.isEmpty()) {
                    String voiceSearchQuery = result.get(0);
                    editTextSearch.setText(voiceSearchQuery);
                    filterGames();
                }
            }
        }
    }
}
