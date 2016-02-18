package com.stefandekanski.tictactoe.field;

import com.stefandekanski.tictactoe.game.Player;

public class Field {

    public static final int ONE = 1;

    private Field horizontalParent;
    private Field verticalParent;
    private Field backslashParent;
    private Field otherDiagonalParent;

    private int horizontalSize = 1;
    private int verticalSize = 1;
    private int backslashSize = 1;
    private int otherDiagonalSize = 1;

    public final Player playerOwner;
    public final int x;
    public final int y;

    public Field(int x, int y, Player playerOwner) {
        this.x = x;
        this.y = y;
        this.playerOwner = playerOwner;
        horizontalParent = this;
        verticalParent = this;
        backslashParent = this;
        otherDiagonalParent = this;
    }

    public boolean isOwnerTheSame(Field other) {
        return playerOwner.equals(other.playerOwner);
    }

    public boolean tryUnion(Field other, Direction direction) {
        checkUnionPreconditions(other, direction);

        Field myDirectionParent = findDirectionParent(direction);
        Field otherDirectionParent = other.findDirectionParent(direction);
        if (myDirectionParent.equals(otherDirectionParent)) return false;

        // smaller one becomes child
        if (myDirectionParent.getDirectionSize(direction) < otherDirectionParent.getDirectionSize(direction)) {
            myDirectionParent.setDirectionParent(direction, otherDirectionParent);
            otherDirectionParent.addToDirectionSize(direction, myDirectionParent);
        } else {
            otherDirectionParent.setDirectionParent(direction, myDirectionParent);
            myDirectionParent.addToDirectionSize(direction, otherDirectionParent);
        }
        return true;
    }

    public boolean isGameOver(int minLinked) {
        for (Direction d : Direction.values()) {
            int dirParSize = getParentDirectionSize(d);
            if (dirParSize >= minLinked) {
                return true;
            }
        }
        return false;
    }

    boolean isHorizontalParent() {
        return equals(horizontalParent);
    }

    boolean isVerticalParent() {
        return equals(verticalParent);
    }

    boolean getBackslashParent() {
        return equals(backslashParent);
    }

    boolean isOtherDiagonalParent() {
        return equals(otherDiagonalParent);
    }

    boolean isFieldHorizontalAdjacent(Field other) {
        return isOwnerTheSame(other) && (y == other.y && ((x + ONE) == other.x || (x - ONE) == other.x));
    }

    boolean isFieldVerticalAdjacent(Field other) {
        return isOwnerTheSame(other) && (x == other.x && ((y + ONE) == other.y || (y - ONE) == other.y));
    }

    boolean isFieldBackSlashAdjacent(Field other) {
        return isOwnerTheSame(other) && (((x - ONE) == other.x && y - ONE == other.y) || ((x + ONE) == other.x && (y + ONE) == other.y));
    }

    boolean isFieldOtherDiagonalAdjacent(Field other) {
        return isOwnerTheSame(other) && ((x + ONE == other.x && y - ONE == other.y) || (((x - ONE) == other.x) && (y + ONE == other.y)));
    }

    int getParentDirectionSize(Direction direction) {
        return findDirectionParent(direction).getDirectionSize(direction);
    }

    Field findDirectionParent(Direction direction) {
        switch (direction) {
            case HORIZONTAL:
                return findHorizontalParent();
            case VERTICAL:
                return findVerticalParent();
            case BACKSLASH_DIAGONAL:
                return findBackslashParent();
            case OTHER_DIAGONAL:
                return findOtherDiagonalParent();
            default:
                throw new IllegalStateException("Missing case");
        }
    }

    private Field findHorizontalParent() {
        Field current = this;
        while (!current.isHorizontalParent()) {
            horizontalParent = horizontalParent.horizontalParent;
            current = horizontalParent;
        }
        return current;
    }

    private Field findVerticalParent() {
        Field current = this;
        while (!current.isVerticalParent()) {
            verticalParent = verticalParent.verticalParent;
            current = verticalParent;
        }
        return current;
    }

    private Field findBackslashParent() {
        Field current = this;
        while (!current.getBackslashParent()) {
            backslashParent = backslashParent.backslashParent;
            current = backslashParent;
        }
        return current;
    }

    private Field findOtherDiagonalParent() {
        Field current = this;
        while (!current.isOtherDiagonalParent()) {
            otherDiagonalParent = otherDiagonalParent.otherDiagonalParent;
            current = otherDiagonalParent;
        }
        return current;
    }

    private void checkUnionPreconditions(Field other, Direction direction) {
        if (!isOwnerTheSame(other)) {
            throw new IllegalStateException("Trying to union with different owner!");
        }
        switch (direction) {
            case HORIZONTAL:
                if (!isFieldHorizontalAdjacent(other))
                    throwNonAdjacentFieldException();
                break;
            case VERTICAL:
                if (!isFieldVerticalAdjacent(other))
                    throwNonAdjacentFieldException();
                break;
            case BACKSLASH_DIAGONAL:
                if (!isFieldBackSlashAdjacent(other))
                    throwNonAdjacentFieldException();
                break;
            case OTHER_DIAGONAL:
                if (!isFieldOtherDiagonalAdjacent(other))
                    throwNonAdjacentFieldException();
                break;
            default:
                throw new IllegalStateException("Missing case");
        }
    }

    private int getDirectionSize(Direction direction) {
        switch (direction) {
            case HORIZONTAL:
                return horizontalSize;
            case VERTICAL:
                return verticalSize;
            case BACKSLASH_DIAGONAL:
                return backslashSize;
            case OTHER_DIAGONAL:
                return otherDiagonalSize;
            default:
                throw new IllegalStateException("Missing case");
        }
    }

    private void addToDirectionSize(Direction direction, Field from) {
        switch (direction) {
            case HORIZONTAL:
                horizontalSize += from.getDirectionSize(direction);
                break;
            case VERTICAL:
                verticalSize += from.getDirectionSize(direction);
                break;
            case BACKSLASH_DIAGONAL:
                backslashSize += from.getDirectionSize(direction);
                break;
            case OTHER_DIAGONAL:
                otherDiagonalSize += from.getDirectionSize(direction);
                break;
            default:
                throw new IllegalStateException("Missing case");
        }
    }

    private void setDirectionParent(Direction direction, Field newParent) {
        if (newParent == null) {
            throw new IllegalStateException("New parent can't be null!");
        }
        switch (direction) {
            case HORIZONTAL:
                horizontalParent = newParent;
                break;
            case VERTICAL:
                verticalParent = newParent;
                break;
            case BACKSLASH_DIAGONAL:
                backslashParent = newParent;
                break;
            case OTHER_DIAGONAL:
                otherDiagonalParent = newParent;
                break;
            default:
                throw new IllegalStateException("Missing case");
        }
    }

    private void throwNonAdjacentFieldException() {
        throw new IllegalStateException("Trying union with nonAdjacent field!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (x != field.x) return false;
        return y == field.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
