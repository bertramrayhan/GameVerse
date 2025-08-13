package com.example.gameverse.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.gameverse.R;
import com.example.gameverse.adapters.GameAdapter;
import com.example.gameverse.models.Game;
import com.example.gameverse.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpcomingGamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingGamesFragment extends Fragment {

    private MainViewModel mainViewModel;
    private RecyclerView gamesRecyclerView;
    private GameAdapter gameAdapter;
    private ProgressBar loadingBar;

    public UpcomingGamesFragment() {
        // Required empty public constructor
    }

    public static UpcomingGamesFragment newInstance(String param1, String param2) {
        UpcomingGamesFragment fragment = new UpcomingGamesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_games, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingBar = view.findViewById(R.id.loadingBar);

        gamesRecyclerView = view.findViewById(R.id.gamesRecyclerView);
        gamesRecyclerView.setLayoutManager(new LinearLayoutManager(this.requireContext()));

        gameAdapter = new GameAdapter(new ArrayList<>());
        gamesRecyclerView.setAdapter(gameAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.fetchUpcomingGamesData();

        mainViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading){
                    gamesRecyclerView.setVisibility(View.GONE);
                    loadingBar.setVisibility(View.VISIBLE);
                }else {
                    loadingBar.setVisibility(View.GONE);
                    gamesRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        mainViewModel.getGameListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Game>>() {

            @Override
            public void onChanged(List<Game> gameList) {
                gameAdapter.setGameList(gameList);
            }
        });
    }
}