package com.example.gameverse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.gameverse.models.GameDetail;
import com.example.gameverse.viewModels.DetailViewModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private DetailViewModel detailViewModel;
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

        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        detailViewModel.fetchDetailGameData(gameId);
        detailViewModel.getGameDetailLiveData().observe(this, new Observer<GameDetail>(){

            @Override
            public void onChanged(GameDetail gameDetail) {
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
        });

        backBtn.setOnClickListener(v -> finish());
    }
}