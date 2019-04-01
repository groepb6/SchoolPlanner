package simulation.sims;

import data.persons.Person;
import data.persons.Teacher;
import data.schedulerelated.Schedule;
import data.schoolrelated.Group;
import data.schoolrelated.School;
import gui.settings.ApplicationSettings;
import simulation.SimUpdate;

import java.util.ArrayList;

public class SimHandler {
    Sim[][] sims;
    ArrayList<Teacher> teachers;
    private SimUpdate simUpdate;
    private School school;

    public SimHandler(School school, SimUpdate simUpdate) {
        this.simUpdate = simUpdate;
        this.school = school;
        createStudents();
        createTeachers();
    }


    public void createStudents() {
        for (Group group : school.getGroups()) {
            simUpdate.createStudents(group);
        }
    }

    public void createTeachers() {
        Group group = new Group("Teachers");
        for (Person teacher : school.getTeachers()) {
            simUpdate.createTeacher(group, teacher.getName());
        }
    }

    public void updateSchedules() {

    }
}
