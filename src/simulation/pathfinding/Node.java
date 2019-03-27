package simulation.pathfinding;

import simulation.data.Area;

import java.awt.geom.Point2D;
import java.util.HashMap;

public class Node {
    public int score;
    public int x;
    public int y;
    public boolean walkable = true;
    public int[] scores;

    public Node(int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
        scores = new int[30]; // Space for 30 areas at the moment.
    }

    public void addScore(int areaNumber) {
        scores[areaNumber]=this.score;
    }

    public int getScore(int areaNumber)  {
        return scores[areaNumber];
    }

    public Point2D getPosition() {
        return new Point2D.Double(x,y);
    }
}
