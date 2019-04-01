package simulation.data;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * @author Dustin Hendriks
 * The Area class contains information about a specific zone. Information such as x value, y value, width, height can be retrieved.
 */

public class Area {
    public JsonArray data;
    public String areaName;
    public int areaID;
    public int areaWidth;
    public int areaHeight;
    public int x;
    public int y;

    /**
     * The area constructor gets parsed a JsonObject area. Out of the area information can be retrieved.
     * @param area Is needed to retrieve information.
     */

    public Area(JsonObject area) {
        data = area.getJsonArray("data");
        areaName = area.getString("name");
        areaID = area.getInt("id");
        areaWidth = area.getInt("width");
        areaHeight = area.getInt("height");
        x = area.getInt("x");
        y = area.getInt("y");
    }
}
