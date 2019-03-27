//package simulation;
//
//import javafx.animation.AnimationTimer;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import org.jfree.fx.FXGraphics2D;
//import simulation.data.Area;
//import simulation.sims.Sim;
//import simulation.sims.SimSkin;
//import java.awt.geom.Point2D;
//import java.util.*;
//
///**
// * @author Dustin Hendriks
// * The class SimUpdate handles drawing and controlling Sims, surroundings, objects and layers using the Map class. Everything has to be drawed on exactly the correct moment, this is what the SimUpdate class is for.
// */
//
//public class SimUpdate {
//    private FXGraphics2D g2d;
//    private Canvas canvas;
//    private Scene scene;
//    private double delay = 0.;
//    private int counter = 0;
//    private SchoolMap map;
//    private ArrayList<Sim> sims = new ArrayList<>();
//    private AnimationTimer animationTimer;
//    private ArrayList<SimSkin> simSkins = new ArrayList<>();
//
//    /**
//     * The constructor initializes an animation timer.
//     * @param g2d Needed to draw Sims / objects / layers on canvas.
//     * @param canvas Needed to draw on.
//     * @param scene Scene can be used to connect actions to. For example a mouse press or button click.
//     * @param map Map is used to draw layers, objects and calculate the pathfinding using the Pathfinding class.
//     */
//
//    public SimUpdate(FXGraphics2D g2d, Canvas canvas, Scene scene, SchoolMap map) {
//        this.g2d = g2d;
//        this.canvas = canvas;
//        this.scene = scene;
//        this.map = map;
//        createSimsForTesting();
//
//        animationTimer = new AnimationTimer() {
//            long last = -1;
//
//            @Override
//            public void handle(long now) {
//                if (last == -1)
//                    last = now;
//                update((now - last) / 1.e9);
//                last = now;
//            }
//        };
//        animationTimer.start();
//    }
//
//
//
//    /**
//     * Create sims for testing creates a few skins which can be used for sims. Afterwards each Sim receives a random skin and is spawned on a random position in the parkinglot. Hence a Sim is only spawned if there is a free spot available.
//     */
//
//    private void createSimsForTesting() {
//        for (int i = 0; i < 9; i++)
//            simSkins.add(new SimSkin(SimSkin.Role.student, i)); // 0 was i
//        Area spawnArea = null;
//        for (Area area : map.areas)
//            if (area.areaName.equals("ParkingLot"))
//                spawnArea = area;
//
//        int amountOfSimsToBeAdded = 50;
//        int amountOfTries=0;
//        LinkedList<Point2D> spawnPositions = new LinkedList<>();
//        while (sims.size() < amountOfSimsToBeAdded) {
//            amountOfTries++;
//            if (amountOfTries>=10*amountOfSimsToBeAdded)
//            {
//                System.out.println("You are trying to spawn too many sims in a too small area");
//                break;
//            }
//            Point2D spawnPos = new Point2D.Double(spawnArea.x + (Math.random() * spawnArea.areaWidth), spawnArea.y + (Math.random() * spawnArea.areaHeight));
//            if (map.getCollisionLayer()[(int)Math.round(spawnPos.getX()/32)][(int) Math.round(spawnPos.getY()/32)].walkable)
//                if (canAdd(spawnPos)) {
//                    spawnPositions.add(spawnPos);
//                    sims.add(new Sim(spawnPos, g2d, simSkins.get((int) (Math.random() * simSkins.size() - 1)), canvas, map.areas));
//                }
//        }
//        map.sims = sims;
//    }
//
//    /**
//     * Can add decides whether a character can be spawned without being spawned inside another character.
//     * @param spawnPos Defines the spawn position a new Sim could receive.
//     * @return Returns true or false depending if the spot is free.
//     */
//
//    private boolean canAdd(Point2D spawnPos) {
//        boolean canAdd = true;
//        for (Sim sim : sims) {
//            if (!canAddHelper(spawnPos, sim.getCurrentPos())) {
//                canAdd = false;
//                break;
//            }
//        }
//        return canAdd;
//    }
//
//    /**
//     * Defines whether the distance is smaller or bigger than 64. Helper function for the canAdd() emthod.
//     * @param spawnPos SpawnPos defines the potential new spawn position.
//     * @param otherSimPos otherSImPos defines the position of another sim.
//     * @return Is the distance between these points smaller than 64?
//     */
//
//    private boolean canAddHelper(Point2D spawnPos, Point2D otherSimPos) {
//        return (!(spawnPos.distance(otherSimPos) < 64));
//    }
//
//    /**
//     * stopTimer can interrupt the animationTimer. This action should be performed when exiting the simulator.
//     */
//
//    public void stopTimer() {
//        animationTimer.stop();
//    }
//
//    /**
//     * The update function performs every action on the right time.
//     * @param deltatime deltatime is the time between the last run and current thing.
//     */
//
//    private void update(double deltatime) {
//        double startDelay = .075;
//        if (map.getPathFinder().loaded) {
//            delay += deltatime;
//            counter = (int) (Math.round(delay * 10.) / 10.);
//            if (delay >= startDelay) {
//                delay = 0;
//                map.restoreCanvas();
//                updatePositionSims();
//                drawSims();
//                map.drawWalls();
//                map.drawCollision();
//                map.drawStringPathFinder(0);
//                map.activatePathFindingOnSims();
//            }
//        }
//    }
//
//    /**
//     * The drawSims method draws every Sim depending on the corresponding height. Highest first lowest last.
//     */
//
//    private void drawSims() {
//        sims.sort(new Comparator<Sim>() {
//            @Override
//            public int compare(Sim sim1, Sim sim2) {
//                return (int) sim1.getCurrentPos().getY() < sim2.getCurrentPos().getY() ? -1 : sim1.getCurrentPos().getY() == sim2.getCurrentPos().getY() ? 0 : 1;
//            }
//        });
//        for (Sim sim : sims) {
//            sim.update(sims, map.getCollisionLayer());
//            sim.draw();
//        }
//    }
//
//    /**
//     * When the mouse is moved every Sim is moved tto the mouse position, if enabled.
//     */
//
//    private void updatePositionSims() {
//        canvas.setOnMouseMoved(e -> {
//            for (Sim sim : sims)
//                if (sim.getSpeed() != 0 && !map.activatedPathFinding)
//                    sim.setTargetPos(new Point2D.Double(e.getX(), e.getY()));
//        });
//
//
//    }
//}
