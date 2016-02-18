package com.stefandekanski.tictactoe;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class FieldTest {
    private static final String OWNER_X = "X";
    private static final String OWNER_O = "O";
    Field field;

    @Before
    public void setUp() {
        field = new Field(0, 0, OWNER_X);
    }

    @Test
    public void isOwnerTheSame() {
        assertThat(field.isOwnerTheSame(new Field(12, 34, OWNER_O)), is(false));
        assertThat(field.isOwnerTheSame(new Field(1, 2, OWNER_X)), is(true));
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
        assertThat(field.isFieldHorizontalAdjacent(new Field(1, 0, OWNER_X)), is(true));
        assertThat(field.isFieldHorizontalAdjacent(new Field(-1, 0, OWNER_X)), is(true));
    }

    @Test
    public void isFieldVerticalAdjacent() {
        assertThat(field.isFieldVerticalAdjacent(new Field(0, 1, OWNER_X)), is(true));
        assertThat(field.isFieldVerticalAdjacent(new Field(0, -1, OWNER_X)), is(true));
    }

    @Test
    public void isFieldBackSlashAdjacent() {
        assertThat(field.isFieldBackSlashAdjacent(new Field(-1, 1, OWNER_X)), is(true));
        assertThat(field.isFieldBackSlashAdjacent(new Field(1, -1, OWNER_X)), is(true));
    }

    @Test
    public void isFieldOtherDiagonalAdjacent() {
        assertThat(field.isFieldOtherDiagonalAdjacent(new Field(-1, -1, OWNER_X)), is(true));
        assertThat(field.isFieldOtherDiagonalAdjacent(new Field(1, 1, OWNER_X)), is(true));
    }


    @Test
    public void tryHorizontalUnionTests() {
        Field leftAdjacent = new Field(-1, 0, OWNER_X);
        //caller.size == arg.size, arg.parent = caller.parent
        assertThat(field.tryUnion(leftAdjacent, Direction.HORIZONTAL), is(true));
        assertThat(field.isHorizontalParent(), is(true));
        assertThat(field.getParentDirectionSize(Direction.HORIZONTAL), is(2));
        assertThat(leftAdjacent.isHorizontalParent(), is(false));
        assertThat(leftAdjacent.getParentDirectionSize(Direction.HORIZONTAL), is(2));
        assertThat(leftAdjacent.findDirectionParent(Direction.HORIZONTAL), is(field));

        //caller.size > arg.size, arg.parent = caller.parent
        Field rightAdjacent = new Field(1, 0, OWNER_X);
        assertThat("caller.size > arg.size", field.getParentDirectionSize(Direction.HORIZONTAL) > rightAdjacent.getParentDirectionSize(Direction.HORIZONTAL));
        assertThat(field.findDirectionParent(Direction.HORIZONTAL), is(not(rightAdjacent.findDirectionParent(Direction.HORIZONTAL))));
        assertThat(field.tryUnion(rightAdjacent, Direction.HORIZONTAL), is(true));
        assertThat(field.findDirectionParent(Direction.HORIZONTAL), is(rightAdjacent.findDirectionParent(Direction.HORIZONTAL)));
        assertThat(field.getParentDirectionSize(Direction.HORIZONTAL), is(3));
        assertThat(field.getParentDirectionSize(Direction.HORIZONTAL), is(rightAdjacent.getParentDirectionSize(Direction.HORIZONTAL)));

        //caller.size < arg.size, caller.parent = arg.parent
        Field farLeft = new Field(-2, 0, OWNER_X);
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
        Field moreRightAdjacent = new Field(2, 0, OWNER_X);
        moreRightAdjacent.tryUnion(field, Direction.HORIZONTAL);
    }

    @Test(expected = IllegalStateException.class)
    public void tryHorizontalUnionNonSameOwner() {
        Field leftAdjacent = new Field(-1, 0, OWNER_O);
        field.tryUnion(leftAdjacent, Direction.HORIZONTAL);
    }

}
