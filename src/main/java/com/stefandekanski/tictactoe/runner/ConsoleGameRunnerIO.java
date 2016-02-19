package com.stefandekanski.tictactoe.runner;

import com.stefandekanski.tictactoe.game.Board;
import com.stefandekanski.tictactoe.game.Game;
import com.stefandekanski.tictactoe.game.Move;
import com.stefandekanski.tictactoe.game.Player;
import com.stefandekanski.tictactoe.util.Util;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleGameRunnerIO implements GameRunnerIO {
    private static final String EXIT_COMMAND = "exit";

    private final Scanner scanner;
    private final Pattern commandPattern;
    private final int N;

    public ConsoleGameRunnerIO(int N) {
        this.scanner = new Scanner(System.in);
        this.commandPattern = Pattern.compile(createPattern(N));
        this.N = N;
    }

    @Override
    public Move getPlayerMove(Player player,boolean silent) throws Game.GameTerminatedException {
        if(!silent) {
            System.out.println("Player " + player + " turn:");
        }
        return nextCorrectMove();
    }

    @Override
    public void currentGameBoard(Board gameBoard) {
        System.out.println(gameBoard.toString());
    }

    @Override
    public void gameDraw() {
        System.out.println("Game has no winner, It's a draw!");
    }

    @Override
    public void gameWinner(Player winner) {
        System.out.println("The Game winner is Player: " + winner);
    }

    @Override
    public void gameTerminated(String reason) {
        System.out.println("The Game was terminated : " + reason);
    }

    @Override
    public void illegalMoveTryAgain(String moveDetails) {
        System.out.println("The move was illegal : " + moveDetails + ", please try again");
    }

    private String createPattern(int N) {
        char low = (char) ('a' + N - 1);
        char up = (char) ('A' + N - 1);
        return "^[a-" + low + "A-" + up + "][1-" + N + "]$";
    }

    private Move nextCorrectMove() throws Game.GameTerminatedException {
        while (true) {
            String command = scanner.nextLine();
            checkForExitCommand(command);
            if (commandPattern.matcher(command).matches()) {
                char column = command.charAt(0);
                int row = Integer.parseInt(String.valueOf(command.charAt(1)));
                return new Move(Util.translateLetter(column), Util.invertRowNum(N, row));
            } else {
                System.out.println("Invalid command, valid format is columnLetterRowNumber (for example 'A1'). " +
                        "You can always exit app by typing exit");
            }
        }
    }

    private void checkForExitCommand(String command) throws Game.GameTerminatedException {
        if (EXIT_COMMAND.equals(command)) {
            throw new Game.GameTerminatedException("User requested exit");
        }
    }

}
