package com.spring.ERSBackend.Entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "game", fetch = FetchType.EAGER)
    private Set<Player> players = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> centerPile;

    @ElementCollection(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<Integer> deck;



    //----------------------Getters + Setters--------------------------//

    /**
     *
     * @return id of game
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id sets id of game
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return set of players in game, null if empty
     */
    public Set<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @param players sets players into a game as a hashset
     */
    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    /**
     *
     * @return name of game
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name sets name of game
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return centerpile of game which is list of 52 ints
     */
    public List<Integer> getCenterPile() {
        return centerPile;
    }

    /**
     *
     * @param centerPile sets centerpile to a list of 52, null if empty
     */
    public void setCenterPile(List<Integer> centerPile) {
        this.centerPile = centerPile;
    }

    /**
     *
     * @return returns deck which is the entire deck in that specific game
     */
    public List<Integer> getDeck() {
        return deck;
    }

    /**
     *
     * @param deck sets the deck, null if empty
     */
    public void setCards(List<Integer> deck) {
        this.deck = deck;
    }
}
