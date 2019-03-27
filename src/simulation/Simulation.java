package simulation;

import data.persons.Person;
import data.persons.Student;
import data.persons.Teacher;
import data.schedulerelated.Schedule;
import data.schoolrelated.Group;
import data.schoolrelated.School;
import data.schoolrelated.Subject;
import javafx.animation.AnimationTimer;
import org.jfree.fx.FXGraphics2D;
import simulation.sims.Sim;
import simulation.sims.SimSkin;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Simulation {
    private School school;
    private Map<Group, Set<Schedule>> groupSchedules;
    private Map<Teacher, Set<Schedule>> teacherSchedules;
    private SimTime time;
    private SchoolMap map;
    private Sim[] sims;

    public Simulation(School school, SchoolMap map, FXGraphics2D graphics) {
        this.school = school;
        this.groupSchedules = school.findGroupSchedules();
        this.teacherSchedules = school.findTeacherSchedules();
        this.time = new SimTime(8);
        this.setupTimer(graphics);
        this.map = map;
        this.createPeople();
        this.createSims();

    }

    /**
     * Adds Student objects to Groups and a Teacher object for every Subject, but only if the School doesn't have Student or Teacher objects.
     */
    private void createPeople() {
        for (Group group : this.school.getGroups()) {
            if (group.getStudents().size() < 1) {
                for (int i = 0; i < 10; i++) {
                    group.addStudent(new Student(""));
                }
            }
        }

        if (this.school.getTeachers().size() < 1) {
            for (Subject subject : this.school.getSubjects()) {
                Teacher teacher = new Teacher("");
                teacher.setSubject(subject);
                this.school.addTeacher(teacher);
            }
        }

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
        this.updateLessons();

        for (Sim sim : this.sims) {
            sim.update(this.sims);
        }
        //TODO: add update methods

    }

    public void draw(FXGraphics2D graphics) {
        this.map.restoreCanvas(graphics);

        for (Sim sim : this.sims) {
            sim.draw(graphics);
        }

        this.map.drawWalls();

        //graphics.setColor(Color.BLUE);
        //graphics.drawString(this.time.toString(), 50, 50);

        //TODO: add map drawing methods
        //TODO: add sim drawing methods
    }

//    /**
//     * Checks if any groups have a Schedule with a time that corresponds to the current simulation time.
//     */
//    public void updateGroups() { //TODO: update teachers
//        if (this.time.isUpdated()) { //Makes it only update every hour
//
//            for (Group group : this.school.getGroups()) {
//                for (Schedule schedule : this.groupSchedules.get(group)) {
//                    if (schedule.getTime() == this.time.getTimeSlot()) {
//                        //TODO: Send all students in that group to the location
//                        for (Person person : group.getStudents()) {
//                            person.getSim().updateDestination(); //TODO: updateDestination method
//                        }
//
//                    }
//                }
//            }
//            this.time.updateRecieved();
//        }
//    }

    /**
     * Checks if there are any schedules starting during this hour and executes the startSchedule method with those schedules.
     * Only runs when SimTime has been updated.
     */
    public void updateLessons() {
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
     * @param schedule The schedule that is starting.
     */
    public void startSchedule(Schedule schedule) {
        System.out.println("Class: " + schedule.getGroup().getName() +
                " is going to classroom: " + schedule.getRoom().getName() +
                " for " + schedule.getSubject().getName() +
                " with " + schedule.getTeacher().getName());

        for (Student student : schedule.getGroup().getStudents()) {
            //student.getSim().
        }
        //schedule.getTeacher().getSim().
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
