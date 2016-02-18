package com.stefandekanski.tictactoe.field;

public class AdjacentField {
    public final Field adjacent;
    public final Direction direction;

    public AdjacentField(Field adjacent, Direction direction) {
        if (adjacent == null || direction == null) {
            throw new IllegalStateException("Adjacent or direction can't be null");
        }
        this.adjacent = adjacent;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdjacentField that = (AdjacentField) o;

        if (!adjacent.equals(that.adjacent)) return false;
        return direction == that.direction;

    }

    @Override
    public int hashCode() {
        int result = adjacent.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }
}
