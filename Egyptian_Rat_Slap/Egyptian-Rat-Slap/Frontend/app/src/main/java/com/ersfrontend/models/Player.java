package com.ersfrontend.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Player {

    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("playerHand")
    private List<Integer> playerHand;
    @SerializedName("gamesPlayed")
    private int gamesPlayed;
    @SerializedName("gamesWon")
    private int gamesWon;
    @SerializedName("inGame")
    private boolean inGame;
    @SerializedName("isHost")
    private boolean isHost;
    @SerializedName("game")
    private Game game;
    @SerializedName("friends")
    private List<Player> friends;

    public Player() {

    }

    /**
     * Returns id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns gamesPlayed
     * @return gamesPlayed
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Sets gamesPlayed
     * @param gamesPlayed
     */
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Returns gamesWon
     * @return gamesWon
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * sets gamesWon
     * @param gamesWon
     */
    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    /**
     * Returns inGame status
     * @return inGame
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Sets inGame status
     * @param inGame
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * Returns game
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets game
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Returns playerHand
     * @return playerHand
     */
    public List<Integer> getPlayerHand() {
        return playerHand;
    }

    /**
     * Sets playerHand
     * @param playerDeck
     */
    public void setPlayerHand(List<Integer> playerDeck) {
        playerHand = new ArrayList<>();
        playerHand.addAll(playerDeck);
    }

    /**
     * Returns isHost status
     * @return isHost
     */
    public boolean isHost() {
        return isHost;
    }

    /**
     * Sets isHost status
     * @param host
     */
    public void setHost(boolean host) {
        isHost = host;
    }

    /**
     * Returns friends list
     * @return friends
     */
    public List<Player> getFriends() {
        return friends;
    }

    /**
     * Sets friends list
     * @param friends
     */
    public void setFriends(List<Player> friends) {
        this.friends = friends;
    }
}
