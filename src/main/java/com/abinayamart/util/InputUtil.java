package com.abinayamart.util;

import java.util.Scanner;

/** Small Scanner helpers with validation (no crashes on bad input). */
public final class InputUtil {
    private InputUtil() {
    }

    public static String readLine(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            int v = readInt(sc, prompt);
            if (v >= min && v <= max) {
                return v;
            }
            System.out.println("Enter a value between " + min + " and " + max + ".");
        }
    }

    public static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
