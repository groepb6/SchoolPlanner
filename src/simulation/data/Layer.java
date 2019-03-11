package simulation.data;

import org.jfree.fx.FXGraphics2D;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Layer {
    private JsonObject layer;
    private JsonArray data;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<BufferedImage> subImages = new ArrayList<>();
    private String layerName;
    private int layerID;
    private int layerWidth;
    private int layerHeight;
    private FXGraphics2D g2d;

    public Layer(JsonObject layer, FXGraphics2D g2d, ArrayList<BufferedImage> subImages) {
       this.layer = layer;
       this.subImages = subImages;
       this.g2d = g2d;
       data = layer.getJsonArray("data");
       layerName = layer.getString("name");
       layerID = layer.getInt("id");
       layerWidth = layer.getInt("width");
       layerHeight = layer.getInt("height");
       saveTiles();
    }

    public void saveTiles() {
        int index = 0;
        for (int y = 0; y < layerHeight; y++)
            for (int x = 0; x < layerWidth; x++) {
                if (data.getInt(index) != 0)
                    tiles.add(new Tile(subImages.get(data.getInt(index)-1), x, y, g2d));
                index++;
            }
    }

    public void draw() {
        for (Tile tile : tiles) {
            tile.draw();
        }
    }

    public int getLayerID() {
        return layerID;
    }
    public String getLayerName() { return layerName; }
}
