package com.stefandekanski.tictactoe.runner;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BoardToStringTest {

    BoardToString boardToString;

    @Before
    public void createBoardConsoleWriter() {
        boardToString = new BoardToString(3);
    }

    @Test
    public void writeTopRow() {
        String expected = " #A#B#C# \n";
        boardToString.writeTopRow();
        assertThat(boardToString.toString(), is(expected));
    }

    @Test
    public void writeBorderRow() {
        String expected = "#||==||==||==||#\n";
        boardToString.writeBorderRow();
        assertThat(boardToString.toString(), is(expected));
    }

    @Test
    public void playerRow() {
        
    }
}
