# 🏀 Basketball Arena - Seat Allocation System

Multithreaded Java application simulating concurrent seat allocation through 4 entrance gates.

## Features

- 4 concurrent threads (ExecutorService)
- Thread-safe seat allocation (synchronized)
- SOLID principles & clean architecture
- Interactive console interface

## Architecture
```
models/       → Domain objects (Arena, FanGroup, SeatCategory)
repository/   → Thread-safe data access
service/      → Business logic
processor/    → Thread coordination
Main          → Entry point
```

## Key Concepts

**Thread Pool**: 4 reusable threads process fan groups concurrently

**Synchronization**: Prevents race conditions when allocating seats
```java
synchronized (lock) {
    // Only one thread at a time
}
```

**Immutability**: Thread-safe models by design

## Running
```bash
javac -d out src/java/org/backend/**/*.java
java -cp out org.backend.Main
```

## Learning Goals

- ExecutorService & thread pools
- Race conditions & synchronization
- Clean code architecture

---

University coursework on multithreading in Java
