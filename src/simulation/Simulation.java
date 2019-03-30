package simulation;

import data.persons.Student;
import data.schedulerelated.Schedule;
import data.schoolrelated.School;
import gui.settings.ApplicationSettings;
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
    private boolean paused;

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
        this.time = new SimTime(ApplicationSettings.SIMULATIONSTARTINGHOUR);
        this.paused = false;
        this.setupTimer(graphics);

    }

    public void update(double deltaTime) {
        if (this.map.getPathFinder().loaded && !this.paused) {
            this.time.update(deltaTime);
            this.updateLessons();

            for (Sim sim : this.sims) {
                for (int i = 0; i < this.time.getSpeed(); i++) {
                    if (!sim.equals(this.map.simToFollow) || !this.map.hijackedSim) {
                        sim.pathFind(this.map.getPathFinder().getAllNodes());
                    }
                    sim.update(this.sims, this.map.getCollisionLayer());
                }
                this.map.sitOnChair(sim);
            }
        }

    }

    public void draw(FXGraphics2D graphics) {
        this.map.restoreCanvas();
        this.map.drawCollision();

        for (Sim sim : this.sims) {
            sim.draw();
        }

        this.map.drawWalls();

        if (this.map.simToFollow != null) {
            this.map.drawStringPathFinder(this.map.simToFollow.getTargetArea());
        }

        this.map.followPerson();
        this.map.drawFireDrill();
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
     * Pauses the simulation if it is not paused, and unpauses it if it is paused.
     */
    public void pausePlay() {
        this.paused = !this.paused;
    }

    /**
     * Runs the changeSpeed method on SimTime and updates the speed of all sims.
     *
     * @param speedChange A positive or negative double that will be added to the current speed.
     */
    public void changeSpeed(double speedChange) {
        this.time.changeSpeed(speedChange);
    }

    public void minSpeed() {
        this.time.minSpeed();
    }

    public void maxSpeed() {
        this.time.maxSpeed();
    }

    /**
     * Resets the simulation so it can run from the beginning.
     */
    public void reset() {
        //TODO: make and test method
        //It is preferable to create a new Simulation
        this.time.reset();
    }

    public SimTime getTime() {
        return time;
    }

    public SchoolMap getMap() {
        return map;
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

    public void fireDrill() {
        this.map.fireDrill();
        this.time.toggleUpdates();
    }

}
