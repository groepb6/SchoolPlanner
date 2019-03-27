package simulation;

import gui.components.frames.StartSim;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import simulation.data.Area;
import simulation.data.Layer;
import simulation.pathfinding.Node;
import simulation.pathfinding.PathFinder;

import javax.imageio.ImageIO;
import javax.json.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Dustin Hendriks
 */

public class Map {
    private FXGraphics2D g2d;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private ArrayList<BufferedImage> subImages = new ArrayList<>();
    private int tileWidth;
    private int tileHeight;
    private double scaleMultiplier = 1.;
    private JsonObject mapFile;
    private Canvas canvas;
    private Scene scene;
    private double mousePosX;
    private double mousePosY;
    private StartSim startSim;
    private ScrollPane scrollPane;
    public boolean followPerson = false;
    private Group group;
    private Stage stage;
    private String map = "schoolmap.json";
    private ArrayList<Layer> layers = new ArrayList<>();
    private ArrayList<Area> areas = new ArrayList<>();
    private Layer collisionLayer;
    private java.awt.Image image;
    private int amountOfTilesWidth;
    private int amountOfTilesHeight;


    public Map(FXGraphics2D g2d, Canvas canvas, Scene scene, StartSim startSim, ScrollPane scrollPane, Group group, Stage stage) {
        readJSON();
        this.scrollPane = scrollPane;
        this.group = group;
        this.stage = stage;
        this.g2d = g2d;
        this.scene = scene;
        this.canvas = canvas;
        setActions();
        this.startSim = startSim;
        saveAreas();
        saveSprites();
        saveLayers();
        loadPathFinder();
        drawLayers();
        image = getImageOfCanvas();
    }

    private Image getImageOfCanvas() {
        return SwingFXUtils.fromFXImage(this.canvas.snapshot(new SnapshotParameters(), new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight())), null);
    }

    private void readJSON() {
        JsonReader jsonReader = Json.createReader(getClass().getResourceAsStream("/json/maps/" + map));
        mapFile = jsonReader.readObject();
    }

    //After saveSprites!!!
    private void loadPathFinder() {
        PathFinder pathFinder = new PathFinder(collisionLayer.getNodes(), amountOfTilesWidth, amountOfTilesHeight, tileWidth, tileHeight, areas);
    }

    private void saveAreas() {
        JsonArray areas = mapFile.getJsonArray("layers").getJsonObject(mapFile.getJsonArray("layers").size() - 1).getJsonArray("objects");
        for (int i = 0; i < areas.size() - 1; i++) {
            this.areas.add(new Area(areas.getJsonObject(i)));
        }
    }

    private void saveSprites() {
        tileWidth = mapFile.getInt("tilewidth");
        tileHeight = mapFile.getInt("tileheight");
        amountOfTilesWidth = mapFile.getInt("width");
        amountOfTilesHeight = mapFile.getInt("height");

        JsonArray data = mapFile.getJsonArray("tilesets");

        for (int i = 0; i < data.size(); i++) {
            try {
                sprites.add(ImageIO.read(getClass().getResourceAsStream("/json/tilesets/" + data.getJsonObject(i).getString("link"))));
            } catch (Exception error) {
            }
        }

        sprites.forEach(sprite -> {
            for (int y = 0; y < sprite.getHeight(); y += tileHeight)
                for (int x = 0; x < sprite.getWidth(); x += tileWidth)
                    subImages.add(sprite.getSubimage(x, y, tileWidth, tileHeight));
        });
    }

    private void saveLayers() {
        for (int i = 0; i < mapFile.getJsonArray("layers").size() - 1; i++) {
            if (!mapFile.getJsonArray("layers").getJsonObject(i).getString("name").equals("Collision"))
                layers.add(new Layer(mapFile.getJsonArray("layers").getJsonObject(i), g2d, subImages));
            else collisionLayer = new Layer(mapFile.getJsonArray("layers").getJsonObject(i), g2d, subImages);
        }
        System.out.println("done");
    }

    public void restoreCanvas() {
        g2d.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        g2d.drawImage(image, null, null);
    }

    public void drawLeaves() {
        for (Layer layer : layers) {
            if (layer.getLayerName().equals("TreeLeave-layer"))
                layer.draw();
        }
    }

    public void drawLayers() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setTransform(1, 0, 0, 1, 0, 0);
        for (Layer layer : layers) {
            layer.draw();
        }
    }

    public void drawWalls() {
        for (Layer layer : layers) {
            if (layer.getLayerName().equals("SchoolWallDoor-layer")) layer.draw();
            if (layer.getLayerName().equals("SchoolWallOutside-layer")) layer.draw();
        }
    }

    public void drawCollision() {
        this.collisionLayer.draw();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void followPerson(double x, double y) {
        if (followPerson) {
            scrollPane.setHvalue((((x) - ((scrollPane.getWidth()) / 2)) / ((canvas.getWidth()) - (scrollPane.getWidth()))));
            scrollPane.setVvalue((((y) - ((scrollPane.getHeight()) / 2)) / ((canvas.getHeight()) - (scrollPane.getHeight()))));
        }
    }

    private void setActions() {
        scrollPane.setOnMouseMoved(event -> {
            mousePosX = scrollPane.getHvalue() * (canvas.getWidth() - scrollPane.getWidth()) + event.getX();
            mousePosY = scrollPane.getVvalue() * (canvas.getHeight() - scrollPane.getHeight()) + event.getY();
        });

        scrollPane.setOnKeyTyped(event -> {
            if (event.getCharacter().equals("F") || event.getCharacter().equals("f")) {
                stage.setFullScreenExitHint("Press 'Esc' to minimize school simulation");
                stage.setFullScreen(true);
            }

            if (event.getCharacter().equals("=") || event.getCharacter().equals("+")) {
                scaleMultiplier = 2;
                canvas.setScaleX(scaleMultiplier);
                canvas.setScaleY(scaleMultiplier);
                scrollPane.setHvalue((((mousePosX) - ((scrollPane.getWidth()) / 2)) / ((canvas.getWidth()) - (scrollPane.getWidth()))));
                scrollPane.setVvalue((((mousePosY) - ((scrollPane.getHeight()) / 2)) / ((canvas.getHeight()) - (scrollPane.getHeight()))));
                //System.out.println("MousePositionX "+mousePosX + " MousePositionY " + mousePosY + " Scrollpane width " + scrollPane.getWidth() + " Hvalue scrollpane " + scrollPane.getHvalue() + " canvas width " + canvas.getWidth() + " pos on canvas " + canvas.getTranslateX() + " group pos " + group.getTranslateX());
            }

            if (event.getCharacter().equals("-") || event.getCharacter().equals("_")) {
                if (scaleMultiplier > 1) {
                    scaleMultiplier *= 0.5;
                    canvas.setScaleX(scaleMultiplier);
                    canvas.setScaleY(scaleMultiplier);
                }
            }
            if (event.getCharacter().equals("d"))
                scrollPane.setHvalue(scrollPane.getHvalue() + .2);
            if (event.getCharacter().equals("a"))
                scrollPane.setHvalue(scrollPane.getHvalue() - .2);
            if (event.getCharacter().equals("w"))
                scrollPane.setVvalue(scrollPane.getVvalue() - .05);
            if (event.getCharacter().equals("s"))
                scrollPane.setVvalue(scrollPane.getVvalue() + .05);
            //event.consume();
        });
    }
}

