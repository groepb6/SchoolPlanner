package simulation.sims;

import data.persons.Person;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Dustin Hendriks
 */

public class Sim {
    SimSkin simSkin;
    private static final double aggressionFactorInBehaviour = .6;
    public int index = 0;
    private double angle;
    private double targetAngle = 0;
    private int speed;
    Point2D currentPos;
    Point2D targetPos;
    private FXGraphics2D g2d;
    private BufferedImage bufferedImage;
    private Canvas canvas;
    private ArrayList<Sim> sims = new ArrayList<>();
    private double angleMin = 9999;
    private double angleMax = -9999;

    public Sim(Point2D startPos, FXGraphics2D g2d, SimSkin simSkin, Canvas canvas) {
        this.currentPos = startPos;
        this.targetPos = startPos;
        this.speed = (int) (Math.random() * 2) + 3;
        this.g2d = g2d;
        this.simSkin = simSkin;
        this.canvas = canvas;
        this.angle = Math.random() * Math.PI * 2;
        this.targetPos = new Point2D.Double(500, 500);
    }

    public void setTargetAngle(double angle) {
        targetAngle = angle;
    }

    public void setSimSpeed(int speed) {
        this.speed = speed;
    }

    public void setTargetPos(Point2D targetPos) {
        this.targetPos = targetPos;
    }

    public void setCurrentPos(Point2D currentPos) {
        this.currentPos = currentPos;
    }

    public void update(ArrayList<Sim> sims) {
        Point2D newPos = new Point2D.Double(currentPos.getX() + this.speed * Math.cos(angle), currentPos.getY() + this.speed * Math.sin(angle));
        this.sims = sims;
        boolean hasCollision = false;
        for (Sim sim : sims) {
            if (sim != this && sim.simCollision(newPos)) {
                hasCollision = true;
                break;
            }
        }

        boolean onTarget = currentPos.distance(targetPos) < speed;
        if (!onTarget) {
            if (!hasCollision) currentPos = newPos;
            else angle += 2 * aggressionFactorInBehaviour;
            Point2D difference = new Point2D.Double(targetPos.getX() - currentPos.getX(), targetPos.getY() - currentPos.getY());
            double targetAngle = Math.atan2(difference.getY(), difference.getX());
            double angleDifference = targetAngle - angle;
            while (angleDifference > Math.PI) angleDifference -= 2 * Math.PI;
            while (angleDifference < -Math.PI) angleDifference += 2 * Math.PI;
            if (angleDifference < -aggressionFactorInBehaviour)
                angle -= aggressionFactorInBehaviour;
            else if (angleDifference > aggressionFactorInBehaviour)
                angle += aggressionFactorInBehaviour;
            else angle = targetAngle;
            setFrame();
        }
    }

    private void setFrame() {
        double angleRad = angle / Math.PI;
        while (angleRad > 1) angleRad -= 2;
        while (angleRad < -1) angleRad += 2;
        if (angleRad > 0.75) {
            angleRad = angleRad - 2;
        }
        ;
        int rotatedAngle = (int) ((((angleRad) * 2) + 2.5)); // 0 t/m 7
        //System.out.println("degrees "+Math.toDegrees(angle)+" angleInRad: "+ ((angle/Math.PI))+" rotatedAngle "+rotatedAngle);
        switch (rotatedAngle) {
            case 0:
                setSimSkinDir(simSkin.walkLeft(this));
                break;
            case 1:
                setSimSkinDir(simSkin.walkUp(this));
                break;
            case 2:
                setSimSkinDir(simSkin.walkRight(this));
                break;
            case 3:
                setSimSkinDir(simSkin.walkDown(this));
                break;
            default:
                System.out.println(rotatedAngle + " ERROR");
        }
    }


    private boolean isAvailable(Point2D position, Point2D simPosition) {
        return (position.distance(simPosition) < 32);
    }

    public Point2D getCurrentPos() {
        return this.currentPos;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void draw() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(currentPos.getX() - 32, currentPos.getY() - 32 - 28); // extra -28 to translate to feet
        g2d.drawImage(bufferedImage, affineTransform, null);
    }

    public boolean pushAside(ArrayList<Sim> sims, Point2D position) {
        for (Sim sim : sims) {
            if ((hasCollision(position, sim.currentPos)) && sim != this)
                sim.setTargetPos(new Point2D.Double(sim.getCurrentPos().getX() + 32, sim.getCurrentPos().getY() + 32));
        }
        return false;
    }

    public boolean checkCollision(ArrayList<Sim> sims, Point2D position) {
        for (Sim sim : sims) {
            if ((hasCollision(position, sim.currentPos)) && sim != this) return true;
        }
        return false;
    }

    public boolean simCollision(Point2D otherPos) {
        return otherPos.distance(currentPos) < 32;
    }

    public boolean hasCollision(Point2D position, Point2D comparePosition) {
        return comparePosition.distance(position) < 32; // was 64
    }

    public void setSimSkinDir(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }


}
