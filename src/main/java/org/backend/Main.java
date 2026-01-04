package org.backend;

import org.backend.config.ArenaConfigurer;
import org.backend.config.GroupConfigurer;
import org.backend.models.Arena;
import org.backend.models.FanGroup;
import org.backend.models.SeatCategory;
import org.backend.processor.GateProcessor;
import org.backend.repository.ArenaRepository;
import org.backend.repository.IArenaRepository;
import org.backend.service.ISeatAllocationService;
import org.backend.service.SeatAllocationService;
import org.backend.util.ConsoleFormatter;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    // Number of entrance gates (threads)
    private static final int GATE_COUNT = 4;
    private static final AtomicInteger successCounter = new AtomicInteger(0);
    private static final AtomicInteger rejectedCounter = new AtomicInteger(0);

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            // Print header
            ConsoleFormatter.printHeader();

            // Configure arena and groups
            Arena arena = ArenaConfigurer.configureUserInput(scanner);
            ConsoleFormatter.printArenaCapacity(arena.getCapacityMap());

            List<FanGroup> groups = GroupConfigurer.configureFromUserInput(scanner);

            // Initialize dependencies
            IArenaRepository repository = new ArenaRepository(arena);
            ISeatAllocationService service = new SeatAllocationService(repository);

            // Process groups
            ConsoleFormatter.printSection("PROCESSING GROUPS THROUGH GATES");
            processGroups(groups, service);

            // Print final report
            printFinalReport(repository, arena);
        }
    }

    /**
     * Processes fan groups using thread pool.
     */
    private static void processGroups(List<FanGroup> groups,
                                      ISeatAllocationService service) {

        ExecutorService executor = Executors.newFixedThreadPool(GATE_COUNT);

        try {
            AtomicInteger gateCounter = new AtomicInteger(1);

            for (FanGroup group : groups) {
                int gateId = ((gateCounter.getAndIncrement() - 1) % GATE_COUNT) + 1;

                executor.submit(() -> {
                    GateProcessor.processGroup(group, service, gateId);
                    successCounter.incrementAndGet();
                });
            }

            executor.shutdown();
            System.out.println("\n>>> Waiting for all gates to finish...\n");

            boolean finished = executor.awaitTermination(5, TimeUnit.MINUTES);

            if (!finished) {
                System.err.println("WARNING: Processing timeout!");
                executor.shutdownNow();
            }

        } catch (InterruptedException e) {
            System.err.println("Processing interrupted: " + e.getMessage());
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Prints final statistics report.
     */
    private static void printFinalReport(IArenaRepository repository, Arena arena) {
        Map<SeatCategory, Integer> currentState = repository.getCurrentState();
        Map<SeatCategory, Integer> totalCapacity = arena.getCapacityMap();

        ConsoleFormatter.printFinalReport(
                currentState,
                totalCapacity,
                successCounter.get(),
                rejectedCounter.get()
        );

        System.out.println("\nAll entrance gates closed.");
    }
}