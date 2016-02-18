package com.stefandekanski.tictactoe.game;


import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PlayerTest {

    @Test(expected = Player.IllegalNameException.class)
    public void createPlayerWithNonValidName() throws Player.IllegalNameException {
        new Player("xy");
    }

    @Test
    public void getPlayerNameIsUpperCase() throws Player.IllegalNameException {
        String name = "x";
        Player player = new Player(name);

        String upperCase = player.getName();
        assertThat(name.toUpperCase().equals(upperCase), is(true));
    }
}
