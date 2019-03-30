package simulation.data;

import data.objects.Chair;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import org.jfree.fx.FXGraphics2D;
import simulation.pathfinding.Node;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    private void saveTiles() {
        boolean isCollisionLayer = layerName.equals("Collision");
        boolean isStudentChairLayer = layerName.equals("SchoolInteriorStudentChairs");
        boolean isTeacherChairLayer = layerName.equals("SchoolInteriorTeacherChairs");

        int x;
        int y;

        if (isCollisionLayer)
            nodes = new Node[layerWidth][layerHeight];
        if (isStudentChairLayer)
            chairs = new Chair[layerWidth][layerHeight];

        int index = 0;
        for (y = 0; y < layerHeight; y++)
            for (x = 0; x < layerWidth; x++) {
                if (isCollisionLayer) {
                    nodes[x][y] = new Node(x, y, -1);
                    nodes[x][y].walkable = true;
                }

                if (isStudentChairLayer) {
                    chairs[x][y] = new Chair();
                    chairs[x][y].setAvailable(false);
                    chairs[x][y].isChair = false;
                }

                if (data.getInt(index) != 0) {
                    try {
                        tiles.add(new Tile(subImages.get(data.getInt(index) - 1), x, y, g2d));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Have you pasted the tileset links in the schoolmap.json file? If not add ''link'' under every source path under the tilesets array.");
                    }
                    if (isCollisionLayer)
                        nodes[x][y].walkable = false;
                    if (isStudentChairLayer) {
                        chairs[x][y].isChair = true;
                        chairs[x][y].setAvailable(true);
                        chairs[x][y].isAvailable = true;
                        chairs[x][y].x = x;
                        chairs[x][y].y = y;

                        switch (data.getInt(index) - 1) {
                            case 1562:
                                chairs[x][y].setDir(Chair.Direction.DOWN);
                                break;
                            case 1563:
                                chairs[x][y].setDir(Chair.Direction.DOWN);
                                break;
                            case 1564:
                                chairs[x][y].setDir(Chair.Direction.UP);
                                break;
                            case 1565:
                                chairs[x][y].setDir(Chair.Direction.RIGHT);
                                break;
                            case 1572:
                                chairs[x][y].setDir(Chair.Direction.RIGHT);
                                break;
                            case 1573:
                                chairs[x][y].setDir(Chair.Direction.LEFT);
                                break;
                            case 1574:
                                chairs[x][y].setDir(Chair.Direction.LEFT);
                                break;
                            case 1575:
                                chairs[x][y].setDir(Chair.Direction.UP);
                                break;
                        }
                    }
                }
                index++;
            }
    }

    public void draw() {
        for (Tile tile : tiles) {
            tile.draw();
        }
    }

    public Node[][] getNodes() {
        return nodes;
    }

    public Chair[][] getChairs() {
        return chairs;
    }

    public int getLayerID() {
        return layerID;
    }

    public String getLayerName() {
        return layerName;
    }
}
