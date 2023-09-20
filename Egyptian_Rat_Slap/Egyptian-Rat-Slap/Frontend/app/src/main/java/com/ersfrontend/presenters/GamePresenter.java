package com.ersfrontend.presenters;

import static com.ersfrontend.services.GameService.getGameStored;
import static com.ersfrontend.services.GameService.getGamesStored;
import static com.ersfrontend.threading.CallStrings.GETALLGAMES;
import static com.ersfrontend.threading.CallStrings.GETGAMEBYID;
import static com.ersfrontend.threading.CallStrings.POSTGAME;
import static com.ersfrontend.util.RetrofitBuilder.createGameApi;

import com.ersfrontend.models.Game;
import com.ersfrontend.threading.SlapThread;
import com.ersfrontend.threading.SlapThreadCallBack;
import com.ersfrontend.util.QuickCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Response;

public class GamePresenter {


    /**
     * Gets a game from database, used with multi-threading to get a game synchronously by a given id.
     * Used in other methods which accomplish the same goal
     * @param slapThreadCallBack
     * @param countDownLatch
     * @param game_id
     */
    public static void getGameById(SlapThreadCallBack slapThreadCallBack, CountDownLatch countDownLatch, int game_id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Game> getGameCall = createGameApi().getGame(game_id);
                try {
                    Response<Game> response = getGameCall.execute();
                    if (response.isSuccessful()) {
                        Game result = response.body();
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
     * Gets all games in the database, used with multi-threading to get all games synchronously.
     * Used in other methods which accomplish the same goal
     * @param slapThreadCallBack
     * @param countDownLatch
     */
    public static void getGames(SlapThreadCallBack slapThreadCallBack, CountDownLatch countDownLatch) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<ArrayList<Game>> getGamesCall = createGameApi().getAllGames();
                try {
                    Response<ArrayList<Game>> response = getGamesCall.execute();
                    if (response.isSuccessful()) {
                        ArrayList<Game> result = new ArrayList<>();
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
     * Posts a game synchronously using multi-threading so that the backend can return the game for further use.
     * Used in other methods which accomplish the same goal
     * @param slapThreadCallBack
     * @param countDownLatch
     * @param game
     */
    public static void postGame(SlapThreadCallBack slapThreadCallBack, CountDownLatch countDownLatch, Game game) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Game> postGameCall = createGameApi().createGame(game);
                try {
                    Response<Game> response = postGameCall.execute();
                    if (response.isSuccessful()) {
                        Game result = response.body();
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
     * Updates a game asynchronously
     * @param gameToUpdate
     * @param game_id
     */
    public static void updateGame(Game gameToUpdate, int game_id) {
        createGameApi().updateGame(game_id, gameToUpdate).enqueue(new QuickCallBack<Game>(game ->{
            // game updated
        }));
    }

    /**
     * Deletes a game asynchronously
     * @param game_id
     */
    public static void deleteGame(int game_id) {
        createGameApi().deleteGame(game_id).enqueue(new QuickCallBack<Game>(game ->{
            // game deleted
        }));
    }

    /**
     * Gets a game by id synchronously
     * @param game_id
     * @return getGameStored()
     */
    public static Game getGameByIDSync(int game_id) {
        CountDownLatch countDownLatchGame = new CountDownLatch(2);
        SlapThread slapThreadGame = new SlapThread(countDownLatchGame, GETGAMEBYID, "SlapThread", game_id);
        slapThreadGame.start();
        try {
            countDownLatchGame.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return getGameStored();
    }

    /**
     * Gets all games synchronously
     * @return getGamesStored()
     */
    public static ArrayList<Game> getGamesSync() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        SlapThread slapThread = new SlapThread(countDownLatch, GETALLGAMES, "SlapThread");
        slapThread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return getGamesStored();
    }

    /**
     * Posts a game synchronously so that the response of the newly posted game from backend can be used
     * @param game
     * @return getGameStored()
     */
    public static Game postGameSync(Game game) {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        SlapThread slapThread = new SlapThread(countDownLatch, POSTGAME, "SlapThread", game);
        slapThread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return getGameStored();
    }
}