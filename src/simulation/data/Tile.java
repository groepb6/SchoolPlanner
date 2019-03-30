package simulation.data;

import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

class Tile {
    private int tileWidth = 32;
    private int tileHeight = 32;
    private BufferedImage tileImage;
    private FXGraphics2D g2d;
    private int x;
    private int y;

    Tile(BufferedImage image, int x, int y, FXGraphics2D g2d) {
        this.tileImage = image;
        this.x = x;
        this.y = y;
        this.g2d = g2d;
    }

    void draw() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToTranslation(x * tileWidth, y * tileHeight);
        g2d.drawImage(tileImage, affineTransform, null);
    }
}
