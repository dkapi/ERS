package com.spring.ERSBackend.controller;

import com.spring.ERSBackend.Entities.Game;
import com.spring.ERSBackend.Repository.GameRepository;
import com.spring.ERSBackend.Repository.PlayerRepository;
import com.spring.ERSBackend.Services.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.ERSBackend.Entities.Player;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    GameRepository gameRepository;


	/**
	 * @param player - param passed in by front end to create player with specific variables like username
	 * @return - returns response entity of type player that is the created player in database
	 * @throws IOException - thrown incase some error occurs when incorrectly inputting
	 */
	@PostMapping()
	public ResponseEntity<Player> create(@RequestBody Player player) throws IOException {
		List<Integer> playerHand = new LinkedList<>();
		player.setPlayerHand(playerHand);
		Player savedPlayer = playerRepository.save(player);
		return ResponseEntity.ok(savedPlayer);
	}

	/**
	 * @param game_id - given game id to lookup
	 * @param player  - adding existing player to game, this param is that player passed in by frontend
	 * @return returns response entity of type player that was the player added to game
	 */
	@PostMapping("/{game_id}")
	public ResponseEntity<Player> addToGame(@PathVariable("game_id") int game_id, @RequestBody Player player) {
		Optional<Game> game = gameRepository.findById(game_id);
		if (game.isEmpty()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		player.setGame(game.get());
		Player savedPlayer = playerRepository.save(player);
		return ResponseEntity.ok(savedPlayer);
	}

	/**
	 * @param game_id   - id passed in to lookup
	 * @param player_id - id passed in to lookup
	 * @param player    - param of player to add passed in
	 * @return response entity of type player that is the existing player added to that game
	 */
	@PutMapping("/{player_id}/{game_id}")
	public ResponseEntity<Player> placePlayerInGame(@PathVariable("game_id") int game_id,
													@PathVariable("player_id") int player_id,
													@RequestBody Player player) {
		Optional<Game> updateGame = gameRepository.findById(game_id);
		Optional<Player> updatePlayer = playerRepository.findById(player_id);
		if (updateGame.isEmpty()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		if (updatePlayer.isEmpty()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		Set<Player> updateSet = updateGame.get().getPlayers();
		updateSet.add(updatePlayer.get());
		updateGame.get().setPlayers(updateSet);
		updatePlayer.get().setGame((updateGame.get()));
		updatePlayer.get().setInGame(true);
		Game savedGame = gameRepository.save(updateGame.get());
		ResponseEntity.ok(savedGame);
		Player savedPlayer = playerRepository.save(updatePlayer.get());
		//savedPlayer.setInGame(true);
		return ResponseEntity.ok(savedPlayer);
	}

	/**
	 * @param gameId id passed in to lookup
	 * @return response entity of type game of game that was deleted, but it is actually empty because deleted.
	 */
	@DeleteMapping("/deleteGame/{game_id}")
	public ResponseEntity<Game> deleteGameAndRemovePlayerFromGame(@PathVariable("game_id") int gameId) {
		Optional<Game> gameToDelete = gameRepository.findById(gameId);
		if (gameToDelete.isEmpty()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		Set<Player> playersInGame = gameToDelete.get().getPlayers();
		for (Player player : playersInGame) {
			player.setGame(null);
			player.setInGame(false);
			player.setPlayerHand(null);
		}
		gameRepository.deleteById(gameId);
		return ResponseEntity.ok().build();
	}


	/**
	 * @param playerId       - id to lookup
	 * @param playerToUpdate - player passed in to update
	 * @return response entity of type player that is the updated player
	 */
	@PutMapping("/{player_id}")
	public ResponseEntity<Player> updatePlayer(@PathVariable("player_id") int playerId, @RequestBody Player playerToUpdate) {
		Optional<Player> updatePlayer = playerRepository.findById(playerId);
		if (updatePlayer.isEmpty()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		Player savePlayer = updatePlayer.get();
		savePlayer.setId(playerId);
		savePlayer = playerRepository.save(playerToUpdate);
		return ResponseEntity.ok(savePlayer);
	}


	@PutMapping("/{player_id1}/add/{player_id2}")
	public ResponseEntity<Player> addFriend(@PathVariable("player_id1") int playerId1,
											@PathVariable("player_id2") int playerId2){
		Optional<Player> player = playerRepository.findById(playerId1);
		Optional<Player> playerToAdd = playerRepository.findById(playerId2);

		if(player.isEmpty()){
			return ResponseEntity.unprocessableEntity().build();
		}
		if(playerToAdd.isEmpty()){
			return  ResponseEntity.unprocessableEntity().build();
		}
		ArrayList<Player> friendList = new ArrayList<>(player.get().getFriends());
		friendList.add(playerToAdd.get());
		player.get().setFriends(friendList);
		Player savedPlayer = playerRepository.save(player.get());
		return  ResponseEntity.ok(savedPlayer);
	}

	@GetMapping("/{player_id1}/find/{player_id2}")
	public ResponseEntity<Player> FindFriend(@PathVariable("player_id1") int playerId1,
											@PathVariable("player_id2") int playerId2){
		Optional<Player> player1 = playerRepository.findById(playerId1);
		Optional<Player> playerToFind = playerRepository.findById(playerId2);

		if(player1.isEmpty()){
			return ResponseEntity.unprocessableEntity().build();
		}
		if(playerToFind.isEmpty()){
			return  ResponseEntity.unprocessableEntity().build();
		}
		ArrayList<Player> friendList = new ArrayList<>(player1.get().getFriends());

		for (Player player : friendList) {
			if (player.getId() == playerId2) {
				return ResponseEntity.ok(player);
			}
		}
		return  ResponseEntity.unprocessableEntity().build();
	}




	@DeleteMapping("/{player_id1}/remove/{player_id2}")
	public ResponseEntity<Player> RemoveAFriend(@PathVariable("player_id1") int playerId1,
												@PathVariable("player_id2") int playerId2){
		Optional<Player> player1 = playerRepository.findById(playerId1);
		Optional<Player> playerToRemove = playerRepository.findById(playerId2);
		if(player1.isEmpty() || playerToRemove.isEmpty()){
			return ResponseEntity.unprocessableEntity().build();
		}
		ArrayList<Player> friendsList = new ArrayList<>(player1.get().getFriends());
		Iterator<Player> itr = friendsList.iterator();

		while(itr.hasNext()){
			Player curr = (Player) itr.next();
			if(curr.getId() == playerToRemove.get().getId()){
				itr.remove();
				break;
			}
			itr.next();
		}
		player1.get().setFriends(friendsList);
		Player savedPlayer = playerRepository.save(player1.get());
		return ResponseEntity.ok(savedPlayer);
	}


	/**
         *
         * @param playerId - id to lookup
         * @return response entity of type player that is the player found by lookup id
         */
	@GetMapping("/{player_id}")
	public ResponseEntity<Player> getPlayerById(@PathVariable("player_id") int playerId) {
		Player player = playerRepository.findById(playerId).get();
		return ResponseEntity.ok(player);
	}

    /**
     * @return returns arraylist of all players
     */
    @GetMapping
    public ResponseEntity<ArrayList<Player>> getAll() {
        ArrayList<Player> players = new ArrayList<>();
        playerRepository.findAll().forEach(players::add);
        return ResponseEntity.ok(players);
    }

    /**
     * @param playerId - id to lookup
     * @throws IOException - thrown in case id does not exist
     *                     deletes one player by their id
     */
    @DeleteMapping("/{id}")
    public void deletePlayerById(@PathVariable("id") int playerId) throws IOException {
        playerRepository.deleteById(playerId);
    }

    /**
     * @return arraylist of players that are top 10 in leaderboard based on their gameswon variable
     */
    @GetMapping("/topPlayers")
    public ResponseEntity<ArrayList<Player>> getTopTen() {
        ArrayList<Player> players = new ArrayList<>();
        playerRepository.findAll().forEach(players::add);
        ArrayList<Player> TopTen = Sort.SortTopTen(players);
        return ResponseEntity.ok(TopTen);
    }

	@DeleteMapping("/deleteAll")
	public ResponseEntity<Game> deleteAll(){
		playerRepository.deleteAll();
		return ResponseEntity.ok().build();
	}
}