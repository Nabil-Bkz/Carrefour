package carrefour;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom semaphore implementation for thread synchronization.
 * Uses Dijkstra's P (wait) and V (signal) operations.
 * 
 * @author PC-DELL
 */
public class Semaphore {
    private int count;
    private final String name;
    private static final Logger LOGGER = Logger.getLogger(Semaphore.class.getName());
    
    /**
     * Creates a new semaphore with the specified initial count.
     * 
     * @param initialCount the initial count (must be >= 0)
     * @param name the name of the semaphore for debugging
     * @throws IllegalArgumentException if initialCount is negative
     */
    public Semaphore(int initialCount, String name) {
        if (initialCount < 0) {
            throw new IllegalArgumentException("Initial count must be non-negative");
        }
        this.count = initialCount;
        this.name = name;
    }
    
    /**
     * P operation (wait/proberen) - decrements the semaphore count.
     * If count is 0, the thread blocks until another thread calls V().
     */
    public synchronized void P() {
        while (count == 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                LOGGER.log(Level.SEVERE, "Semaphore " + name + " interrupted", ex);
            }
        }
        count--;
    }
    
    /**
     * V operation (signal/verhogen) - increments the semaphore count.
     * Wakes up one waiting thread if any.
     */
    public synchronized void V() {
        count++;
        notifyAll();
    }
    
    /**
     * Gets the current count of the semaphore.
     * 
     * @return the current count
     */
    public synchronized int getCount() {
        return count;
    }
    
    /**
     * Gets the name of the semaphore.
     * 
     * @return the semaphore name
     */
    public String getName() {
        return name;
    }
}  

