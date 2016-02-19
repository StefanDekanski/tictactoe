package com.stefandekanski.tictactoe.move;


public class MoveTranslator {
    private final int[] inverted;

    public MoveTranslator(int N) {
        inverted = new int[N];
        int index = 0;
        for (int i = N; i >= 1; i--) {
            inverted[index++] = i;
        }
    }

    int translateLetter(char ch) {
        char lower = Character.toLowerCase(ch);
        return lower - 'a' + 1;
    }

    int invertRowNum(int i) {
        return inverted[i - 1];
    }

    public Move translateToMove(char column, int row) {
        return new Move(translateLetter(column), invertRowNum(row));
    }
}
