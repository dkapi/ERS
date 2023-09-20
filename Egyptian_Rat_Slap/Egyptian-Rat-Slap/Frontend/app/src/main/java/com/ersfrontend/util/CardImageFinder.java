package com.ersfrontend.util;

import com.activities.R;

public class CardImageFinder {

    /**
     * Finds the id for the image of the corresponding cardID
     * This is the lingest switch statement i've ever seen
     * @param cardID
     * @return image
     */
    public static Integer findCardImage(Integer cardID) {
        Integer image = null;
        switch (cardID) {
            case 0:
                image = R.drawable.ace_of_clubs;
                break;
            case 1:
                image = R.drawable.two_of_clubs;
                break;
            case 2:
                image = R.drawable.three_of_clubs;
                break;
            case 3:
                image = R.drawable.four_of_clubs;
                break;
            case 4:
                image = R.drawable.five_of_clubs;
                break;
            case 5:
                image = R.drawable.six_of_clubs;
                break;
            case 6:
                image = R.drawable.seven_of_clubs;
                break;
            case 7:
                image = R.drawable.eight_of_clubs;
                break;
            case 8:
                image = R.drawable.nine_of_clubs;
                break;
            case 9:
                image = R.drawable.ten_of_clubs;
                break;
            case 10:
                image = R.drawable.jack_of_clubs;
                break;
            case 11:
                image = R.drawable.queen_of_clubs;
                break;
            case 12:
                image = R.drawable.king_of_clubs;
                break;
            case 13:
                image = R.drawable.ace_of_spades;
                break;
            case 14:
                image = R.drawable.two_of_spades;
                break;
            case 15:
                image = R.drawable.three_of_spades;
                break;
            case 16:
                image = R.drawable.four_of_spades;
                break;
            case 17:
                image = R.drawable.five_of_spades;
                break;
            case 18:
                image = R.drawable.six_of_spades;
                break;
            case 19:
                image = R.drawable.seven_of_spades;
                break;
            case 20:
                image = R.drawable.eight_of_spades;
                break;
            case 21:
                image = R.drawable.nine_of_spades;
                break;
            case 22:
                image = R.drawable.ten_of_spades;
                break;
            case 23:
                image = R.drawable.jack_of_spades;
                break;
            case 24:
                image = R.drawable.queen_of_spades;
                break;
            case 25:
                image = R.drawable.king_of_spades;
                break;
            case 26:
                image = R.drawable.ace_of_diamonds;
                break;
            case 27:
                image = R.drawable.two_of_diamonds;
                break;
            case 28:
                image = R.drawable.three_of_diamonds;
                break;
            case 29:
                image = R.drawable.four_of_diamonds;
                break;
            case 30:
                image = R.drawable.five_of_diamonds;
                break;
            case 31:
                image = R.drawable.six_of_diamonds;
                break;
            case 32:
                image = R.drawable.seven_of_diamonds;
                break;
            case 33:
                image = R.drawable.eight_of_diamonds;
                break;
            case 34:
                image = R.drawable.nine_of_diamonds;
                break;
            case 35:
                image = R.drawable.ten_of_diamonds;
                break;
            case 36:
                image = R.drawable.jack_of_diamonds;
                break;
            case 37:
                image = R.drawable.queen_of_diamonds;
                break;
            case 38:
                image = R.drawable.king_of_diamonds;
                break;
            case 39:
                image = R.drawable.ace_of_hearts;
                break;
            case 40:
                image = R.drawable.two_of_hearts;
                break;
            case 41:
                image = R.drawable.three_of_hearts;
                break;
            case 42:
                image = R.drawable.four_of_hearts;
                break;
            case 43:
                image = R.drawable.five_of_hearts;
                break;
            case 44:
                image = R.drawable.six_of_hearts;
                break;
            case 45:
                image = R.drawable.seven_of_hearts;
                break;
            case 46:
                image = R.drawable.eight_of_hearts;
                break;
            case 47:
                image = R.drawable.nine_of_hearts;
                break;
            case 48:
                image = R.drawable.ten_of_hearts;
                break;
            case 49:
                image = R.drawable.jack_of_hearts;
                break;
            case 50:
                image = R.drawable.queen_of_hearts;
                break;
            case 51:
                image = R.drawable.king_of_hearts;
                break;
        }
        return image;
    }
}
