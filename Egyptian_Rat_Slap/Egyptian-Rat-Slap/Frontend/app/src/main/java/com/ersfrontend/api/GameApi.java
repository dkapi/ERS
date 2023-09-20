package com.ersfrontend.api;

import static com.ersfrontend.util.AppURLs.BASE_URL_GAME;

import com.ersfrontend.models.Game;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GameApi {
    @GET(BASE_URL_GAME)
    Call<ArrayList<Game>> getAllGames();
    @GET(BASE_URL_GAME + "/{game_id}")
    Call<Game> getGame(@Path("game_id") int game_id);
    @POST(BASE_URL_GAME)
    Call<Game> createGame(@Body Game newGame);
    @PUT(BASE_URL_GAME + "/{game_id}")
    Call<Game> updateGame(@Path("game_id") int game_id, @Body Game gameToUpdate);
    @DELETE(BASE_URL_GAME + "/{game_id}")
    Call<Game> deleteGame(@Path("game_id") int game_id);
}
