package com.example.gameverse;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameverse.adapters.GameAdapter;
import com.example.gameverse.network.ApiClient;
import com.example.gameverse.network.RawgApiService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView gamesRecyclerView;
    private GameAdapter gameAdapter;
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

        gamesRecyclerView = findViewById(R.id.gamesRecyclerView);
        gamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        gameAdapter = new GameAdapter(new ArrayList<>());
        gamesRecyclerView.setAdapter(gameAdapter);
    }

    private void fetchGamesData(){
        RawgApiService apiService = ApiClient.getClient().create(RawgApiService.class);
    }
}