package com.sovaowlsova;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputGetter {
    public static int getInt(Scanner scanner, String message) {
        System.out.printf("\n%s", message);
        int res;
        try {
            res = scanner.nextInt();
        } catch (NoSuchElementException e) {
            System.out.print("Int required. Try again");
            scanner.nextLine();
            return getInt(scanner, message);
        }
        return res;
    }

    public static int getIntInRange(Scanner scanner, String message, int min, int max) {
        System.out.printf("\n%s", message);
        int res;
        try {
            res = scanner.nextInt();
            if (res < min || res > max) {
                System.out.print("Invalid option. Try again");
                return getIntInRange(scanner, message, min, max);
            }
        } catch (NoSuchElementException e) {
            System.out.print("Int required. Try again");
            scanner.nextLine();
            return getIntInRange(scanner, message, min, max);
        }
        return res;
    }
}
