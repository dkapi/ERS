package com.ersfrontend.threading;

import static com.ersfrontend.presenters.GamePresenter.getGameById;
import static com.ersfrontend.presenters.GamePresenter.getGames;
import static com.ersfrontend.presenters.GamePresenter.postGame;
import static com.ersfrontend.presenters.PlayerPresenter.getPlayerById;
import static com.ersfrontend.presenters.PlayerPresenter.getPlayers;
import static com.ersfrontend.presenters.PlayerPresenter.getTopTenPlayers;
import static com.ersfrontend.services.GameService.setGameStored;
import static com.ersfrontend.services.GameService.setGamesStored;
import static com.ersfrontend.services.PlayerService.setPlayerStored;
import static com.ersfrontend.services.PlayerService.setPlayersStored;
import static com.ersfrontend.services.PlayerService.setTopTenPlayersStore;

import com.ersfrontend.models.Game;
import com.ersfrontend.models.Player;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class SlapThread extends Thread {

    private CountDownLatch countDownLatch;
    private CallStrings callString;
    private int Id;
    private Game game;

    /**
     * Constuctor for a basic SlapThread
     * @param countDownLatch
     * @param callString
     * @param threadType
     */
    public SlapThread(CountDownLatch countDownLatch, CallStrings callString, String threadType) {
        super(threadType);
        this.countDownLatch = countDownLatch;
        this.callString = callString;
    }

    /**
     * Constructor for a SlapThread requiring an Id value for a specific call
     * @param countDownLatch
     * @param callString
     * @param threadType
     * @param Id
     */
    public SlapThread(CountDownLatch countDownLatch, CallStrings callString, String threadType, int Id) {
        super(threadType);
        this.countDownLatch = countDownLatch;
        this.callString = callString;
        this.Id = Id;
    }

    /**
     * Constructor for specifically posting a game
     * @param countDownLatch
     * @param callString
     * @param threadType
     * @param game
     */
    public SlapThread(CountDownLatch countDownLatch, CallStrings callString, String threadType, Game game) {
        super(threadType);
        this.countDownLatch = countDownLatch;
        this.callString = callString;
        this.game = game;
    }

    /**
     * Runs the created SlapThread's commands based on the given callString
     */
    @Override
    public void run() {
        switch (callString) {
            case GETALLPLAYERS:
                getPlayers(new SlapThreadCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                    }
                    @Override
                    public void onSuccessArrayList(ArrayList result) {
                        setPlayersStored(result);
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                }, countDownLatch);
                countDownLatch.countDown();
                break;
            case GETPLAYERBYID:
                getPlayerById(new SlapThreadCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        setPlayerStored((Player) result);
                    }
                    @Override
                    public void onSuccessArrayList(ArrayList result) {
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                }, countDownLatch, Id);
                countDownLatch.countDown();
                break;
            case GETALLGAMES:
                getGames(new SlapThreadCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                    }
                    @Override
                    public void onSuccessArrayList(ArrayList result) {
                        setGamesStored(result);
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                }, countDownLatch);
                countDownLatch.countDown();
                break;
            case GETGAMEBYID:
                getGameById(new SlapThreadCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        setGameStored((Game) result);
                    }
                    @Override
                    public void onSuccessArrayList(ArrayList result) {
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                }, countDownLatch, Id);
                countDownLatch.countDown();
                break;
            case POSTGAME:
                postGame(new SlapThreadCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        setGameStored((Game) result);
                    }
                    @Override
                    public void onSuccessArrayList(ArrayList result) {

                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                    }
                }, countDownLatch, game);
                countDownLatch.countDown();
                break;
            case GETTOP10PLAYERS:
                getTopTenPlayers(new SlapThreadCallBack() {
                    @Override
                    public void onSuccess(Object result) {

                    }
                    @Override
                    public void onSuccessArrayList(ArrayList result) {
                        setTopTenPlayersStore(result);
                    }
                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                }, countDownLatch);
                countDownLatch.countDown();
                break;
        }
    }
}
