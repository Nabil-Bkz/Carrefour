package carrefour;

import java.awt.BorderLayout;
import javax.swing.*;

/**
 * Main frame for the Carrefour traffic intersection simulation.
 * 
 * @author PC-DELL
 */
public class CarrefourFrame extends JFrame {
    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 900;
    private static final int WINDOW_X = 100;
    private static final int WINDOW_Y = 80;
    private static final int STATISTICS_PANEL_WIDTH = 250;
    
    private final Semaphore sfeu1;
    private final Semaphore sfeu2;
    private final Semaphore svoie2;
    private final Semaphore svoie1;
    private StatisticsPanel statisticsPanel;
    
    /**
     * Creates a new Carrefour frame with the specified semaphores and traffic controller.
     * 
     * @param sfeu1 semaphore for traffic light 1
     * @param sfeu2 semaphore for traffic light 2
     * @param svoie2 semaphore for lane 2
     * @param svoie1 semaphore for lane 1
     * @param trafficController the traffic light controller panel
     * @param statisticsTracker the statistics tracker
     */
    public CarrefourFrame(Semaphore sfeu1, Semaphore sfeu2, Semaphore svoie2, 
                         Semaphore svoie1, TrafficController trafficController,
                         StatisticsTracker statisticsTracker) {
        this.sfeu1 = sfeu1;
        this.sfeu2 = sfeu2;
        this.svoie2 = svoie2;
        this.svoie1 = svoie1;
        
        initializeFrame(trafficController, statisticsTracker);
    }
    
    /**
     * Initializes the frame properties and adds the traffic controller panel.
     * 
     * @param trafficController the traffic controller panel to add
     * @param statisticsTracker the statistics tracker
     */
    private void initializeFrame(TrafficController trafficController, StatisticsTracker statisticsTracker) {
        setTitle("Carrefour - Traffic Intersection Simulation with Statistics");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLocation(WINDOW_X, WINDOW_Y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        if (trafficController != null) {
            add(trafficController, BorderLayout.CENTER);
        }
        
        // Create and add statistics panel
        if (statisticsTracker != null && trafficController != null) {
            statisticsPanel = new StatisticsPanel(statisticsTracker, trafficController);
            statisticsPanel.setPreferredSize(new java.awt.Dimension(STATISTICS_PANEL_WIDTH, WINDOW_HEIGHT));
            add(statisticsPanel, BorderLayout.EAST);
            
            // Link statistics panel to traffic controller for light change notifications
            trafficController.setStatisticsPanel(statisticsPanel);
        }
        
        setVisible(true);
    }
    
    /**
     * Gets the statistics panel.
     * 
     * @return the statistics panel
     */
    public StatisticsPanel getStatisticsPanel() {
        return statisticsPanel;
    }
}

