package com.stefandekanski.tictactoe.move;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MoveTranslatorTest {

    MoveTranslator moveTranslator;

    @Before
    public void createMoveTranslator() {
        moveTranslator = new MoveTranslator(8);
    }

    @Test
    public void translateLetterToNumber() {
        char ch = 'A';
        for (int i = 0; i < 8; i++) {
            assertThat(moveTranslator.translateLetter((char) (ch + i)), is(i + 1));
        }
    }

    @Test
    public void invertRowNum() {
        for (int i = 1; i <= 8; i++) {
            assertThat(moveTranslator.invertRowNum(i), is(9 - i));
        }
    }

    @Test
    public void translateMove() {
        Move expected = new Move(3, 2);
        Move translated = moveTranslator.translateToMove('c', 7);
        assertThat(translated, is(expected));

        expected = new Move(8, 8);
        translated = moveTranslator.translateToMove('H', 1);
        assertThat(translated, is(expected));
    }
}
