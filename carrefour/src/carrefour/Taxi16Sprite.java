package carrefour;

/**
 * Sprite for horizontal lane vehicles (taxis moving left/right).
 * 
 * @author PC-DELL
 */
public class Taxi16Sprite extends VehicleSprite {
    private static final String IMAGE_PATH = "resources/taxi16.png";
    
    /**
     * Creates a new taxi sprite for horizontal movement.
     * 
     * @param x initial x position
     * @param y initial y position
     * @param width sprite width
     * @param height sprite height
     */
    public Taxi16Sprite(int x, int y, int width, int height) {
        super(IMAGE_PATH, x, y, width, height);
    }
}

