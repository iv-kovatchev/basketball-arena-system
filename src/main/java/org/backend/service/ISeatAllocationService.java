package org.backend.service;

import org.backend.models.AllocationResult;
import org.backend.models.FanGroup;
import org.backend.models.SeatCategory;

import java.util.Map;

/**
 * Service interface for seat allocation business logic.
 */
public interface ISeatAllocationService {

    /**
     * Attempts to allocate seats for a fan group.
     *
     * @return AllocationResult with success status and message
     */
    AllocationResult allocateSeats(FanGroup group);

    /**
     * Gets current arena status for all categories.
     *
     * @return Map of seat categories to available seats
     */
    Map<SeatCategory, Integer> getArenaStatus();
}