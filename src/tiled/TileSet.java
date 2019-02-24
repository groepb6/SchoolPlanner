package tiled;

import javax.imageio.ImageIO;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TileSet {
    private BufferedImage image;
    private int imageHeight;
    private int imageWidth;
    private int margin;
    private String name;
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
    public TileSet(JsonObject jsonTileSet) {
        if (jsonTileSet.getString("type").equals("tileset")) {
            try {
                File imageFile = new File(this.getClass().getResource("tiled/tilesets/") + jsonTileSet.getString("image") + ".bmp"); //todo: fix
                System.out.println("Attempting to load from: " + imageFile);
                this.image = ImageIO.read(imageFile);
            } catch (IOException exception) {
                System.out.println("Image loading failed!");
            }
            this.imageHeight = jsonTileSet.getInt("imageheight");
            this.imageWidth = jsonTileSet.getInt("imagewidth");
            this.margin = jsonTileSet.getInt("margin");
            this.name = jsonTileSet.getString("name");
            this.spacing = jsonTileSet.getInt("spacing");
            this.tileCount = jsonTileSet.getInt("tilecount");
            this.tileHeight = jsonTileSet.getInt("tileheight");
            this.tileWidth = jsonTileSet.getInt("tilewidth");
            this.type = jsonTileSet.getString("type");
            this.addTiles();
        } else {
            System.out.println("You tried loading a non-tileset file into a tileset object!");
        }
    }

    /**
     * Adds all of the tiles to the tiles list
     */
    private void addTiles() {
        int rows = imageHeight / tileHeight;
        int columns = imageWidth / tileWidth;
        for (int i = 0; i < this.tileCount; i++) {
            this.tiles.add(new Tile(this.image.getSubimage(this.tileWidth * (i % columns),
                    this.tileHeight * (i / rows),
                    this.tileWidth,
                    this.tileHeight)));
        }
    }

    public String getName() {
        return name;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

}
