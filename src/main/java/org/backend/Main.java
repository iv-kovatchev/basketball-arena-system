package org.backend;

import org.backend.models.Arena;
import org.backend.models.FanGroup;
import org.backend.models.SeatCategory;

import java.util.EnumMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Тест 1: Builder pattern
        Arena arena = new Arena.Builder()
                .withVIP(100)
                .withCourtside(80)
                .withLower(200)
                .withUpper(300)
                .build();

        System.out.println(arena);
        // Arena Capacity:
        //   VIP        : 100 seats
        //   Courtside  : 80 seats
        //   Lower      : 200 seats
        //   Upper      : 300 seats
        //   TOTAL      : 680 seats

        // Тест 2: Getters
        System.out.println("\nVIP capacity: " + arena.getCapacity(SeatCategory.VIP)); // 100
        System.out.println("Total: " + arena.getTotalCapacity()); // 680

        // Тест 3: Has category
        System.out.println("\nHas VIP? " + arena.hasCategory(SeatCategory.VIP)); // true

        // Тест 4: Get capacity map (immutable)
        Map<SeatCategory, Integer> capacityMap = arena.getCapacityMap();
        try {
            capacityMap.put(SeatCategory.VIP, 999); // Ще хвърли UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("\n✓ Map is immutable - cannot modify!"); // ✓
        }

        // Тест 5: Invalid capacity
        try {
            Map<SeatCategory, Integer> invalid = new EnumMap<>(SeatCategory.class);
            invalid.put(SeatCategory.VIP, -50);
            Arena badArena = new Arena(invalid);
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + e.getMessage());
        }
    }
}