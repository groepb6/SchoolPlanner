package simulation.data;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.geom.Point2D;

public class Area {
    public JsonArray data;
    public String areaName;
    public int areaID;
    public int areaWidth;
    public int areaHeight;
    public int x;
    public int y;

    public Area(JsonObject layer) {
        data = layer.getJsonArray("data");
        areaName = layer.getString("name");
        areaID = layer.getInt("id");
        areaWidth = layer.getInt("width");
        areaHeight = layer.getInt("height");
        x = layer.getInt("x");
        y = layer.getInt("y");
    }

    /**
     * Calculates the center point of the Area.
     * @return A Point2D marking the center of the Area.
     */
    public Point2D getCenter() {
        return new Point2D.Double(this.x + (this.areaWidth / 2), this.y + (this.areaHeight / 2));
    }
}
