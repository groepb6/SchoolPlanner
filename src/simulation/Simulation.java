package simulation;

import data.persons.Person;
import data.persons.Student;
import data.schedulerelated.Schedule;
import data.schoolrelated.School;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;
import simulation.data.Area;
import simulation.sims.Sim;
import simulation.sims.SimSkin;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Runs the simulation
 * todo: documentation
 */
public class Simulation {
    private School school;
    private SimTime time;
    private SchoolMap map;
    private Sim[] sims;

    public static final int STUDENTSPERGROUP = 1;
    public static final String SPAWNAREA = "ParkingLot";
    public static final int MAXSPAWNATTEMPTS = 10;

    public Simulation(School school, SchoolMap map, FXGraphics2D graphics, Canvas canvas) {
        this.school = school;
        this.time = new SimTime(8);
        this.map = map;
        this.school.createStudents(this.STUDENTSPERGROUP);
        this.createSims(graphics, canvas);
        this.setupTimer(graphics);

    }

    public void update(double deltaTime) {
        if (this.map.getPathFinder().loaded) {
            this.time.update(deltaTime);
            this.updateLessons();
            this.map.activatePathFindingOnSims();

            for (Sim sim : this.sims) {
                sim.update(this.sims, this.map.getCollisionLayer());
            }
        }

    }

    public void draw(FXGraphics2D graphics) {
        this.map.restoreCanvas();

        for (Sim sim : this.sims) {
            sim.draw();
        }

        this.map.drawWalls();
        this.map.drawCollision();
        this.map.drawStringPathFinder(9);
        //TODO: draw the timer

//        graphics.setColor(Color.BLUE);
//        graphics.drawString(this.time.toString(), 50, 50);
    }

    /**
     * Checks if there are any schedules starting during this hour and executes the startSchedule method with those schedules.
     * Only runs when SimTime has been updated.
     */
    private void updateLessons() {
        if (this.time.isUpdated()) {
            for (Schedule schedule : this.school.getSchedules()) {
                if (schedule.getTime() == this.time.getTimeSlot()) {
                    this.startSchedule(schedule);
                }
            }
            this.time.updateRecieved();
        }
    }

    /**
     * Gives a notification about the schedule that is starting and sends the students and teacher to the appropriate classroom.
     *
     * @param schedule The schedule that is starting.
     */
    private void startSchedule(Schedule schedule) {
        System.out.println("Class: " + schedule.getGroup().getName() +
                " is going to classroom: " + schedule.getRoom().getName() +
                " for " + schedule.getSubject().getName() +
                " with " + schedule.getTeacher().getName());
        //TODO: Graphical implementation of this println

        Area area = this.map.searchArea(schedule.getRoom().getName());
        for (Student student : schedule.getGroup().getStudents()) {
            student.getSim().setTargetArea(area);
            //System.out.println(area.areaID);
        }
        schedule.getTeacher().getSim().setTargetArea(area);
    }

    /**
     * Resets the simulation so it can run from the beginning.
     */
    public void reset() {
        //TODO: make and test method
    }

    /**
     * Sets up the AnimationTimer
     *
     * @param graphics The FXGraphics2D object that is needed to execute the draw method.
     */
    private void setupTimer(FXGraphics2D graphics) {
        AnimationTimer animationTimer = new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1.e9);
                draw(graphics);
                last = now;
            }
        };
        animationTimer.start();
    }


    /**
     * Gets all Sim objects held by people and puts them in an array.
     */
    private void refreshSims() {
        this.sims = new Sim[this.school.getPeople().size()];
        int index = 0;
        for (Person person : this.school.getPeople()) {
            this.sims[index] = person.getSim();
            index++;
        }
    }

    /**
     * Puts sims from a list into the sims array.
     * @param tempSims The List of Sim objects the simulation should have.
     */
    private void makeSimsArray(List<Sim> tempSims) {
        int index = 0;
        this.sims = new Sim[tempSims.size()];
        for (Sim sim : tempSims) {
            this.sims[index] = sim;
            index++;
        }
    }

    //sim adding methods

    /**
     * Attaches a Sim to each Person in the School.
     *
     * @param g2d    An FXGraphics2D object required in the constructor of Sim.
     * @param canvas A Canvas object required in the constructor of Sim.
     * @author Dustin Hendriks
     */
    private void createSims(FXGraphics2D g2d, Canvas canvas) {
        //Create SimSkins
        SimSkin[] simSkins = new SimSkin[9];
        for (int i = 0; i < 9; i++) {
            simSkins[i] = new SimSkin(SimSkin.Role.student, i);
        }

        //Get spawnArea
        Area spawnArea = null;
        for (Area area : this.map.areas) {
            if (area.areaName.equals(this.SPAWNAREA)) {
                spawnArea = area;
            }
        }
        if (spawnArea == null) {
            System.out.println("Spawn area could not be found!");
            System.out.println("Your resources might not support areas, or the area is not referenced correctly!");
        }

        //Spawning sims
        List<Sim> tempSims = new ArrayList<>();
        int amountOfTries = 0;
        boolean noneNeeded = false;
        while (!noneNeeded) {
            noneNeeded = true;
            for (Person person : this.school.getPeople()) {
                if (person.getSim() == null) {
                    noneNeeded = false;
                    Point2D spawnPos = new Point2D.Double(spawnArea.x + (Math.random() * spawnArea.areaWidth), spawnArea.y + (Math.random() * spawnArea.areaHeight));
                    if (map.getCollisionLayer()[(int) Math.round(spawnPos.getX() / 32)][(int) Math.round(spawnPos.getY() / 32)].walkable) { //TODO: sims walk out of bounds
                        if (canAdd(spawnPos, tempSims)) {
                            Sim sim = new Sim(spawnPos, g2d, simSkins[((int) (Math.random() * simSkins.length - 1))], canvas, map.areas);
                            sim.setTargetArea(this.map.searchArea(this.SPAWNAREA));
                            tempSims.add(sim);
                            person.setSim(sim);
                        }
                    }

                }
            }

            //Gives up after too many tries
            amountOfTries++;
            if (amountOfTries >= this.MAXSPAWNATTEMPTS) {
                System.out.println("Was unable to spawn all sims after " + this.MAXSPAWNATTEMPTS + " tries!");
                System.out.println("Created " + tempSims.size() + " sims out of " + this.school.getPeople().size());
                break;
            } //TODO: this method of spawning sims is unreliable
        }
        this.makeSimsArray(tempSims);
    }

    /**
     * Can add decides whether a character can be spawned without being spawned inside another character.
     *
     * @param spawnPos Defines the spawn position a new Sim could receive.
     * @return Returns true or false depending if the spot is free.
     * @author Dustin Hendriks
     */
    private boolean canAdd(Point2D spawnPos, List<Sim> tempSims) {
        boolean canAdd = true;
        for (Sim sim : tempSims) {
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
     * @author Dustin Hendriks
     */
    private boolean canAddHelper(Point2D spawnPos, Point2D otherSimPos) {
        return (!(spawnPos.distance(otherSimPos) < 64));
    }

}
