package com.stefandekanski.tictactoe;


import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

public class GameTest {

    public static final Player PLAYER_ONE = new Player("X");
    public static final Player PLAYER_TWO = new Player("O");

    int winningScore = 3;

    Board board = new Board(3);

    LinkedHashSet<Player> playerNames = new LinkedHashSet<>(Arrays.asList(PLAYER_ONE, PLAYER_TWO));

    Game game;

    @Before
    public void createGame() {
        game = new Game(winningScore, board, playerNames);
    }

    @Test
    public void getNextPlayer() throws Game.GameTerminatedException {
        Player nextPlayer = game.nextPlayer();
        assertThat(PLAYER_ONE.equals(nextPlayer), is(true));

        nextPlayer = game.nextPlayer();
        assertThat(PLAYER_TWO.equals(nextPlayer), is(true));

        nextPlayer = game.nextPlayer();
        assertThat(PLAYER_ONE.equals(nextPlayer), is(true));
    }

    @Test
    public void makeAMove() throws Game.GameTerminatedException {
        Player currPlayer = game.nextPlayer();
        boolean isWinner = game.playerMove(1, 1, currPlayer);

        assertThat(isWinner, is(false));
    }

    @Test
    public void simulateSimpleGame() throws Game.GameTerminatedException {
        Player oneToWin = PLAYER_ONE;

        boolean winner = game.playerMove(3, 1, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(1, 1, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(1, 3, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(2, 2, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(3, 3, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(3, 2, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(2, 3, game.nextPlayer());
        assertThat(game.getWinner(), is(oneToWin));
        assertThat(winner, is(true));
    }

    @Test
    public void simulateDrawGame() throws Game.GameTerminatedException {
        boolean winner = game.playerMove(2, 2, game.nextPlayer()); //X
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(3, 1, game.nextPlayer());//O
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(1, 1, game.nextPlayer());//X
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(3, 3, game.nextPlayer());//O
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(1, 3, game.nextPlayer());//X
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(1, 2, game.nextPlayer());//O
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(3, 2, game.nextPlayer());//X
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(2, 1, game.nextPlayer());//O
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        winner = game.playerMove(2, 3, game.nextPlayer());//X
        assertThat(game.getWinner(), nullValue());
        assertThat(winner, is(false));

        Game.GameTerminatedException exception = null;
        try {
            game.nextPlayer();
        } catch (Game.GameTerminatedException gameIsDraw) {
            exception = gameIsDraw;
        }
        assertThat(exception, is(not(nullValue())));
    }

}
