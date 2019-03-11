package simulation.sims;

import data.persons.Person;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;
import simulation.pathfinding.Astar;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class Sim{
    SimSkin simSkin;
    public int index = 0;
    private int speed;
    Point2D currentPos;
    Point2D destinationPos;
    Person.Gender gender;
    private FXGraphics2D g2d;
    private BufferedImage bufferedImage;
    private Canvas canvas;
    private ArrayList<Sim> sims = new ArrayList<>();

    public Sim(Point2D startPos, Person.Gender gender, FXGraphics2D g2d, SimSkin simSkin, Canvas canvas) {
        this.currentPos = startPos;
        this.destinationPos = startPos;
        this.gender = gender;
        this.speed = (int) (Math.random() * 5) + 3;
        this.g2d = g2d;
        this.simSkin = simSkin;
        this.canvas = canvas;
    }

    public void setSimSpeed(int speed) {
        this.speed = speed;
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    private Direction direction;

    public void setDestinationPos(Point2D destinationPos) {
        this.destinationPos = destinationPos;
    }

    public void setCurrentPos(Point2D currentPos) {
        this.currentPos = currentPos;
    }

    public void update(ArrayList<Sim> sims) {
        this.sims = sims;
        if (this.currentPos.getX() != this.destinationPos.getX() && Math.abs(this.currentPos.getX() - this.destinationPos.getX()) > this.speed) {
            if (this.destinationPos.getX() > this.currentPos.getX()) moveRight();
            else if (this.destinationPos.getX() < this.currentPos.getX()) moveLeft();

        } else if (this.currentPos.getY() != this.destinationPos.getY() && Math.abs(this.currentPos.getY() - this.destinationPos.getY()) > this.speed) {
            if (this.destinationPos.getY() > this.currentPos.getY()) moveDown();
            else if (this.destinationPos.getY() < this.currentPos.getY()) moveUp();
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

    private void moveLeft() {
        if (!checkCollision(sims, new Point2D.Double(currentPos.getX() - speed, currentPos.getY()))) {
            currentPos.setLocation(currentPos.getX() - speed, currentPos.getY());
            setSimSkinDir(simSkin.walkLeft(this));
        } else moveUp();
    }

    private void moveRight() {
        if (!checkCollision(sims, new Point2D.Double(currentPos.getX() + speed, currentPos.getY()))) {
            currentPos.setLocation(currentPos.getX() + speed, currentPos.getY());
            setSimSkinDir(simSkin.walkRight(this));
        } else moveDown();
    }

    private void moveUp() {
        if (!checkCollision(sims, new Point2D.Double(currentPos.getX(), currentPos.getY()-speed))) {
            currentPos.setLocation(currentPos.getX(), currentPos.getY() - speed);
            setSimSkinDir(simSkin.walkUp(this));
        }
    }

    private void moveDown() {
        if (!checkCollision(sims, new Point2D.Double(currentPos.getX(), currentPos.getY()+speed))) {
            currentPos.setLocation(currentPos.getX(), currentPos.getY() + speed);
            setSimSkinDir(simSkin.walkDown(this));
        }
    }

    public void draw() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(currentPos.getX() - 32, currentPos.getY() - 32);
        g2d.drawImage(bufferedImage, affineTransform, null);
    }

    public boolean pushAside(ArrayList<Sim> sims, Point2D position) {
        for (Sim sim : sims) {
            if ((hasCollision(position, sim.currentPos)) && sim != this) sim.setDestinationPos(new Point2D.Double(sim.getCurrentPos().getX()+32, sim.getCurrentPos().getY()+32));
        }
        return false;
    }

    public boolean checkCollision(ArrayList<Sim> sims, Point2D position) {
        for (Sim sim : sims) {
            if ((hasCollision(position, sim.currentPos)) && sim != this) return true;
        }
        return false;
    }

    public boolean hasCollision(Point2D position, Point2D comparePosition) {
        return comparePosition.distance(position) < 32;
    }

    public void setSimSkinDir(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }


}
