package com.stefandekanski.tictactoe.runner;


import com.stefandekanski.tictactoe.game.Board;
import com.stefandekanski.tictactoe.game.Game;
import com.stefandekanski.tictactoe.game.Move;
import com.stefandekanski.tictactoe.game.Player;

import java.util.Arrays;
import java.util.LinkedHashSet;

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
        boolean silent = false;
        while (true) {
            try {
                Move move = gameRunnerIO.getPlayerMove(player, silent);
                return game.playerMove(move.x, move.y, player);
            } catch (Board.IllegalMove illegalMove) {
                gameRunnerIO.illegalMoveTryAgain(illegalMove.getMessage());
                silent = true;
            }
        }
    }

    public static void main(String[] args) throws Player.IllegalNameException {
        int winningLineLen = 4;
        int boardDimension = 8;
        Board gameBoard = new Board(boardDimension);

        Player playerOne = new Player("X");
        Player playerTwo = new Player("O");
        Player playerThree = new Player("Y");
        LinkedHashSet<Player> players = new LinkedHashSet<>(Arrays.asList(playerOne, playerTwo, playerThree));

        Game game = new Game(winningLineLen, gameBoard, players);
        GameRunnerIO gameRunnerIO = new ConsoleGameRunnerIO(boardDimension);

        System.out.println("The game is configurable. The hardcoded values are 4 in line (all directions) for win, 3 players ('X','O','Y'), and board 8x8.");
        System.out.println("Have fun!");
        System.out.println();

        new GameRunner(game, gameRunnerIO).runGame();

        //sleep a little before exit, so message can be read
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
