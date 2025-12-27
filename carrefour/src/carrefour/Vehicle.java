package carrefour;

/**
 * Interface for vehicles that can traverse the intersection.
 * 
 * @author PC-DELL
 */
public interface Vehicle extends Runnable {
    /**
     * Gets the vehicle's sprite for rendering.
     * 
     * @return the vehicle sprite
     */
    VehicleSprite getSprite();
    
    /**
     * Checks if the vehicle has completed crossing the intersection.
     * 
     * @return true if the vehicle has finished crossing
     */
    boolean isFinished();
}

