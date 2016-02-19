package com.stefandekanski.tictactoe.util;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UtilTest {

    int N = 8;

    @Test
    public void translateLetterToNumber() {
        char ch = 'A';
        for (int i = 0; i < N; i++) {
            assertThat(Util.translateLetter((char) (ch + i)), is(i + 1));
        }
    }

    @Test
    public void invertRowNum() {
        for (int i = 1; i <= N; i++) {
            assertThat(Util.invertRowNum(N, i), is(9 - i));
        }
    }
}
