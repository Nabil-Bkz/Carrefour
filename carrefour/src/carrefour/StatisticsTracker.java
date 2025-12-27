package carrefour;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread-safe statistics tracker for the traffic simulation.
 * Tracks vehicle counts, wait times, and other metrics.
 * 
 * @author PC-DELL
 */
public class StatisticsTracker {
    private final AtomicInteger verticalVehiclesCrossed = new AtomicInteger(0);
    private final AtomicInteger horizontalVehiclesCrossed = new AtomicInteger(0);
    private final AtomicInteger verticalVehiclesWaiting = new AtomicInteger(0);
    private final AtomicInteger horizontalVehiclesWaiting = new AtomicInteger(0);
    private final AtomicLong totalVerticalWaitTime = new AtomicLong(0);
    private final AtomicLong totalHorizontalWaitTime = new AtomicLong(0);
    private final AtomicInteger verticalVehicleCount = new AtomicInteger(0);
    private final AtomicInteger horizontalVehicleCount = new AtomicInteger(0);
    
    /**
     * Records that a vertical vehicle has started waiting.
     */
    public void verticalVehicleStarted() {
        verticalVehicleCount.incrementAndGet();
        verticalVehiclesWaiting.incrementAndGet();
    }
    
    /**
     * Records that a vertical vehicle has crossed the intersection.
     * 
     * @param waitTimeMs the time the vehicle waited in milliseconds
     */
    public void verticalVehicleCrossed(long waitTimeMs) {
        verticalVehiclesCrossed.incrementAndGet();
        verticalVehiclesWaiting.decrementAndGet();
        totalVerticalWaitTime.addAndGet(waitTimeMs);
    }
    
    /**
     * Records that a horizontal vehicle has started waiting.
     */
    public void horizontalVehicleStarted() {
        horizontalVehicleCount.incrementAndGet();
        horizontalVehiclesWaiting.incrementAndGet();
    }
    
    /**
     * Records that a horizontal vehicle has crossed the intersection.
     * 
     * @param waitTimeMs the time the vehicle waited in milliseconds
     */
    public void horizontalVehicleCrossed(long waitTimeMs) {
        horizontalVehiclesCrossed.incrementAndGet();
        horizontalVehiclesWaiting.decrementAndGet();
        totalHorizontalWaitTime.addAndGet(waitTimeMs);
    }
    
    /**
     * Gets the number of vertical vehicles that have crossed.
     * 
     * @return the count
     */
    public int getVerticalVehiclesCrossed() {
        return verticalVehiclesCrossed.get();
    }
    
    /**
     * Gets the number of horizontal vehicles that have crossed.
     * 
     * @return the count
     */
    public int getHorizontalVehiclesCrossed() {
        return horizontalVehiclesCrossed.get();
    }
    
    /**
     * Gets the total number of vehicles that have crossed.
     * 
     * @return the total count
     */
    public int getTotalVehiclesCrossed() {
        return verticalVehiclesCrossed.get() + horizontalVehiclesCrossed.get();
    }
    
    /**
     * Gets the number of vertical vehicles currently waiting.
     * 
     * @return the count
     */
    public int getVerticalVehiclesWaiting() {
        return verticalVehiclesWaiting.get();
    }
    
    /**
     * Gets the number of horizontal vehicles currently waiting.
     * 
     * @return the count
     */
    public int getHorizontalVehiclesWaiting() {
        return horizontalVehiclesWaiting.get();
    }
    
    /**
     * Gets the average wait time for vertical vehicles in milliseconds.
     * 
     * @return the average wait time, or 0 if no vehicles have crossed
     */
    public double getAverageVerticalWaitTime() {
        int crossed = verticalVehiclesCrossed.get();
        if (crossed == 0) {
            return 0;
        }
        return (double) totalVerticalWaitTime.get() / crossed;
    }
    
    /**
     * Gets the average wait time for horizontal vehicles in milliseconds.
     * 
     * @return the average wait time, or 0 if no vehicles have crossed
     */
    public double getAverageHorizontalWaitTime() {
        int crossed = horizontalVehiclesCrossed.get();
        if (crossed == 0) {
            return 0;
        }
        return (double) totalHorizontalWaitTime.get() / crossed;
    }
    
    /**
     * Gets the overall average wait time in milliseconds.
     * 
     * @return the average wait time, or 0 if no vehicles have crossed
     */
    public double getOverallAverageWaitTime() {
        int totalCrossed = getTotalVehiclesCrossed();
        if (totalCrossed == 0) {
            return 0;
        }
        long totalWait = totalVerticalWaitTime.get() + totalHorizontalWaitTime.get();
        return (double) totalWait / totalCrossed;
    }
    
    /**
     * Resets all statistics.
     */
    public void reset() {
        verticalVehiclesCrossed.set(0);
        horizontalVehiclesCrossed.set(0);
        verticalVehiclesWaiting.set(0);
        horizontalVehiclesWaiting.set(0);
        totalVerticalWaitTime.set(0);
        totalHorizontalWaitTime.set(0);
        verticalVehicleCount.set(0);
        horizontalVehicleCount.set(0);
    }
}

