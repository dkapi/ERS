package com.ersfrontend.presenters;

import static com.ersfrontend.services.PlayerService.getPlayerStored;
import static com.ersfrontend.services.PlayerService.getPlayersStored;
import static com.ersfrontend.services.PlayerService.getTopTenPlayersStore;
import static com.ersfrontend.threading.CallStrings.GETALLPLAYERS;
import static com.ersfrontend.threading.CallStrings.GETPLAYERBYID;
import static com.ersfrontend.threading.CallStrings.GETTOP10PLAYERS;
import static com.ersfrontend.util.RetrofitBuilder.createPlayerApi;

import com.ersfrontend.models.Player;
import com.ersfrontend.threading.SlapThread;
import com.ersfrontend.threading.SlapThreadCallBack;
import com.ersfrontend.util.QuickCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Response;

public class PlayerPresenter {

    /**
     * Gets a player synchronously using multi-threading
     * Used in other methods which accomplish the same goal
     * @param slapThreadCallBack
     * @param countDownLatch
     * @param player_id
     */
    public static void getPlayerById(SlapThreadCallBack slapThreadCallBack, CountDownLatch countDownLatch, int player_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Player> getPlayerCall = createPlayerApi().getPlayerById(player_id);
                try {
                    Response<Player> response = getPlayerCall.execute();
                    if (response.isSuccessful()) {
                        Player result = response.body();
                        slapThreadCallBack.onSuccess(result);
                    } else {
                        throw new IOException("Unexpected HTTP code " + response.code());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();
    }

    /**
     * Gets all players synchronously through multi-threading
     * Used in other methods which accomplish the same goal
     * @param slapThreadCallBack
     * @param countDownLatch
     */
    public static void getPlayers(SlapThreadCallBack slapThreadCallBack, CountDownLatch countDownLatch) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<ArrayList<Player>> getPlayersCall = createPlayerApi().getAll();
                try {
                    Response<ArrayList<Player>> response = getPlayersCall.execute();
                    if (response.isSuccessful()) {
                        ArrayList<Player> result = new ArrayList<>();
                        result.addAll(response.body());
                        slapThreadCallBack.onSuccessArrayList(result);
                    } else {
                        throw new IOException("Unexpected HTTP code " + response.code());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();
    }

    public static void getTopTenPlayers(SlapThreadCallBack slapThreadCallBack, CountDownLatch countDownLatch) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<ArrayList<Player>> getPlayersCall = createPlayerApi().getTopTen();
                try {
                    Response<ArrayList<Player>> response = getPlayersCall.execute();
                    if (response.isSuccessful()) {
                        ArrayList<Player> result = new ArrayList<>();
                        result.addAll(response.body());
                        slapThreadCallBack.onSuccessArrayList(result);
                    } else {
                        throw new IOException("Unexpected HTTP code " + response.code());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();
    }

    /**
     * Posts a player asynchronously
     * @param newPlayer
     */
    public static void postPlayer(Player newPlayer) {
        createPlayerApi().create(newPlayer).enqueue(new QuickCallBack<Player>(player -> {
            // player posted
        }));
    }

    /**
     * Puts a player into a game asynchronously
     * @param player_id
     * @param game_id
     * @param playerToPut
     */
    public static void putPlayer(int player_id, int game_id, Player playerToPut) {
        createPlayerApi().placePlayerInGame(player_id, game_id, playerToPut).enqueue(new QuickCallBack<Player>(player -> {
            // player put
        }));
    }

    /**
     * Updates a player asynchronously
     * @param player_id
     * @param playerToUpdate
     */
    public static void updatePlayer(int player_id, Player playerToUpdate) {
        createPlayerApi().updatePlayer(player_id, playerToUpdate).enqueue(new QuickCallBack<Player>(player -> {
        }));
    }

    /**
     * Adds a friend with player_id2 to the friends list of player_id1
     * @param player_id1
     * @param player_id2
     */
    public static void addFriend(int player_id1, int player_id2) {
        createPlayerApi().addFriend(player_id1, player_id2).enqueue(new QuickCallBack<Player>(player -> {
        }));
    }

    /**
     * Deletes a player asynchronously
     * @param player_id
     */
    public static void deletePlayer(int player_id) {
        createPlayerApi().deletePlayerById(player_id).enqueue(new QuickCallBack<Player>(player -> {
            // player deleted
        }));
    }

    /**
     * Removes a friend with player_id2 from the friends list of player_id1
     * @param player_id1
     * @param player_id2
     */
    public static void removeFriend(int player_id1, int player_id2) {
        createPlayerApi().removeFriend(player_id1, player_id2).enqueue(new QuickCallBack<Player>(player -> {

        }));
    }

    /**
     * Gets a player synchronously
     * @param player_id
     * @return getPlayerStored()
     */
    public static Player getPlayerByIDSync(int player_id) {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        SlapThread slapThread = new SlapThread(countDownLatch, GETPLAYERBYID, "SlapThread", player_id);
        slapThread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return getPlayerStored();
    }

    /**
     * Gets all players synchronously
     * @return getPlayersStored()
     */
    public static ArrayList<Player> getPlayersSync() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        SlapThread slapThread = new SlapThread(countDownLatch, GETALLPLAYERS, "SlapThread");
        slapThread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return getPlayersStored();
    }

    /**
     * Returns the top ten players in a synchronous way
     * @return getTopTenPlayersStore()
     */
    public static ArrayList<Player> getTopTenPlayersSync() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        SlapThread slapThread = new SlapThread(countDownLatch, GETTOP10PLAYERS, "SlapThread");
        slapThread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return getTopTenPlayersStore();
    }
}
