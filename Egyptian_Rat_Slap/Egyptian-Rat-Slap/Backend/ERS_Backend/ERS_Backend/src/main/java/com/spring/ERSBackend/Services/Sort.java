package com.spring.ERSBackend.Services;

import com.spring.ERSBackend.Entities.Player;

import java.util.ArrayList;

public class Sort {

    public static ArrayList<Player> SortTopTen(ArrayList<Player> players){
        //Perform insertion sort on players
        int n = players.size();
        for (int i = 1; i < n; ++i) {
            Player key = players.get(i);
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && players.get(j).getGamesWon() > key.getGamesWon()) {
                players.set(j + 1, players.get(j));
                j = j - 1;
            }
            players.set(j + 1, key);
        }
        Player[] topTen;
        if(players.size() >= 10) { //Checks to see if we have more than 10 players
            topTen = new Player[10];
        }
        else {
            topTen = new Player[players.size()];// If not, just returns the players.
        }

        for(int i = 0; i < topTen.length; i++){
            topTen[i] = players.get(i);
        }
        // Define the array
        int[] array = {1, 2, 3, 4, 5};

// Define the ArrayList
        ArrayList<Player> NewArrayList = new ArrayList<>();

// Copy the contents of the array into the ArrayList using a loop
        for (int i = 0; i < array.length; i++) {
            NewArrayList.add(topTen[i]);
        }
        return NewArrayList;
    }

    public static void SortById(ArrayList<Integer> playersId){
        //Perform insertion sort on players
        int n = playersId.size();
        for (int i = 1; i < n; ++i) {
            int key = playersId.get(i);
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && playersId.get(j) > key) {
                playersId.set(j + 1, playersId.get(j));
                j = j - 1;
            }
            playersId.set(j + 1, key);
        }

       // return playersId;
    }
}
