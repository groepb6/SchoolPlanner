package simulation.pathfinding;

import java.awt.geom.Point2D;

public class Node {
    public int score;
    public int x;
    public int y;
    public boolean walkable = true;

    public Node(int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
    }

    public Point2D getPosition() {
        return new Point2D.Double(x,y);
    }
}
