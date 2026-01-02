package org.backend.models;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Represents a basketball arena with different seat categories and their capacities.
 * Immutable value object that holds the initial configuration of the arena.
 * The actual seat availability is managed by the repository layer.
 */
public final class Arena {

    private final Map<SeatCategory, Integer> capacity;
    private final int totalCapacity;

    /**
     * Constructor for Arena.
     */
    public Arena(Map<SeatCategory, Integer> capacity) {
        if (capacity == null) {
            throw new IllegalArgumentException("Capacity map cannot be null");
        }

        // Validate all capacities are positive
        for (Map.Entry<SeatCategory, Integer> entry : capacity.entrySet()) {
            if (entry.getValue() == null || entry.getValue() < 0) {
                throw new IllegalArgumentException(
                        String.format("Invalid capacity for %s: %s",
                                entry.getKey(), entry.getValue())
                );
            }
        }

        // Create immutable copy using EnumMap for better performance
        this.capacity = Collections.unmodifiableMap(new EnumMap<>(capacity));

        // Calculate total capacity
        this.totalCapacity = capacity.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    /**
     * Builder pattern for easier Arena creation.
     */
    public static class Builder {
        private final Map<SeatCategory, Integer> capacity = new EnumMap<>(SeatCategory.class);

        public Builder withVIP(int seats) {
            capacity.put(SeatCategory.VIP, seats);
            return this;
        }

        public Builder withCourtside(int seats) {
            capacity.put(SeatCategory.COURTSIDE, seats);
            return this;
        }

        public Builder withLower(int seats) {
            capacity.put(SeatCategory.LOWER, seats);
            return this;
        }

        public Builder withUpper(int seats) {
            capacity.put(SeatCategory.UPPER, seats);
            return this;
        }

        public Arena build() {
            return new Arena(capacity);
        }
    }

    /**
     * Gets the capacity for a specific seat category.
     */
    public int getCapacity(SeatCategory category) {
        return capacity.getOrDefault(category, 0);
    }

    /**
     * Gets the full capacity map (unmodifiable).
     */
    public Map<SeatCategory, Integer> getCapacityMap() {
        return capacity;
    }

    /**
     * Gets the total capacity of the arena across all categories.
     */
    public int getTotalCapacity() {
        return totalCapacity;
    }

    /**
     * Checks if the arena has a specific seat category configured.
     *
     * @return true if category exists, false otherwise
     */
    public boolean hasCategory(SeatCategory category) {
        return capacity.containsKey(category);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Arena Capacity:\n");

        for (Map.Entry<SeatCategory, Integer> entry : capacity.entrySet()) {
            sb.append(String.format("  %-10s : %d seats\n",
                    entry.getKey().getDisplayName(), entry.getValue()));
        }

        sb.append(String.format("  %-10s : %d seats", "TOTAL", totalCapacity));

        return sb.toString();
    }
}