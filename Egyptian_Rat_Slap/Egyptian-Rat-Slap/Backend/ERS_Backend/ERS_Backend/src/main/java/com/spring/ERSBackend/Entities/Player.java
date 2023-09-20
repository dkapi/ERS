package com.spring.ERSBackend.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Kyle Kohl
 *@Modified Dino Kapic
 */

@Entity
@Table(name = "players")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;

	@Column(name = "inGame")
	private boolean inGame;

	@Column(name = "isHost")
	private boolean isHost;

	@Column(name = "gamesPlayed")
	private int gamesPlayed;

	@Column(name = "gamesWon")
	private int gamesWon;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Game game;

	@ElementCollection()
	@Nullable
	//@CollectionTable(name = "my_queue")
	private List<Integer> playerHand;



	@ManyToMany
	private List<Player> friends = new ArrayList<>(0);

	public int getId() {
		return id;
	}

	/**
	 *
	 * @param id id passed in to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 *
	 * @return username of player
	 */
	public String getUsername() {
		return username;
	}

	/**
	 *
	 * @param username set username of current player
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 *
	 * @return password of current player
	 */
	public String getPassword() {
		return password;
	}

	/**
	 *
	 * @param password set password of current player
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 *
	 * @return boolean if player is in the game
	 */
	public boolean isInGame() {
		return inGame;
	}

	/**
	 *
	 * @param inGame sets boolean of if player is in game
	 */
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	/**
	 *
	 * @return returns boolean of if player is a host
	 */
	public boolean isHost() {
		return isHost;
	}

	/**
	 *
	 * @param host boolean to set player to host
	 */
	public void setHost(boolean host) {
		isHost = host;
	}

	/**
	 *
	 * @return number of games player
 	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}

	/**
	 *
	 * @param gamesPlayed sets gamesplayed
	 */
	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	/**
	 *
	 * @return number of games won
	 */
	public int getGamesWon() {
		return gamesWon;
	}

	/**
	 *
	 * @param gamesWon sets games won
	 */
	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}

	/**
	 *
	 * @return current game player is in
	 */
	public Game getGame() {
		return game;
	}

	/**
	 *
	 * @param game sets player to a game, set to null if not in a game
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 *
	 * @return players card hand as list
	 */
	public List<Integer> getPlayerHand() {
		return playerHand;
	}

	/**
	 *
	 * @param playerHand sets players hand to a shuffled list, null if empty
	 */
	public void setPlayerHand(List<Integer> playerHand) {
		this.playerHand = playerHand;
	}

	public List<Player> getFriends() {
		return friends;
	}

	public void setFriends(List<Player> friends) {
		this.friends = friends;
	}
}