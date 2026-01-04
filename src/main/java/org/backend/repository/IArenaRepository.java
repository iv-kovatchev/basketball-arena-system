package org.backend.repository;

import org.backend.models.SeatCategory;

import java.util.Map;

/**
 * Repository interface for managing arena seat availability.
 * Implementations must be thread-safe.
 */
public interface IArenaRepository {

    /**
     * Attempts to allocate seats for a specific category.
     * This operation must be thread-safe.
     * @return true if allocation successful, false if insufficient seats
     */
    boolean tryAllocateSeats(SeatCategory category, int count);

    /**
     * Gets the current number of available seats for a category.
     *
     * @return Number of available seats
     */
    int getAvailableSeats(SeatCategory category);

    /**
     * Gets the current state of all seat categories.
     */
    Map<SeatCategory, Integer> getCurrentState();

    /**
     * Gets the total capacity for a category (never changes).
     */
    int getTotalCapacity(SeatCategory category);
}