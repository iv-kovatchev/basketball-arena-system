package org.backend.models;

/**
 * Represents the result of a seat allocation attempt.
 * Immutable result object following the Result pattern.
 * Used to communicate success/failure without throwing exceptions.
 */
public final class AllocationResult {

    private final boolean success;
    private final String message;

    /**
     * Private constructor to enforce factory methods usage.
     *
     * @param success Whether the allocation was successful
     * @param message Descriptive message about the result
     */
    private AllocationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Factory method for successful allocation.
     *
     * @param message Success message (e.g., "VIP: 85 available")
     * @return AllocationResult indicating success
     */
    public static AllocationResult success(String message) {
        return new AllocationResult(true, message);
    }

    /**
     * Factory method for failed allocation.
     *
     * @param message Failure reason (e.g., "Insufficient seats")
     * @return AllocationResult indicating failure
     */
    public static AllocationResult failure(String message) {
        return new AllocationResult(false, message);
    }

    /**
     * Checks if the allocation was successful.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets the result message.
     *
     * @return Descriptive message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", success ? "SUCCESS" : "FAILURE", message);
    }
}