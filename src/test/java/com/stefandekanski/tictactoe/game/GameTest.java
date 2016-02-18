package com.stefandekanski.tictactoe.game;


import com.stefandekanski.tictactoe.game.Board;
import com.stefandekanski.tictactoe.game.Game;
import com.stefandekanski.tictactoe.game.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class GameTest {

    int winningScore = 3;
    Board board = new Board(3);
    LinkedHashSet<Player> playerNames;

    Game game;

    Player PLAYER_ONE;
    Player PLAYER_TWO;

    @Before
    public void createGame() throws Player.IllegalNameException {
        PLAYER_ONE = new Player("X");
        PLAYER_TWO = new Player("O");
        playerNames = new LinkedHashSet<>(Arrays.asList(PLAYER_ONE, PLAYER_TWO));
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
    public void makeAMove() throws Game.GameTerminatedException, Board.IllegalMove {
        Player currPlayer = game.nextPlayer();
        boolean isGameOver = game.playerMove(1, 1, currPlayer);

        assertThat(isGameOver, is(false));
    }

    @Test
    public void simulateSimpleGame() throws Game.GameTerminatedException, Board.IllegalMove {
        Player oneToWin = PLAYER_ONE;

        boolean gameOver = game.playerMove(3, 1, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(1, 1, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(1, 3, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(2, 2, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(3, 3, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(3, 2, game.nextPlayer());
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(2, 3, game.nextPlayer());
        assertThat(game.getWinner(), is(oneToWin));
        assertThat(gameOver, is(true));
    }

    @Test
    public void simulateDrawGame() throws Game.GameTerminatedException, Board.IllegalMove {
        boolean gameOver = game.playerMove(2, 2, game.nextPlayer()); //X
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(3, 1, game.nextPlayer());//O
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(1, 1, game.nextPlayer());//X
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(3, 3, game.nextPlayer());//O
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(1, 3, game.nextPlayer());//X
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(1, 2, game.nextPlayer());//O
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(3, 2, game.nextPlayer());//X
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(2, 1, game.nextPlayer());//O
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(false));

        gameOver = game.playerMove(2, 3, game.nextPlayer());//X
        assertThat(game.getWinner(), nullValue());
        assertThat(gameOver, is(true));
        
    }

}
