package simulation;

import data.objects.Chair;
import gui.components.frames.StartSim;
import gui.settings.ApplicationSettings;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import simulation.data.Area;
import simulation.data.Layer;
import simulation.pathfinding.Node;
import simulation.pathfinding.PathFinder;
import simulation.sims.Sim;

import javax.imageio.ImageIO;
import javax.json.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Dustin Hendriks
 * The map class draws the map using all layers, areas and extra information. It also calculates distance maps using PathFinder and initializes custom actions.
 */

public class SchoolMap {
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
    private PathFinder pathFinder;
    public Sim simToFollow;
    private boolean followPerson = false;
    private Group group;
    private Stage stage;
    private String map = "schoolmap.json";
    private ArrayList<Layer> layers = new ArrayList<>();
    public ArrayList<Area> areas = new ArrayList<>();
    private Layer collisionLayer;
    private Layer studentChairLayer;
    private java.awt.Image image;
    private int amountOfTilesWidth;
    private int amountOfTilesHeight;
    public boolean showDebug = false;
    public boolean hijackedSim = false;
    public boolean showCollision = false;
    private boolean fireDrill = false;
    Sim[] sims;

    /**
     * The map class needs a few parameters to initialize.
     *
     * @param g2d        Is needed to draw the map on a canvas.
     * @param canvas     All drawings are located on the canvas.
     * @param scene      Can be used to bind actions to.
     * @param startSim   Unused at the moment.
     * @param scrollPane Contains the canvas. Can scroll independently from adjacent nodes.
     * @param group      Contains the scene, can be used for specific key input actions.
     * @param stage      Stage Needed to put the application on fullscreen, when a user presses f, for example.
     */

    public SchoolMap(FXGraphics2D g2d, Canvas canvas, Scene scene, StartSim startSim, ScrollPane scrollPane, Group group, Stage stage) {
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

    /**
     * Converts the canvas to 1 large picture, saves a lot of draw time and optimizes overall performance.
     *
     * @return Receive the canvas as a picture
     */

    private BufferedImage getImageOfCanvas() {
        return SwingFXUtils.fromFXImage(this.canvas.snapshot(new SnapshotParameters(), new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight())), null);
    }

    /**
     * Read the JSON file located in the given directory.
     */

    private void readJSON() {
        JsonReader jsonReader = Json.createReader(getClass().getResourceAsStream("/json/maps/" + map));
        mapFile = jsonReader.readObject();
    }

    /**
     * Launch the pathfinder (calculating takes some time!)
     */
    //After saveSprites!!!
    private void loadPathFinder() {
        this.pathFinder = new PathFinder(collisionLayer.getNodes(), amountOfTilesWidth, amountOfTilesHeight, tileWidth, tileHeight, areas, g2d, canvas, scene);
    }

    /**
     * Receive an object of the PathFinder class. Can be used to guide Sims automatically to their target.
     *
     * @return Receive a pathfinder object.
     */

    PathFinder getPathFinder() {
        return pathFinder;
    }

    /**
     * Receive the collision layer nodes.
     *
     * @return Receive all nodes of the collision layer.
     */

    Node[][] getCollisionLayer() {
        return collisionLayer.getNodes();
    }

    Chair[][] getStudentChairLayer() {
        return studentChairLayer.getChairs();
    }

    /**
     * Draws the distance map of area areaNumber.
     */

    void drawStringPathFinder(int areaNumber) {
        if (showDebug && areaNumber < areas.size())
            this.pathFinder.draw(areaNumber);
    }

    /**
     * Save all areas by reading them out of the JSON file and putting them in an array.
     */

    private void saveAreas() {
        JsonArray areas = mapFile.getJsonArray("layers").getJsonObject(mapFile.getJsonArray("layers").size() - 1).getJsonArray("objects");
        for (int i = 0; i < areas.size() - 1; i++) {
            this.areas.add(new Area(areas.getJsonObject(i)));
            this.areas.get(i).areaID = i; //***IMPORTANT DO NOT DELETE IN MERGE***
        }
    }

    public void setSims(Sim[] sims) {
        this.sims = sims;
    }

    /**
     * Searches for an Area by name.
     *
     * @param searchedAreaName The name of the Area being searched.
     * @return An Area that exactly matches the searched name or null.
     */
    public Area searchArea(String searchedAreaName) {
        Area foundArea = null;
        for (Area area : this.areas) {
            if (area.areaName.equals(searchedAreaName)) {
                foundArea = area;
            }
        }
        if (foundArea == null) {
            System.out.println("COULD NOT FIND AREA");
        }
        return foundArea;
    }

    public void fireDrill() {
        if (pathFinder.loaded) {
            fireDrill = !fireDrill;
            int targetArea = -1;
            if (fireDrill) {
                for (int i = 0; i < areas.size(); i++)
                    if (areas.get(i).areaName.equals("ParkingLot"))
                        targetArea = i;
                setTargetAreaSims(targetArea);
            } else
                setOldTargetAreaSims();

        }
    }

    void drawFireDrill() {
        if (fireDrill && System.currentTimeMillis()%500<250) {
            int alpha = 80; // 50% transparent -- Alpha was 127
            Color myColour = new Color(255, 0, 0, alpha);
            Shape rectangle = new Rectangle2D.Double(0, 0, canvas.getWidth(), canvas.getHeight());
            g2d.setColor(myColour);
            g2d.fill(rectangle);
            g2d.draw(rectangle);
        }
    }

    private void setTargetAreaSims(int areaNumber) {
        for (Sim sim : sims) {
            sim.setTargetArea(areaNumber);
        }
    }

    private void setOldTargetAreaSims() {
        for (Sim sim : sims)
            sim.setOldTargetArea();

    }

    /**
     * Save all sprites.
     */

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
                error.printStackTrace();
            }
        }

        sprites.forEach(sprite -> {
            for (int y = 0; y < sprite.getHeight(); y += tileHeight)
                for (int x = 0; x < sprite.getWidth(); x += tileWidth)
                    subImages.add(sprite.getSubimage(x, y, tileWidth, tileHeight));
        });
    }

    /**
     * Save all layers. The collisionLayer is being stored independently for easier access.
     */

    private void saveLayers() {
        for (int i = 0; i < mapFile.getJsonArray("layers").size() - 1; i++) {
            if (!mapFile.getJsonArray("layers").getJsonObject(i).getString("name").equals("Collision")) {
                layers.add(new Layer(mapFile.getJsonArray("layers").getJsonObject(i), g2d, subImages, canvas));

                if (mapFile.getJsonArray("layers").getJsonObject(i).getString("name").equals("SchoolInteriorStudentChairs")) {
                    studentChairLayer = layers.get(layers.size() - 1);
                }
            } else collisionLayer = new Layer(mapFile.getJsonArray("layers").getJsonObject(i), g2d, subImages, canvas);
        }
    }

    /**
     * Restore the canvas to the previously initialized drawed objects.
     */

    void restoreCanvas() {
        try {
            g2d.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
            g2d.drawImage(image, null, null);
        } catch (OutOfMemoryError e) {
            System.out.println("Application is running low on memory!");
        }
    }

    /**
     * Can be used to draw all leaves overlapping the characters if turns out to be an issue.
     */

    public void drawLeaves() {
        for (Layer layer : layers) {
            if (layer.getLayerName().equals("TreeLeave-layer"))
                layer.draw();
        }
    }

    /**
     * Draw every layer, except the collision layer, since it is not stored by the layers array.
     */

    private void drawLayers() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setTransform(1, 0, 0, 1, 0, 0);
        for (Layer layer : layers) {
            layer.draw();
        }
    }

    /**
     * Draw all wall layers (doors and overlapping parts).
     */

    void drawWalls() {
        for (Layer layer : layers) {
            if (layer.getLayerName().equals("SchoolWallDoor-layer")) layer.draw();
            if (layer.getLayerName().equals("SchoolWallOutside-layer")) layer.draw();
        }
    }

    void sitOnChair(Sim sim) {
        if (!sim.gotoChair) {
            if (sim.isInTargetArea()) {
                Chair simChair = getChair(sim);
                if (simChair != null) {
                    sim.gotoChair(new Point2D.Double(simChair.x, simChair.y), simChair.direction, simChair);
                }
            }
        }
    }

    private Chair getChair(Sim sim) {
        Area targetArea = areas.get(sim.getTargetArea());
        int areaX = targetArea.x / 32;
        int areaY = targetArea.y / 32;
        int width = targetArea.areaWidth / 32;
        int height = targetArea.areaHeight / 32;
        Chair[][] chairs = studentChairLayer.getChairs();
        for (int y = areaY; y < (areaY + height); y++) {
            for (int x = areaX; x < (areaX + width); x++) {
                    if (chairs[x][y].isChair && chairs[x][y].isAvailable) {
                        chairs[x][y].isAvailable = false;
                        return chairs[x][y];
                    }
                }
        }
        return null;
    }

    /**
     * Draw the collision layer.
     */

    void drawCollision() {
        if (showCollision)
            this.collisionLayer.draw();
    }

    /**
     * Receive the canvas.
     *
     * @return The canvas is being returned which contains all written data (shapes, objects, Sims, etc).
     */

    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Can be used to translate the camera position to a given x, or y. This way a Sim could be tracked and followed / stalked by the camera.
     * <p>
     * x Defines the x value to translate to (this is just the X location of a Sim in above described case).
     * y Defines the y value to translate to (this is just the Y location of a Sim in above described case).
     */

    void followPerson() {
        if (simToFollow != null) {
            double x = simToFollow.getCurrentPos().getX();
            double y = simToFollow.getCurrentPos().getY();
            if (followPerson) {
                scrollPane.setHvalue((((x) - ((scrollPane.getWidth()) / 2)) / ((canvas.getWidth()) - (scrollPane.getWidth()))));
                scrollPane.setVvalue((((y) - ((scrollPane.getHeight()) / 2)) / ((canvas.getHeight()) - (scrollPane.getHeight()))));
                g2d.setColor(Color.WHITE);
                drawFollowText(simToFollow.name, (int) x, (int) y);
            }
        }
    }

    private void drawFollowText(String text, int x, int y) {
        int rectWidth = 32;
        int rectHeight = 64;
        y -= 50;
        g2d.setFont(ApplicationSettings.font);
        if (!hijackedSim)
        g2d.drawString(text + " -> " + simToFollow.areas.get(simToFollow.getTargetArea()).areaName, x, y - 5);
        else
            g2d.drawString(text+" (mouse)", x, y-5);
        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y + 16, rectWidth, rectHeight);
    }

    private Sim getNearestSim(Point2D comparePos) {
        double smallestDistance = Integer.MAX_VALUE;
        if (sims.length > 0) {
            Sim smallestDistanceSim = sims[0];
            for (Sim sim : sims) {
                if (sim.getCurrentPos().distance(comparePos) < smallestDistance) {
                    smallestDistance = sim.getCurrentPos().distance(comparePos);
                    smallestDistanceSim = sim;
                }
            }
            return smallestDistanceSim;
        }
        return null;
    }

    /**
     * Set all button actions (zooming, debugging, scrolling, etc). New desired actions can be programmed here.
     */

    private void setActions() {
        scrollPane.setOnMouseMoved(event -> {
            mousePosX = scrollPane.getHvalue() * (canvas.getWidth() - scrollPane.getWidth()) + event.getX();
            mousePosY = scrollPane.getVvalue() * (canvas.getHeight() - scrollPane.getHeight()) + event.getY();
        });

        scene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.MIDDLE) {
                Sim sim = getNearestSim(new Point2D.Double(mousePosX, mousePosY));
                if (sim != null) {
                    simToFollow = sim;
                    followPerson = true;
                }
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                followPerson = false;
                hijackedSim=false;
            }
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
            }

            if (event.getCharacter().equals("l")) showDebug = !showDebug;
            if (event.getCharacter().equals("k")) hijackedSim = !hijackedSim;
            if (event.getCharacter().equals("o")) showCollision = !showCollision;

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
        });
    }
}

