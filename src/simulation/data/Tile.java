package simulation.data;

import org.jfree.fx.FXGraphics2D;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author Dustin Hendriks
 * The Tile class represents 1 tile read from the JSON schoolmap file. It contains information about the x pos, y pos, width, height and which image the tile contains. It also contains a draw Class for easy drawing the tile.
 */

class Tile {
    private int tileWidth = 32;
    private int tileHeight = 32;
    private BufferedImage tileImage;
    private FXGraphics2D g2d;
    private int x;
    private int y;

    /**
     * The Tile class needs some values when created.
     * @param image The image parameter defines which image the JSON file contains.
     * @param x Defines the x pos
     * @param y Defines the y pos
     * @param g2d Is needed for drawing
     */

    Tile(BufferedImage image, int x, int y, FXGraphics2D g2d) {
        this.tileImage = image;
        this.x =x;
        this.y =y;
        this.g2d=g2d;
    }

    /**
     * The draw method drwas this.tileImage on the x and y position.
     */

    void draw() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToTranslation(x*tileWidth,y*tileHeight);
        g2d.drawImage(tileImage, affineTransform, null);
    }
}
