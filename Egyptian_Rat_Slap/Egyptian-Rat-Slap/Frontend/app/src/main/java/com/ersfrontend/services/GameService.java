package com.ersfrontend.services;

import static com.ersfrontend.presenters.GamePresenter.getGamesSync;
import static com.ersfrontend.presenters.GamePresenter.postGameSync;
import static com.ersfrontend.presenters.PlayerPresenter.putPlayer;
import static com.ersfrontend.util.CardImageFinder.findCardImage;

import com.ersfrontend.models.Game;
import com.ersfrontend.models.Player;
import com.ersfrontend.slapsockets.SlapSocketBuilder;
import com.ersfrontend.util.SlapQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameService {
    private static Game gameStore;
    private static ArrayList<Game> gamesStore;
    private static int gameStoreId;
    private static SlapSocketBuilder gameSocket;

    /**
     * Returns a game received from api calls.
     * @return gameStore
     */
    public static Game getGameStored() {
        return gameStore;
    }

    /**
     * Saves a game received from api calls.
     * @param game
     */
    public static void setGameStored(Game game) {
        gameStore = game;
    }

    /**
     * Returns an arraylist of all games from an api call.
     * @return gamesStore
     */
    public static ArrayList<Game> getGamesStored() {
        return gamesStore;
    }

    /**
     * Saves all games received by an api call.
     * @param games
     */
    public static void setGamesStored(ArrayList<Game> games) {
        gamesStore = new ArrayList<>();
        gamesStore.addAll(games);
    }

    /**
     * Returns the id of a stored game.
     * @return gameStoreId
     */
    public static int getGameStoreId() {
        return gameStoreId;
    }

    /**
     * Saves the id of a game.
     * @param gameStoreId
     */
    public static void setGameStoreId(int gameStoreId) {
        GameService.gameStoreId = gameStoreId;
    }

    /**
     * Starts a game by putting a player into a game if there is an existing open game. If there are no existing games the method
     * calls createDefaultGame(player).
     * @param player
     */
    public static void startGame(Player player) {
        ArrayList<Game> games = getGamesSync();

        for (Game game : games) {
            if (game.getPlayers().size() < 4 && !player.isInGame()) {
                putPlayer(player.getId(), game.getId(), player);
                player.setInGame(true);
                setGameStoreId(game.getId());
                gameSocket = SlapSocketBuilder.getInstance();
                gameSocket.gameSocketBuilder(game.getId(), player.getUsername());
            }
        }
        if (!player.isInGame()) {
            createDefaultGame(player);
        }
        gameSocket.closeGame();
    }

    /**
     * Creates and posts a game that is custom created.
     */
    public static void hostGame(Game gameCreated, Player playerToPut) {
        Game game = postGameSync(gameCreated);
        putPlayer(playerToPut.getId(), game.getId(), playerToPut);
        // TODO: User entered information for a non default game
        gameSocket = SlapSocketBuilder.getInstance();
        gameSocket.gameSocketBuilder(game.getId(), playerToPut.getUsername());
        gameSocket.closeGame();
    }

    /**
     * Joins an existing custom game.
     * @param gameToJoin
     * @param playerToPut
     */
    public static void joinGame(Game gameToJoin, Player playerToPut) {
        if (gameToJoin.getPlayers().size() < 4 && !playerToPut.isInGame()) {
            putPlayer(playerToPut.getId(), gameToJoin.getId(), playerToPut);
            setGameStoreId(gameToJoin.getId());
            gameSocket = SlapSocketBuilder.getInstance();
            gameSocket.gameSocketBuilder(gameToJoin.getId(), playerToPut.getUsername());
            gameSocket.closeGame();
        }
    }

    /**
     * Sets up the game by dealing out the cards to each of the 4 players. Returns a map containing each player hand
     * and an identifying key: 1, 2, 3, or 4.
     * @param game
     * @return playerHands
     */
    public static Map<Integer, ArrayList<Integer>> setUpGame(Game game) {
        int j = 0; // j will increase 0 - 51 and not be affected by i, thing runs in theta(52) whatever that means
        Map<Integer, ArrayList<Integer>> playerHands = new HashMap<>();
        int temp = 0;
        for (Player player : game.getPlayers()) {
            ArrayList<Integer> cardIds = new ArrayList<>();
            for (int i = 0; i < 13; ++i) {
                cardIds.add(game.getDeck().get(j));
                j++;
            }
            playerHands.put(temp, cardIds);
            temp++;
        }
        return playerHands;
    }

    /**
     * Simulates setting a card in the center pile, mainly the part of removing the card from the player's hand
     * and adding it to the game's center pile.
     * @param centerPile
     * @param playerPile
     * @return centerPile
     */
    public static SlapQueue<Integer> setCardCenterPile(SlapQueue<Integer> centerPile, SlapQueue<Integer> playerPile) {
        if (playerPile.size() > 0) { // Keep player from setting a card with no cards, better solutions may exist
            Integer cardID = playerPile.dequeue();
            centerPile.enqueue(cardID);
        }
        return centerPile;
    }

    /**
     * Returns the image ids of the center pile, calls findCardImage(int cardId) to accomplish this.
     * @param centerPile
     * @return cardIDs
     */
    public static ArrayList<Integer> getCenterPileImages(SlapQueue<Integer> centerPile) {
        ArrayList<Integer> cardIDs = new ArrayList<>();
        if (centerPile.size() >= 3) {
            // NGL this sucks
            cardIDs.add(findCardImage(centerPile.get(centerPile.getBackIndex() - 2)));
            cardIDs.add(findCardImage(centerPile.get(centerPile.getBackIndex() - 1)));
            cardIDs.add(findCardImage(centerPile.get(centerPile.getBackIndex())));
        } else {
            for (int i = 0; i < centerPile.size(); ++i) {
                cardIDs.add(findCardImage(centerPile.get(centerPile.getBackIndex() - i)));
            }
        }
        return cardIDs;
    }

    /**
     * Creates a default game with a standard rule set. Puts the player into the game and stores the game's id
     * for further use.
     * @param playerToPut
     */
    private static void createDefaultGame(Player playerToPut) {
        Game defaultGame = new Game();
        defaultGame.setName("Default");
        defaultGame = postGameSync(defaultGame);
        putPlayer(playerToPut.getId(), defaultGame.getId(), playerToPut);
        playerToPut.setInGame(true);
        setGameStoreId(defaultGame.getId());
        gameSocket = SlapSocketBuilder.getInstance();
        gameSocket.gameSocketBuilder(defaultGame.getId(), playerToPut.getUsername());
    }

    public static SlapQueue slap(SlapQueue playerQueue, SlapQueue gameQueue) {
        for (int i = 0; i < gameQueue.size(); ++i) {
            playerQueue.enqueue(gameQueue.dequeue());
        }
        return playerQueue;
    }

    public static boolean gameEnded(Player playerB) {
        if (playerB.getPlayerHand().size() == 52) {
            return true;
        }
        return false;
    }
}
