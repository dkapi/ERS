package com.ersfrontend.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("deck")
    private List<Integer> deck;
    @SerializedName("centerPile")
    private List<Integer> centerPile;
    @SerializedName("players")
    private Set<Player> players = new HashSet<>();
    @SerializedName("privateState")
    private boolean privateState;
    public Game() {
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
     * Returns name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns deck
     * @return deck
     */
    public List<Integer> getDeck() {
        return deck;
    }

    /**
     * Sets deck
     * @param deck
     */
    public void setDeck(List<Integer> deck) {
        this.deck = deck;
    }

    /**
     * Returns centerPile
     * @return centerPile
     */
    public List<Integer> getCenterPile() {
        return centerPile;
    }

    /**
     * Sets centerPile
     * @param centerPile
     */
    public void setCenterPile(List<Integer> centerPile) {
        this.centerPile = centerPile;
    }

    /**
     * Returns players
     * @return players
     */
    public Set<Player> getPlayers() {
        return players;
    }

    /**
     * Sets players
     * @param players
     */
    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    /**
     * Returns the boolean value of the games private state, true = private, false = public
     * @return privateState
     */
    public boolean isPrivateState() {
        return privateState;
    }

    /**
     * Sets privateState
     * @param privateState
     */
    public void setPrivateState(boolean privateState) {
        this.privateState = privateState;
    }

    /**
     * Returns a game as a printable game
     * @return "id: " + getId() + "\nname: " + getName() + "\n"
     */
    public String printGame() {
        return "id: " + getId() + "\nname: " + getName() + "\n";
    }

}
