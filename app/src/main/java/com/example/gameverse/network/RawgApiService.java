package com.example.gameverse.network;

import com.example.gameverse.models.GameDetail;
import com.example.gameverse.models.GameResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RawgApiService {
    @GET("games")
    Call<GameResponse> getPopularGames(@Query("key") String apiKey);

    @GET("games/{id}")
    Call<GameDetail> getDetailGames(@Path("id") int gameId, @Query("key") String apiKey);

    @GET("games")
    Call<GameResponse> getUpcomingGames(
            @Query("key") String apiKey,
            @Query("dates") String dateRange,
            @Query("ordering") String ordering
    );
}

