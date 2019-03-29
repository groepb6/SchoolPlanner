package gui.components.frames;

import data.readwrite.DataReader;
import data.schoolrelated.School;
import gui.components.window.Sizeable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import simulation.CreateSims;
import simulation.SchoolMap;
import simulation.Simulation;


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

    public StartSim(Stage stage, Scene scene, ScrollPane scrollPane) {
        super.setProportions(0, 5000, 0, 5000, 800, 600, stage);
        canvas = new Canvas(3200, 3840);
        g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        this.scene = new Scene(group);
        SchoolMap schoolMap = new SchoolMap(g2d, canvas, scene, this, scrollPane, group, stage);
        group.getChildren().add(schoolMap.getCanvas());
        group.setAutoSizeChildren(false);

        School school = DataReader.readSchool();
        CreateSims createSims = new CreateSims(school, schoolMap, g2d, canvas);
        Simulation simulation = new Simulation(createSims.getSchool(), createSims.getMap(), createSims.getSims(), g2d);
    }

    public void clean() {
        //this.simUpdate.stopTimer();
    }

    public Scene getSim() {
        return scene;
    }

    public Group getGroup() {
        return group;
    }
}
