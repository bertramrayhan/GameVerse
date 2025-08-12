package com.example.gameverse;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameverse.adapters.GameAdapter;
import com.example.gameverse.models.Game;
import com.example.gameverse.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private RecyclerView gamesRecyclerView;
    private GameAdapter gameAdapter;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadingBar = findViewById(R.id.loadingBar);

        gamesRecyclerView = findViewById(R.id.gamesRecyclerView);
        gamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        gameAdapter = new GameAdapter(new ArrayList<>());
        gamesRecyclerView.setAdapter(gameAdapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.fetchGamesData();

        mainViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
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

        mainViewModel.getGameListLiveData().observe(this, new Observer<List<Game>>() {

            @Override
            public void onChanged(List<Game> gameList) {
                gameAdapter.setGameList(gameList);
            }
        });
    }
}