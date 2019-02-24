package tiled;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private int height;
    private int width;
    private boolean infinite;
    private int tileHeight;
    private int tileWidth;
    private String renderOrder;
    private String backgroundColor;
    private int nextLayerId;
    private int nextObjectId;
    private String orientation;
    private List<String> properties; //do we need this?
    private List<TileSet> tileSets;
    private List<Layer> layers;
    private String type;

    /**
     * This class represents a map that can be drawn. It is only compatible with orthogonal maps.
     * todo: Map creates all needed Layer and TileSet objects by itself
     *
     * @param jsonMap This JsonObject can be obtained from TiledReader.readMap()
     */
    public Map(JsonObject jsonMap) {
        if (jsonMap.getString("type").equals("map")) {
            this.height = jsonMap.getInt("height");
            this.width = jsonMap.getInt("width");
            this.infinite = jsonMap.getBoolean("infinite", false);
            this.tileHeight = jsonMap.getInt("tileheight");
            this.tileWidth = jsonMap.getInt("tilewidth");
            this.renderOrder = jsonMap.getString("renderorder");
            this.backgroundColor = jsonMap.getString("backgroundcolor");
            this.nextLayerId = jsonMap.getInt("nextlayerid");
            this.nextObjectId = jsonMap.getInt("nextobjectid");
            this.orientation = jsonMap.getString("orientation");
            this.addProperties(jsonMap);
            this.addTileSets();
            this.addLayers();
            this.type = jsonMap.getString("type");
        } else {
            System.out.println("You tried loading a non-map file into a map object!");
        }
    }

    public void draw() {

    }

    /**
     * Adds the properties from the json file
     *
     * @param jsonMap This JsonObject can be obtained in the constructor
     */
    private void addProperties(JsonObject jsonMap) {
        this.properties = new ArrayList<>();
        JsonArray jsonArray = jsonMap.getJsonArray("properties");
        for (int i = 0; i < jsonArray.size(); i++) {
            this.properties.add(jsonArray.getString(i));
        }
    }

    private void addTileSets() {
        //todo: have this method add TileSet objects
    }

    private void addLayers() {
        //todo: have this method add Layer objects
    }

}