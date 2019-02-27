package tiled;

import javax.imageio.ImageIO;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TileSet {
    private BufferedImage image;
    private int imageHeight;
    private int imageWidth;
    private int margin;
    private int spacing;
    private int tileCount;
    private int tileHeight;
    private int tileWidth;
    private String type;
    private List<Tile> tiles;

    /**
     * Represents a tileset. Contains a list of the types of tiles it has.
     *
     * @param jsonTileSet This JsonObject can be obtained from TiledReader.readTileSet()
     */
    public TileSet(JsonObject jsonTileSet, int tileWidth, int tileHeight) {
        try {
            String path = this.getClass().getResource("/tiled/tilesets") + jsonTileSet.getString("link");
            File imageFile = new File(path);
            System.out.println("Attempting to load from: " + imageFile);
            this.image = ImageIO.read(imageFile);
        } catch (IOException exception) {
            System.out.println("Image loading failed!");
        }
        this.imageWidth = this.image.getWidth();
        this.imageHeight = this.image.getHeight();
        this.margin = jsonTileSet.getInt("margin", 0);
        this.spacing = jsonTileSet.getInt("spacing", 0);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tiles = new ArrayList<>();
        this.addTiles();
    }

    /**
     * Adds all of the tiles to the tiles list
     */
    private void addTiles() {
        int rows = imageHeight / tileHeight; //todo: test
        int columns = imageWidth / tileWidth; //todo: test
        for (int i = 0; i < rows * columns; i++) {
            this.tiles.add(new Tile(this.image.getSubimage(this.tileWidth * (i % columns),
                    this.tileHeight * (i / rows),
                    this.tileWidth,
                    this.tileHeight)));
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

}
