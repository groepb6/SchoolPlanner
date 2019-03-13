package tiled;

import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Layer {
    private Tile[] tiles;
    private TileSet[] tileSets;
    private String name;
    private int height; // can be same as map
    private int width; //can be same as map
    private String drawOrder;
    private String encoding;
    private int id;
    private double opacity;
    private String type;
    private boolean visible;
    private int x; //always 0
    private int y; //always 0
    private double offsetX; //default = 0
    private double offsetY; //default = 0

    /**
     * Represents a layer that consists of Tile objects.
     * @param jsonLayer Given by the Map object upon creation.
     * @param tileSets Given by the Map object upon creation.
     */
    public Layer(JsonObject jsonLayer, TileSet[] tileSets) {
        if (jsonLayer.getString("type").equals("tilelayer")) {
            this.tileSets = tileSets;
            this.name = jsonLayer.getString("name");
            this.height = jsonLayer.getInt("height");
            this.width = jsonLayer.getInt("width");
            this.addTiles(jsonLayer.getJsonArray("data"));
            this.drawOrder = jsonLayer.getString("draworder", "topdown");
            this.encoding = jsonLayer.getString("encoding", "csv");
            this.id = jsonLayer.getInt("id");
            this.opacity = jsonLayer.getJsonNumber("opacity").doubleValue();
            this.type = jsonLayer.getString("type");
            this.visible = jsonLayer.getBoolean("visible", true);
            this.x = 0;
            this.y = 0;
            try {
                this.offsetX = jsonLayer.getJsonNumber("offsetx").doubleValue();
                this.offsetY = jsonLayer.getJsonNumber("offsety").doubleValue();
            } catch (Exception exception) {
                this.offsetX = 0;
                this.offsetY = 0;
            }
        } else {
            System.out.println("You tried loading a non-tilelayer file into a tilelayer object!");
        }
    }

    public void draw(FXGraphics2D graphics) {
        for (Tile tile : this.tiles) {
            tile.draw(graphics);
        }
    }

    /**
     * First it calculates what size the tiles array needs to be. Then it fills the array with new Tile objects.
     *
     */
    private void addTiles(JsonArray data) {
        int tileCount = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.getInt(i) != 0) {
                tileCount ++;
            }
        }
        this.tiles = new Tile[tileCount];

        int tileIndex = 0;
        int dataIndex = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (data.getInt(dataIndex) != 0) {

                    for (int i = 0; i < this.tileSets.length - 1; i++) {
                        if (data.getInt(dataIndex) < this.tileSets[i+1].getFirstGlobalId()) {
                            this.tiles[tileIndex] = new Tile(
                                    this.tileSets[i].getSubImages()[data.getInt(dataIndex + 1) - this.tileSets[i].getFirstGlobalId()],
                                    x, y, this.tileSets[i].getTileWidth(), this.tileSets[i].getTileHeight());
                        } //todo: fix out of bounds error
                    }
                    tileIndex++;
                    //todo: adds tiles from last layer
                }
                dataIndex++;
            }
        }


    }

    private void addData() {
        //todo: create addData method
    }

    @Override
    public String toString() {
        return "Layer{" +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", drawOrder='" + drawOrder + '\'' +
                ", encoding='" + encoding + '\'' +
                ", id=" + id +
                ", opacity=" + opacity +
                ", type='" + type + '\'' +
                ", visible=" + visible +
                ", x=" + x +
                ", y=" + y +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                '}';
    }
}
