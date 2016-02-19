package com.stefandekanski.tictactoe.runner;

import com.stefandekanski.tictactoe.game.Board;
import com.stefandekanski.tictactoe.game.Game;
import com.stefandekanski.tictactoe.game.Player;
import com.stefandekanski.tictactoe.move.Move;
import com.stefandekanski.tictactoe.move.MoveTranslator;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleGameRunnerIO implements GameRunnerIO {
    private static final String EXIT_COMMAND = "exit";

    private final Scanner scanner;
    private final Pattern commandPattern;
    private final MoveTranslator moveTranslator;

    private final int rowLength;

    public ConsoleGameRunnerIO(int N) {
        scanner = new Scanner(System.in);

        char low = (char) ('a' + N - 1);
        char up = (char) ('A' + N - 1);
        String pattern = "^[a-" + low + "A-" + up + "][1-" + N + "]$";

        commandPattern = Pattern.compile(pattern);
        moveTranslator = new MoveTranslator(N);
        rowLength = N;
    }

    @Override
    public Move getPlayerMove(Player player) throws Game.GameTerminatedException {
        System.out.println("Player " + player.getName() + " turn:");
        return nextCorrectMove();
    }

    private Move nextCorrectMove() throws Game.GameTerminatedException {
        while (true) {
            String command = scanner.nextLine();
            checkForExitCommand(command);
            if (commandPattern.matcher(command).matches()) {
                char column = command.charAt(0);
                int row = Integer.parseInt(String.valueOf(command.charAt(1)));
                return moveTranslator.translateToMove(column, row);
            } else {
                System.out.println("Invalid command, valid format is columnLetterRowNumber (for example 'A1'). You can always exit app by typing exit");
            }
        }
    }

    private void checkForExitCommand(String command) throws Game.GameTerminatedException {
        if (EXIT_COMMAND.equals(command)) {
            throw new Game.GameTerminatedException("User requested exit");
        }
    }

    @Override
    public void currentGameBoard(Board gameBoard) {
        List<Player> fieldOwners = gameBoard.getFieldOwnerList();

    }

    @Override
    public void gameDraw() {
        System.out.println("Game has no winner, It's a draw!");
    }

    @Override
    public void gameWinner(Player winner) {
        System.out.println("The Game winner is Player: " + winner.getName());
    }

    @Override
    public void gameTerminated(String reason) {
        System.out.println("The Game was terminated : " + reason);
    }

    @Override
    public void illegalMoveTryAgain(String moveDetails) {
        System.out.println("The move was illegal : " + moveDetails + ", please try again");
    }

}
