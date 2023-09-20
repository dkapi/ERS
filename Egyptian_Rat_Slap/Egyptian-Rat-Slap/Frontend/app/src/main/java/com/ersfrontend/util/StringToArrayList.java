package com.ersfrontend.util;

import java.util.ArrayList;

public class StringToArrayList {

    public static ArrayList<Integer> stringToArrayList(String text) {
        ArrayList<Integer> integers = new ArrayList<>();
        String noBrackets = text.substring(1, text.length() - 1);
        String[] stringVals = noBrackets.split(", ");
        for (int i = 0; i < stringVals.length; ++i) {
            integers.add(Integer.valueOf(stringVals[i]));
        }
        System.out.println(integers);
        return integers;
    }

}
