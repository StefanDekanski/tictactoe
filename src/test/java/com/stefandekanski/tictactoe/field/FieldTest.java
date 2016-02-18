package com.stefandekanski.tictactoe.field;

import com.stefandekanski.tictactoe.game.Player;
import org.junit.Before;
import org.junit.Test;

import static com.stefandekanski.tictactoe.field.Field.ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class FieldTest {

    Player PLAYER_ONE;
    Player PLAYER_TWO;

    Field field;

    @Before
    public void setUp() throws Player.IllegalNameException {
        PLAYER_ONE = new Player("X");
        PLAYER_TWO = new Player("O");
        field = new Field(3, 3, PLAYER_ONE);
    }

    @Test
    public void isOwnerTheSame() {
        assertThat(field.isOwnerTheSame(new Field(12, 34, PLAYER_TWO)), is(false));
        assertThat(field.isOwnerTheSame(new Field(1, 2, PLAYER_ONE)), is(true));
    }

    @Test
    public void isParentInAllDirections() {
        assertThat(field.isHorizontalParent(), is(true));
        assertThat(field.isVerticalParent(), is(true));
        assertThat(field.getBackslashParent(), is(true));
        assertThat(field.isOtherDiagonalParent(), is(true));
    }

    @Test
    public void isFieldHorizontalAdjacent() {
        assertThat(field.isFieldHorizontalAdjacent(new Field(field.x + ONE, field.y, field.playerOwner)), is(true));
        assertThat(field.isFieldHorizontalAdjacent(new Field(field.x - ONE, field.y, field.playerOwner)), is(true));
    }

    @Test
    public void isFieldVerticalAdjacent() {
        assertThat(field.isFieldVerticalAdjacent(new Field(field.x, field.y + ONE, field.playerOwner)), is(true));
        assertThat(field.isFieldVerticalAdjacent(new Field(field.x, field.y - ONE, field.playerOwner)), is(true));
    }

    @Test
    public void isFieldBackSlashAdjacent() {
        assertThat(field.isFieldBackSlashAdjacent(new Field(field.x - ONE, field.y - ONE, field.playerOwner)), is(true));
        assertThat(field.isFieldBackSlashAdjacent(new Field(field.x + ONE, field.y + ONE, field.playerOwner)), is(true));
    }

    @Test
    public void isFieldOtherDiagonalAdjacent() {
        assertThat(field.isFieldOtherDiagonalAdjacent(new Field(field.x - ONE, field.y + ONE, field.playerOwner)), is(true));
        assertThat(field.isFieldOtherDiagonalAdjacent(new Field(field.x + ONE, field.y - ONE, field.playerOwner)), is(true));
    }


    @Test
    public void tryHorizontalUnionTests() {
        Field leftAdjacent = new Field(field.x - 1, field.y, field.playerOwner);
        //caller.size == arg.size, arg.parent = caller.parent
        assertThat(field.tryUnion(leftAdjacent, Direction.HORIZONTAL), is(true));
        assertThat(field.isHorizontalParent(), is(true));
        assertThat(field.getParentDirectionSize(Direction.HORIZONTAL), is(2));
        assertThat(leftAdjacent.isHorizontalParent(), is(false));
        assertThat(leftAdjacent.getParentDirectionSize(Direction.HORIZONTAL), is(2));
        assertThat(leftAdjacent.findDirectionParent(Direction.HORIZONTAL), is(field));

        //caller.size > arg.size, arg.parent = caller.parent
        Field rightAdjacent = new Field(field.x + 1, field.y, field.playerOwner);
        assertThat("caller.size > arg.size", field.getParentDirectionSize(Direction.HORIZONTAL) > rightAdjacent.getParentDirectionSize(Direction.HORIZONTAL));
        assertThat(field.findDirectionParent(Direction.HORIZONTAL), is(not(rightAdjacent.findDirectionParent(Direction.HORIZONTAL))));
        assertThat(field.tryUnion(rightAdjacent, Direction.HORIZONTAL), is(true));
        assertThat(field.findDirectionParent(Direction.HORIZONTAL), is(rightAdjacent.findDirectionParent(Direction.HORIZONTAL)));
        assertThat(field.getParentDirectionSize(Direction.HORIZONTAL), is(3));
        assertThat(field.getParentDirectionSize(Direction.HORIZONTAL), is(rightAdjacent.getParentDirectionSize(Direction.HORIZONTAL)));

        //caller.size < arg.size, caller.parent = arg.parent
        Field farLeft = new Field(leftAdjacent.x - 1, leftAdjacent.y, leftAdjacent.playerOwner);
        assertThat("caller.size < arg.size", farLeft.getParentDirectionSize(Direction.HORIZONTAL) < leftAdjacent.getParentDirectionSize(Direction.HORIZONTAL));
        assertThat(farLeft.findDirectionParent(Direction.HORIZONTAL), is(not(leftAdjacent.findDirectionParent(Direction.HORIZONTAL))));
        assertThat(farLeft.tryUnion(leftAdjacent, Direction.HORIZONTAL), is(true));
        assertThat(farLeft.findDirectionParent(Direction.HORIZONTAL), is(leftAdjacent.findDirectionParent(Direction.HORIZONTAL)));
        assertThat(farLeft.getParentDirectionSize(Direction.HORIZONTAL), is(4));
        assertThat(farLeft.getParentDirectionSize(Direction.HORIZONTAL), is(leftAdjacent.getParentDirectionSize(Direction.HORIZONTAL)));

        //the field remains the horizontal father
        assertThat(field.isHorizontalParent(), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void tryHorizontalUnionNonAdjacentField() {
        Field moreRightAdjacent = new Field(field.x - 2, field.y, field.playerOwner);
        moreRightAdjacent.tryUnion(field, Direction.HORIZONTAL);
    }

    @Test(expected = IllegalStateException.class)
    public void tryHorizontalUnionNonSameOwner() {
        Field leftAdjacent = new Field(field.x - 1, field.y, PLAYER_TWO);
        field.tryUnion(leftAdjacent, Direction.HORIZONTAL);
    }

}
