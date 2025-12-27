package carrefour;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Base class for vehicle sprites that can be rendered on the screen.
 * 
 * @author PC-DELL
 */
public abstract class VehicleSprite extends JPanel {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image vehicleImage;
    protected String imagePath;
    
    /**
     * Creates a new vehicle sprite.
     * 
     * @param imagePath path to the vehicle image file
     * @param x initial x position
     * @param y initial y position
     * @param width sprite width
     * @param height sprite height
     */
    public VehicleSprite(String imagePath, int x, int y, int width, int height) {
        this.imagePath = imagePath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setOpaque(false);
        loadImage();
    }
    
    /**
     * Loads the vehicle image from file.
     */
    private void loadImage() {
        try {
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                vehicleImage = ImageIO.read(imgFile);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, 
                "Could not load vehicle image: " + imagePath + "\n" + ex.getMessage(),
                "Image Loading Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Updates the vehicle's position.
     * 
     * @param deltaX change in x position
     * @param deltaY change in y position
     */
    public void updatePosition(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
        this.setBounds(x, y, width, height);
    }
    
    /**
     * Gets the current x position.
     * 
     * @return x position
     */
    public int getX() {
        return x;
    }
    
    /**
     * Gets the current y position.
     * 
     * @return y position
     */
    public int getY() {
        return y;
    }
    
    /**
     * Gets the sprite width.
     * 
     * @return width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Gets the sprite height.
     * 
     * @return height
     */
    public int getHeight() {
        return height;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (vehicleImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(vehicleImage, 0, 0, width, height, null);
        }
    }
}

