package com.stefandekanski.tictactoe;

public class Board {
    public static class IllegalBoardStateException extends Exception {
        public IllegalBoardStateException(String message) {
            super(message);
        }
    }

    private final int N;
    private final Field[] board;

    public Board(int dimensions) throws IllegalBoardStateException {
        if (dimensions > 8 || dimensions < 3) {
            throw new IllegalBoardStateException("Dimensions allowed are N >= 3 && N <= 8");
        }
        N = dimensions;
        board = new Field[N * N];
    }

    public boolean isFieldOpen(int x, int y) throws IllegalBoardStateException {
        validRowAndColumn(x, y);
        return board[fieldIndex(x, y)] != null;
    }

    public void openField(int x, int y, String player) throws IllegalBoardStateException {
        if (isFieldOpen(x, y)) {
            throw new IllegalBoardStateException("Can't open already open field!");
        }
        board[fieldIndex(x, y)] = new Field(x, y, player);
    }

    private int fieldIndex(int i, int j) {
        return (i - 1) * N + j - 1;
    }

    private void validRowAndColumn(int i, int j) throws IllegalBoardStateException {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IllegalBoardStateException("Can't choose field that is out of board!");
        }
    }
}
