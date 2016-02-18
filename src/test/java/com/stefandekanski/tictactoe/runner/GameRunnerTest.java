package com.stefandekanski.tictactoe.runner;


import com.stefandekanski.tictactoe.game.Board;
import com.stefandekanski.tictactoe.game.Game;
import com.stefandekanski.tictactoe.game.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class GameRunnerTest {

    Player PLAYER_ONE;
    Player PLAYER_TWO;

    int winningScore = 3;
    Board board = new Board(3);
    LinkedHashSet<Player> playerNames;

    Game game;

    GameSimulationDevice inputDevice;

    GameRunner gameRunner;

    @Before
    public void createGameRunnerTest() throws Player.IllegalNameException {
        PLAYER_ONE = new Player("X");
        PLAYER_TWO = new Player("O");
        playerNames = new LinkedHashSet<>(Arrays.asList(PLAYER_ONE, PLAYER_TWO));
        game = new Game(winningScore, board, playerNames);
        inputDevice = new GameSimulationDevice();
        gameRunner = new GameRunner(game, inputDevice);
    }

    @Test
    public void runGameSimulation() {
        List<Move> simulated = getSimulatedPlayerOneWin();
        inputDevice.setSimulatedMoves(simulated.iterator());
        gameRunner.runGame();

        assertThat(gameRunner.hasWinner(), is(true));
        assertThat(gameRunner.gameWinner(), is(PLAYER_ONE));
        assertThat(inputDevice.currentGameBoardCalls, is(simulated.size() + 1));
        assertThat(inputDevice.gameDrawCalls, is(0));
        assertThat(inputDevice.gameWinnerCalls, is(1));
        assertThat(inputDevice.gameTerminatedCalls, is(0));
    }

    @Test
    public void runGameSimulationDrawGame() {
        List<Move> simulated = getSimulatedDrawGame();
        inputDevice.setSimulatedMoves(simulated.iterator());
        gameRunner.runGame();

        assertThat(gameRunner.hasWinner(), is(false));
        assertThat(gameRunner.gameWinner(), is(nullValue()));
        assertThat(inputDevice.currentGameBoardCalls, is(simulated.size() + 1));
        assertThat(inputDevice.gameDrawCalls, is(1));
        assertThat(inputDevice.gameWinnerCalls, is(0));
        assertThat(inputDevice.gameTerminatedCalls, is(0));
    }


    private static class GameSimulationDevice implements GameRunnerIO {

        Iterator<Move> simulatedMoves;

        int currentGameBoardCalls = 0;
        int gameDrawCalls = 0;
        int gameWinnerCalls = 0;
        int gameTerminatedCalls = 0;
        int illegalMoveTryAgainCalls = 0;

        public void setSimulatedMoves(Iterator<Move> moves) {
            simulatedMoves = moves;
        }

        @Override
        public Move getPlayerMove(Player player) {
            return simulatedMoves.next();
        }

        @Override
        public void currentGameBoard(Board gameBoard) {
            currentGameBoardCalls++;
        }

        @Override
        public void gameDraw() {
            gameDrawCalls++;
        }

        @Override
        public void gameWinner(Player winner) {
            gameWinnerCalls++;
        }

        @Override
        public void gameTerminated(String message) {
            gameTerminatedCalls++;
        }

        @Override
        public void illegalMoveTryAgain(String moveDetails) {
            illegalMoveTryAgainCalls++;
        }
    }

    public List<Move> getSimulatedPlayerOneWin() {
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(3, 1));
        moves.add(new Move(1, 1));
        moves.add(new Move(1, 3));
        moves.add(new Move(2, 2));
        moves.add(new Move(3, 3));
        moves.add(new Move(3, 2));
        moves.add(new Move(2, 3));
        return moves;
    }

    public List<Move> getSimulatedDrawGame() {
        LinkedList<Move> moves = new LinkedList<>();
        moves.add(new Move(2, 2));
        moves.add(new Move(3, 1));
        moves.add(new Move(1, 1));
        moves.add(new Move(3, 3));
        moves.add(new Move(1, 3));
        moves.add(new Move(1, 2));
        moves.add(new Move(3, 2));
        moves.add(new Move(2, 1));
        moves.add(new Move(2, 3));
        return moves;
    }
}
