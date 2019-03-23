package simulation;

import data.persons.Person;
import data.persons.Student;
import data.persons.Teacher;
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
    private Map<Teacher, List<Schedule>> teacherSchedules;
    private SimTime time;
    private SchoolMap map;
    private Sim[] sims;

    public Simulation(School school, SchoolMap map, FXGraphics2D graphics) {
        this.school = school;
        this.groupSchedules = school.findGroupSchedules();
        this.teacherSchedules = school.findTeacherSchedules();
        this.time = new SimTime(8, 1.0);
        this.setupTimer(graphics);
        this.map = map;

    }

    /**
     * Attaches a Sim to each Person in the school.
     */
    private void createSims() {
        SimSkin[] simSkins = new SimSkin[9];
        for (int i = 0; i < 9; i++) {
            simSkins[i] = new SimSkin(SimSkin.Role.student, i);
        }

        int index = 0;
        this.sims = new Sim[this.school.amountOfPeople()];
        for (Group group : this.school.getGroups()) {
            for (Student student : group.getStudents()) {
                Sim sim = new Sim(new Point2D.Double(0, 0), simSkins[(int) (Math.random() * simSkins.length-1)]); //Todo: location
                student.setSim(sim);
                this.sims[index] = sim;
                index++;
            }
        }

        for (Person teacher : this.school.getTeachers()) {
            Sim sim = new Sim(new Point2D.Double(0, 0), simSkins[(int) (Math.random() * simSkins.length-1)]); //Todo: location
            teacher.setSim(sim);
            this.sims[index] = sim;
            index++;
        }

    }

    public void update(double deltaTime) {
        this.time.update(deltaTime);

        for (Sim sim : this.sims) {
            sim.update(this.sims);
        }
        //TODO: add update methods

    }

    public void draw(FXGraphics2D graphics) {
        this.map.restoreCanvas(graphics);
        this.map.drawWalls();

        for (Sim sim : this.sims) {
            sim.draw(graphics);
        }
        //TODO: add map drawing methods
        //TODO: add sim drawing methods
    }

    /**
     * Checks if any groups have a Schedule with a time that corresponds to the current simulation time.
     */
    public void updateGroups() { //TODO: update teachers
        if (this.time.isUpdated()) { //Makes it only update every hour

            for (Group group : this.school.getGroups()) {
                for (Schedule schedule : this.groupSchedules.get(group)) {
                    if (schedule.getTime() == this.time.getTimeSlot()) {
                        //TODO: Send all students in that group to the location
                        for (Person person : group.getStudents()) {
                            person.getSim().updateDestination(); //TODO: updateDestination method
                        }

                    }
                }
            }
            this.time.setUpdated(false);
        }
    }


    //private methods
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
