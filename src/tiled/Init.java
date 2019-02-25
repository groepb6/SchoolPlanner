package tiled;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;

public class Init extends Application {
    private Canvas canvas = new Canvas(3200,3200);
    private FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
    private Scene scene = new Scene(new Group(canvas));

    public Init() {
        g2d.setBackground(Color.BLACK);
        g2d.clearRect(0,0, (int)canvas.getWidth(), (int)canvas.getHeight());
        DrawMap drawMap = new DrawMap(g2d, canvas, scene);
    }

    public void start(Stage stage) {
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String args[]) {
        launch(Init.class);
    }
}
