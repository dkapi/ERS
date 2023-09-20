package com.spring.ERSBackend.Websockets;

import com.spring.ERSBackend.Entities.Game;
import com.spring.ERSBackend.Entities.Player;
import com.spring.ERSBackend.Repository.GameRepository;
import com.spring.ERSBackend.Services.Sort;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.core.io.NumberInput.parseInt;

@Controller
@ServerEndpoint(value = "/game/{game_id}/{username}")
public class GameSockets {

    private static GameRepository gameRepository;

    @Autowired
    public void setGameRepository(GameRepository repo) {
        gameRepository = repo;
    }

    //ima be honest idk if i should be mapping a game here or a something else to correlate to game, we will see
    private static Map<Session, String> sessionGameMap = new Hashtable<>();
    private static Map<String, Session> gameSessionMap = new Hashtable<>();
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();


    private final Logger logger = LoggerFactory.getLogger(GameSockets.class);
   // public static ArrayList<Integer> playerOrder = new ArrayList<>();;

    @OnOpen
    public void onOpen(Session session, @PathParam("game_id") String gameId, @PathParam("username") String username) throws IOException {
        sessionGameMap.put(session, gameId);
        gameSessionMap.put(gameId, session);
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

    }

    //this finna be a big case
    @OnMessage
    public void onMessage(Session session, String msg) throws IOException {
        String gameId = sessionGameMap.get(session);
        Integer id = Integer.parseInt(gameId);
        Game game = gameRepository.findById(id).get();
        List<Game> allGames = new ArrayList<>();
        allGames = gameRepository.findAll();
        logger.info("This is msg param:" + msg);
        logger.info("this is id:" + id);
        logger.info("this is all games in repo:" + allGames);
        System.out.println("this is game.getplayer.size" + game.getPlayers().size());
        ArrayList<Integer> playerOrder = new ArrayList<>();
        for (Player player : gameRepository.findById(parseInt(gameId)).get().getPlayers()) {
            playerOrder.add(player.getId());
        }
        Sort.SortById(playerOrder);

       String playerSize = String.valueOf(game.getPlayers().size());
        if(msg.equals("00")){
            broadcast(("00" + " " + playerSize), gameId);
        }
        else if(msg.charAt(0) == '4' && msg.charAt(1) == '1'){
            broadcast(msg, gameId);
        }
        else if(msg.charAt(0) == '0'&& msg.charAt(1) == '1'){
            broadcast(("01" + " " + playerOrder), gameId);
        }
        else {
            broadcast("40", gameId);
            session.getBasicRemote().sendText("40");
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        String username = sessionUsernameMap.get(session);
        String gameId = sessionGameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        sessionGameMap.remove(session);
        gameSessionMap.remove(gameId);
    }
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    private void broadcast(String msg, String gameId) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                String sessionGameId = sessionGameMap.get(session);
                if (sessionGameId.equals((gameId))) {
                    session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
