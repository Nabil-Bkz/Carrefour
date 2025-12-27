package carrefour;

/**
 * Sprite for vertical lane vehicles (taxis moving up/down).
 * 
 * @author PC-DELL
 */
public class TaxiSprite extends VehicleSprite {
    private static final String IMAGE_PATH = "resources/taxi.png";
    
    /**
     * Creates a new taxi sprite for vertical movement.
     * 
     * @param x initial x position
     * @param y initial y position
     * @param width sprite width
     * @param height sprite height
     */
    public TaxiSprite(int x, int y, int width, int height) {
        super(IMAGE_PATH, x, y, width, height);
    }
}

