package simulation.sims;

import data.objects.Chair;
import gui.settings.ApplicationSettings;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;
import simulation.data.Area;
import simulation.pathfinding.Node;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sim {
    SimSkin simSkin;
    private static final double aggressionFactorInBehaviour = .5;
    int index = 0;
    private double angle;
    private double targetAngle = 0;
    private int speed;
    Point2D currentPos;
    Point2D targetPos;
    private FXGraphics2D g2d;
    private BufferedImage bufferedImage;
    private Node nodes[][];
    private Canvas canvas;
    private Sim[] sims;
    public ArrayList<Area> areas;
    private int targetArea;
    private int oldTargetArea;
    private int originalSpeed;
    public String name;
    private Chair chair;
    public boolean gotoChair = false;
    private boolean horizontalFirst = true;
    private Chair.Direction chairDir;

    public Sim(Point2D startPos, FXGraphics2D g2d, SimSkin simSkin, Canvas canvas, ArrayList<Area> areas, String name) {
        this.currentPos = startPos;
        this.targetPos = startPos;
        this.speed = (int) (Math.random() * 2) + 4;
        this.originalSpeed = speed;
        this.g2d = g2d;
        this.simSkin = simSkin;
        this.canvas = canvas;
        this.angle = Math.random() * Math.PI * 2;
        this.targetPos = new Point2D.Double(500, 500);
        this.areas = areas;
        this.targetArea = (int) (Math.random() * areas.size() - 1);
        this.name = name;
    }

    public void setTargetAngle(double angle) {
        targetAngle = angle;
    }

    public void setTargetPos(Point2D targetPos) {
        this.targetPos = targetPos;
    }

    public void update(Sim[] sims, Node[][] collisionNodes) {
        if (!gotoChair) {
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
                    if (!hasCollision && collisionNodes[(int) Math.round(newPos.getX() / 32)][(int) Math.round(newPos.getY() / 32)].walkable)
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
        } else
            searchChair();
    }
//    }

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
                System.out.println(rotatedAngle + " ERROR IN SIM ANGLE");
                throw new IllegalArgumentException("Your SIM angle is invalid!");
        }
    }

    private void searchChair() {
        if (horizontalFirst) {
            if (!(Math.abs(currentPos.getX() - targetPos.getX()) <= speed)) {
                if (currentPos.getX() < targetPos.getX()) {
                    currentPos.setLocation(currentPos.getX() + speed, currentPos.getY());
                    setSimSkinDir(simSkin.walkRight(this));
                } else if (currentPos.getX() > targetPos.getX()) {
                    currentPos.setLocation(currentPos.getX() - speed, currentPos.getY());
                    setSimSkinDir(simSkin.walkLeft(this));
                }
            } else {
                if (Math.abs(currentPos.getY() - targetPos.getY()) <= speed) {
                    setSimFrame();
                } else if (currentPos.getY() < targetPos.getY()) {
                    currentPos.setLocation(currentPos.getX(), currentPos.getY() + speed);
                    setSimSkinDir(simSkin.walkDown(this));
                } else if (currentPos.getY() > targetPos.getY()) {
                    currentPos.setLocation(currentPos.getX(), currentPos.getY() - speed);
                    setSimSkinDir(simSkin.walkUp(this));
                }
            }
        } else {
            if (!(Math.abs(currentPos.getY() - targetPos.getY()) <= speed)) {
                if (currentPos.getY() < targetPos.getY()) {
                    currentPos.setLocation(currentPos.getX(), currentPos.getY() + speed);
                    setSimSkinDir(simSkin.walkDown(this));
                } else if (currentPos.getY() > targetPos.getY()) {
                    currentPos.setLocation(currentPos.getX(), currentPos.getY() - speed);
                    setSimSkinDir(simSkin.walkUp(this));
                }
            } else {
                if (Math.abs(currentPos.getX() - targetPos.getX()) <= speed) {
                    setSimFrame();
                } else if (currentPos.getX() < targetPos.getX()) {
                    currentPos.setLocation(currentPos.getX() + speed, currentPos.getY());
                    setSimSkinDir(simSkin.walkRight(this));
                } else if (currentPos.getX() > targetPos.getX()) {
                    currentPos.setLocation(currentPos.getX() - speed, currentPos.getY());
                    setSimSkinDir(simSkin.walkLeft(this));
                }
            }
        }
    }

    private void setSimFrame() {
        switch (this.chairDir) {
            case RIGHT:
                setSimSkinDir(simSkin.stationaryRight());
                break;
            case LEFT:
                setSimSkinDir(simSkin.stationaryLeft());
                break;
            case UP:
                setSimSkinDir(simSkin.stationaryUp());
                break;
            case DOWN:
                setSimSkinDir(simSkin.stationaryDown());
                break;
            default:
                setSimSkinDir(simSkin.stationaryUp());
        }
    }

    public Point2D getCurrentPos() {
        return this.currentPos;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void draw() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(currentPos.getX() - 16, currentPos.getY() - 16 - 28); // extra -28 to translate to feet
        g2d.drawImage(bufferedImage, affineTransform, null);
    }

    private boolean isOutOfBounds(Point2D pos) {
        return (pos.getX() < 0 || pos.getX() >= canvas.getWidth() - 32 || pos.getY() < 0 || pos.getY() >= canvas.getHeight() - 32);
    }

    private boolean isBetterNode(int score, int currentPosX, int currentPosY, Node[][] nodes, int maxWidth, int maxHeight) {
        if (currentPosX > -1 && currentPosX < maxWidth && currentPosY > -1 && currentPosY < maxHeight && nodes[currentPosX][currentPosY].scores[this.targetArea] != -1) {
            return (nodes[currentPosX][currentPosY].scores[this.targetArea] < score);
        } else return false;
    }

    public boolean isInTargetArea() {
        return (((currentPos.getX() > areas.get(targetArea).x) && currentPos.getX() < (areas.get(targetArea).x + areas.get(targetArea).areaWidth)) && ((currentPos.getY() > areas.get(targetArea).y)) && currentPos.getY() < (areas.get(targetArea).y + areas.get(targetArea).areaHeight));
    }

    public void pathFind(Node[][] nodes) {
        if (!gotoChair) {
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
    }

    public void gotoChair(Point2D targetPos, Chair.Direction direction, Chair chair) {
        if (chair.direction == Chair.Direction.DOWN)
            this.targetPos = new Point2D.Double(targetPos.getX() * 32, targetPos.getY() * 32 + 16);
        else this.targetPos = new Point2D.Double(targetPos.getX() * 32, targetPos.getY() * 32);
        this.chair = chair;
        this.gotoChair = true;
        horizontalFirst = ApplicationSettings.getHorizontalFirst(areas.get(targetArea).areaName);
        chairDir = direction;
    }

    public int getTargetArea() {
        return targetArea;
    }

    public void setTargetArea(Area area) {
        this.setTargetArea(area.areaID);
    }

    public void setTargetArea(int areaNumber) {
        destroyAllTargets();
        this.oldTargetArea = targetArea;
        this.targetArea = areaNumber;
    }

    private void destroyAllTargets() {
        gotoChair = false;
        if (chair != null)
            chair.isAvailable = true;
    }

    public void setOldTargetArea() {
        this.targetArea = oldTargetArea;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean simCollision(Point2D otherPos) {
        return otherPos.distance(currentPos) < 32;
    }

    private void setSimSkinDir(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
