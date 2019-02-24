package tiled;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;

    /**
     * Represents a single type of tile.
     *
     * @param image A subimage recieved from the TileSet
     */
    public Tile(BufferedImage image) {
        this.image = image;
    }

}
