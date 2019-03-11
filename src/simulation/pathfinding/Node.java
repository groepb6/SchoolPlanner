package simulation.pathfinding;

import java.awt.geom.Point2D;

public class Node {
    private int gCost;
    private int hCost;
    private int score;
    public Point2D position;

    public Node(Point2D position) {
//        gCost=(int)startPos.distance(ownPos);
//        hCost=(int)ownPos.distance(destinationPos);
//        score=gCost + hCost;
        this.position = position;
    }

    public int getScore() {
        return this.score;
    }
}
