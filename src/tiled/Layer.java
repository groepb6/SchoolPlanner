package tiled;

import org.jfree.fx.FXGraphics2D;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * Layer creates and keeps the Tile objects it is supposed to have, so that each layer can be drawn separately.
 */
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
     * Creates a Layer object.
     * Executes the addTiles method in order to create the Tile objects.
     * This constructor does not allow for the creation of object layers, even though they are included in the JsonArray of layers.
     *
     * @param jsonLayer Given by the Map when it creates a Layer object.
     * @param tileSets  Given by the Map when it creates a Layer object.
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

    /**
     * Executes the draw method of each Tile contained in this object.
     *
     * @param graphics A parameter given by Map when it creates a Layer object.
     */
    public void draw(FXGraphics2D graphics) {
        for (Tile tile : this.tiles) {
            tile.draw(graphics);
        }
    }

    /**
     * Calculates the amount of non-empty tiles that the data of this layer contains, to create an array with that size.
     * Goes through the height and width of this Layer in order to add all of the tiles the Layer is supposed to have.
     * Checks for each TileSet if the tile data is higher than the current firstGlobalId and lower than the next.
     * Then adds a new Tile using the image from the appropriate TileSet.
     *
     * @param data
     */
    private void addTiles(JsonArray data) {
        int tileCount = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.getInt(i) != 0) {
                tileCount++;
            }
        }
        this.tiles = new Tile[tileCount];

        int tileIndex = 0;
        int dataIndex = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (data.getInt(dataIndex) != 0) {

                    for (int i = 0; i < this.tileSets.length - 1; i++) {
                        if (data.getInt(dataIndex) > this.tileSets[i].getFirstGlobalId() && data.getInt(dataIndex) < this.tileSets[i + 1].getFirstGlobalId()) { //checking if between current and next
                            this.tiles[tileIndex] = new Tile(
                                    this.tileSets[i].getSubImages()[data.getInt(dataIndex) - this.tileSets[i].getFirstGlobalId()],
                                    x, y, this.tileSets[i].getTileWidth(), this.tileSets[i].getTileHeight());

                        }
                    }
                    tileIndex++;
                    //todo: adds tiles from last tileset
                }
                dataIndex++;
            }
        }


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
