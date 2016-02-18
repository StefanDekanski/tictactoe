package com.stefandekanski.tictactoe.game;

public class Player {
    public static class IllegalNameException extends Exception {
        public IllegalNameException(String msg) {
            super(msg);
        }
    }

    private final String name;

    public Player(String letter) throws IllegalNameException {
        if (letter == null || letter.length() != 1) {
            throw new IllegalNameException("Only single letter name allowed!");
        }
        name = letter.toUpperCase();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
