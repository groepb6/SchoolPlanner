package simulation.pathfinding;

import simulation.data.Area;

import java.awt.geom.Point2D;
import java.util.HashMap;

/**
 * @author Dustin Hendriks
 * Class node is needed to bind a score to a specific tile represented by a x and y value. The node also contains collision information about this tile.
 */

public class Node {
    public int score;
    public int x;
    public int y;
    public boolean walkable = true;
    public int[] scores;

    /**
     * The constructor needs the x-value, y-value and score of the Node.
     * @param x Defines the x-value of the node.
     * @param y Defines the y-value of the node.
     * @param score Defines the distance to the goal position.
     */

    public Node(int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
        scores = new int[30]; // Space for 30 areas at the moment.
    }

    /**
     * addScore reads the current score value from attribute score and copy's it to the int[areaNumber] scores list
     * @param areaNumber Defines for which area the score is.
     */

    void addScore(int areaNumber) {
        scores[areaNumber]=this.score;
    }

    /**
     * Receive the score (currently not used because of public variables.
     * @param areaNumber Defines the areaNumber (areas List).
     * @return Return an integer with the score.
     */

    public int getScore(int areaNumber)  {
        return scores[areaNumber];
    }

    /**
     * Receive a Point2D with the position of the Node.
     * @return Receive a Point2D with the position of the Node.
     */

    public Point2D getPosition() {
        return new Point2D.Double(x,y);
    }
}
