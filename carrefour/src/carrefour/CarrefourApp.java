package carrefour;

/**
 * Main application class for the Carrefour traffic intersection simulation.
 * This application demonstrates multi-threaded programming concepts using
 * semaphores for synchronization.
 * 
 * @author PC-DELL
 */
public class CarrefourApp {
    private static final int VEHICLE_COUNT = 100;
    private static final int VEHICLE_SPAWN_DELAY_MS = 600;
    
    /**
     * Main entry point of the application.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Initialize semaphores
            Semaphore lane1 = new Semaphore(1, "lane1");
            Semaphore lane2 = new Semaphore(1, "lane2");
            Semaphore trafficLight1 = new Semaphore(1, "trafficLight1"); // Initially green
            Semaphore trafficLight2 = new Semaphore(0, "trafficLight2"); // Initially red
            
            // Create statistics tracker
            StatisticsTracker statisticsTracker = new StatisticsTracker();
            
            // Create and start traffic light controller
            TrafficController trafficController = new TrafficController(
                trafficLight1, trafficLight2, lane2, lane1);
            Thread trafficControllerThread = new Thread(trafficController);
            trafficControllerThread.setDaemon(true);
            trafficControllerThread.start();
            
            // Create main frame with statistics panel
            CarrefourFrame frame = new CarrefourFrame(
                trafficLight1, trafficLight2, lane2, lane1, trafficController, statisticsTracker);
            
            // Create vehicle threads
            createVehicles(lane1, trafficLight1, lane2, trafficLight2, 
                         trafficController, statisticsTracker);
            
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Creates and starts vehicle threads for both lanes.
     * 
     * @param lane1 semaphore for lane 1
     * @param trafficLight1 semaphore for traffic light 1
     * @param lane2 semaphore for lane 2
     * @param trafficLight2 semaphore for traffic light 2
     * @param trafficController the traffic controller for vehicle registration
     * @param statisticsTracker the statistics tracker for recording metrics
     */
    private static void createVehicles(Semaphore lane1, Semaphore trafficLight1,
                                      Semaphore lane2, Semaphore trafficLight2,
                                      TrafficController trafficController,
                                      StatisticsTracker statisticsTracker) {
        Thread[] verticalVehicles = new Thread[VEHICLE_COUNT];
        Thread[] horizontalVehicles = new Thread[VEHICLE_COUNT];
        
        for (int i = 0; i < VEHICLE_COUNT; i++) {
            // Create vertical lane vehicle
            Vehicle verticalVehicle = new VerticalVehicle(lane1, trafficLight1, 
                                                        trafficController, statisticsTracker);
            verticalVehicles[i] = new Thread(verticalVehicle, "VerticalVehicle-" + i);
            
            // Register vehicle sprite with traffic controller
            trafficController.registerVehicle(verticalVehicle.getSprite(), true);
            
            // Create horizontal lane vehicle
            Vehicle horizontalVehicle = new HorizontalVehicle(lane2, trafficLight2, 
                                                            trafficController, statisticsTracker);
            horizontalVehicles[i] = new Thread(horizontalVehicle, "HorizontalVehicle-" + i);
            
            // Register vehicle sprite with traffic controller
            trafficController.registerVehicle(horizontalVehicle.getSprite(), false);
            
            // Start both vehicles
            verticalVehicles[i].start();
            horizontalVehicles[i].start();
            
            // Delay between vehicle spawns
            try {
                Thread.sleep(VEHICLE_SPAWN_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrupted while spawning vehicles");
                break;
            }
        }
    }
}

