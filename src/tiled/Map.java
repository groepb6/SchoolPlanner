package tiled;

import org.jfree.fx.FXGraphics2D;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * Map object can be drawn to show a Tiled.exe map from a .json file.
 * Only a Map object should have to be created for the possibility to draw a map.
 * Because the Map object creates all of the necessary TileSet, Layer and Tile objects.
 */
public class Map {
    private int height;
    private int width;
    private boolean infinite;
    private int tileHeight;
    private int tileWidth;
    private String renderOrder;
    private int nextLayerId;
    private int nextObjectId;
    private String orientation;
    private TileSet[] tileSets;
    private Layer[] layers;
    private String type;

    /**
     * Creates a Map object.
     * Executes the addTileSets method to add all of the needed TileSet objects.
     * Executes the addLayers method to add all of the needed Layer objects.
     *
     * @param jsonMap This JsonObject can be obtained from TiledReader.readMap(). It should refer to the Tiled.exe map file that needs to be drawn.
     */
    public Map(JsonObject jsonMap) { //Todo: check if orthogonal
        if (jsonMap.getString("type").equals("map")) {
            this.height = jsonMap.getInt("height");
            this.width = jsonMap.getInt("width");
            this.infinite = jsonMap.getBoolean("infinite", false);
            this.tileHeight = jsonMap.getInt("tileheight");
            this.tileWidth = jsonMap.getInt("tilewidth");
            this.renderOrder = jsonMap.getString("renderorder");
            this.nextLayerId = jsonMap.getInt("nextlayerid");
            this.nextObjectId = jsonMap.getInt("nextobjectid");
            this.orientation = jsonMap.getString("orientation");
            this.addTileSets(jsonMap.getJsonArray("tilesets"));
            this.addLayers(jsonMap.getJsonArray("layers"));
            this.type = jsonMap.getString("type");
        } else {
            System.out.println("You tried loading a non-map file into a map object!");
        }
    }

    /**
     * Executes the draw method of each Layer object, which in turn execute the draw method for each Tile object they contain.
     *
     * @param graphics An FXGraphics2D object to draw the Map with. Translations done to this object should not break the way the Map is drawn.
     */
    public void draw(FXGraphics2D graphics) {
        for (Layer layer : this.layers) {
            layer.draw(graphics);
        }
    }

    /**
     * Adds all of the tileSet objects needed to create the Layer objects.
     *
     * @param tileSets An array that is read from the .json file in the constructor of Map.
     */
    private void addTileSets(JsonArray tileSets) {
        this.tileSets = new TileSet[tileSets.size()];
        for (int i = 0; i < tileSets.size(); i++) {
            this.tileSets[i] = new TileSet(tileSets.getJsonObject(i), this.tileWidth, this.tileHeight);
        }
    }

    /**
     * Adds all of the layers that this map should contain.
     * The tilesets should be created before the layers are created.
     *
     * @param layers An array that is read from the .json file in the constructor of Map.
     */
    private void addLayers(JsonArray layers) {
        this.layers = new Layer[layers.size()];
        for (int i = 0; i < layers.size(); i++) {
            this.layers[i] = new Layer(layers.getJsonObject(i), this.tileSets);
        }
    }

    @Override
    public String toString() {
        return "Map{" +
                "height=" + height +
                ", width=" + width +
                ", infinite=" + infinite +
                ", tileHeight=" + tileHeight +
                ", tileWidth=" + tileWidth +
                ", renderOrder='" + renderOrder + '\'' +
                ", nextLayerId=" + nextLayerId +
                ", nextObjectId=" + nextObjectId +
                ", orientation='" + orientation + '\'' +
                ", tileSets=" + tileSets +
                ", layers=" + layers +
                ", type='" + type + '\'' +
                '}';
    }
}