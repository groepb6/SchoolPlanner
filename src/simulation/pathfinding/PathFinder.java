package simulation.pathfinding;
import simulation.data.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class PathFinder {
    private LinkedList<Node> openList = new LinkedList<>(); // Moet nog gedaan worden
    private LinkedList<Node> closedList = new LinkedList<>(); // Is hier al geweest
    private int amountOfTilesWidth;
    private int amountOfTilesHeight;
    private int addCount = 0;
    private Node[][] allNodes;

    public PathFinder(Node[][] nodes, int amountOfTilesWidth, int amountOfTilesHeight, int tileWidth, int tileHeight, ArrayList<Area> areas) {
        this.amountOfTilesWidth = amountOfTilesWidth;
        this.amountOfTilesHeight = amountOfTilesHeight;
        this.allNodes = nodes;
        Area testArea = areas.get(0);
        int areaX = testArea.x / tileWidth;
        int areaY = testArea.y / tileHeight;
        //Point2D targetPoint = new Point2D.Double(areaX, areaY);
        Point2D targetPoint = new Point2D.Double(50,50);
        openList.add(new Node(100,100, 0));
        addPoint(openList.get(0));
        while (openList.size() >0)
            addPoint(openList.get(0));
    }

    public void addPoint(Node node) {
        closedList.add(node);
        openList.remove(node);

        Point2D nodePos = node.getPosition();
        Node leftNode = getNode((int) nodePos.getX() - 1, (int) nodePos.getY());
        Node rightNode = getNode((int) nodePos.getX() + 1, (int) nodePos.getY());
        Node aboveNode = getNode((int) nodePos.getX(), (int) nodePos.getY() - 1);
        Node belowNode = getNode((int) nodePos.getX(), (int) nodePos.getY() + 1);

        if (leftNode != null)
            if (checkCanAdd(leftNode)) {
                leftNode.score=(node.score + 1);
                closedList.add(leftNode);
                openList.add(leftNode);
            }

        if (rightNode != null)
            if (checkCanAdd(rightNode)) {
                rightNode.score=(node.score + 1);
                closedList.add(rightNode);
                openList.add(rightNode);
            }

        if (aboveNode != null)
            if (checkCanAdd(aboveNode)) {
                aboveNode.score=(node.score+ 1);
                closedList.add(aboveNode);
                openList.add(aboveNode);

            }

        if (belowNode != null)
            if (checkCanAdd(belowNode)) {
                belowNode.score=(node.score + 1);
                closedList.add(belowNode);
                openList.add(belowNode);
            }
        //System.out.println(closedList.size()); //TODO: !Hier zitten superveel dingen in blijkbaar!

    }

    private Node getNode(int x, int y) {
        Point2D nodePos = new Point2D.Double(x, y);
        if ((nodePos.getX() >= 0) && (nodePos.getX() < amountOfTilesWidth) && (nodePos.getY() >= 0) && (nodePos.getY() < amountOfTilesHeight))
            return allNodes[(int) nodePos.getX()][(int) nodePos.getY()];
        else return null;
    }

    private boolean checkCanAdd(Node node) {
        return (((node.getPosition().getX() >= 0) && (node.getPosition().getX() < amountOfTilesWidth) && (node.getPosition().getY() >= 0) && (node.getPosition().getY() < amountOfTilesHeight)) && (!(closedList.contains(node)))) && !closedList.contains(node) && allNodes[(int) node.getPosition().getX()][(int) node.getPosition().getY()].walkable;
    }

    public LinkedList<Node> getClosedList() {
        return closedList;
    }



}
