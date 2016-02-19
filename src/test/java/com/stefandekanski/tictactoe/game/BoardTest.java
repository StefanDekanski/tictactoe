package com.stefandekanski.tictactoe.game;

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

    Board board;

    Player PLAYER_ONE;
    Player PLAYER_TWO;

    @Before
    public void setUp() throws Player.IllegalNameException {
        board = new Board(3);
        PLAYER_ONE = new Player("X");
        PLAYER_TWO = new Player("O");
    }

    @Test(expected = IllegalStateException.class)
    public void testOpenFieldIllegal1() throws Board.IllegalMove {
        board.openField(0, 1, PLAYER_ONE);
    }

    @Test(expected = IllegalStateException.class)
    public void testOpenFieldIllegal2() throws Board.IllegalMove {
        board.openField(1, 0, PLAYER_ONE);
    }

    @Test(expected = IllegalStateException.class)
    public void testOpenFieldIllegal3() throws Board.IllegalMove {
        board.openField(4, 3, PLAYER_ONE);
    }

    @Test(expected = IllegalStateException.class)
    public void testOpenFieldIllegal4() throws Board.IllegalMove {
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
    public void testOpenField() throws Board.IllegalMove {
        board.openField(1, 1, PLAYER_ONE);
        assertThat(board.isFieldOpen(1, 1), is(true));
    }

    @Test
    public void testGetField() throws Board.IllegalMove {
        assertThat(board.getField(3, 3), CoreMatchers.nullValue());

        Field field = new Field(3, 3, PLAYER_ONE);
        board.openField(field.x, field.y, field.playerOwner);

        assertThat(board.getField(field.x, field.y), is(field));
    }

    @Test
    public void getAdjacentFields() throws Board.IllegalMove {
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
    public void adjecentFieldsFieldOwnerListRowToString() throws Board.IllegalMove {
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

    @Test
    public void fieldOwnerListTest() throws Board.IllegalMove {
        simpleFieldSetup();
        List<Player> fieldOwnerList = board.getFieldOwnerList(0);
        assertThat(fieldOwnerList.get(0), is(PLAYER_ONE));
        assertThat(fieldOwnerList.get(1), is(PLAYER_TWO));
        assertThat(fieldOwnerList.get(2), is(PLAYER_ONE));

        fieldOwnerList = board.getFieldOwnerList(1);
        assertThat(fieldOwnerList.get(0), is(PLAYER_ONE));
        assertThat(fieldOwnerList.get(1), is(PLAYER_ONE));
        assertThat(fieldOwnerList.get(2), is(PLAYER_TWO));

        fieldOwnerList = board.getFieldOwnerList(2);
        assertThat(fieldOwnerList.get(0), is(PLAYER_TWO));
        assertThat(fieldOwnerList.get(1), is(PLAYER_ONE));
        assertThat(fieldOwnerList.get(2), is(Player.NULL_PLAYER));
    }

    @Test
    public void writeTopRow() {
        StringBuilder sb = new StringBuilder();

        String expected = " |A|B|C| \n";
        board.writeHorizontalIndexRow(sb);

        assertThat(sb.toString(), is(expected));
    }

    @Test
    public void writeBorderRow() {
        StringBuilder sb = new StringBuilder();

        String expected = "=|=|=|=|=\n";
        board.writeBorderRow(sb);

        assertThat(sb.toString(), is(expected));
    }


    @Test
    public void writeRowTest0() throws Board.IllegalMove {
        simpleFieldSetup();
        StringBuilder sb = new StringBuilder();

        String expected = "3|" + PLAYER_ONE + "|" + PLAYER_TWO + "|" + PLAYER_ONE + "|3\n";
        board.writeRow(sb, 0);

        assertThat(sb.toString(), is(expected));
    }

    @Test
    public void writeRowTest1() throws Board.IllegalMove {
        simpleFieldSetup();
        StringBuilder sb = new StringBuilder();

        String expected = "2|" + PLAYER_ONE + "|" + PLAYER_ONE + "|" + PLAYER_TWO + "|2\n";
        board.writeRow(sb, 1);

        assertThat(sb.toString(), is(expected));
    }

    @Test
    public void writeRowTest2() throws Board.IllegalMove {
        simpleFieldSetup();
        StringBuilder sb = new StringBuilder();

        String expected = "1|" + PLAYER_TWO + "|" + PLAYER_ONE + "|" + Player.NULL_PLAYER + "|1\n";
        board.writeRow(sb, 2);

        assertThat(sb.toString(), is(expected));
    }

    @Test
    public void toStringPlayGround() throws Board.IllegalMove {
        simpleFieldSetup();

        System.out.println(board.toString());
    }


    private void simpleFieldSetup() throws Board.IllegalMove {
        board.openField(1, 1, PLAYER_ONE);
        board.openField(2, 1, PLAYER_TWO);
        board.openField(3, 1, PLAYER_ONE);

        board.openField(1, 2, PLAYER_ONE);
        board.openField(2, 2, PLAYER_ONE);
        board.openField(3, 2, PLAYER_TWO);

        board.openField(1, 3, PLAYER_TWO);
        board.openField(2, 3, PLAYER_ONE);
        //field 3,3 is NULL_PLAYER
    }
}
