package carrefour;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Statistics panel that displays real-time simulation metrics.
 * Shows vehicle counts, wait times, and traffic light countdown.
 * 
 * @author PC-DELL
 */
public class StatisticsPanel extends JPanel {
    private static final int UPDATE_INTERVAL_MS = 100;
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font VALUE_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final Color PANEL_BACKGROUND = new Color(240, 240, 240);
    private static final Color HEADER_COLOR = new Color(50, 50, 150);
    
    private final StatisticsTracker statisticsTracker;
    private final TrafficController trafficController;
    
    // Labels for displaying statistics
    private JLabel totalCrossedLabel;
    private JLabel verticalCrossedLabel;
    private JLabel horizontalCrossedLabel;
    private JLabel verticalWaitingLabel;
    private JLabel horizontalWaitingLabel;
    private JLabel avgVerticalWaitLabel;
    private JLabel avgHorizontalWaitLabel;
    private JLabel overallAvgWaitLabel;
    private JLabel lightStatusLabel;
    private JLabel countdownLabel;
    
    private Timer updateTimer;
    private long lastLightChangeTime;
    private int currentCountdown;
    
    /**
     * Creates a new statistics panel.
     * 
     * @param statisticsTracker the statistics tracker to monitor
     * @param trafficController the traffic controller for light status
     */
    public StatisticsPanel(StatisticsTracker statisticsTracker, TrafficController trafficController) {
        this.statisticsTracker = statisticsTracker;
        this.trafficController = trafficController;
        this.lastLightChangeTime = System.currentTimeMillis();
        this.currentCountdown = TrafficController.getTrafficLightChangeInterval() / 1000;
        
        initializePanel();
        startUpdateTimer();
    }
    
    /**
     * Initializes the panel components.
     */
    private void initializePanel() {
        setLayout(new GridBagLayout());
        setBackground(PANEL_BACKGROUND);
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
            "Simulation Statistics",
            0, 0,
            HEADER_FONT,
            HEADER_COLOR));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Section: Vehicle Counts
        addSectionHeader("Vehicle Counts", gbc, 0);
        
        totalCrossedLabel = createStatLabel("Total Crossed: 0", gbc, 1);
        verticalCrossedLabel = createStatLabel("Vertical Crossed: 0", gbc, 2);
        horizontalCrossedLabel = createStatLabel("Horizontal Crossed: 0", gbc, 3);
        
        // Section: Current Status
        addSectionHeader("Current Status", gbc, 4);
        
        verticalWaitingLabel = createStatLabel("Vertical Waiting: 0", gbc, 5);
        horizontalWaitingLabel = createStatLabel("Horizontal Waiting: 0", gbc, 6);
        
        // Section: Wait Times
        addSectionHeader("Average Wait Times", gbc, 7);
        
        avgVerticalWaitLabel = createStatLabel("Vertical Avg: 0.0 ms", gbc, 8);
        avgHorizontalWaitLabel = createStatLabel("Horizontal Avg: 0.0 ms", gbc, 9);
        overallAvgWaitLabel = createStatLabel("Overall Avg: 0.0 ms", gbc, 10);
        
        // Section: Traffic Light Status
        addSectionHeader("Traffic Light Status", gbc, 11);
        
        lightStatusLabel = createStatLabel("Status: Vertical GREEN", gbc, 12);
        lightStatusLabel.setForeground(Color.GREEN);
        countdownLabel = createStatLabel("Countdown: " + currentCountdown + "s", gbc, 13);
        countdownLabel.setForeground(Color.RED);
    }
    
    /**
     * Adds a section header label.
     */
    private void addSectionHeader(String text, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel header = new JLabel(text);
        header.setFont(HEADER_FONT);
        header.setForeground(HEADER_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(header, gbc);
        
        gbc.gridwidth = 1;
    }
    
    /**
     * Creates a statistics label.
     */
    private JLabel createStatLabel(String text, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel label = new JLabel(text);
        label.setFont(VALUE_FONT);
        add(label, gbc);
        
        gbc.gridwidth = 1;
        return label;
    }
    
    /**
     * Starts the update timer to refresh statistics.
     */
    private void startUpdateTimer() {
        updateTimer = new Timer(UPDATE_INTERVAL_MS, e -> updateStatistics());
        updateTimer.start();
    }
    
    /**
     * Updates all statistics displays.
     */
    private void updateStatistics() {
        // Update vehicle counts
        totalCrossedLabel.setText("Total Crossed: " + statisticsTracker.getTotalVehiclesCrossed());
        verticalCrossedLabel.setText("Vertical Crossed: " + statisticsTracker.getVerticalVehiclesCrossed());
        horizontalCrossedLabel.setText("Horizontal Crossed: " + statisticsTracker.getHorizontalVehiclesCrossed());
        
        // Update waiting counts
        verticalWaitingLabel.setText("Vertical Waiting: " + statisticsTracker.getVerticalVehiclesWaiting());
        horizontalWaitingLabel.setText("Horizontal Waiting: " + statisticsTracker.getHorizontalVehiclesWaiting());
        
        // Update wait times
        double avgVertical = statisticsTracker.getAverageVerticalWaitTime();
        double avgHorizontal = statisticsTracker.getAverageHorizontalWaitTime();
        double avgOverall = statisticsTracker.getOverallAverageWaitTime();
        
        avgVerticalWaitLabel.setText(String.format("Vertical Avg: %.1f ms", avgVertical));
        avgHorizontalWaitLabel.setText(String.format("Horizontal Avg: %.1f ms", avgHorizontal));
        overallAvgWaitLabel.setText(String.format("Overall Avg: %.1f ms", avgOverall));
        
        // Update traffic light status and countdown
        updateTrafficLightStatus();
    }
    
    /**
     * Updates the traffic light status and countdown display.
     */
    private void updateTrafficLightStatus() {
        int lightState = trafficController.getCurrentLightState();
        
        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - lastLightChangeTime;
        int remaining = (int) ((TrafficController.getTrafficLightChangeInterval() - elapsed) / 1000);
        
        if (remaining < 0) {
            remaining = 0;
            lastLightChangeTime = currentTime;
        }
        
        currentCountdown = remaining;
        
        if (lightState == 1) {
            lightStatusLabel.setText("Status: Vertical Lane - GREEN");
            lightStatusLabel.setForeground(Color.GREEN);
        } else {
            lightStatusLabel.setText("Status: Horizontal Lane - GREEN");
            lightStatusLabel.setForeground(Color.GREEN);
        }
        
        countdownLabel.setText("Countdown: " + currentCountdown + "s");
        
        // Change countdown color as it approaches 0
        if (currentCountdown <= 2) {
            countdownLabel.setForeground(Color.RED);
        } else if (currentCountdown <= 5) {
            countdownLabel.setForeground(Color.ORANGE);
        } else {
            countdownLabel.setForeground(Color.BLUE);
        }
    }
    
    /**
     * Notifies that the traffic light has changed.
     */
    public void onTrafficLightChanged() {
        lastLightChangeTime = System.currentTimeMillis();
        currentCountdown = TrafficController.getTrafficLightChangeInterval() / 1000;
    }
    
    /**
     * Stops the update timer.
     */
    public void stop() {
        if (updateTimer != null) {
            updateTimer.stop();
        }
    }
}

