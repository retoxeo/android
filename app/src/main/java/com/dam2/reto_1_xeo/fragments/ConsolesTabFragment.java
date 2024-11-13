package com.dam2.reto_1_xeo.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.adapters.ConsolesAdapter;
import com.dam2.reto_1_xeo.models.Console;
import com.dam2.reto_1_xeo.viewmodels.ConsolesViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class ConsolesTabFragment extends Fragment implements ConsolesAdapter.OnConsoleClickListener {

    private EditText editTextSearch;
    private ConsolesAdapter consolesAdapter;
    private ConsolesViewModel consolesViewModel;
    private final List<Console> filteredConsoleList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean hasErrorBeenShown = false;
    private static final int REQUEST_VOICE_SEARCH = 100;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consoles_tab, container, false);

        RecyclerView recyclerViewConsoles = rootView.findViewById(R.id.recyclerViewConsoles);
        recyclerViewConsoles.setLayoutManager(new GridLayoutManager(getContext(), 2));

        consolesAdapter = new ConsolesAdapter(filteredConsoleList, this);
        recyclerViewConsoles.setAdapter(consolesAdapter);

        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            hasErrorBeenShown = false;
            consolesViewModel.loadConsoles();
        });

        editTextSearch = rootView.findViewById(R.id.editTextSearch);

        consolesViewModel = new ViewModelProvider(this).get(ConsolesViewModel.class);

        consolesViewModel.getConsoles().observe(getViewLifecycleOwner(), consoles -> {
            filteredConsoleList.clear();
            filteredConsoleList.addAll(consoles);
            consolesAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });

        consolesViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
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
                filterConsoles();
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });

        consolesViewModel.loadConsoles();

        AppCompatImageButton btnVoiceSearch = rootView.findViewById(R.id.btnVoiceSearchConsole);
        btnVoiceSearch.setOnClickListener(v -> startVoiceSearch());

        return rootView;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void filterConsoles() {
        String searchQuery = editTextSearch.getText().toString().toLowerCase();

        List<Console> consoles = consolesViewModel.getConsoles().getValue();
        if (consoles != null) {
            filteredConsoleList.clear();
            for (Console console : consoles) {
                if (console.getNombre().toLowerCase().contains(searchQuery)) {
                    filteredConsoleList.add(console);
                }
            }
            consolesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onConsoleClick(Console console) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("console", console);

        navController.navigate(R.id.navigation_console_details, bundle);
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
                    filterConsoles();
                }
            }
        }
    }
}
