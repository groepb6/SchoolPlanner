package simulation.pathfinding;

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

public class PathFinder {
    private LinkedList<Node> openList = new LinkedList<>(); // Moet nog gedaan worden
    private LinkedList<Node> closedList = new LinkedList<>(); // Is hier al geweest
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
                for (int i = 0; i < areas.size(); i++) {
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
                    while (openList.size() > 0)
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
        bindTask(task);
    }

    private void restoreMouse() {
        this.primaryScene.setCursor(Cursor.DEFAULT);
    }

    private void bindTask(Task task) {
        createProgressBar();
        progressBar.progressProperty().bind(task.progressProperty());
        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.setPrefWidth(65);
        progressIndicator.setPrefHeight(65);
        new Thread(task).start();
    }

    private void updateImage() {
        progressIndicator.snapshot(snapshotParameters, writableImage);
        primaryScene.setCursor(new ImageCursor(writableImage));
    }

    private void initProgress() {
        snapshotParameters.setFill(Color.TRANSPARENT);
    }

    private void createProgressBar() {
        popUp = new HBox();
        this.scene = new Scene(popUp);
        //popUp.getChildren().add(progressBar);
        popUp.getChildren().add(progressIndicator);
        stage.setScene(scene);
        progressBar.setProgress(0);
        stage.setTitle("Loading...");
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        this.popUp.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        initProgress();
        //stage.show();

    }

    private void clearLists() {
        openList.clear();
        closedList.clear();
    }

    private void initNodeAreas() {
        for (int y = 0; y < amountOfTilesHeight; y++)
            for (int x = 0; x < amountOfTilesWidth; x++)
                for (int i = 0; i < areas.size(); i++) {
                    if (allNodes[x][y].scores[i] == 0)
                        allNodes[x][y].addScore(i);
                }
    }

    public void draw(int iWantToDrawAreaNumber) {
        int index = 0;
        for (int y = 0; y < amountOfTilesHeight; y++)
            for (int x = 0; x < amountOfTilesWidth; x++) {
                //System.out.println(allNodes[x][y].scores.size());
                if (allNodes[x][y].scores[iWantToDrawAreaNumber] != 0)
                    g2d.drawString(Integer.toString(allNodes[x][y].scores[iWantToDrawAreaNumber]), x * 32, y * 32);
                index++;
            }
    }

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

    private Node getNode(int x, int y) {
        Point2D nodePos = new Point2D.Double(x, y);
        if ((nodePos.getX() >= 0) && (nodePos.getX() < amountOfTilesWidth) && (nodePos.getY() >= 0) && (nodePos.getY() < amountOfTilesHeight))
            return allNodes[(int) nodePos.getX()][(int) nodePos.getY()];
        else return null;
    }

    public Node[][] getAllNodes() {
        return this.allNodes;
    }

    private boolean checkCanAdd(Node node) {
        return (((node.getPosition().getX() >= 0) && (node.getPosition().getX() < amountOfTilesWidth) && (node.getPosition().getY() >= 0) && (node.getPosition().getY() < amountOfTilesHeight)) && (!(closedList.contains(node)))) && (allNodes[(int) node.getPosition().getX()][(int) node.getPosition().getY()].walkable);
    }

}
