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

## Project Structure

```
Carrefour-main/
├── carrefour/                    # Main project directory
│   ├── src/
│   │   └── carrefour/            # Source code (package: carrefour)
│   │       ├── CarrefourApp.java          # Main application entry point
│   │       ├── CarrefourFrame.java        # Main GUI frame
│   │       ├── TrafficController.java     # Traffic light controller
│   │       ├── Semaphore.java             # Custom semaphore implementation
│   │       ├── VerticalVehicle.java       # Vertical lane vehicles
│   │       ├── HorizontalVehicle.java     # Horizontal lane vehicles
│   │       ├── StatisticsTracker.java     # Statistics collection engine
│   │       ├── StatisticsPanel.java      # Statistics display panel
│   │       ├── Vehicle.java                # Vehicle interface
│   │       ├── VehicleSprite.java         # Vehicle sprite base class
│   │       ├── TaxiSprite.java            # Vertical taxi sprite
│   │       ├── Taxi16Sprite.java           # Horizontal taxi sprite
│   │       └── VehicleLanePanel.java      # Lane display panel
│   ├── resources/                # Application resources
│   │   ├── taxi.png              # Vertical vehicle sprite
│   │   ├── taxi16.png             # Horizontal vehicle sprite
│   │   ├── traficverre.jpg        # Green traffic light image
│   │   └── traficrouge.jpg        # Red traffic light image
│   ├── docs/                      # Documentation
│   │   ├── REFACTORING_SUMMARY.md
│   │   ├── NEW_FEATURE_SUMMARY.md
│   │   └── CLEANUP_SUMMARY.md
│   ├── build/                     # Compiled classes (generated)
│   ├── dist/                      # JAR distribution (generated)
│   ├── nbproject/                 # NetBeans project configuration
│   ├── build.xml                  # Apache Ant build file
│   └── manifest.mf                # JAR manifest
└── README.md                      # This file
```

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

## Configuration

### Adjusting Vehicle Count
Edit `carrefour/src/carrefour/CarrefourApp.java`:
```java
private static final int VEHICLE_COUNT = 100; // Change this value
```

### Adjusting Traffic Light Timing
Edit `carrefour/src/carrefour/TrafficController.java`:
```java
private static final int TRAFFIC_LIGHT_CHANGE_INTERVAL_MS = 2000; // Change this value
```

### Adjusting Vehicle Spawn Rate
Edit `carrefour/src/carrefour/CarrefourApp.java`:
```java
private static final int VEHICLE_SPAWN_DELAY_MS = 600; // Change this value
```

## Documentation

See the `carrefour/docs/` folder for detailed documentation:
- **REFACTORING_SUMMARY.md**: Details about code improvements and best practices applied
- **NEW_FEATURE_SUMMARY.md**: Complete documentation for the statistics panel feature
- **CLEANUP_SUMMARY.md**: Repository cleanup and preparation documentation

## Code Quality

This project follows Java best practices:
- ✅ Proper naming conventions (PascalCase for classes)
- ✅ Comprehensive JavaDoc comments
- ✅ Thread-safe implementations
- ✅ Proper error handling
- ✅ Separation of concerns
- ✅ DRY (Don't Repeat Yourself) principles
- ✅ Organized package structure

## Educational Value

This project is excellent for learning:
- Multi-threaded programming concepts
- Semaphore synchronization
- Thread coordination and communication
- GUI programming with Swing
- Real-time system design
- Statistics collection and display

## License

This project is part of an educational assignment and is provided for learning purposes.

## Contributing

This is an educational project. Feel free to fork, modify, and learn from it!

## Author

Developed as part of a third-year college project to demonstrate concurrent programming concepts.
