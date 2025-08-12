package com.example.gameverse.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gameverse.Rahasia;
import com.example.gameverse.models.GameDetail;
import com.example.gameverse.network.ApiClient;
import com.example.gameverse.network.RawgApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<GameDetail> gameDetailLiveData = new MutableLiveData<>();

    public LiveData<GameDetail> getGameDetailLiveData() {
        return gameDetailLiveData;
    }

    public void fetchDetailGameData(int gameId){
        RawgApiService apiService = ApiClient.getClient().create(RawgApiService.class);
        Call<GameDetail> call = apiService.getDetailGames(gameId, Rahasia.API_KEY);

        call.enqueue(new Callback<GameDetail>() {

            @Override
            public void onResponse(Call<GameDetail> call, Response<GameDetail> response) {
                if(response.isSuccessful() && response.body() != null) {
                    GameDetail gameDetail = response.body();
                    gameDetailLiveData.postValue(gameDetail);
                }
            }

            @Override
            public void onFailure(Call<GameDetail> call, Throwable t) {

            }
        });
    }
}
