//package com.spring.ERSBackend.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.spring.ERSBackend.Entities.Game;
//import com.spring.ERSBackend.Entities.Player;
//import com.spring.ERSBackend.Repository.GameRepository;
//import com.spring.ERSBackend.Repository.PlayerRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(PlayerController.class)
//public class TestPlayerController {
//
//
//    @Autowired
//    private MockMvc controller;
//
//    @MockBean
//    private GameRepository gameRepo;
//
//    @MockBean
//    private PlayerRepository playerRepo;
//
//
//    //testing post
//    @Test
//    public void testPlayerCreateEndpoint() throws Exception {
//        List<Player> players = new ArrayList<>();
//        when(playerRepo.findAll()).thenReturn(players);
//        Player player = new Player();
//        player.setId(1);
//        when(playerRepo.save(any(Player.class)))
//        .thenReturn(player);
//
//        controller.perform(post("/players")
//                        .content(asJsonString(player))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"));
//    }
//
//    //test player get returns 200 Ok status
//    @Test
//    public void PlayerGetEndpoint() throws Exception {
//        List<Player> players = new ArrayList<>();
//
//        when(playerRepo.findAll()).thenReturn(players);
//        Player player = new Player();
//        // mock the save() method to save argument to the list
//        when(playerRepo.save(any(Player.class))).thenReturn(player);
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(get("/players")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"));
//    }
//
//    //testing findbyid player
//    @Test
//    public void GetPlayerByIdEndpointShouldReturnOk() throws Exception {
//        Player player = new Player();
//        player.setId(1);
//        when(playerRepo.findById(1)).thenReturn(Optional.of(player));
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(get("/players/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//        ;
//    }
//
//    //testing indbyid when looking for nonexistant id
//    @Test
//    public void getPlayerByIdReturnsUnprocessable() throws Exception{
//        Player player = new Player();
//        player.setId(1);
//        when(playerRepo.findById(1)).thenReturn(Optional.of(player));
//
//        controller.perform(get("/players/2")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//        .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    public void DeleteAPlayerByIdShouldReturnOk() throws Exception {
//        Player player = new Player();
//        when(playerRepo.findById(1)).thenReturn(Optional.of(player));
//        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
//        controller.perform(delete("/players/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    public void addPlayerToGame() throws Exception{
//        Player player = new Player();
//        Game game = new Game();
//        player.setId(1);
//        game.setId(1);
//
//        when(gameRepo.findById(1)).thenReturn(Optional.of(game));
//        when(playerRepo.findById(1)).thenReturn((Optional.of(player)));
//
//        controller.perform(post("/players/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
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
//}