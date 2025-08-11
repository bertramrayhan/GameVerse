package com.example.gameverse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.gameverse.models.GameDetail;
import com.example.gameverse.network.ApiClient;
import com.example.gameverse.network.RawgApiService;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView judulGame, rating, releaseDate, synopsis;
    private ImageView backdropImage;
    private ShapeableImageView posterImage;
    private ConstraintLayout backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        judulGame = findViewById(R.id.judulGame);
        rating = findViewById(R.id.rating);
        releaseDate = findViewById(R.id.releaseDate);
        synopsis = findViewById(R.id.synopsis);
        backdropImage = findViewById(R.id.backdropImage);
        posterImage = findViewById(R.id.posterImage);
        backBtn = findViewById(R.id.backBtn);

        Intent intent = getIntent();
        int gameId = intent.getIntExtra("GAME_ID", -1);
        fetchDetailGameData(gameId);

        backBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void fetchDetailGameData(int gameId){
        RawgApiService apiService = ApiClient.getClient().create(RawgApiService.class);
        Call<GameDetail> call = apiService.getDetailGames(gameId, Rahasia.API_KEY);

        call.enqueue(new Callback<GameDetail>() {

            @Override
            public void onResponse(Call<GameDetail> call, Response<GameDetail> response) {
                if(response.isSuccessful() && response.body() != null){
                    GameDetail gameDetail = response.body();
                    Glide.with(DetailActivity.this)
                            .load(gameDetail.getBackgroundImage())
                            .into(backdropImage);

                    Glide.with(DetailActivity.this)
                            .load(gameDetail.getBackgroundImage())
                            .into(posterImage);

                    judulGame.setText(gameDetail.getName());
                    rating.setText("⭐ " + gameDetail.getRating());

                    try {
                        String rawReleaseDate = gameDetail.getReleased();
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        Date dateObject = inputFormat.parse(rawReleaseDate);
                        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                        releaseDate.setText(outputFormat.format(dateObject));
                    } catch(Exception e){
                        Log.e("DetailActivity", e.getMessage());
                    }

                    synopsis.setText(gameDetail.getDescriptionRaw());
                }
            }

            @Override
            public void onFailure(Call<GameDetail> call, Throwable t) {

            }
        });
    }
}