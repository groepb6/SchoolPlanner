package tiled;

import javax.imageio.ImageIO;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileSet {
    private BufferedImage image;
    private int firstGlobalId;
    private int imageHeight;
    private int imageWidth;
    private int margin;
    private int spacing;
    private int tileHeight;
    private int tileWidth;
    private BufferedImage[] subImages;

    /**
     * Represents a tileset. Contains a list of the types of tiles it has.
     *
     * @param jsonTileSet This JsonObject can be obtained from TiledReader.readTileSet()
     */
    public TileSet(JsonObject jsonTileSet, int tileWidth, int tileHeight) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/tiles/tilesets/"+jsonTileSet.getString("link")));
            System.out.println("Attempting to load image");
            this.image = image;
        } catch (IOException exception) {
            System.out.println("Image loading failed!");
            exception.printStackTrace();
        }
        this.firstGlobalId = jsonTileSet.getInt("firstgid");
        this.imageWidth = this.image.getWidth();
        this.imageHeight = this.image.getHeight();
        this.margin = jsonTileSet.getInt("margin", 0);
        this.spacing = jsonTileSet.getInt("spacing", 0);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.addSubImages();
    }

    /**
     * Calculates the amount of tiles that would fit in the spritesheet. Then creates an array with that size
     */
    private void addSubImages() {
        int imageCount = 0;
        for (int y = 0; y < this.imageHeight; y += this.tileHeight) {
            for (int x = 0; x < this.imageWidth; x += this.tileWidth) {
                imageCount++;
            }
        }
        this.subImages = new BufferedImage[imageCount];

        int imageIndex = 0;
        for (int y = 0; y < this.imageHeight; y += this.tileHeight) {
            for (int x = 0; x < this.imageWidth; x += this.tileWidth) {
                this.subImages[imageIndex] = this.image.getSubimage(x, y, this.tileWidth, this.tileHeight);
                imageIndex++;
            }
        }
    }

    public int getFirstGlobalId() {
        return firstGlobalId;
    }

    public BufferedImage[] getSubImages() {
        return subImages;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    @Override
    public String toString() {
        return "TileSet{" +
                "image=" + image +
                ", firstGlobalId=" + firstGlobalId +
                ", imageHeight=" + imageHeight +
                ", imageWidth=" + imageWidth +
                ", margin=" + margin +
                ", spacing=" + spacing +
                ", tileHeight=" + tileHeight +
                ", tileWidth=" + tileWidth +
                ", subImages=" + subImages.length +
                '}';
    }
}
