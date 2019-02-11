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
        putTime();
    }

    private void putTime() {
        String text="08:00";
        float x=100;
        float y=100;
        g2d.drawString(text, x, y);
    }

    BorderPane getCanvasWithPane = new BorderPane();
}
