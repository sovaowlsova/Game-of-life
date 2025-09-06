package com.sovaowlsova;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputGetters {
    public static int getInt(Scanner scanner, String message) {
        System.out.printf("\n%s", message);
        int res;
        try {
            res = scanner.nextInt();
        } catch (NoSuchElementException e) {
            System.out.print("\nInt required. Try again");
            scanner.nextLine();
            return getInt(scanner, message);
        }
        return res;
    }

    public static Field getFieldManualInput(Scanner scanner) {
        System.out.printf("\nInput field size. Max size of a field is %d in each direction\n", Game.MAX_FIELD_SIZE);
        int width = getInt(scanner, "Width: ");
        int height = getInt(scanner, "Height: ");
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

    public static Field getFieldFromFile(Scanner scanner) {
        System.out.print("Path: ");
        String path = scanner.next();
        try (FileReader fileReader = new FileReader(path)) {
            int i;
            while ((i = fileReader.read()) != -1) {
                System.out.print((char) i);
            }
            fileReader.close();
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Try again");
            return getFieldFromFile(scanner);
        } catch (IOException e) {
            System.out.println("Something went wrong. File might be damaged. Try again");
            return getFieldFromFile(scanner);
        }
    }
}
