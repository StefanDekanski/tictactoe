package com.stefandekanski.tictactoe;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class FieldTest {
    private static final String OWNER_X = "X";
    private static final String OWNER_Y = "Y";
    Field field;

    @Before
    public void setUp() {
        field = new Field(OWNER_X, 0, 0);
    }

    @Test
    public void isOwnerTheSame() {
        assertThat(field.isOwnerTheSame(new Field(OWNER_Y, 12, 34)), is(false));
        assertThat(field.isOwnerTheSame(new Field(OWNER_X, 1, 2)), is(true));
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
        assertThat(field.isFieldHorizontalAdjacent(new Field(OWNER_X, 1, 0)), is(true));
        assertThat(field.isFieldHorizontalAdjacent(new Field(OWNER_X, -1, 0)), is(true));
    }

    @Test
    public void isFieldVerticalAdjacent() {
        assertThat(field.isFieldVerticalAdjacent(new Field(OWNER_X, 0, 1)), is(true));
        assertThat(field.isFieldVerticalAdjacent(new Field(OWNER_X, 0, -1)), is(true));
    }

    @Test
    public void isFieldBackSlashAdjacent() {
        assertThat(field.isFieldBackSlashAdjacent(new Field(OWNER_X, -1, 1)), is(true));
        assertThat(field.isFieldBackSlashAdjacent(new Field(OWNER_X, 1, -1)), is(true));
    }

    @Test
    public void isFieldOtherDiagonalAdjacent() {
        assertThat(field.isFieldOtherDiagonalAdjacent(new Field(OWNER_X, -1, -1)), is(true));
        assertThat(field.isFieldOtherDiagonalAdjacent(new Field(OWNER_X, 1, 1)), is(true));
    }


    @Test
    public void tryHorizontalUnionTests() {
        Field leftAdjacent = new Field(OWNER_X, -1, 0);
        //caller.size == arg.size, arg.parent = caller.parent
        assertThat(field.tryUnion(leftAdjacent, Field.Direction.HORIZONTAL), is(true));
        assertThat(field.isHorizontalParent(), is(true));
        assertThat(field.getParentDirectionSize(Field.Direction.HORIZONTAL), is(2));
        assertThat(leftAdjacent.isHorizontalParent(), is(false));
        assertThat(leftAdjacent.getParentDirectionSize(Field.Direction.HORIZONTAL), is(2));
        assertThat(leftAdjacent.findDirectionParent(Field.Direction.HORIZONTAL), is(field));

        //caller.size > arg.size, arg.parent = caller.parent
        Field rightAdjacent = new Field(OWNER_X, 1, 0);
        assertThat("caller.size > arg.size", field.getParentDirectionSize(Field.Direction.HORIZONTAL) > rightAdjacent.getParentDirectionSize(Field.Direction.HORIZONTAL));
        assertThat(field.findDirectionParent(Field.Direction.HORIZONTAL), is(not(rightAdjacent.findDirectionParent(Field.Direction.HORIZONTAL))));
        assertThat(field.tryUnion(rightAdjacent, Field.Direction.HORIZONTAL), is(true));
        assertThat(field.findDirectionParent(Field.Direction.HORIZONTAL), is(rightAdjacent.findDirectionParent(Field.Direction.HORIZONTAL)));
        assertThat(field.getParentDirectionSize(Field.Direction.HORIZONTAL), is(3));
        assertThat(field.getParentDirectionSize(Field.Direction.HORIZONTAL), is(rightAdjacent.getParentDirectionSize(Field.Direction.HORIZONTAL)));

        //caller.size < arg.size, caller.parent = arg.parent
        Field farLeft = new Field(OWNER_X, -2, 0);
        assertThat("caller.size < arg.size", farLeft.getParentDirectionSize(Field.Direction.HORIZONTAL) < leftAdjacent.getParentDirectionSize(Field.Direction.HORIZONTAL));
        assertThat(farLeft.findDirectionParent(Field.Direction.HORIZONTAL), is(not(leftAdjacent.findDirectionParent(Field.Direction.HORIZONTAL))));
        assertThat(farLeft.tryUnion(leftAdjacent, Field.Direction.HORIZONTAL), is(true));
        assertThat(farLeft.findDirectionParent(Field.Direction.HORIZONTAL), is(leftAdjacent.findDirectionParent(Field.Direction.HORIZONTAL)));
        assertThat(farLeft.getParentDirectionSize(Field.Direction.HORIZONTAL), is(4));
        assertThat(farLeft.getParentDirectionSize(Field.Direction.HORIZONTAL), is(leftAdjacent.getParentDirectionSize(Field.Direction.HORIZONTAL)));

        //the field remains the horizontal father
        assertThat(field.isHorizontalParent(), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void tryHorizontalUnionNonAdjacentField() {
        Field moreRightAdjacent = new Field(OWNER_X, 2, 0);
        moreRightAdjacent.tryUnion(field, Field.Direction.HORIZONTAL);
    }

    @Test(expected = IllegalStateException.class)
    public void tryHorizontalUnionNonSameOwner() {
        Field leftAdjacent = new Field(OWNER_Y, -1, 0);
        field.tryUnion(leftAdjacent, Field.Direction.HORIZONTAL);
    }

}
