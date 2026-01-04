package org.backend.processor;

import org.backend.models.AllocationResult;
import org.backend.models.FanGroup;
import org.backend.service.ISeatAllocationService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Processor for handling fan group allocation through entrance gates.
 * Each gate runs in its own thread.
 */
public class GateProcessor {

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    /**
     * Processes a fan group through a specific gate.
     * This method is called by worker threads.
     */
    public static void processGroup(FanGroup group,
                                    ISeatAllocationService service,
                                    int gateId) {

        // Log gate pickup
        String timestamp = getCurrentTimestamp();
        System.out.printf("[GATE-%d][%s] Picked up %s%n",
                gateId, timestamp, group);

        try {
            // Simulate processing time (checking tickets, etc.)
            Thread.sleep(getRandomDelay());

            // Log processing
            System.out.printf("[GATE-%d][%s] Processing...%n",
                    gateId, timestamp);

            // Attempt seat allocation
            AllocationResult result = service.allocateSeats(group);

            // Log result
            timestamp = getCurrentTimestamp();
            if (result.isSuccess()) {
                System.out.printf("[GATE-%d][%s] SUCCESS -> %s%n",
                        gateId, timestamp, result.getMessage());
            } else {
                System.out.printf("[GATE-%d][%s] REJECTED -> %s%n",
                        gateId, timestamp, result.getMessage());
            }

        } catch (InterruptedException e) {
            System.err.printf("[GATE-%d] Interrupted: %s%n",
                    gateId, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Gets current timestamp for logging.
     */
    private static String getCurrentTimestamp() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    private static long getRandomDelay() {
        return 10000 + (long) (Math.random() * 1000);
    }
}