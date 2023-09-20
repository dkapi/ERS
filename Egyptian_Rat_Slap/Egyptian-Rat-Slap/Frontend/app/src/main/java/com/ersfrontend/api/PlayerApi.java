package com.ersfrontend.api;

import static com.ersfrontend.util.AppURLs.BASE_URL_PLAYER;

import com.ersfrontend.models.Player;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlayerApi {
    @GET(BASE_URL_PLAYER)
    Call<ArrayList<Player>> getAll();
    @GET(BASE_URL_PLAYER + "/{player_id}")
    Call<Player> getPlayerById(@Path("player_id") int player_id);
    @GET(BASE_URL_PLAYER + "/topPlayers")
    Call<ArrayList<Player>> getTopTen();
    @POST(BASE_URL_PLAYER)
    Call<Player> create(@Body Player newPlayer);
    @PUT(BASE_URL_PLAYER + "/{player_id}/{game_id}")
    Call<Player> placePlayerInGame(@Path("player_id") int player_id,
                                   @Path("game_id") int game_id,
                                   @Body Player playerToPut);
    @PUT(BASE_URL_PLAYER + "/{player_id}")
    Call<Player> updatePlayer(@Path("player_id") int player_id, @Body Player playerToUpdate);
    @PUT(BASE_URL_PLAYER + "/{player_id1}/add/{player_id2}")
    Call<Player> addFriend(@Path("player_id1") int player_id1, @Path("player_id2") int player_id2);
    @DELETE(BASE_URL_PLAYER + "/{player_id}")
    Call<Player> deletePlayerById(@Path("player_id") int player_id);
    @DELETE(BASE_URL_PLAYER + "/{player_id1}/remove/{player_id2}")
    Call<Player> removeFriend(@Path("player_id1") int player_id1, @Path("player_id2") int player_id2);
}
