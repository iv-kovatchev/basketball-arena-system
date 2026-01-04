package org.backend.service;

import org.backend.models.AllocationResult;
import org.backend.models.FanGroup;
import org.backend.models.SeatCategory;
import org.backend.repository.IArenaRepository;

import java.util.Map;

/**
 * Implementation of seat allocation service.
 * Contains business logic for validating and processing seat allocations.
 */
public class SeatAllocationService implements ISeatAllocationService {

    private final IArenaRepository repository;

    /**
     * Constructor with dependency injection.
     *
     * @param repository Arena repository for data access
     */
    public SeatAllocationService(IArenaRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    @Override
    public AllocationResult allocateSeats(FanGroup group) {
        // Validation
        if (group == null) {
            return AllocationResult.failure("Invalid group: null");
        }

        if (group.getFanCount() <= 0) {
            return AllocationResult.failure(
                    String.format("Invalid fan count: %d", group.getFanCount())
            );
        }

        if (group.getCategory() == null) {
            return AllocationResult.failure("Seat category cannot be null");
        }

        // Business logic
        SeatCategory category = group.getCategory();
        int requestedSeats = group.getFanCount();

        // Check availability
        int available = repository.getAvailableSeats(category);

        if (available < requestedSeats) {
            return AllocationResult.failure(
                    String.format("Insufficient seats! Need: %d, Available: %d",
                            requestedSeats, available)
            );
        }

        // Attempt allocation (thread-safe in repository)
        boolean allocated = repository.tryAllocateSeats(category, requestedSeats);

        if (allocated) {
            int remaining = repository.getAvailableSeats(category);
            return AllocationResult.success(
                    String.format("%s: %d available", category, remaining)
            );
        } else {
            // This can happen in rare race condition scenarios
            return AllocationResult.failure(
                    "Allocation failed - seats taken by another thread"
            );
        }
    }

    @Override
    public Map<SeatCategory, Integer> getArenaStatus() {
        return repository.getCurrentState();
    }
}