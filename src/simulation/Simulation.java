package simulation;

import data.persons.Student;
import data.schedulerelated.Schedule;
import data.schoolrelated.School;
import javafx.animation.AnimationTimer;
import org.jfree.fx.FXGraphics2D;
import simulation.data.Area;
import simulation.sims.Sim;

/**
 * Runs the simulation
 * Updates and draws the simulation.
 * Sends students to their lessons.
 * todo: documentation
 */
public class Simulation {
    private School school;
    private SimTime time;
    private SchoolMap map;
    private Sim[] sims;

    public static final int STARTINGHOUR = 8;

    /**
     * Creates a Simulation object and automatically starts the simulation.
     *
     * @param school   Should be obtained from CreateSims.getSchool().
     * @param map      Should be obtained from CreateSims.getMap().
     * @param sims     Should be obtained from CreateSims.getSims().
     * @param graphics An FXGraphics2D object to draw everything.
     */
    public Simulation(School school, SchoolMap map, Sim[] sims, FXGraphics2D graphics) {
        this.school = school;
        this.map = map;
        this.sims = sims;
        this.time = new SimTime(this.STARTINGHOUR);
        this.setupTimer(graphics);

    }

    public void update(double deltaTime) {
        if (this.map.getPathFinder().loaded) {
            this.time.update(deltaTime);
            this.updateLessons();


            for (Sim sim : this.sims) {
                sim.update(this.sims, this.map.getCollisionLayer());
                sim.pathFind(this.map.getPathFinder().getAllNodes());
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
            this.time.updateReceived();
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
     * Checks if the speed of SimTime has been updated. If it was updated, the speed of every Sim will be changed.
     */
    private void updateSpeed() {
        if (this.time.isSpeedUpdated()) {
            for (Sim sim : this.sims) {
                sim.setSimSpeed((int) this.time.getSpeed() * Sim.DEFAULTSPEED);
            }
            this.time.speedUpdateReceived();
        }
    }

    /**
     * Resets the simulation so it can run from the beginning.
     */
    public void reset() {
        //TODO: make and test method
        //It is preferable to create a new Simulation
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


}
