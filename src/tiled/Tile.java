package tiled;

import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    private int mapX;
    private int mapY;
    private int tileWidth;
    private int tileHeight;

    /**
     * Represents a single type of tile.
     *
     * @param image A subimage recieved from the TileSet
     */
    public Tile(BufferedImage image, int mapX, int mapY, int tileWidth, int tileHeight) {
        this.image = image;
        this.mapX = mapX;
        this.mapY = mapY;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public void draw(FXGraphics2D graphics) {
        AffineTransform transform = new AffineTransform();
        transform.setToTranslation(this.mapX*this.tileWidth,this.mapY*this.tileHeight);
        graphics.drawImage(image, transform, null);
    }

    @Override
    public String toString() {
        return "Tile{" +
                "image=" + image +
                ", mapX=" + mapX +
                ", mapY=" + mapY +
                ", tileWidth=" + tileWidth +
                ", tileHeight=" + tileHeight +
                '}';
    }
}
