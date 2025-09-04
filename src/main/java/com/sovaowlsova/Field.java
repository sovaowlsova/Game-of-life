package com.sovaowlsova;

import java.util.Arrays;
import java.util.StringJoiner;

public class Field {
    private final char[][] field;
    private final int width;
    private final int height;

    public Field(char[][] field, int width, int height) {
        this.field = field;
        this.width = width;
        this.height = height;
    }

    public void tick() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int neighboursCount = getNeighbours(j, i);
                if (neighboursCount < 2 || neighboursCount > 3) {
                    field[i][j] = Game.DEAD_CELL;
                } else if (neighboursCount == 3) {
                    field[i][j] = Game.ALIVE_CELL;
                }
            }
        }
    }

    private int getNeighbours(int x, int y) {
        int sum = 0;

        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                int[] inBoundariesCoordinates = inBoundaries(x, y);
                int inBoundariesX = inBoundariesCoordinates[0];
                int inBoundariesY = inBoundariesCoordinates[1];
                if (field[inBoundariesY][inBoundariesX] == Game.ALIVE_CELL) {
                    sum++;
                }
            }
        }

        return sum;
    }

    private int[] inBoundaries(int x, int y) {
        int inBoundariesX, inBoundariesY;
        if (x < 0) {
            inBoundariesX = width - 1;
        } else if (x >= width) {
            inBoundariesX = 0;
        } else {
            inBoundariesX = x;
        }

        if (y < 0) {
            inBoundariesY = height - 1;
        } else if (y >= height) {
            inBoundariesY = 0;
        } else {
            inBoundariesY = y;
        }

        return new int[]{x, y};
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");
        Arrays.stream(field).forEach(s -> joiner.add(new String(s)));
        return joiner.toString();
    }
}
