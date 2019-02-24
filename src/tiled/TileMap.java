package tiled;

import javax.imageio.ImageIO;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class to represent a tilemap. Contains a list of the types of tiles it has.
 */
public class TileMap {
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

    public TileMap(JsonObject jsonTileMap) {
        try {
            //File imageFile = new File(this.getClass().getResource("tiled/tilemaps/") + jsonTileMap.getString("image") + ".bmp");
            File imageFile = new File("D:\\School\\Avans jaar 1\\Periode 3\\Proftaak\\SchoolPlanner\\resources\\tiled\\tilemaps\\Test.bmp");
            System.out.println("Attempting to load from: " + imageFile);
            this.image = ImageIO.read(imageFile);
        } catch (IOException exception) {
            System.out.println("Image loading failed!");
        }
        if (jsonTileMap.getString("type").equals("tileset")) {
            this.imageHeight = jsonTileMap.getInt("imageheight");
            this.imageWidth = jsonTileMap.getInt("imagewidth");
            this.margin = jsonTileMap.getInt("margin");
            this.name = jsonTileMap.getString("name");
            this.spacing = jsonTileMap.getInt("spacing");
            this.tileCount = jsonTileMap.getInt("tilecount");
            this.tileHeight = jsonTileMap.getInt("tileheight");
            this.tileWidth = jsonTileMap.getInt("tilewidth");
            this.type = jsonTileMap.getString("type");
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
            this.tiles.add(new Tile(this.image.getSubimage(this.tileWidth * (i%columns),
                    this.tileHeight * (i/rows),
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
