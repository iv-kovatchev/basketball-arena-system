package org.backend.config;

import org.backend.models.Arena;

import java.util.Scanner;

public class ArenaConfigurer {
    public static Arena configureUserInput(Scanner scanner) {
        System.out.println("Configure arena capacity:");

        int vip = readPositiveInteger(scanner, "Enter VIP seats: ");
        int courtside = readPositiveInteger(scanner, "Enter Courtside seats: ");
        int lower = readPositiveInteger(scanner, "Enter Lower seats: ");
        int upper = readPositiveInteger(scanner, "Enter Upper seats: ");

        scanner.nextLine();

        System.out.println();

        return new Arena.Builder()
                .withVIP(vip)
                .withCourtside(courtside)
                .withLower(lower)
                .withUpper(upper)
                .build();
    }

    private static int readPositiveInteger(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();

                if (value < 0) {
                    System.out.println("  Error: Value cannot be negative! Try again.");
                    continue;
                }

                if (value == 0) {
                    System.out.println("  Warning: Setting capacity to 0 (no seats available).");
                }

                return value;

            } catch (java.util.InputMismatchException e) {
                System.out.println("  Error: Please enter a valid number!");
                scanner.nextLine();
            }
        }
    }
}
