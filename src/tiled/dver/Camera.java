package tiled.dver;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Camera {
    private Point2D center;
    private double scale;
    private Canvas canvas;

    public Camera(Canvas canvas) {
        this.scale = 1;
        this.center = new Point2D.Double(0, 0);
        this.canvas = canvas;
        this.setupKeys();
        this.canvas.setOnScroll(event -> {
            this.scale *= 1 + (event.getDeltaY() / 100.0f);
            if (this.scale < 0.02) {
                this.scale = 0.02; //minimum scale
            }
        });
    }

    private void setupKeys() {
        this.canvas.setOnKeyTyped(event -> {
            if (event.getCharacter().equals("w")) {
                this.center = new Point2D.Double(center.getX(), center.getY() - (5 * this.scale));
            }
            if (event.getCharacter().equals("a")) {
                this.center = new Point2D.Double(center.getX() + (5 * this.scale), center.getY());
            }
            if (event.getCharacter().equals("s")) {
                this.center = new Point2D.Double(center.getX(), center.getY() + (5 * this.scale));
            }
            if (event.getCharacter().equals("d")) {
                this.center = new Point2D.Double(center.getX() - (5 * this.scale), center.getY());
            }
        });
    }

    public AffineTransform getTransform() {
        AffineTransform transform = new AffineTransform();
        //transform.translate(canvas.getWidth()/2, canvas.getHeight()/2);
        transform.translate(this.center.getX(), this.center.getY());
        transform.scale(this.scale, this.scale);
        return transform;
    }

}
