package gui.components.frames;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;

class VirtualizedSchedule  {
    private Canvas canvas = new Canvas(800, 600);
    private BorderPane borderPane = new BorderPane();
    FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

    private VirtualizedSchedule() {
        borderPane.setCenter(canvas);
        drawTime();
    }

    private void drawTime() {

    }

    BorderPane getCanvasWithPane = new BorderPane();
}
