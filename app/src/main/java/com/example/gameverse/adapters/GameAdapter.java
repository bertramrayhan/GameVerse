package com.example.gameverse.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gameverse.DetailActivity;
import com.example.gameverse.R;
import com.example.gameverse.models.Game;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> gameList;

    public GameAdapter(List<Game> gameList){
        this.gameList = gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList.clear();
        this.gameList.addAll(gameList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_card, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game currentGame = gameList.get(position);
        holder.judulGame.setText(currentGame.getName());
        holder.rating.setText("⭐ " + currentGame.getRating());
        Glide.with(holder.itemView.getContext())
                .load(currentGame.getBackgroundImage())
                .into(holder.posterGame);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int gameId = currentGame.getId();

                Context context = v.getContext();

                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("GAME_ID", gameId);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView posterGame;
        TextView judulGame, rating;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            posterGame = itemView.findViewById(R.id.posterGame);
            judulGame = itemView.findViewById(R.id.judulGame);
            rating = itemView.findViewById(R.id.rating);
        }

    }
}
