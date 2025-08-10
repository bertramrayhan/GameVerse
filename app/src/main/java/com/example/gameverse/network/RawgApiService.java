package com.example.gameverse.network;

import com.example.gameverse.models.GameResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RawgApiService {
    @GET("games")
    Call<GameResponse> getPopularGames(@Query("key") String apiKey);
}

