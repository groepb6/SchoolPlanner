package tiled;

import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Represents a tile that can be drawn to create a map.
 */
public class Tile {
    private BufferedImage image;
    private int mapX;
    private int mapY;
    private int tileWidth;
    private int tileHeight;

    /**
     * Creates a Tile object.
     *
     * @param image      A BufferedImage obtained from the TileSet.
     * @param mapX       The X position the tile has on the map, regardless of tile size.
     * @param mapY       The Y position the tile has on the map, regardless of tile size.
     * @param tileWidth  The amount of pixels the tile reaches in width in the TileSet.
     * @param tileHeight The amount of pixels the tile reaches in height in the TileSet.
     */
    public Tile(BufferedImage image, int mapX, int mapY, int tileWidth, int tileHeight) {
        this.image = image;
        this.mapX = mapX;
        this.mapY = mapY;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    /**
     * Draws a single Tile.
     *
     * @param graphics A parameter given by Layer when it creates a Tile object.
     */
    public void draw(FXGraphics2D graphics) { //Todo: test
        AffineTransform transform = new AffineTransform();
        transform.setToTranslation(this.mapX * this.tileWidth, this.mapY * this.tileHeight);
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
