package com.ersfrontend.util;

public class GameRules {

    /**
     * Takes in a gameQueue and determines if that queue is slappable
     * @param gameQueue
     * @return slappable
     */
    public static boolean setSlappable(SlapQueue<Integer> gameQueue) {
        boolean slappable = false;
        if (gameQueue.size() == 2) {
            int cardFront = gameQueue.peekLast();
            int cardMiddle = gameQueue.peekLast() - 1;
            cardFront %= 13;
            cardMiddle %= 13;
            if (cardFront == cardMiddle) slappable = true;
            else if ((cardFront == 11 && cardMiddle == 12) || (cardFront == 12 && cardMiddle == 11)) slappable = true;
        } else if (gameQueue.size() > 2) {
            int cardFront = gameQueue.peekLast();
            int cardMiddle = gameQueue.peekLast() - 1;
            int cardBack = gameQueue.peekLast() - 2;
            cardFront %= 13;
            cardMiddle %= 13;
            cardBack %= 13;
            if (cardFront == cardMiddle) slappable = true;
            else if (cardFront == cardBack && cardMiddle != cardFront) slappable = true;
            else if ((cardFront == 11 && cardMiddle == 12) || (cardFront == 12 && cardMiddle == 11)) slappable = true;
        }
        return slappable;
    }
}