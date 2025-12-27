# Carrefour Traffic Intersection Simulation

A Java GUI application demonstrating multi-threaded programming concepts with semaphores and thread synchronization.

## Overview

The Carrefour Traffic Intersection Simulation is an educational Java application that simulates a traffic intersection with multiple lanes. This project demonstrates practical implementation of concurrent programming concepts, including thread synchronization, semaphores, and resource management.

## Features

- **Traffic Intersection Simulation**: Visual representation of vehicles crossing an intersection
- **Multi-threaded Architecture**: Multiple vehicles operating simultaneously as separate threads
- **Custom Semaphore Implementation**: Thread-safe synchronization using Dijkstra's P/V operations
- **Traffic Light Controller**: Automatic switching between lanes with configurable timing
- **Real-time Statistics Panel**: Live monitoring of:
  - Vehicle counts (total, per lane)
  - Current vehicles waiting in queues
  - Average wait times
  - Traffic light status with countdown timer
- **Visual GUI**: Swing-based interface with vehicle sprites and traffic lights

## Technologies Used

- **Java 8+**: Core programming language
- **Java Swing**: GUI framework
- **Java AWT Graphics**: Custom rendering
- **Java Threading API**: Concurrent programming
- **Apache Ant**: Build automation

## Prerequisites

- Java JDK 8 or higher
- Apache Ant (optional, for building)



## Building the Project

### Using Apache Ant
```bash
cd carrefour
ant build
```

### Using Java Compiler Directly
```bash
cd carrefour/src
javac -d ../build/classes carrefour/*.java
```

## Running the Application

### From JAR File
```bash
cd carrefour/dist
java -jar carrefour.jar
```

### Using Apache Ant
```bash
cd carrefour
ant run
```

### Direct Execution
```bash
cd carrefour
java -cp "build/classes:resources:." carrefour.CarrefourApp
```

**Note**: Make sure the `resources` folder is in the classpath or in the same directory as the JAR when running.

## Key Concepts Demonstrated

### Thread Synchronization
- Custom semaphore implementation with P (wait) and V (signal) operations
- Thread-safe resource sharing
- Proper interrupt handling

### Concurrent Programming Patterns
- **Producer-Consumer Pattern**: Vehicles waiting for traffic lights
- **Resource Pooling**: Lane semaphores managing access
- **Thread Coordination**: Traffic controller managing multiple vehicle threads

### GUI Programming
- Custom component painting
- Real-time updates using Swing Timer
- Event-driven architecture

## How It Works

1. **Initialization**: Creates semaphores for lanes and traffic lights
2. **Traffic Controller**: Runs in separate thread, alternates traffic lights every 2 seconds
3. **Vehicle Threads**: Each vehicle is a separate thread that:
   - Waits for lane access (semaphore)
   - Waits for green light (semaphore)
   - Crosses intersection
   - Releases semaphores
4. **Statistics**: Tracks all vehicle movements and wait times
5. **Display**: Updates GUI and statistics panel in real-time


This project is excellent for learning:
- Multi-threaded programming concepts
- Semaphore synchronization
- Thread coordination and communication
- GUI programming with Swing
- Real-time system design
- Statistics collection and display

