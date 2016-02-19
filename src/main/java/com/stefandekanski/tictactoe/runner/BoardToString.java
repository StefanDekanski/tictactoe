package com.stefandekanski.tictactoe.runner;

public class BoardToString {

    private final StringBuilder sb;
    private final int rowLength;

    public BoardToString(int N) {
        rowLength = N;
        int dimen = (4 + 2 * N - 1);
        int charsNeeded = dimen * dimen + dimen;
        sb = new StringBuilder(charsNeeded);
    }

    public void writeTopRow() {
        sb.append(" #");
        for (int i = 0; i < rowLength; i++) {
            sb.append((char) ('A' + i)).append("#");
        }
        sb.append(" \n");
    }

    public void writeBorderRow() {
        sb.append("#||");
        for (int i = 0; i < rowLength; i++) {
            sb.append("==").append("||");
        }
        sb.append("#\n");
    }

    public String toString() {
        return sb.toString();
    }


}
