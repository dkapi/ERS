package com.ersfrontend.services;

import com.ersfrontend.models.Player;

import java.util.ArrayList;

public class PlayerService {
    private static Player playerStore;
    private static ArrayList<Player> playersStore;
    private static ArrayList<Player> topTenPlayersStore;

    /**
     * Returns a stored player
     * @return playerStore
     */
    public static Player getPlayerStored() {
        return playerStore;
    }

    /**
     * Sets storedPlayer to a given player
     * @param player
     */
    public static void setPlayerStored(Player player) {
        playerStore = player;
    }

    /**
     * Returns a list of all players
     * @return playersStore
     */
    public static ArrayList<Player> getPlayersStored() {
        return playersStore;
    }

    /**
     * Stores all players into playersStore
     * @param players
     */
    public static void setPlayersStored(ArrayList<Player> players) {
        playersStore = new ArrayList<>();
        playersStore.addAll(players);
    }

    public static ArrayList<Player> getTopTenPlayersStore() {
        return topTenPlayersStore;
    }

    public static void setTopTenPlayersStore(ArrayList<Player> players) {
        topTenPlayersStore = new ArrayList<>();
        topTenPlayersStore.addAll(players);
    }
}
