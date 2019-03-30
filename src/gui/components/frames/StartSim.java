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
import simulation.sims.Sim;

import java.awt.geom.Point2D;


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
    private Simulation simulation;

    public StartSim(Stage stage, Scene scene, ScrollPane scrollPane) {
        super.setProportions(0, 5000, 0, 5000, 800, 600, stage);
        canvas = new Canvas(3200, 3840);
        g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        this.scene = new Scene(group);
        SchoolMap map = new SchoolMap(g2d, canvas, scene, this, scrollPane, group, stage);
        group.getChildren().add(map.getCanvas());
        group.setAutoSizeChildren(false);

        School school = DataReader.readSchool();
        CreateSims createSims = new CreateSims(school, map, g2d, canvas);
        this.simulation = new Simulation(createSims.getSchool(), createSims.getMap(), createSims.getSims(), g2d);
        this.setupSimHijack();
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    public SchoolMap getMap() {
        return this.simulation.getMap();
    }

    public void clean() {
        //TODO: make sure this StartSim is deleted and a new one is created every time the simulation is run
    }

    public Group getGroup() {
        return group;
    }

    private void setupSimHijack() {
        canvas.setOnMouseMoved(e -> {
            Sim sim = this.simulation.getMap().simToFollow;
            if (sim != null)
                if (sim.getSpeed() != 0 && this.simulation.getMap().hijackedSim && this.simulation.getMap().simToFollow != null)
                    sim.setTargetPos(new Point2D.Double(e.getX(), e.getY()));
        });


    }

}
