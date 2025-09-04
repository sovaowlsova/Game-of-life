package com.sovaowlsova;

import java.util.Scanner;

public class Game {
    public static final int MAX_FIELD_SIZE = 1000;
    public static final Character ALIVE_CELL = 'o';
    public static final Character DEAD_CELL = '!';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                You've launched game of life. Select an option:
                
                0 — manually input field
                1 — input field from a file
                2 — make a field in UI
                3 — generate random field
                
                You can press arrow key up and arrow key down
                to change the speed of the simulation""");
        int option = InputGetters.getInt(scanner, "Select an option: ");
        if (option == 0) {
            Field field = InputGetters.getFieldManualInput(scanner);
            System.out.println(field);
        }
        scanner.close();
    }
}