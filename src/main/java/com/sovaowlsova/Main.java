package com.sovaowlsova;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                You've launched game of life. Select an option:
                
                0 — manually input field
                1 — launch a preset
                2 — input field from a file
                3 — make a field in UI
                4 — generate random field
                
                You can press arrow key up and arrow key down
                to change the speed of the simulation""");
        int option = InputGetter.getIntInRange(scanner, "Option: ", 0, 4);
        Field field = null;

        if (option == 0) {
            field = FieldMaker.getFieldManualInput(scanner);
        } else if (option == 1) {
            field = FieldMaker.getPreset(scanner);
        } else if (option == 2) {
            field = FieldMaker.getFieldFromFile(scanner);
        } else if (option == 3) {
            // TODO: implement
        } else if (option == 4) {
            field = FieldMaker.getRandomField(scanner);
        }
        System.out.println(field);
        Game.run(field);
        scanner.close();
    }
}