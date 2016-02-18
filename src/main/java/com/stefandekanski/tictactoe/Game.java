package com.stefandekanski.tictactoe;

import com.stefandekanski.tictactoe.field.AdjacentField;
import com.stefandekanski.tictactoe.field.Field;

import java.util.LinkedHashSet;
import java.util.List;

public class Game {
    public static class GameTerminatedException extends Exception {
        public GameTerminatedException(String reason) {
            super(reason);
        }
    }
    private final int winningScore;
    private final Board board;
    private final Player[] players;
    private int currIndex = -1;

    private Player winner = null;

    public Game(int winningScore, Board board, LinkedHashSet<Player> playerNames) {
        if (playerNames.size() < 2) {
            throw new IllegalStateException("Can't have less then two players");
        }
        if (winningScore < 3 || winningScore > board.N) {
            throw new IllegalStateException("Winning score can't be less then tree or bigger then board dimension");
        }
        this.winningScore = winningScore;
        this.board = board;
        this.players = new Player[playerNames.size()];
        int i = 0;
        for (Player p : playerNames) {
            this.players[i++] = p;
        }
    }

    public Player nextPlayer() throws GameTerminatedException {
        if (!board.hasFieldsLeft()) {
            throw new GameTerminatedException("No more fields left, it's a draw");
        }
        if (++currIndex == players.length) {
            currIndex = 0;
        }
        return players[currIndex];
    }

    public boolean playerMove(int x, int y, Player player) {
        Field move = board.openField(x, y, player);
        List<AdjacentField> adjacentFields = board.getAdjacentFieldsOf(move);
        for (AdjacentField field : adjacentFields) {
            boolean unionSuccess = move.tryUnion(field.adjacent, field.direction);
            if (unionSuccess && move.isGameOver(winningScore)) {
                winner = player;
                return true;
            }
        }
        return false;
    }

    public Player getWinner() {
        return winner;
    }
}
