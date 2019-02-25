package tiled.dver;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Camera {
    private Point2D center;
    private double scale;
    private Canvas canvas;

    public Camera(Canvas canvas) {
        this.canvas = canvas;
        this.canvas.setOnScroll(event -> {
            this.scale *= 1 + (event.getDeltaY() / 100.0);
            if (this.scale < 0.02) {
                this.scale = 0.02; //minimum scale
            }
        });
    }

    public AffineTransform getTransform() {
        AffineTransform transform = new AffineTransform();
        transform.scale(this.scale, this.scale);
        return transform;
    }

}
