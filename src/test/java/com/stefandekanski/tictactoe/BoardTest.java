package com.stefandekanski.tictactoe;

import com.stefandekanski.tictactoe.field.AdjacentField;
import com.stefandekanski.tictactoe.field.Direction;
import com.stefandekanski.tictactoe.field.Field;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class BoardTest {

    public static final Player PLAYER_ONE = new Player("X");
    public static final Player PLAYER_TWO = new Player("O");

    Board board;

    @Before
    public void setUp() {
        board = new Board(3);
    }

    @Test(expected = IllegalStateException.class)
    public void testOpenFieldIllegal1() {
        board.openField(0, 1, PLAYER_ONE);
    }

    @Test(expected = IllegalStateException.class)
    public void testOpenFieldIllegal2() {
        board.openField(1, 0, PLAYER_ONE);
    }

    @Test(expected = IllegalStateException.class)
    public void testOpenFieldIllegal3() {
        board.openField(4, 3, PLAYER_ONE);
    }

    @Test(expected = IllegalStateException.class)
    public void testOpenFieldIllegal4() {
        board.openField(3, 4, PLAYER_ONE);
    }

    @Test(expected = IllegalStateException.class)
    public void testIsFieldOpenIllegal() {
        board.isFieldOpen(0, 0);
    }

    @Test
    public void testIsFieldOpen() {
        assertThat(board.isFieldOpen(1, 1), is(false));
    }

    @Test
    public void testOpenField() {
        board.openField(1, 1, PLAYER_ONE);
        assertThat(board.isFieldOpen(1, 1), is(true));
    }

    @Test
    public void testGetField() {
        assertThat(board.getField(3, 3), CoreMatchers.nullValue());

        Field field = new Field(3, 3, PLAYER_ONE);
        board.openField(field.x, field.y, field.playerOwner);

        assertThat(board.getField(field.x, field.y), is(field));
    }

    @Test
    public void testHasFieldsLeft() {

    }

    @Test
    public void getAdjacentFields() {
        Field one = board.openField(2, 1, PLAYER_ONE);
        Field two = board.openField(3, 2, PLAYER_ONE);
        Field three = board.openField(1, 3, PLAYER_ONE);

        board.openField(1, 1, PLAYER_TWO);
        board.openField(3, 1, PLAYER_TWO);
        board.openField(1, 2, PLAYER_TWO);
        board.openField(2, 3, PLAYER_TWO);

        Field middleField = board.openField(2, 2, PLAYER_ONE);
        List<AdjacentField> adjacent = board.getAdjacentFieldsOf(middleField);

        assertThat(adjacent.size(), is(3));
        assertThat(adjacent,
                hasItems(
                        new AdjacentField(one, Direction.VERTICAL),
                        new AdjacentField(two, Direction.HORIZONTAL),
                        new AdjacentField(three, Direction.OTHER_DIAGONAL)));
    }

    @Test
    public void getAdjacentFields2() {
        Field one = board.openField(1, 1, PLAYER_ONE);
        Field two = board.openField(3, 1, PLAYER_ONE);
        Field three = board.openField(1, 2, PLAYER_ONE);
        Field four = board.openField(2, 3, PLAYER_ONE);

        board.openField(2, 1, PLAYER_TWO);
        board.openField(3, 2, PLAYER_TWO);
        board.openField(1, 3, PLAYER_TWO);

        Field middleField = board.openField(2, 2, PLAYER_ONE);
        List<AdjacentField> adjacent = board.getAdjacentFieldsOf(middleField);

        assertThat(adjacent.size(), is(4));
        assertThat(adjacent,
                hasItems(
                        new AdjacentField(one, Direction.BACKSLASH_DIAGONAL),
                        new AdjacentField(two, Direction.OTHER_DIAGONAL),
                        new AdjacentField(three, Direction.HORIZONTAL),
                        new AdjacentField(four, Direction.VERTICAL)));
    }

}
