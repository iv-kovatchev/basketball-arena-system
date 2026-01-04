package org.backend.util;

import org.backend.models.SeatCategory;

import java.util.Map;

public class ConsoleFormatter {
    private static final String SEPARATOR = "========================================================";
    private static final String LINE = "--------------------------------------------------------";

    /**
     * Prints application header.
     */
    public static void printHeader() {
        System.out.println(SEPARATOR);
        System.out.println("        BASKETBALL ARENA - SEATING SYSTEM");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    /**
     * Prints arena capacity.
     */
    public static void printArenaCapacity(Map<SeatCategory, Integer> capacity) {
        System.out.println("ARENA CAPACITY:");
        System.out.println(LINE);

        int total = 0;
        for (Map.Entry<SeatCategory, Integer> entry : capacity.entrySet()) {
            System.out.printf("%-12s : %d seats%n",
                    entry.getKey(), entry.getValue());
            total += entry.getValue();
        }

        System.out.printf("%-12s : %d seats%n", "TOTAL", total);
        System.out.println(LINE);
        System.out.println();
    }

    /**
     * Prints section separator.
     */
    public static void printSection(String message) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("    " + message);
        System.out.println(SEPARATOR);
        System.out.println();
    }

    /**
     * Prints final report with statistics.
     */
    public static void printFinalReport(Map<SeatCategory, Integer> currentState,
                                        Map<SeatCategory, Integer> totalCapacity,
                                        int successCount,
                                        int rejectedCount) {

        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("                  FINAL REPORT");
        System.out.println(SEPARATOR);

        // Header
        System.out.printf("%-12s | %-8s | %-8s | %-8s | %-8s%n",
                "Category", "Capacity", "Occupied", "Available", "Fill %");
        System.out.println(LINE);

        int totalCapacitySum = 0;
        int totalOccupied = 0;

        // Each category
        for (SeatCategory category : SeatCategory.values()) {
            int capacity = totalCapacity.getOrDefault(category, 0);
            int available = currentState.getOrDefault(category, 0);
            int occupied = capacity - available;
            double fillPercent = capacity > 0 ? (occupied * 100.0 / capacity) : 0;

            System.out.printf("%-12s | %8d | %8d | %8d | %7.1f%%%n",
                    category, capacity, occupied, available, fillPercent);

            totalCapacitySum += capacity;
            totalOccupied += occupied;
        }

        System.out.println(LINE);

        // Total
        double totalFillPercent = totalCapacitySum > 0
                ? (totalOccupied * 100.0 / totalCapacitySum) : 0;

        System.out.printf("%-12s | %8d | %8d | %8d | %7.1f%%%n",
                "TOTAL", totalCapacitySum, totalOccupied,
                totalCapacitySum - totalOccupied, totalFillPercent);

        System.out.println(SEPARATOR);
        System.out.println();

        // Statistics
        System.out.printf("Successfully seated groups: %d%n", successCount);
        System.out.printf("Rejected groups: %d%n", rejectedCount);
        System.out.printf("Total groups processed: %d%n", successCount + rejectedCount);

        System.out.println(SEPARATOR);
    }
}
