package com.stefandekanski.tictactoe.runner;


import com.stefandekanski.tictactoe.game.Board;
import com.stefandekanski.tictactoe.game.Game;
import com.stefandekanski.tictactoe.game.Player;

public class GameRunner {

    private final Game game;
    private final GameRunnerIO gameRunnerIO;

    public GameRunner(Game game, GameRunnerIO gameRunnerIO) {
        this.game = game;
        this.gameRunnerIO = gameRunnerIO;
    }

    public void runGame() {
        try {
            boolean gameOver = false;
            gameRunnerIO.currentGameBoard(game.gameBoard());
            while (!gameOver) {
                Player currPlayer = game.nextPlayer();
                gameOver = nextValidMove(currPlayer);
                //draw board after each move
                gameRunnerIO.currentGameBoard(game.gameBoard());
            }
            if (game.getWinner() != null) {
                gameRunnerIO.gameWinner(game.getWinner());
            } else {
                gameRunnerIO.gameDraw();
            }
        } catch (Game.GameTerminatedException e) {
            gameRunnerIO.gameTerminated(e.getMessage());
        }

    }

    private boolean nextValidMove(Player player) throws Game.GameTerminatedException {
        while (true) {
            try {
                Move move = gameRunnerIO.getPlayerMove(player);
                return game.playerMove(move.x, move.y, player);
            } catch (Board.IllegalMove illegalMove) {
                gameRunnerIO.illegalMoveTryAgain(illegalMove.getMessage());
            }
        }
    }

    public boolean hasWinner() {
        return game.getWinner() != null;
    }

    public Player gameWinner() {
        return game.getWinner();
    }

}
