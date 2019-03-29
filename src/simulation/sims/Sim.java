package simulation.sims;

import data.persons.Person;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;
import simulation.data.Area;
import simulation.pathfinding.Node;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class Sim {
    SimSkin simSkin;
    private static final double aggressionFactorInBehaviour = .5;
    public int index = 0;
    private double angle;
    private double targetAngle = 0;
    private int speed; //TODO: turn into double
    Point2D currentPos;
    Point2D targetPos;
    private FXGraphics2D g2d;
    private BufferedImage bufferedImage;
    private Node nodes[][];
    private Canvas canvas;
    private Sim[] sims;

    public ArrayList<Area> areas;
    private int targetArea;

    public static final int DEFAULTSPEED = 3;

    public Sim(Point2D startPos, FXGraphics2D g2d, SimSkin simSkin, Canvas canvas, ArrayList<Area> areas) {
        this.currentPos = startPos;
        this.targetPos = startPos;
        this.speed = (int) (Math.random() * 2) + 3;
        this.g2d = g2d;
        this.simSkin = simSkin;
        this.canvas = canvas;
        this.angle = Math.random() * Math.PI * 2;
        this.targetPos = new Point2D.Double(500, 500);
        this.areas = areas;
        this.targetArea = (int)(Math.random()*areas.size()-1);
    }

    public void setTargetArea(Area area) {
        this.targetArea = area.areaID; //TODO: make Sim use Area objects instead of just the ID
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

    public void update(Sim[] sims, Node[][] collisionNodes) {
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
            if (!isOutOfBounds(newPos))
                if (!hasCollision && collisionNodes[(int)Math.round(newPos.getX() / 32)][(int)Math.round(newPos.getY() / 32)].walkable)
                    currentPos = newPos;
                else angle += 1.5 * aggressionFactorInBehaviour;
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

        int rotatedAngle = (int) ((((angleRad) * 2) + 2.5)); // 0 t/m 7
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

    private boolean isOutOfBounds(Point2D pos) {
        return (pos.getX() < 0 || pos.getX() > canvas.getWidth() || pos.getY() < 0 || pos.getY() > canvas.getHeight());
    }

    private boolean isBetterNode(int score, int currentPosX, int currentPosY, Node[][] nodes, int maxWidth, int maxHeight) {
        if (currentPosX > -1 && currentPosX < maxWidth && currentPosY > -1 && currentPosY < maxHeight && nodes[currentPosX][currentPosY].scores[this.targetArea] != -1) {
            return (nodes[currentPosX][currentPosY].scores[this.targetArea] < score);
        } else return false;
    }

    public void pathFind(Node[][] nodes) {
        int maxWidth = (int) (canvas.getWidth() / 32);
        int maxHeight = (int) (canvas.getHeight() / 32);
        int currentPosX = (int) Math.round(currentPos.getX() / 32);
        int currentPosY = (int) Math.round(currentPos.getY() / 32);
        Node bestNode;
        bestNode = nodes[currentPosX][currentPosY];

        if (bestNode.scores[targetArea] < 0)
            bestNode.scores[targetArea] = Integer.MAX_VALUE;

        if (isBetterNode(bestNode.scores[targetArea], currentPosX - 1, currentPosY, nodes, maxWidth, maxHeight)) {
            if (bestNode.scores[targetArea] == Integer.MAX_VALUE)
                bestNode.scores[targetArea] = -1;
            bestNode = nodes[currentPosX - 1][currentPosY];
        }
        if (isBetterNode(bestNode.scores[targetArea], currentPosX + 1, currentPosY, nodes, maxWidth, maxHeight)) {
            if (bestNode.scores[targetArea] == Integer.MAX_VALUE)
                bestNode.scores[targetArea] = -1;
            bestNode = nodes[currentPosX + 1][currentPosY];
        }
        if (isBetterNode(bestNode.scores[targetArea], currentPosX, currentPosY - 1, nodes, maxWidth, maxHeight)) {
            if (bestNode.scores[targetArea] == Integer.MAX_VALUE)
                bestNode.scores[targetArea] = -1;
            bestNode = nodes[currentPosX][currentPosY - 1];
        }
        if (isBetterNode(bestNode.scores[targetArea], currentPosX, currentPosY + 1, nodes, maxWidth, maxHeight)) {
            if (bestNode.scores[targetArea] == Integer.MAX_VALUE)
                bestNode.scores[targetArea] = -1;
            bestNode = nodes[currentPosX][currentPosY + 1];
        }
        this.targetPos = new Point2D.Double((int) bestNode.getPosition().getX() * 32, (int) bestNode.getPosition().getY() * 32);
    }

    public int getNumber(int targetPosX, int targetPosY, Node[][] nodes) {
        return nodes[targetPosX][targetPosY].scores[targetArea];
    }

    public boolean checkCollision(Sim[] sims, Point2D position) {
        for (Sim sim : sims) {
            if ((hasCollision(position, sim.currentPos)) && sim != this) return true;
        }
        return false;
    }

    public void setNodes(Node[][] nodes) {
        this.nodes = nodes;
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
