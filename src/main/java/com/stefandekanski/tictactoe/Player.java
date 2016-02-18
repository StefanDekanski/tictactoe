package com.stefandekanski.tictactoe;

public class Player {

    private final String name;

    public Player(String letter) {
        if (letter == null || letter.length() != 1) {
            throw new IllegalStateException("Only single letter name allowed!");
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
