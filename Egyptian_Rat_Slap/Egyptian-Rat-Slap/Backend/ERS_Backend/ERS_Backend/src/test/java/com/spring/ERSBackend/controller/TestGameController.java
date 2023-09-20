
//package com.spring.ERSBackend.controller;
//Import our classes

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ERSBackend.Entities.Game;
import com.spring.ERSBackend.Repository.GameRepository;
import com.spring.ERSBackend.Repository.PlayerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//
//@RunWith(SpringRunner.class)
//@WebMvcTest(GameController.class)
//public class TestGameController {
//


//    @Autowired
//    private MockMvc controller;
//
//    @MockBean // note that this repo is also needed in controller
//    private GameRepository GameRepo;
//
//    @MockBean
//    private PlayerRepository PlayerRepo;
//
//    /*
//     * There are three steps here:
//     *   1 - set up mock database methods
//     *   2 - set up mock service methods
//     *   3 - call and test the results of the controller
//     */
//
//    //Tested the Post: Create Game method correctly // This should work
//    @Test
//    public void testGameCreateEndpoint() throws Exception {
//
//        List<Game> games = new ArrayList<>();
//        // mock the findAll method
//        when(GameRepo.findAll()).thenReturn(games);
//        Game game = new Game();
//        game.setId(1);
//        // mock the save() method to save argument to the list
//        when(GameRepo.save(any(Game.class)))
//                .thenReturn(game);
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(post("/games")
//                        .content(asJsonString(game))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"));
//    }
//
//    @Test
//    public void GetAllPlayersShouldReturnOk() throws Exception {
//        //Creating a player and Game to mock
//        Game game = new Game();
//        game.setId(1);
//        Player player = new Player();
//        Set<Player> players = new HashSet<>();
//        player.setId(1);
//        game.setPlayers(players);
//        // mock the save() method to save argument to the list
//        when(GameRepo.save(any(Game.class))).thenReturn(game);
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        when(GameRepo.findById(1)).thenReturn(Optional.of(game));
//            controller.perform(get("/games/1/players")
//                            .content(asJsonString(game))
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk());
//        }
//
//    @Test
//    public void GetAllPlayersShouldReturnUnprocessableEntity() throws Exception {
//        //Creating a player and Game to mock
//        Game game = new Game();
//        game.setId(1);
//        Player player = new Player();
//        Set<Player> players = new HashSet<>();
//        player.setId(1);
//        game.setPlayers(players);
//        // mock the save() method to save argument to the list
//        when(GameRepo.save(any(Game.class))).thenReturn(game);
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        when(GameRepo.findById(1)).thenReturn(Optional.of(game));
//        controller.perform(get("/games/2/players")
//                        .content(asJsonString(game))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//
//
//    //Testing Get: should return 200
//    @Test
//    public void GameGetEndpoint() throws Exception {
//        List<Game> games = new ArrayList<>();
//        // mock the findAll method
//
//        when(GameRepo.findAll()).thenReturn(games);
//        Game game = new Game();
//        // mock the save() method to save argument to the list
//        when(GameRepo.save(any(Game.class))).thenReturn(game);
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(get("/games")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"));
//    }
//
//    //Testing Get by Id: Should work
//    @Test
//    public void GetGameByIdEndpointShouldReturnOk() throws Exception {
//        Game game = new Game();
//        game.setId(1);
//        when(GameRepo.findById(1)).thenReturn(Optional.of(game));
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(get("/games/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//
//    @Test
//    public void UpdateGameShouldReturnOk() throws Exception {
//        List<Game> games = new ArrayList<>();
//        // mock the findAll method
//        when(GameRepo.findAll()).thenReturn(games);
//        Game game = new Game();
//        game.setId(2);
//        // mock the save() method to save argument to the list
//        when(GameRepo.save(any(Game.class)))
//                .thenReturn(game);
//        when(GameRepo.findById(1)).thenReturn(Optional.of(game));
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(put("/games/1")
//                        .content(asJsonString(game))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"));
//    }
//
//    @Test
//    public void UpdateGameShouldReturnIsUnprocessableEntity() throws Exception {
//        List<Game> games = new ArrayList<>();
//        // mock the findAll method
//        when(GameRepo.findAll()).thenReturn(games);
//        Game game = new Game();
//        game.setId(1);
//        // mock the save() method to save argument to the list
//        when(GameRepo.save(any(Game.class)))
//                .thenReturn(game);
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(put("/games/2")
//                        .content(asJsonString(game))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    public void GetGameByIdEndpointShouldReturnUnprocessable() throws Exception {
//        Game game = new Game();
//        game.setId(1);
//        // Game savedGame = GameRepo.save(game);
//        when(GameRepo.findById(1)).thenReturn(Optional.of(game));
//
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//
//        controller.perform(get("/games/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//
//    @Test
//    public void DeleteGameAndRemovePlayerFromGameShouldReturnOk() throws Exception {
//        //Creating a player and Game to mock
//        Game game = new Game();
//        game.setId(1);
//        Player player = new Player();
//        Set<Player> players = new HashSet<>();
//        player.setId(1);
//        game.setPlayers(players);
//        // Game savedGame = GameRepo.save(game);
//        when(GameRepo.findById(1)).thenReturn(Optional.of(game));
//
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(delete("/games/deleteGame/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void DeleteGameAndRemovePlayerFromGameShouldReturnUnprocessableEntity() throws Exception {
//        //Creating a player and Game to mock
//        Game game = new Game();
//        game.setId(2);
//        Player player = new Player();
//        Set<Player> players = new HashSet<>();
//        player.setId(1);
//        game.setPlayers(players);
//        // Game savedGame = GameRepo.save(game);
//        when(GameRepo.findById(1)).thenReturn(Optional.of(game));
//
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(delete("/games/deleteGame/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    public void DeleteAGameByIdShouldReturnOk() throws Exception {
//        Game game = new Game();
//        when(GameRepo.findById(1)).thenReturn(Optional.of(game));
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(delete("/games/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    public void DeleteAllGamesShouldReturnOk() throws Exception {
//        Game game = new Game();
//        when(GameRepo.save(any(Game.class))).thenReturn(game);
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(delete("/games/everythingGoes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


//
