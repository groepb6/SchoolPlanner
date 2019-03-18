package tiled;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

/**
 * A main class in order to test the new Tiled classes.
 * Todo: remove this class
 */
public class TiledMain extends Application {
    private ResizableCanvas canvas;
    private Map map;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.map = new Map(TiledReader.readMap("schoolmap.json"));

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();
    }

    public void draw(FXGraphics2D graphics) {
        this.map.draw(graphics);
    }

    public void update(double time) {

    }



}
