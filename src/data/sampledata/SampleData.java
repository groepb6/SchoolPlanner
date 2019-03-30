package data.sampledata;

import data.persons.Teacher;
import data.rooms.Classroom;
import data.schoolrelated.Group;
import data.schoolrelated.School;
import data.schoolrelated.Subject;

/**
 * @author Dustin Hendriks
 */

public class SampleData {
    private School school = new School("School");

    public SampleData() {
        addGroups();
        addTeachers();
        addSubjects();
        addLocations();
    }

    private void addGroups() {
        school.getGroups().add(new Group("Klas-A"));
        school.getGroups().add(new Group("Klas-B"));
        school.getGroups().add(new Group("Klas-C"));
        school.getGroups().add(new Group("Klas-D"));
    }

    private void addSubjects() {
        school.getSubjects().add(new Subject("OGP"));
        school.getSubjects().add(new Subject("OOM"));
        school.getSubjects().add(new Subject("Math"));
        school.getSubjects().add(new Subject("Proftaak"));
        school.getSubjects().add(new Subject("HI"));
        school.getSubjects().add(new Subject("P&OC"));
    }

    private void addTeachers() {
        school.getTeachers().add(new Teacher("Johan Talboom"));
        school.getTeachers().add(new Teacher("Maurice Snoeren"));
        school.getTeachers().add(new Teacher("Etiënne Goossens"));
        school.getTeachers().add(new Teacher("Paul de Mast"));
        school.getTeachers().add(new Teacher("Peter Kailuhu"));
        school.getTeachers().add(new Teacher("Pieter Kop Jansen"));
        school.getTeachers().add(new Teacher("Genevieve Smid"));
        school.getTeachers().add(new Teacher("Hans van der Linden"));
        school.getTeachers().add(new Teacher("Jessica van der Heijden"));
        school.getTeachers().add(new Teacher("Jan Oostindie"));
        school.getTeachers().add(new Teacher("Bart Oerlemans"));
        school.getTeachers().add(new Teacher("Paul Lindelauf"));
    }

    private void addLocations() {
        school.getRooms().add(new Classroom("LA001"));
        school.getRooms().add(new Classroom("LA002"));
        school.getRooms().add(new Classroom("LA003"));
        school.getRooms().add(new Classroom("LA004"));
        school.getRooms().add(new Classroom("LA005"));
        school.getRooms().add(new Classroom("LA006"));
    }

    public School getSchool() {
        return school;
    }
}
