package org.backend.config;

import org.backend.models.FanGroup;
import org.backend.models.SeatCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles fan group configuration from user input.
 */
public class GroupConfigurer {

    private static final String LINE = "--------------------------------------------------------";

    /**
     * Configures fan groups through user input.
     *
     * @return List of configured fan groups
     */
    public static List<FanGroup> configureFromUserInput(Scanner scanner) {
        System.out.print("How many fan groups will arrive? ");
        int groupCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<FanGroup> groups = new ArrayList<>();

        System.out.println("\n>>> Configuring groups...\n");

        for (int i = 1; i <= groupCount; i++) {
            FanGroup group = configureSingleGroup(scanner, i);
            groups.add(group);
        }

        printGroupSummary(groups, scanner);

        return groups;
    }

    /**
     * Configures a single fan group.
     */
    private static FanGroup configureSingleGroup(Scanner scanner, int groupId) {
        System.out.println("Group #" + groupId + ":");

        int fanCount = readPositiveInteger(scanner, "  How many fans? ");
        scanner.nextLine();

        SeatCategory category = null;
        while (category == null) {
            System.out.print("  Category (VIP/Courtside/Lower/Upper): ");
            String categoryInput = scanner.nextLine();

            category = SeatCategory.fromString(categoryInput);

            if (category == null) {
                System.out.println("  Invalid category! Try again.");
            }
        }

        System.out.println();

        return new FanGroup(groupId, fanCount, category);
    }

    /**
     * Prints summary of configured groups and waits for user confirmation.
     */
    private static void printGroupSummary(List<FanGroup> groups, Scanner scanner) {
        System.out.println(">>> All groups configured!\n");
        System.out.println("SUMMARY OF GROUPS:");
        System.out.println(LINE);

        for (FanGroup group : groups) {
            System.out.println(group);
        }

        System.out.println(LINE);
        System.out.println("\nPress ENTER to start processing...");
        scanner.nextLine();
    }

    private static int readPositiveInteger(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();

                if (value <= 0) {
                    System.out.println("  Error: Value must be greater than 0! Try again.");
                    continue;
                }

                return value;

            } catch (java.util.InputMismatchException e) {
                System.out.println("  Error: Please enter a valid number!");
                scanner.nextLine();
            }
        }
    }
}