package gui.components.frames;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;

class VirtualizedView {
    private static final int pixelHeight = 20 * 13;
    private static final int planWidth = 50;
    private static final int yCorrection = 10;

    static Canvas drawTimePanel() {
        Canvas canvas = new Canvas(65, pixelHeight);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        for (int i = 0; i < 13; i++) {
            graphics.drawLine(50, 20 * i + yCorrection, 40, 20 * i + yCorrection);
            if (i <= 1) {
                graphics.drawString("0" + (i + 8) + " : 00", 0, i * 20 + yCorrection);
            } else {
                graphics.drawString((i + 8) + " : 00", 0, i * 20 + yCorrection);
            }
        }
        return canvas;
    }

    static Canvas schedule(int minutesBegin, int minutesEnd, String name) {
        Canvas canvas = new Canvas(planWidth, pixelHeight);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        double pixelsPerMinute = (pixelHeight / (13 * 60.));
        int beginPixels = (int) (pixelsPerMinute * minutesBegin);
        int endPixels = (int) (pixelsPerMinute * minutesEnd);
        graphics.setColor(new Color((int) (Math.random() * 0x1000000)));
        new Color(0x1000000);
        Rectangle2D rectangle2D = new Rectangle2D.Double(0, beginPixels + yCorrection, planWidth, endPixels - beginPixels);
        graphics.draw(rectangle2D);
        graphics.fill(rectangle2D);
        graphics.setColor(Color.BLACK);
        graphics.drawString(name, 0, yCorrection);
        return canvas;

    }
}
