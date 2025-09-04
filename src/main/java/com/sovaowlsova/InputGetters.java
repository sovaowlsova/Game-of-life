package com.sovaowlsova;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputGetters {
    public static int getInt(Scanner scanner, String message) {
        System.out.print(message);
        int res;
        try {
            res = scanner.nextInt();
        } catch (NoSuchElementException e) {
            System.out.print("Int required. Try again\n");
            scanner.nextLine();
            return getInt(scanner, message);
        }
        return res;
    }

    public static Field getFieldManualInput(Scanner scanner) {
        System.out.printf("\nInput field size. Max size of a field is %d in each direction\n", Game.MAX_FIELD_SIZE);
        int width = getInt(scanner, "Input field width: ");
        int height = getInt(scanner, "Input field height: ");
        if (width <= 0 || height <= 0 || width > 1000 || height > 1000) {
            System.out.println("\nInvalid boundaries");
            return getFieldManualInput(scanner);
        }
        char[][] field = new char[height][width];
        System.out.printf("""
                \nInput field line by line. %c is an alive cell.
                You can use any other symbols as dead cells.
                Please note that:
                1. Characters outside of declared field width will be ignored
                2. If a line is too small it will be appended by dead cells
                """, Game.ALIVE_CELL);
        scanner.nextLine();
        for (int i = 0; i < height; i++) {
            String next = scanner.nextLine();
            for (int j = 0; j < width; j++) {
                char c;
                if (j >= next.length()) {
                    c = Game.DEAD_CELL;
                } else {
                    c = next.charAt(j) == Game.ALIVE_CELL ? Game.ALIVE_CELL : Game.DEAD_CELL;
                }
                field[i][j] = c;
            }
        }
        return new Field(field, width, height);
    }
}
