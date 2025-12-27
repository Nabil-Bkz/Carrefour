package carrefour;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Vehicle that moves horizontally through the intersection.
 * 
 * @author PC-DELL
 */
public class HorizontalVehicle implements Vehicle {
    private static final int INITIAL_X = 0;
    private static final int INITIAL_Y = 30;
    private static final int VEHICLE_WIDTH = 70;
    private static final int VEHICLE_HEIGHT = 40;
    private static final int MOVEMENT_SPEED = 5;
    private static final int INTERSECTION_X = 250;
    private static final Logger LOGGER = Logger.getLogger(HorizontalVehicle.class.getName());
    
    private final Semaphore laneSemaphore;
    private final Semaphore trafficLightSemaphore;
    private final TrafficController trafficController;
    private final VehicleSprite sprite;
    private final StatisticsTracker statisticsTracker;
    
    private int x;
    private int speed;
    private boolean finished;
    private long waitStartTime;
    
    /**
     * Creates a new horizontal vehicle.
     * 
     * @param laneSemaphore semaphore for the lane
     * @param trafficLightSemaphore semaphore for the traffic light
     * @param trafficController the traffic controller
     * @param statisticsTracker the statistics tracker (can be null)
     */
    public HorizontalVehicle(Semaphore laneSemaphore, Semaphore trafficLightSemaphore,
                            TrafficController trafficController, StatisticsTracker statisticsTracker) {
        this.laneSemaphore = laneSemaphore;
        this.trafficLightSemaphore = trafficLightSemaphore;
        this.trafficController = trafficController;
        this.statisticsTracker = statisticsTracker;
        this.x = INITIAL_X;
        this.speed = MOVEMENT_SPEED;
        this.finished = false;
        this.waitStartTime = System.currentTimeMillis();
        
        this.sprite = new Taxi16Sprite(INITIAL_X, INITIAL_Y, VEHICLE_WIDTH, VEHICLE_HEIGHT);
        
        if (statisticsTracker != null) {
            statisticsTracker.horizontalVehicleStarted();
        }
    }
    
    @Override
    public VehicleSprite getSprite() {
        return sprite;
    }
    
    @Override
    public boolean isFinished() {
        return finished;
    }
    
    @Override
    public void run() {
        try {
            // Wait for lane access
            laneSemaphore.P();
            
            // Wait for green light
            trafficLightSemaphore.P();
            
            // Calculate wait time
            long waitTime = System.currentTimeMillis() - waitStartTime;
            
            // Enter intersection - slow down
            speed = -MOVEMENT_SPEED;
            LOGGER.info("Horizontal vehicle entering intersection");
            
            // Simulate crossing (in real implementation, this would be animated)
            Thread.sleep(100);
            
            // Exit intersection - release semaphores
            trafficLightSemaphore.V();
            laneSemaphore.V();
            
            // Record statistics
            if (statisticsTracker != null) {
                statisticsTracker.horizontalVehicleCrossed(waitTime);
            }
            
            finished = true;
            LOGGER.info("Horizontal vehicle completed crossing in " + waitTime + "ms");
            
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.WARNING, "Horizontal vehicle thread interrupted", ex);
            // Release semaphores on interruption
            if (laneSemaphore.getCount() == 0) {
                laneSemaphore.V();
            }
            if (trafficLightSemaphore.getCount() == 0) {
                trafficLightSemaphore.V();
            }
        }
    }
    
    /**
     * Updates the vehicle's position (called from animation timer).
     * 
     * @param deltaX change in x position
     */
    public void updatePosition(int deltaX) {
        if (!finished) {
            x += deltaX;
            sprite.updatePosition(deltaX, 0);
        }
    }
}

