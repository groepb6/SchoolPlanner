package tiled;

import javax.json.JsonObject;
import java.util.List;

/**
 * Only compatible with Orthogonal maps
 */
public class Map {
    private String backgroundColor;
    private int height;
    private int width;
    private boolean infinite;
    private int tileHeight;
    private int tileWidth;
    private String renderOrder;
    private int nextLayerId;
    private int nextObjectId;
    private String orientation;
    private String[] properties; //do we need this?
    private TileMap[] tileSets;
    private String type;


    private List<Layer> layers;

    public Map(JsonObject jsonMap) {

    }

    public void draw() {

    }

}