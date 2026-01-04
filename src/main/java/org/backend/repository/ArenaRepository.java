package org.backend.repository;

import org.backend.models.Arena;
import org.backend.models.SeatCategory;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Thread-safe implementation of arena repository.
 * Manages current seat availability using synchronized blocks.
 */
public class ArenaRepository implements IArenaRepository {
    private final Map<SeatCategory, Integer> totalCapacity;
    private final Map<SeatCategory, Integer> availableSeats;
    private final Object lock = new Object();


    public ArenaRepository(Arena arena) {
        if (arena == null) {
            throw new IllegalArgumentException("Arena cannot be null");
        }

        // Store total capacity
        this.totalCapacity = new EnumMap<>(arena.getCapacityMap());

        // Initialize available seats (mutable - will change as seats are allocated)
        this.availableSeats = new EnumMap<>(arena.getCapacityMap());
    }

    @Override
    public boolean tryAllocateSeats(SeatCategory category, int count) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive");
        }

        // Only ONE thread can enter here at a time!
        synchronized (lock) {

            int available = availableSeats.getOrDefault(category, 0);

            // Check if enough seats available
            if (available < count) {
                return false;
            }

            // Allocate seats
            availableSeats.put(category, available - count);

            return true;

        }
    }

    @Override
    public int getAvailableSeats(SeatCategory category) {
        if (category == null) {
            return 0;
        }

        // Reading is also synchronized to ensure visibility
        synchronized (lock) {
            return availableSeats.getOrDefault(category, 0);
        }
    }

    @Override
    public Map<SeatCategory, Integer> getCurrentState() {
        synchronized (lock) {
            // Return immutable copy to prevent external modification
            return Collections.unmodifiableMap(new EnumMap<>(availableSeats));
        }
    }

    @Override
    public int getTotalCapacity(SeatCategory category) {
        if (category == null) {
            return 0;
        }

        // Total capacity never changes, no need for synchronization
        return totalCapacity.getOrDefault(category, 0);
    }
}