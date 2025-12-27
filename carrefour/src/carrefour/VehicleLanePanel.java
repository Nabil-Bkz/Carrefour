package carrefour;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Panel that displays vehicles in a lane (either vertical or horizontal).
 * 
 * @author PC-DELL
 */
public class VehicleLanePanel extends JPanel {
    private final boolean isVertical;
    private final List<VehicleSprite> vehicles;
    
    /**
     * Creates a new vehicle lane panel.
     * 
     * @param isVertical true if this is a vertical lane, false if horizontal
     */
    public VehicleLanePanel(boolean isVertical) {
        this.isVertical = isVertical;
        this.vehicles = new ArrayList<>();
        this.setLayout(null);
        this.setOpaque(false);
    }
    
    /**
     * Adds a vehicle to this lane.
     * 
     * @param vehicle the vehicle sprite to add
     */
    public void addVehicle(VehicleSprite vehicle) {
        if (vehicle != null) {
            vehicles.add(vehicle);
            this.add(vehicle);
            vehicle.setBounds(vehicle.getX(), vehicle.getY(), 
                            vehicle.getWidth(), vehicle.getHeight());
        }
    }
    
    /**
     * Removes a vehicle from this lane.
     * 
     * @param vehicle the vehicle sprite to remove
     */
    public void removeVehicle(VehicleSprite vehicle) {
        if (vehicle != null) {
            vehicles.remove(vehicle);
            this.remove(vehicle);
        }
    }
    
    /**
     * Checks if this is a vertical lane.
     * 
     * @return true if vertical, false if horizontal
     */
    public boolean isVertical() {
        return isVertical;
    }
}

