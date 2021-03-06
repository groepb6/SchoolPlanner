package simulation.pathfinding;

import gui.settings.ApplicationSettings;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jfree.fx.FXGraphics2D;
import simulation.data.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Dustin Hendriks
 * The PathFinder class generates distance maps that are used to guide Sims to specific locations.
 */

public class PathFinder {
    private LinkedList<Node> openList = new LinkedList<>(); // The openlist defines nodes that should be checked.
    private LinkedList<Node> closedList = new LinkedList<>();  // The closedlist defines nodes that are already checked.
    private int amountOfTilesWidth;
    private int amountOfTilesHeight;
    private int tileWidth;
    private int tileHeight;
    private ArrayList<Area> areas;
    private Node[][] allNodes;
    private FXGraphics2D g2d;
    private HBox popUp;
    private ProgressBar progressBar = new ProgressBar();
    private ProgressIndicator progressIndicator = new ProgressIndicator();
    private Stage stage = new Stage();
    private Canvas canvas;
    private Scene scene;
    private Scene primaryScene;
    private WritableImage writableImage = new WritableImage(100, 100);
    private SnapshotParameters snapshotParameters = new SnapshotParameters();
    public boolean loaded = false;
    private boolean terminated = false;

    /**
     * The PathFinder class needs some attributes to generate the distance maps.
     * @param nodes The Node[][] nodes class is needed to know which nodes are available and can be used to register scores in.
     * @param amountOfTilesWidth Needed to prevent index out of bounds exception. Caused by width>=100.
     * @param amountOfTilesHeight Needed to prevent index out of bounds exception. Caused by height>=120.
     * @param tileWidth Width of 1 tile (32 in our software).
     * @param tileHeight Height of 1 tile (32 in our software).
     * @param areas A distance map should be made for every area.
     * @param g2d The pathfinder can also draw its scores on the tiles, the FXGraphics2D class is needed for this.
     * @param canvas The Canvas canvas is needed to check the width and height available. In addition can also be used for future purposes.
     * @param primaryScene Defines the primaryScene (can bind actions to), in this case used to set a loading indicator.
     */

    public PathFinder(Node[][] nodes, int amountOfTilesWidth, int amountOfTilesHeight, int tileWidth, int tileHeight, ArrayList<Area> areas, FXGraphics2D g2d, Canvas canvas, Scene primaryScene) {
        this.canvas = canvas;
        this.primaryScene = primaryScene;
        this.amountOfTilesWidth = amountOfTilesWidth;
        this.amountOfTilesHeight = amountOfTilesHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.allNodes = nodes;
        this.areas = areas;
        this.g2d = g2d;
        initNodeAreas();
        initialize();
    }

    /**
     * PATHFINDER LOCATION IS BEING INITIALIZED HERE AT TARGET POINT.
     */

    private void initialize() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                terminated  = false;
                for (int i = 0; i < areas.size(); i++) {
                    if (terminated) {
                        restoreMouse();
                        break;
                    }
                    updateProgress(i, areas.size()-1);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            updateImage();
                        }
                    });
                    Area area = areas.get(i);
                    Point2D targetPoint = new Point2D.Double((int) ((area.x+(area.areaWidth*.5)) / tileWidth), (int) (((area.y+((area.areaHeight*.5)))) / tileHeight));
                    Node newNode = new Node((int) targetPoint.getX(), (int) targetPoint.getY(), -1);
                    newNode.addScore(i);
                    openList.add(newNode);
                    addPoint(openList.get(0), i);
                    while (openList.size() > 0 && !terminated)
                        addPoint(openList.get(0), i);
                    clearLists();
                    if (i == areas.size()-1) {
                        PathFinder.this.loaded = true;
                        Thread.sleep(500);
                        restoreMouse();
                    }
                }
                return null;
            }
        };
        if (!terminated) {
            bindTask(task);
        } else restoreMouse();
    }

    /**
     * Clean up
     */

    public void terminate() {
        this.terminated=true;
    }

    /**
     * Set the mouse cursor to the default of Windows.
     */

    private void restoreMouse() {
        this.primaryScene.setCursor(Cursor.DEFAULT);
    }

    /**
     * bindTask(Task task) is used to link the progress indicator to the pathfinder loading.
     * @param task Defines the task that needs to be bound.
     */

    private void bindTask(Task task) {
        createProgressBar();
        progressBar.progressProperty().bind(task.progressProperty());
        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.setPrefWidth(65);
        progressIndicator.setPrefHeight(65);
        new Thread(task).start();
    }

    /**
     * Update the progress indicator to an updated slide.
     */

    private void updateImage() {
        progressIndicator.snapshot(snapshotParameters, writableImage);
        primaryScene.setCursor(new ImageCursor(writableImage));
    }

    /**
     * Initialize the progress bar (has to be filled transparent for a nicer look).
     */

    private void initProgress() {
        snapshotParameters.setFill(Color.TRANSPARENT);
    }

    /**
     * createProgressBar() sets the progress and initializes the loading icon.
     */

    private void createProgressBar() {
        popUp = new HBox();
        this.scene = new Scene(popUp);
        popUp.getChildren().add(progressIndicator);
        stage.setScene(scene);
        progressBar.setProgress(0);
        stage.setTitle("Loading...");
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        this.popUp.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        initProgress();
    }

    /**
     * clearList() empty's the openList and closedList.
     */

    private void clearLists() {
        openList.clear();
        closedList.clear();
    }

    /**
     * Initialize the score for each node, each area.
     */

    private void initNodeAreas() {
        for (int y = 0; y < amountOfTilesHeight; y++)
            for (int x = 0; x < amountOfTilesWidth; x++)
                for (int i = 0; i < areas.size(); i++) {
                    if (allNodes[x][y].scores[i] == 0)
                        allNodes[x][y].addScore(i);
                }
    }

    /**
     * Draw the pathfinder strings for area number @param.
     * @param iWantToDrawAreaNumber Defines the number in areas that should be drawn.
     */

    public void draw(int iWantToDrawAreaNumber) {
        int index = 0;
        for (int y = 0; y < amountOfTilesHeight; y++)
            for (int x = 0; x < amountOfTilesWidth; x++) {
                if (allNodes[x][y].scores[iWantToDrawAreaNumber] != 0 && allNodes[x][y].scores[iWantToDrawAreaNumber] != -1)
                    g2d.drawString(Integer.toString(allNodes[x][y].scores[iWantToDrawAreaNumber]), x * 32, y * 32+ ApplicationSettings.font.getSize());
                index++;
            }
    }

    /**
     * Add a point to the closedList and remove from the openList.
     * @param node Defines the Node node that should be added.
     * @param areaNumber Defines the areaNumber that the score should be updated for.
     */

    private void addPoint(Node node, int areaNumber) {
        closedList.add(node);
        openList.remove(node);
        Point2D nodePos = node.getPosition();

        Node leftNode = getNode((int) nodePos.getX() - 1, (int) nodePos.getY());
        Node rightNode = getNode((int) nodePos.getX() + 1, (int) nodePos.getY());
        Node aboveNode = getNode((int) nodePos.getX(), (int) nodePos.getY() - 1);
        Node belowNode = getNode((int) nodePos.getX(), (int) nodePos.getY() + 1);

        if (leftNode != null)
            if (checkCanAdd(leftNode)) {
                leftNode.score = (node.score + 1);
                allNodes[(int) leftNode.getPosition().getX()][(int) leftNode.getPosition().getY()].addScore(areaNumber);
                closedList.add(leftNode);
                openList.add(leftNode);
            }
        if (rightNode != null)
            if (checkCanAdd(rightNode)) {
                rightNode.score = (node.score + 1);
                allNodes[(int) rightNode.getPosition().getX()][(int) rightNode.getPosition().getY()].addScore(areaNumber);
                closedList.add(rightNode);
                openList.add(rightNode);
            }
        if (aboveNode != null)
            if (checkCanAdd(aboveNode)) {
                aboveNode.score = (node.score + 1);
                allNodes[(int) aboveNode.getPosition().getX()][(int) aboveNode.getPosition().getY()].addScore(areaNumber);
                closedList.add(aboveNode);
                openList.add(aboveNode);
            }
        if (belowNode != null)
            if (checkCanAdd(belowNode)) {
                belowNode.score = (node.score + 1);
                allNodes[(int) belowNode.getPosition().getX()][(int) belowNode.getPosition().getY()].addScore(areaNumber);
                closedList.add(belowNode);
                openList.add(belowNode);
            }
    }

    /**
     * getNode returns the node that is located on a x and y position.
     * @param x Defines the x value
     * @param y Defines the y value
     * @return Return the corresponding Node.
     */

    private Node getNode(int x, int y) {
        Point2D nodePos = new Point2D.Double(x, y);
        if ((nodePos.getX() >= 0) && (nodePos.getX() < amountOfTilesWidth) && (nodePos.getY() >= 0) && (nodePos.getY() < amountOfTilesHeight))
            return allNodes[(int) nodePos.getX()][(int) nodePos.getY()];
        else return null;
    }

    /**
     * Receive all nodes.
     * @return Return a Node[][] array.
     */

    public Node[][] getAllNodes() {
        return this.allNodes;
    }

    /**
     * Check if a Node can be added.
     * @param node Check this Node.
     * @return Return true or false value depending if the node is in bounds and the Node is walkable (=no collision tile present).
     */

    private boolean checkCanAdd(Node node) {
        return (((node.getPosition().getX() >= 0) && (node.getPosition().getX() < amountOfTilesWidth) && (node.getPosition().getY() >= 0) && (node.getPosition().getY() < amountOfTilesHeight)) && (!(closedList.contains(node)))) && (allNodes[(int) node.getPosition().getX()][(int) node.getPosition().getY()].walkable);
    }

}
