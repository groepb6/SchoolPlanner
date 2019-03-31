package simulation.sims;

import data.objects.Chair;
import data.schoolrelated.Group;
import gui.settings.ApplicationSettings;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;
import simulation.Map;
import simulation.data.Area;
import simulation.pathfinding.Node;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Dustin Hendriks
 * The Sim class enables easy drawing and moving of characters. Also enables collision.
 */

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
    private ArrayList<Sim> sims = new ArrayList<>();
    public ArrayList<Area> areas;
    private int targetArea;
    private int oldTargetArea;
    private int originalSpeed;
    public String name;
    private Chair chair;
    public boolean gotoChair = false;
    private boolean horizontalFirst = true;
    private Chair.Direction chairDir;
    private Group group;
    private Map map;

    /**
     * The Sim constructor needs some basic values at start.
     *
     * @param startPos Needed for init position.
     * @param g2d      Needed for the draw() method.
     * @param simSkin  What does the Sim look like?
     * @param canvas   Needed to define the width and height available.
     * @param areas    Needed to set the target destination for Sims.
     * @param name     Can be displayed when selected.
     * @param group    Is used to keep track of the group that this Sim is part of.
     */

    public Sim(Point2D startPos, FXGraphics2D g2d, SimSkin simSkin, Canvas canvas, ArrayList<Area> areas, String name, Group group, Map map) {
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
        this.group = group;
        this.map = map;
    }

    public Group getGroup() {
        return group;
    }

    public void setTargetArea(Area area) {
        destroyAllTargets();
        targetArea = areas.indexOf(area);
    }

    /**
     * Set the targetAngle of a Sim. Can be used for future purposes.
     *
     * @param angle Defines the angle the Sim should have.
     */

    public void setTargetAngle(double angle) {
        targetAngle = angle;
    }

    /**
     * The targetPos can be used to set the destination position. Can be used for pathfinding.
     *
     * @param targetPos Defines the goal position of the Sim.
     */

    public void setTargetPos(Point2D targetPos) {
        this.targetPos = targetPos;
    }

    /**
     * The update method updates the position of the sims.
     *
     * @param sims           To check for collision an array of every other Sim is needed.
     * @param collisionNodes Check if the Sim target position isn't through a wall.
     */

    public void update(ArrayList<Sim> sims, Node[][] collisionNodes) {
        if (!gotoChair) {
            Point2D newPos = new Point2D.Double(currentPos.getX() + this.speed * Math.cos(angle), currentPos.getY() + this.speed * Math.sin(angle));
            this.sims = sims;
            boolean hasCollision = false;
            for (Sim sim : sims) {
                if (sim != this && sim.simCollision(newPos) && !sim.gotoChair) {
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
            } else if (isTeacher()) {
                setViewForTeacher();
            }
        } else if (!beingControlled()) {
            searchChair();
        } else destroyAllTargets();
    }

    private boolean isTeacher() {
        return (group.getName().equals("Teachers"));
    }

    public boolean beingControlled() {
        return map.simToFollow == this && map.hijackedSim;
    }

    /**
     * The setFrame method grabs the corresponding Sim animation frame.
     */

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

    public void setViewForTeacher() {
        Point2D pos = map.lookUpRoom(areas.get(targetArea).areaName);

        if (pos != null) {
            Point2D currentPos = new Point2D.Double(this.currentPos.getX()/32, this.currentPos.getY()/32);
            if (currentPos.distance(pos) < speed) {
                if (System.currentTimeMillis()%3000<500)
                setSimSkinDir(simSkin.pointWithStickLeftFrontFacing(this));
            }
        }
    }

    /**
     * The searchChair method disables collision and pathFinding and goes to the chair in the most simplistic way possible. But if it works, don't break it!
     */

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
                    currentPos.setLocation(targetPos.getX(), targetPos.getY());
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
                    currentPos.setLocation(targetPos.getX(), targetPos.getY());
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

    /**
     * Alternative method to decide the simSkin when the Sim has reached his chair.
     */

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
            case TOILET:
                setSimSkinDir(simSkin.toiletPee());
                break;
            default:
                setSimSkinDir(simSkin.stationaryUp());
        }
    }

    /**
     * Receive the current position of the Sim.
     *
     * @return Return the current position of the Sim.
     */

    public Point2D getCurrentPos() {
        return this.currentPos;
    }

    /**
     * The getSpeed() method returns the current Sim speed.
     *
     * @return Return an integer that defines the amount of pixels the Sim moves every update.
     */

    public int getSpeed() {
        return this.speed;
    }

    /**
     * The draw() method draws the current bufferedImage (which is ripped from SimSkin) to the current position of the sim. Translate is tuning for the perfect centered position.
     */

    public void draw() {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(currentPos.getX() - 16, currentPos.getY() - 16 - 28); // extra -28 to translate to feet
        g2d.drawImage(bufferedImage, affineTransform, null);
    }

    /**
     * Check if the Sim position is not out of the map.
     *
     * @param pos Defines the position of the Sim.
     * @return Return true or false depending of the position is in or out the map.
     */

    private boolean isOutOfBounds(Point2D pos) {
        return (pos.getX() < 0 || pos.getX() >= canvas.getWidth() - 32 || pos.getY() < 0 || pos.getY() >= canvas.getHeight() - 32);
    }

    /**
     * Check if a specific node is "better == lower)" than the current node.
     *
     * @param score       Defines the score (amount of tiles from target).
     * @param currentPosX Defines the current x position of the Sim.
     * @param currentPosY Defines the curernt y position of the Sim.
     * @param nodes       Is a 2D Node[][] array, used to compare current node with potential better Node.
     * @param maxWidth    Defines the maximum width of the map.
     * @param maxHeight   Defines the maximum height of the map.
     * @return Return true or false value depending if the score is lower or higher than the current score.
     */

    private boolean isBetterNode(int score, int currentPosX, int currentPosY, Node[][] nodes, int maxWidth, int maxHeight) {
        if (currentPosX > -1 && currentPosX < maxWidth && currentPosY > -1 && currentPosY < maxHeight && nodes[currentPosX][currentPosY].scores[this.targetArea] != -1) {
            return (nodes[currentPosX][currentPosY].scores[this.targetArea] < score);
        } else return false;
    }

    /**
     * Check if Sim is in the targetArea.
     *
     * @return Return true or false value depending if the Sim is in his target position.
     */

    public boolean isInTargetArea() {
        return (((currentPos.getX() > areas.get(targetArea).x) && currentPos.getX() < (areas.get(targetArea).x + areas.get(targetArea).areaWidth)) && ((currentPos.getY() > areas.get(targetArea).y)) && currentPos.getY() < (areas.get(targetArea).y + areas.get(targetArea).areaHeight));
    }

    /**
     * Let the Sim path find his goal using the distance map.
     *
     * @param nodes Is needed to grab the distance map.
     */

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

    /**
     * The gotoChair method can be called from the Map class and activates the Chair as target position for the Sims.
     *
     * @param targetPos Defines the target position.
     * @param direction Defines the direction of the chair (left, up, down or right).
     * @param chair     Chair object. Can be used to set the attribute this.chair to chair.
     */

    public void gotoChair(Point2D targetPos, Chair.Direction direction, Chair chair) {
        if (chair.direction == Chair.Direction.DOWN)
            this.targetPos = new Point2D.Double(targetPos.getX() * 32, targetPos.getY() * 32 + 16);
        else this.targetPos = new Point2D.Double(targetPos.getX() * 32, targetPos.getY() * 32);
        this.chair = chair;
        this.gotoChair = true;
        horizontalFirst = ApplicationSettings.getHorizontalFirst(areas.get(targetArea).areaName);
        chairDir = direction;
    }

    /**
     * Receive the targetArea
     *
     * @return Number that defines the area position in the areas list.
     */

    public int getTargetArea() {
        return targetArea;
    }

    /**
     * Change the targetArea to the corresponding number.
     *
     * @param areaNumber Number that defines the area position in the areas list.
     */

    public void setTargetArea(int areaNumber) {
        destroyAllTargets();
        this.oldTargetArea = targetArea;
        this.targetArea = areaNumber;
    }

    public void goBackToPreviousLocation() {
        this.targetArea=this.oldTargetArea;
    }

    /**
     * Can be called in case the target is changed (for example when a lesson is completed).
     */

    public void destroyAllTargets() {
        gotoChair = false;
        if (chair != null) {
            chair.isAvailable = true;
            chair = null;
        }
    }

    /**
     * Revert the targetArea to the old position.
     */

    public void setOldTargetArea() {
        this.targetArea = oldTargetArea;
    }

    /**
     * Check if this has collision with another position.
     *
     * @param otherPos Defines a compare position
     * @return Return true or false depending on if the difference is smaller than 32.
     */

    private boolean simCollision(Point2D otherPos) {
        return otherPos.distance(currentPos) < 32;
    }

    /**
     * setSimSkinDir(BufferedImage bufferedImage) can be used to change the current image to the next frame or to a different animation.
     *
     * @param bufferedImage = A cut out of the Sim sprite.
     */

    private void setSimSkinDir(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
