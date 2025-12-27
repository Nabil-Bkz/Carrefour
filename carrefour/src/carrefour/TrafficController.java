package carrefour;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Traffic controller panel that manages traffic lights and displays the intersection.
 * Alternates between two traffic light states every 2 seconds.
 * 
 * @author PC-DELL
 */
public class TrafficController extends JPanel implements Runnable, ActionListener {
    private static final int TIMER_DELAY_MS = 100;
    private static final int TRAFFIC_LIGHT_CHANGE_INTERVAL_MS = 2000;
    private static final int VERTICAL_LANE_X = 705;
    private static final int VERTICAL_LANE_Y = 0;
    private static final int HORIZONTAL_LANE_X = 0;
    private static final int HORIZONTAL_LANE_Y = 460;
    private static final int ROAD_WIDTH = 600;
    private static final int ROAD_HEIGHT = 350;
    private static final int INTERSECTION_X = 700;
    private static final int INTERSECTION_Y = 450;
    
    private static final Logger LOGGER = Logger.getLogger(TrafficController.class.getName());
    
    private final Timer timer;
    private final Semaphore trafficLight1;
    private final Semaphore trafficLight2;
    private final Semaphore lane2;
    private final Semaphore lane1;
    
    private JPanel redLight1;
    private JPanel greenLight1;
    private JPanel redLight2;
    private JPanel greenLight2;
    
    private VehicleLanePanel verticalLane;
    private VehicleLanePanel horizontalLane;
    
    private Image greenLightImage;
    private Image redLightImage;
    private int currentLightState = 1; // 1 = light1 green, 2 = light2 green
    private StatisticsPanel statisticsPanel; // Reference to statistics panel for updates
    
    /**
     * Creates a new traffic controller.
     * 
     * @param trafficLight1 semaphore for traffic light 1
     * @param trafficLight2 semaphore for traffic light 2
     * @param lane2 semaphore for lane 2
     * @param lane1 semaphore for lane 1
     */
    public TrafficController(Semaphore trafficLight1, Semaphore trafficLight2, 
                            Semaphore lane2, Semaphore lane1) {
        this.trafficLight1 = trafficLight1;
        this.trafficLight2 = trafficLight2;
        this.lane2 = lane2;
        this.lane1 = lane1;
        
        this.timer = new Timer(TIMER_DELAY_MS, this);
        this.setLayout(null);
        
        initializeComponents();
        loadImages();
    }
    
    /**
     * Initializes the GUI components.
     */
    private void initializeComponents() {
        // Create vertical lane panel
        verticalLane = new VehicleLanePanel(true);
        verticalLane.setBounds(VERTICAL_LANE_X, VERTICAL_LANE_Y, 90, 900);
        verticalLane.setBackground(Color.RED);
        this.add(verticalLane);
        
        // Create horizontal lane panel
        horizontalLane = new VehicleLanePanel(false);
        horizontalLane.setBounds(HORIZONTAL_LANE_X, HORIZONTAL_LANE_Y, 1400, 90);
        horizontalLane.setBackground(Color.GREEN);
        this.add(horizontalLane);
        
        // Initialize traffic lights
        initializeTrafficLights();
        
        // Start timer for repainting
        timer.start();
    }
    
    /**
     * Initializes the traffic light panels.
     */
    private void initializeTrafficLights() {
        // Traffic light 1 (vertical direction)
        redLight1 = new JPanel();
        redLight1.setBackground(Color.GRAY);
        redLight1.setBounds(505, 646, 30, 20);
        this.add(redLight1);
        
        greenLight1 = new JPanel();
        greenLight1.setBackground(Color.GREEN);
        greenLight1.setBounds(505, 605, 30, 20);
        this.add(greenLight1);
        
        // Traffic light 2 (horizontal direction)
        redLight2 = new JPanel();
        redLight2.setBackground(Color.RED);
        redLight2.setBounds(855, 292, 30, 20);
        this.add(redLight2);
        
        greenLight2 = new JPanel();
        greenLight2.setBackground(Color.GRAY);
        greenLight2.setBounds(855, 255, 30, 20);
        this.add(greenLight2);
    }
    
    /**
     * Loads traffic light images.
     */
    private void loadImages() {
        try {
            File greenImageFile = new File("resources/traficverre.jpg");
            File redImageFile = new File("resources/traficrouge.jpg");
            
            if (greenImageFile.exists()) {
                greenLightImage = ImageIO.read(greenImageFile);
            }
            if (redImageFile.exists()) {
                redLightImage = ImageIO.read(redImageFile);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "Could not load traffic light images", ex);
        }
    }
    
    /**
     * Registers a vehicle with the appropriate lane panel.
     * 
     * @param vehicle the vehicle to register
     * @param isVertical true if vertical lane, false if horizontal
     */
    public void registerVehicle(VehicleSprite vehicle, boolean isVertical) {
        if (isVertical) {
            verticalLane.addVehicle(vehicle);
        } else {
            horizontalLane.addVehicle(vehicle);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw roads
        g.setColor(Color.BLUE);
        g.fill3DRect(0, 0, ROAD_WIDTH, ROAD_HEIGHT, true);
        g.fill3DRect(0, 550, ROAD_WIDTH, ROAD_HEIGHT, true);
        g.fill3DRect(800, 550, ROAD_WIDTH, ROAD_HEIGHT, true);
        g.fill3DRect(800, 0, ROAD_WIDTH, ROAD_HEIGHT, true);
        
        // Draw intersection lines
        g.setColor(Color.BLACK);
        g.drawLine(INTERSECTION_X, 0, INTERSECTION_X, 340);
        g.drawLine(1400, INTERSECTION_Y, 800, INTERSECTION_Y);
        g.drawLine(0, INTERSECTION_Y, ROAD_WIDTH, INTERSECTION_Y);
        g.drawLine(INTERSECTION_X, 550, INTERSECTION_X, 900);
        
        // Draw traffic light images
        if (greenLightImage != null) {
            g2d.drawImage(greenLightImage, 500, 600, 40, 70, null);
        }
        if (redLightImage != null) {
            g2d.drawImage(redLightImage, 850, 250, 40, 70, null);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(TRAFFIC_LIGHT_CHANGE_INTERVAL_MS);
                switchTrafficLights();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                LOGGER.log(Level.INFO, "Traffic controller thread interrupted");
                break;
            }
        }
    }
    
    /**
     * Switches the traffic lights between the two states.
     */
    private void switchTrafficLights() {
        if (currentLightState == 1) {
            // Switch to light 2 (horizontal) green
            trafficLight1.P();
            trafficLight2.V();
            currentLightState = 2;
            
            greenLight2.setBackground(Color.GREEN);
            redLight2.setBackground(Color.GRAY);
            greenLight1.setBackground(Color.GRAY);
            redLight1.setBackground(Color.RED);
            
            LOGGER.info("Traffic light 2 (horizontal) is now GREEN");
        } else {
            // Switch to light 1 (vertical) green
            trafficLight1.V();
            trafficLight2.P();
            currentLightState = 1;
            
            greenLight2.setBackground(Color.GRAY);
            redLight2.setBackground(Color.RED);
            greenLight1.setBackground(Color.GREEN);
            redLight1.setBackground(Color.GRAY);
            
            LOGGER.info("Traffic light 1 (vertical) is now GREEN");
        }
        
        // Notify statistics panel of light change
        if (statisticsPanel != null) {
            statisticsPanel.onTrafficLightChanged();
        }
    }
    
    /**
     * Sets the statistics panel reference for light change notifications.
     * 
     * @param statisticsPanel the statistics panel
     */
    public void setStatisticsPanel(StatisticsPanel statisticsPanel) {
        this.statisticsPanel = statisticsPanel;
    }
    
    /**
     * Gets the current traffic light state.
     * 
     * @return 1 for vertical green, 2 for horizontal green
     */
    public int getCurrentLightState() {
        return currentLightState;
    }
    
    /**
     * Gets the traffic light change interval in milliseconds.
     * 
     * @return the interval in milliseconds
     */
    public static int getTrafficLightChangeInterval() {
        return TRAFFIC_LIGHT_CHANGE_INTERVAL_MS;
    }
}

