package gui.components.frames;

import gui.components.window.Sizeable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import simulation.Map;
import simulation.SimUpdate;
import simulation.controls.SimulationBar;
import simulation.logic.TimerHandler;


/**
 * @author Dustin Hendriks
 * @since 08-02-2019
 * <p>
 * This class should launch the simulation and return a borderPane (should have canvas and time scrolling bar).
 */

public class StartSim extends Sizeable {
    private Canvas canvas;
    private Scene scene;
    public Group group = new Group();
    private FXGraphics2D g2d;
    private ScrollPane scrollPane;
    private SimUpdate simUpdate;

    public StartSim(Stage stage, Scene scene, ScrollPane scrollPane) {
        super.setProportions(0, 5000, 0, 5000, 800, 600, stage);
        canvas = new Canvas(3200, 3840);
        g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        this.scene = new Scene(group);
        Map map = new Map(g2d, canvas, scene, this, scrollPane, group, stage);
        group.getChildren().add(map.getCanvas());
        group.setAutoSizeChildren(false);
        this.simUpdate = new SimUpdate(g2d, canvas, scene, map);
    }

    public SimUpdate getSimUpdate() {
        return simUpdate;
    }

    public Map getMap() {
        return simUpdate.getMap();
    }

    public void clean() {
        this.simUpdate.stopTimer();
    }

    public Scene getSim() {
        return scene;
    }

    public Group getGroup() {
        return group;
    }
}
