package simulation.data;

import data.objects.Chair;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;
import simulation.pathfinding.Node;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Dustin Hendriks
 * The layer class can contain unique methods for specific layers. It can also draw a layer with one command; draw.
 */

public class Layer {
    private JsonObject layer;
    private JsonArray data;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<BufferedImage> subImages;
    private String layerName;
    private int layerID;
    private int layerWidth;
    private int layerHeight;
    private FXGraphics2D g2d;
    private Node nodes[][];
    private Chair chairs[][];
    private Canvas canvas;

    /**
     * The layer constructor needs some values to enable the drawing.
     * @param layer The "layer" parameter is the JsonObject layer. This layer can be read and interpreted later.
     * @param g2d The g2d component is needed to draw tiles.
     * @param subImages The subImages array contains every Image in the spritesheets so it can be passed to an image.
     * @param canvas The canvas is needed to define the width of the map and height of the map.
     */

    public Layer(JsonObject layer, FXGraphics2D g2d, ArrayList<BufferedImage> subImages, Canvas canvas) {
       this.layer = layer;
       this.canvas = canvas;
       this.subImages = subImages;
       this.g2d = g2d;
       data = layer.getJsonArray("data");
       layerName = layer.getString("name");
       layerID = layer.getInt("id");
       layerWidth = layer.getInt("width");
       layerHeight = layer.getInt("height");
       saveTiles();
    }

    /**
     * The saveTiles() method saves all tiles in this layer. If there are chairs in the layer (name = "SchoolInteriorStudentChairs") a new chair is added with a target direction.
     */

    private void saveTiles() {
        boolean isCollisionLayer = layerName.equals("Collision");
        boolean isStudentChairLayer = layerName.equals("SchoolInteriorStudentChairs");
        boolean isTeacherChairLayer = layerName.equals("SchoolInteriorTeacherChairs");

        int x;
        int y;

        if (isCollisionLayer)
            nodes = new Node[layerWidth][layerHeight];
        if (isStudentChairLayer)
            chairs= new Chair[layerWidth][layerHeight];

        int index = 0;
        for (y = 0; y < layerHeight; y++)
            for (x = 0; x < layerWidth; x++) {
                if (isCollisionLayer) {
                    nodes[x][y] = new Node(x,y,-1);
                    nodes[x][y].walkable=true;
                }

                if (isStudentChairLayer) {
                    chairs[x][y] = new Chair();
                    chairs[x][y].setAvailable(false);
                    chairs[x][y].isChair=false;
                }

                if (data.getInt(index) != 0) {
                  try {
                      tiles.add(new Tile(subImages.get(data.getInt(index) - 1), x, y, g2d));
                  } catch (IndexOutOfBoundsException e) {
                      System.out.println("Have you pasted the tileset links in the schoolmap.json file? If not add ''link'' under every source path under the tilesets array.");
                  }
                    if (isCollisionLayer)
                        nodes[x][y].walkable=false;
                    if (isStudentChairLayer) {
                        chairs[x][y].isChair = true;
                        chairs[x][y].setAvailable(true);
                        chairs[x][y].isAvailable=true;
                        chairs[x][y].x=x;
                        chairs[x][y].y=y;

                        switch (data.getInt(index)-1) {
                            case 1562: chairs[x][y].setDir(Chair.Direction.DOWN); break;
                            case 1563: chairs[x][y].setDir(Chair.Direction.DOWN); break;
                            case 1564: chairs[x][y].setDir(Chair.Direction.UP); break;
                            case 1565: chairs[x][y].setDir(Chair.Direction.RIGHT); break;
                            case 1572: chairs[x][y].setDir(Chair.Direction.RIGHT); break;
                            case 1573: chairs[x][y].setDir(Chair.Direction.LEFT); break;
                            case 1574: chairs[x][y].setDir(Chair.Direction.LEFT); break;
                            case 1575: chairs[x][y].setDir(Chair.Direction.UP); break;
                        }
                    }
                }
                index++;
            }
    }

    /**
     * The draw method draws every Tile in the tiles array.
     */

    public void draw() {
        for (Tile tile : tiles) {
            tile.draw();
        }
    }

    /**
     * The getNodes returns an array of Nodes.
     * @return Receive a Node[][] array. Can receive a node by calling Node[x][y].
     */

    public Node[][] getNodes() {
        return nodes;
    }

    /**
     * The getChairs returns an array of Chairs.
     * @return Receive a Chair[][] array. Can receive a
     */

    public Chair[][] getChairs() {
        return chairs;
    }

    /**
     *
     * @return
     */

    public int getLayerID() {
        return layerID;
    }
    public String getLayerName() { return layerName; }
}
