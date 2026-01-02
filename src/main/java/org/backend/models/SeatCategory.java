package org.backend.models;

/**
 * Represents the different seat categories available in the basketball arena.
 */
public enum SeatCategory {
    VIP("VIP"),
    COURTSIDE("Courtside"),
    LOWER("Lower"),
    UPPER("Upper");

    private final String displayName;

    /**
     * Constructor for SeatCategory enum.
     *
     * @param displayName Human-readable name for the category
     */
    SeatCategory(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the category and return as String.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Parses a string input to a SeatCategory enum.
     * Case-insensitive matching.
     */
    public static SeatCategory fromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        String normalized = input.trim().toUpperCase();

        try {
            return SeatCategory.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}