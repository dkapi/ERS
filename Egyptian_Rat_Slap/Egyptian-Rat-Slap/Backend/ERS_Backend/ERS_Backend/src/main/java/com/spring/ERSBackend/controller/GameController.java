package com.spring.ERSBackend.controller;

import com.spring.ERSBackend.Entities.Game;
import com.spring.ERSBackend.Entities.Player;
import com.spring.ERSBackend.Repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/games")
public class GameController {


    @Autowired
    GameRepository gameRepository;

    /**
     * @param game - param given by frontend to create game with specific variables initilized
     * @return Response entity
     * @throws IOException posts game to database with a shuffled deck and empty centerpile and empty player list
     */
    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) throws IOException {
        List<Integer> cards = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            cards.add(i);
        }
        Collections.shuffle(cards);
        game.setCards(cards);
        Game savedGame = gameRepository.save(game);
        return ResponseEntity.ok(savedGame);
    }

    /**
     * @return Response Entity of arraylist<game> with all games
     */
    @GetMapping
    public ResponseEntity<ArrayList<Game>> GetAllGames() {
        ArrayList<Game> allGames = new ArrayList<>();
        gameRepository.findAll().forEach(allGames::add);
        return ResponseEntity.ok(allGames);
    }

    /**
     * @param gameId - of game to be found by id
     * @return Response entity of a singular game found by its ID
     */
    @GetMapping("/{game_id}")
    public ResponseEntity<Game> getGameById(@PathVariable("game_id") int gameId) {

        Optional<Game> updateGame = gameRepository.findById(gameId);
        if (updateGame.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Game game = gameRepository.findById(gameId).get();
        return ResponseEntity.ok(game);

    }

    /**
     * @param gameId - of game to be found by id
     * @return Response entity of set<player> that holds all players in the chosen game found by its ID
     */
    @GetMapping("/{game_id}/players")
    public ResponseEntity<Set<Player>> getAllPlayers(@PathVariable("game_id") int gameId) {
        Optional<Game> updateGame = gameRepository.findById(gameId);
        if (updateGame.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Game game = gameRepository.findById(gameId).get();
        return ResponseEntity.ok(game.getPlayers());
    }


        /*PUT: This updates the game once a crucial part of the game state is changed
        such as a slap or someone gets the center deck
        This is the complicated one as there are a few things that need to be updated.
        Each player's hand (or queue) needs to be updated.
        The center pile of the deck needs to be updated.
        I think this is all that happens on backEnd. I do believe that frontEnd will immediately
        check to see if any players hand is equal to the whole deck (ie. They won)
        and they should check for any players hands are equal to zero (ie. They are out.)
         */

    /**
     * @param gameId       - to be looked up
     * @param gameToUpdate - is request parameter that front end is requesting to update
     * @return response entity of type game that is the updated game in database
     */
    @PutMapping("/{game_id}")
    public ResponseEntity<Game> updateGame(@PathVariable("game_id") int gameId, @RequestBody Game gameToUpdate) {
        Optional<Game> updateGame = gameRepository.findById(gameId);
        if (updateGame.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Game saveGame = gameRepository.save(gameToUpdate);
        saveGame.setId(gameId);
        return ResponseEntity.ok(saveGame);
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
     * @param game_id - game to be found by its id
     * @throws IOException - needed incase  nothing to delete
     */
    @DeleteMapping("/{game_id}")
    public void deleteGame(@PathVariable("game_id") int game_id) throws IOException {
        gameRepository.deleteById(game_id);
    }

    /**
     * deletes all existing games
     *
     * @throws IOException - needed incase nothing to delete
     */

    @DeleteMapping("/everythingGoes")
    public void deleteAllGames() throws IOException {
        gameRepository.deleteAll();
    }


}
