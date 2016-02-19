package com.stefandekanski.tictactoe.runner;

import com.stefandekanski.tictactoe.game.Board;
import com.stefandekanski.tictactoe.game.Game;
import com.stefandekanski.tictactoe.game.Player;
import com.stefandekanski.tictactoe.move.Move;

public interface GameRunnerIO {
    //player can terminate the game with some 'exit' command
    Move getPlayerMove(Player player) throws Game.GameTerminatedException;

    void currentGameBoard(Board gameBoard);

    void gameDraw();

    void gameWinner(Player winner);

    void gameTerminated(String reason);

    void illegalMoveTryAgain(String moveDetails);
}
