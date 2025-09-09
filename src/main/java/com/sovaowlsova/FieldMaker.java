package com.sovaowlsova;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FieldMaker {
    private static final Map<String, String> PRESETS = Map.of(
            "Glider", "presets/glider.txt",
            "Acorn", "presets/acorn.txt"
    );
    private static final String PRESETS_STRING = mapKeysToString(PRESETS);

    public static Field getFieldManualInput(Scanner scanner) {
        System.out.printf("\nInput field size. Max size of a field is %d in each direction\n", Game.MAX_FIELD_SIZE);
        int width = InputGetter.getInt(scanner, "Width: ");
        int height = InputGetter.getInt(scanner, "Height: ");
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
        try (InputStream file = new FileInputStream(path)) {
            Field field = parseFieldFile(file);
            return field == null ? getFieldFromFile(scanner) : field;
        } catch (IOException e) {
            System.out.println("File not found");
            return getFieldFromFile(scanner);
        }
    }

    public static Field getRandomField(Scanner scanner) {
        int width = InputGetter.getInt(scanner, "Width: ");
        int height = InputGetter.getInt(scanner, "Height: ");
        if (width <= 0 || height <= 0 || width > Game.MAX_SIZE || height > Game.MAX_SIZE) {
            System.out.println("\nInvalid boundaries");
            return getRandomField(scanner);
        }
        char[][] fieldArray = new char[height][width];
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (random.nextInt(4) == 1) {
                    fieldArray[i][j] = Game.ALIVE_CELL;
                } else {
                    fieldArray[i][j] = Game.DEAD_CELL;
                }
            }
        }
        return new Field(fieldArray, width, height);
    }

    public static Field getPreset(Scanner scanner) {
        System.out.println("Choose a preset. Case insensitive");
        System.out.println(PRESETS_STRING);
        System.out.print("preset name: ");
        String name = scanner.nextLine();
        String path = getPresetByName(name);
        if (path.isEmpty()) {
            System.out.println("Invalid preset name. Try again");
            return getPreset(scanner);
        }
        InputStream preset = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        Field field = parseFieldFile(preset);
        return field == null ? getPreset(scanner) : field;
    }

    private static Field parseFieldFile(InputStream input) {
        try (Scanner fileScanner = new Scanner(input)) {
            int height = fileScanner.nextInt();
            int width = fileScanner.nextInt();
            fileScanner.nextLine();
            char[][] fieldArray = new char[height][width];
            for (int i = 0; i < height; i ++) {
                char[] resLine = new char[width];
                Arrays.fill(resLine, Game.DEAD_CELL);
                String line = "";
                if (fileScanner.hasNextLine()) {
                    line = fileScanner.nextLine();
                }
                for (int j = 0; j < width; j++) {
                    if (j >= line.length()) {
                        break;
                    }
                    if (line.charAt(j) == Game.ALIVE_CELL) {
                        resLine[j] = Game.ALIVE_CELL;
                    }
                }
                fieldArray[i] = resLine;
            }
            return new Field(fieldArray, width, height);
        } catch (NoSuchElementException e) {
            System.out.println("File is not properly formatted");
            return null;
        }
    }

    private static String getPresetByName(String name) {
        String nameLowercase = name.toLowerCase();
        for (var entry : PRESETS.entrySet()) {
            if (nameLowercase.equals(entry.getKey().toLowerCase())) {
                return entry.getValue();
            }
        }
        return "";
    }

    private static <K, V> String mapKeysToString(Map<K, V> map) {
        StringJoiner stringJoiner = new StringJoiner("\n");
        map.forEach((key, value) -> stringJoiner.add(String.format("- \"%s\"", key.toString())));
        return stringJoiner.toString();
    }
}
