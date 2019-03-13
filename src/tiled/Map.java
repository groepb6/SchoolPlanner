package tiled;

import org.jfree.fx.FXGraphics2D;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
     * This class represents a map that can be drawn. It is only compatible with orthogonal maps.
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

    public void draw(FXGraphics2D graphics) {
        for (Layer layer : this.layers) {
            layer.draw(graphics);
        }
    }

    private void addTileSets(JsonArray tileSets) {
        //todo: have this method add TileSet objects, testing
        this.tileSets = new TileSet[tileSets.size()];
        for (int i = 0; i < tileSets.size(); i++) {
            this.tileSets[i] = new TileSet(tileSets.getJsonObject(i), this.tileWidth, this.tileHeight);
        }
    }

    private void addLayers(JsonArray layers) {
        //todo: have this method add Layer objects
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