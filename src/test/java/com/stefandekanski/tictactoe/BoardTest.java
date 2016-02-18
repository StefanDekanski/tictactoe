package com.stefandekanski.tictactoe;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BoardTest {

    private static final String PLAYER_ONE = "X";
    private static final String PLAYER_TWO = "O";

    Board board;

    @Before
    public void setUp() throws Board.IllegalBoardStateException {
        board = new Board(3);
    }

    @Test(expected = Board.IllegalBoardStateException.class)
    public void testIsFieldOpenIllegal() throws Board.IllegalBoardStateException {
        board.isFieldOpen(0, 0);
    }

    @Test
    public void testIsFieldOpen() throws Board.IllegalBoardStateException {
        assertThat(board.isFieldOpen(1, 1), is(false));
    }

    @Test(expected = Board.IllegalBoardStateException.class)
    public void testOpenFieldIllegal() throws Board.IllegalBoardStateException {
        board.openField(0, 0, PLAYER_ONE);
    }

    @Test
    public void testOpenField() throws Board.IllegalBoardStateException {
        board.openField(1, 1, PLAYER_ONE);
        assertThat(board.isFieldOpen(1, 1), is(true));
    }

}
