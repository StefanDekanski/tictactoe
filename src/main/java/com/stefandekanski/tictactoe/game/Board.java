package com.stefandekanski.tictactoe.game;

import com.stefandekanski.tictactoe.field.AdjacentField;
import com.stefandekanski.tictactoe.field.Direction;
import com.stefandekanski.tictactoe.field.Field;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {
    public static class IllegalMove extends Exception {
        public IllegalMove(String message) {
            super(message);
        }
    }

    public final int N;
    private final Field[] board;
    private int fieldsLeft;

    public Board(int dimensions) {
        if (dimensions > 8 || dimensions < 3) {
            throw new IllegalStateException("Dimensions allowed are N >= 3 && N <= 8");
        }
        N = dimensions;
        fieldsLeft = N * N;
        board = new Field[fieldsLeft];
    }

    boolean hasFieldsLeft() {
        return fieldsLeft > 0;
    }

    /*
      From left to right, from top to bottom
     */
    public List<Player> getFieldOwnerList() {
        ArrayList<Player> fieldOwners = new ArrayList<>(board.length);
        for (Field f : board) {
            if (f == null) {
                fieldOwners.add(Player.NULL_PLAYER);
            } else {
                fieldOwners.add(f.playerOwner);
            }
        }
        return fieldOwners;
    }

    Field getField(int x, int y) {
        validRowAndColumn(x, y);
        return board[fieldIndex(x, y)];
    }

    boolean isFieldOpen(int x, int y) {
        validRowAndColumn(x, y);
        return board[fieldIndex(x, y)] != null;
    }

    Field openField(int x, int y, Player player) throws IllegalMove {
        if (isFieldOpen(x, y)) {
            throw new IllegalMove("Can't open already open field!");
        }
        Field field = new Field(x, y, player);
        board[fieldIndex(x, y)] = field;
        fieldsLeft--;
        return field;
    }

    List<AdjacentField> getAdjacentFieldsOf(Field field) {
        LinkedList<AdjacentField> linkedList = new LinkedList<>();
        int x = field.x;
        int y = field.y;

        int leftX = x - 1;
        if (leftX > 0) {
            //left
            Field left = getField(leftX, y);
            ifOwnerIsSameAddToList(field, left, Direction.HORIZONTAL, linkedList);

            int upY = y - 1;
            if (upY > 0) {
                //left up
                Field leftUp = getField(leftX, upY);
                ifOwnerIsSameAddToList(field, leftUp, Direction.BACKSLASH_DIAGONAL, linkedList);
            }

            int downY = y + 1;
            if (downY <= N) {
                //left down
                Field leftDown = getField(leftX, downY);
                ifOwnerIsSameAddToList(field, leftDown, Direction.OTHER_DIAGONAL, linkedList);
            }
        }

        int rightX = x + 1;
        if (rightX <= N) {
            //right
            Field right = getField(rightX, y);
            ifOwnerIsSameAddToList(field, right, Direction.HORIZONTAL, linkedList);

            int upY = y - 1;
            if (upY > 0) {
                //right up
                Field rightUp = getField(rightX, upY);
                ifOwnerIsSameAddToList(field, rightUp, Direction.OTHER_DIAGONAL, linkedList);
            }

            int downY = y + 1;
            if (downY <= N) {
                //right down
                Field rightDown = getField(rightX, downY);
                ifOwnerIsSameAddToList(field, rightDown, Direction.BACKSLASH_DIAGONAL, linkedList);
            }
        }

        int downY = y + 1;
        if (downY <= N) {
            //down
            Field down = getField(x, downY);
            ifOwnerIsSameAddToList(field, down, Direction.VERTICAL, linkedList);
        }

        int upY = y - 1;
        if (upY > 0) {
            //up
            Field up = getField(x, upY);
            ifOwnerIsSameAddToList(field, up, Direction.VERTICAL, linkedList);
        }

        return linkedList;
    }

    private void ifOwnerIsSameAddToList(Field field, Field adjacent, Direction direction, LinkedList<AdjacentField> list) {
        if (adjacent != null && field.isOwnerTheSame(adjacent)) {
            list.add(new AdjacentField(adjacent, direction));
        }
    }

    private int fieldIndex(int x, int y) {
        return N * (y - 1) + x - 1;
    }

    private void validRowAndColumn(int x, int y) {
        if (x < 1 || x > N || y < 1 || y > N) {
            throw new IllegalStateException("Can't choose field that is out of board!");
        }
    }
}
