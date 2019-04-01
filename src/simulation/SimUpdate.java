package simulation;

import data.schoolrelated.Group;
import gui.settings.ApplicationSettings;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;
import simulation.data.Area;
import simulation.logic.TimerHandler;
import simulation.sims.NameList;
import simulation.sims.Sim;
import simulation.sims.SimSkin;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * @author Dustin Hendriks
 * The class SimUpdate handles drawing and controlling Sims, surroundings, objects and layers using the Map class. Everything has to be drawed on exactly the correct moment, this is what the SimUpdate class is for.
 */

public class SimUpdate {
    private FXGraphics2D g2d;
    private Canvas canvas;
    private Scene scene;
    private double delay = 0.;
    private int counter = 0;
    private Map map;
    public ArrayList<Sim> simStudents = new ArrayList<>();
    private ArrayList<Sim> simTeachers = new ArrayList<>();
    private AnimationTimer animationTimer;
    private ArrayList<SimSkin> simStudentSkins = new ArrayList<>();
    private ArrayList<SimSkin> simTeacherSkins = new ArrayList<>();
    private NameList nameList = new NameList();
    private double timerMultiplier=1;
    private TimerHandler timerHandler;
    private ArrayList<String> restAreas = new ArrayList<>();

    /**
     * The constructor initializes an animation timer.
     *
     * @param g2d    Needed to draw Sims / objects / layers on canvas.
     * @param canvas Needed to draw on.
     * @param scene  Scene can be used to connect actions to. For example a mouse press or button click.
     * @param map    Map is used to draw layers, objects and calculate the pathfinding using the Pathfinding class.
     */

    public SimUpdate(FXGraphics2D g2d, Canvas canvas, Scene scene, Map map) {
        this.g2d = g2d;
        this.canvas = canvas;
        this.scene = scene;
        this.map = map;
        addSimSkins();
        this.timerHandler = new TimerHandler(this, map);
        animationTimer = new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1.e9);
                last = now;
            }
        };
        animationTimer.start();
        updatePositionSims();
    }

    /**
     * Create skins for each student.
     */

    private void addSimSkins() {
        for (int i = 0; i < 5; i++)
            simStudentSkins.add(new SimSkin(SimSkin.Role.student, i));
        for (int i = 0; i < 4; i++)
            simTeacherSkins.add(new SimSkin(SimSkin.Role.teacher, i));
    }

    public void createStudents(Group group) {
        int amountToAdd = ApplicationSettings.studentsPerGroup;
        int amountOfTries = 0;
        int oldSize = simStudents.size();
        while (simStudents.size()-oldSize < amountToAdd) {
            amountOfTries++;
            if (amountOfTries >= 50 * amountToAdd) {
                System.out.println("You are trying to spawn too many simStudents in a too small area");
                break;
            }
            Area spawnArea = getRandomArea(ApplicationSettings.spawnAreas);
            double xPos = (spawnArea.x + Math.max(0, Math.random() * spawnArea.areaWidth-32));
            double yPos = (spawnArea.y + Math.max(0, Math.random() * spawnArea.areaHeight-32));
            Point2D spawnPos = new Point2D.Double(xPos, yPos);
            if (map.getCollisionLayer()[(int)(spawnPos.getX() / 32)][(int)(spawnPos.getY() / 32)].walkable)
                if (canAdd(spawnPos)) {
                    simStudents.add(new Sim(spawnPos, g2d, simStudentSkins.get((int) (Math.random() * simStudentSkins.size() - 1)), canvas, map.areas, nameList.getName(), group, map));
                    setRestArea(simStudents.get(simStudents.size()-1));
                    map.sims.add(simStudents.get(simStudents.size()-1));
                }
        }
    }

    public void createTeacher(Group group, String name) {
        int amountToAdd = ApplicationSettings.studentsPerGroup;
        int amountOfTries = 0;
        int oldSize = simTeachers.size();
        while (simTeachers.size()-oldSize < amountToAdd) {
            amountOfTries++;
            if (amountOfTries >= 50 * amountToAdd) {
                System.out.println("You are trying to spawn too many simStudents in a too small area");
                break;
            }
            Area spawnArea = getRandomArea(ApplicationSettings.spawnAreas);
            double xPos = (spawnArea.x + Math.max(0, Math.random() * spawnArea.areaWidth-32));
            double yPos = (spawnArea.y + Math.max(0, Math.random() * spawnArea.areaHeight-32));
            Point2D spawnPos = new Point2D.Double(xPos, yPos);
            if (map.getCollisionLayer()[(int)(spawnPos.getX() / 32)][(int)(spawnPos.getY() / 32)].walkable)
                if (canAdd(spawnPos)) {
                    simTeachers.add(new Sim(spawnPos, g2d, simTeacherSkins.get((int) (Math.random() * simTeacherSkins.size() - 1)), canvas, map.areas, name, group, map));
                    setRestAreaTeacher(simTeachers.get(simTeachers.size()-1));
                }
        }
        map.sims.add(simTeachers.get(simTeachers.size()-1));
    }

    public void gotoParkingLot() {
        for (Sim sim : map.sims) {
            sim.setTargetArea(getArea("ParkingLot"));
        }
    }

    public void freeSchedule() {
        for (Sim sim : simStudents) {
            setRestArea(sim);
        }
        for (Sim sim : simTeachers) {
            setRestAreaTeacher(sim);
        }
    }

    public void setStudentTargetGroup(Group group, Area targetArea) {
        for (Sim sim : simStudents) {
            if (sim.getGroup()==group) {
                sim.setTargetArea(targetArea);
            }
        }
    }

    public void setTeacherTarget(String name, Area targetArea) {
        for (Sim sim : simTeachers) {
            if (sim.name.equals(name))
                sim.setTargetArea(targetArea);
        }
    }

    public void setRestArea(Sim sim) {
        sim.setTargetArea(getRandomArea(ApplicationSettings.restAreas));
    }

    public void setRestAreaTeacher(Sim sim) {
        sim.setTargetArea(getRandomArea(ApplicationSettings.teacherAreas));
    }

    private Area getRandomArea(String[] areas) {
        return getArea(areas[(int)(Math.round((areas.length-1)*Math.random()))]);
    }

    private Area getArea(String areaName) {
        for (Area area : map.areas) {
            if (area.areaName.equals(areaName))
                return area;
        }
        return null;
    }

    /**
     * Can add decides whether a character can be spawned without being spawned inside another character.
     *
     * @param spawnPos Defines the spawn position a new Sim could receive.
     * @return Returns true or false depending if the spot is free.
     */

    private boolean canAdd(Point2D spawnPos) {
        boolean canAdd = true;
        for (Sim sim : map.sims) {
            if (!canAddHelper(spawnPos, sim.getCurrentPos())) {
                canAdd = false;
                break;
            }
        }
        return canAdd;
    }

    /**
     * Defines whether the distance is smaller or bigger than 64. Helper function for the canAdd() emthod.
     *
     * @param spawnPos    SpawnPos defines the potential new spawn position.
     * @param otherSimPos otherSImPos defines the position of another sim.
     * @return Is the distance between these points smaller than 64?
     */

    private boolean canAddHelper(Point2D spawnPos, Point2D otherSimPos) {
        return (!(spawnPos.distance(otherSimPos) < 64));
    }

    /**
     * stopTimer can interrupt the animationTimer. This action should be performed when exiting the simulator.
     */

    public void stopTimer() {
        timerMultiplier=0.;
        animationTimer.stop();
    }

    public void startTimer() {
        timerMultiplier=1.;
        animationTimer.start();
    }

    public Map getMap() {
        return map;
    }

    /**
     * The update function performs every action on the right time.
     *
     * @param deltatime deltatime is the time between the last run and current thing.
     */

    private void update(double deltatime) {
       deltatime*=timerMultiplier;
       this.timerHandler.update(deltatime);
        double startDelay = .075;
        if (map.getPathFinder().loaded) {
            delay += deltatime;
            counter = (int) (Math.round(delay * 10.) / 10.);
            if (delay >= startDelay) {
                    delay = 0;
                    map.restoreCanvas();
                    map.drawCollision();
                    map.drawSims(timerMultiplier);
                    map.drawWalls();
                    if (map.simToFollow != null)
                        map.drawStringPathFinder(map.simToFollow.getTargetArea());
                    map.followPerson();
                    map.activatePathFindingOnSims(timerMultiplier);
                    map.drawFireDrill();
                    for (Sim sim : simStudents) {
                        map.sitOnChair(sim);
                    }
                    for (Sim sim : simTeachers)
                        map.gotoTeachSpot(sim);
            }
        }
    }

    public TimerHandler getTimerHandler() {
        return this.timerHandler;
    }

    /**
     * The timer multiplier returns the current multiplication used.
     * @return Return timer multiplier.
     */

    public double getTimerMultiplier() {
        return this.timerMultiplier;
    }

    /**
     * The increase speed method increases the speed by doing a multiplication by 2.
     */

    public void increaseSpeed() {
        if (timerMultiplier*2<=ApplicationSettings.maxSimSpeed)
        timerMultiplier*=2.;
    }

    /**
     * The decrease speed method decreases the speed by doing a division by 2.
     */

    public void decreaseSpeed() {
        if (timerMultiplier/2>=(1./ApplicationSettings.maxSimSpeed))
        timerMultiplier/=2.;
    }

    /**
     * The maxSpeed method sets the timer multiplication to the maximum defined in the ApplicationSettings.
     */

    public void maxSpeed() {
        timerMultiplier=ApplicationSettings.maxSimSpeed;
    }

    /**
     * The minSpeed method sets the timer multiplication to the minimum defined in the ApplicationSettings.
     */

    public void minSpeed() {
        timerMultiplier=(1./ApplicationSettings.maxSimSpeed);
    }

    /**
     * When the mouse is moved every Sim is moved to the mouse position, if enabled.
     */

    private void updatePositionSims() {
        canvas.setOnMouseMoved(e -> {
            Sim sim = map.simToFollow;
            if (sim!=null)
                if (sim.getSpeed() != 0 && map.hijackedSim && map.simToFollow!=null)
                    sim.setTargetPos(new Point2D.Double(e.getX(), e.getY()));
        });
        canvas.setOnMouseClicked(event -> {
            if (event.isAltDown()) System.out.println(event.getX()/32+ " "+event.getY()/32);
        });
    }
}
