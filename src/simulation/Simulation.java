package simulation;

import data.persons.Person;
import data.schedulerelated.Schedule;
import data.schoolrelated.Group;
import data.schoolrelated.School;
import javafx.animation.AnimationTimer;
import org.jfree.fx.FXGraphics2D;
import simulation.sims.Sim;
import simulation.sims.SimSkin;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

public class Simulation {
    private School school;
    private Map<Group, List<Schedule>> groupSchedules;
    private SimTime time;
    private Map map; //TODO: get the right map

    public Simulation(School school) {
        this.school = school;
        this.groupSchedules = school.findGroupSchedules();
        this.time = new SimTime(8, 1.0);
        this.setupTimer();
    }

    /**
     * Attaches a Sim to each Person in a group.
     */
    private void createSims() {
        SimSkin[] simSkins = new SimSkin[9];
        for (int i = 0; i < 9; i++) {
            simSkins[i] = new SimSkin(SimSkin.Role.student, i);
        }

        for (Group group : this.school.getGroups()) {
            for (Person person : group.getStudents()) {
                person.setSim(new Sim(new Point2D.Double(0, 0), simSkins[(int) (Math.random() * simSkins.length-1)])); //Todo: location
            }
        }

    }

    public void update(double deltaTime) {
        this.time.update(deltaTime);


    }

    public void draw(FXGraphics2D graphics) {

    }

    /**
     * Checks if any groups have a Schedule with a time that corresponds to the current simulation time.
     */
    public void updateGroups() {
        if (this.time.isUpdated()) { //Makes it only update every hour

            for (Group group : this.school.getGroups()) {
                for (Schedule schedule : this.groupSchedules.get(group)) {
                    if (schedule.getTime() == this.time.getTimeSlot()) {
                        //TODO: Send all students in that group to the location
                    }
                }
            }
            this.time.setUpdated(false);
        }
    }


    //private methods
    private void setupTimer() {
        AnimationTimer animationTimer = new AnimationTimer() {
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
    }

}
