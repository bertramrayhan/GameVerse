package com.example.gameverse.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gameverse.Rahasia;
import com.example.gameverse.models.Game;
import com.example.gameverse.models.GameResponse;
import com.example.gameverse.network.ApiClient;
import com.example.gameverse.network.RawgApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Game>> gameListLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<List<Game>> getGameListLiveData() {
        return gameListLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void fetchGamesData(){
        isLoading.postValue(true);

        RawgApiService apiService = ApiClient.getClient().create(RawgApiService.class);
        Call<GameResponse> call = apiService.getPopularGames(Rahasia.API_KEY);

        call.enqueue(new Callback<GameResponse>() {
            @Override
            public void onResponse(Call<GameResponse> call, Response<GameResponse> response) {
                isLoading.postValue(false);

                if(response.isSuccessful() && response.body() != null){
                    List<Game> gameList = response.body().getResults();
                    gameListLiveData.postValue(gameList);
                }
            }

            @Override
            public void onFailure(Call<GameResponse> call, Throwable t) {
                isLoading.postValue(false);
            }
        });
    }
}
