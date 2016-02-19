package com.stefandekanski.tictactoe.util;


public class Util {

    public static int translateLetter(char ch) {
        char lower = Character.toLowerCase(ch);
        return lower - 'a' + 1;
    }

    //rowNum is one based
    public static int invertRowNum(int N, int rowNum) {
        return N - (rowNum - 1);
    }
}
