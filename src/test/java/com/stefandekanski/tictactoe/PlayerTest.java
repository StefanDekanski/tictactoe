package com.stefandekanski.tictactoe;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PlayerTest {

    @Before
    public void setUp(){

    }

    @Test(expected = IllegalStateException.class)
    public void createPlayerWithNonValidName() {
        new Player("xy");
    }

    @Test
    public void getPlayerNameIsUpperCase() {
        String name = "x";
        Player player = new Player(name);

        String upperCase = player.getName();
        assertThat(name.toUpperCase().equals(upperCase), is(true));
    }
}
