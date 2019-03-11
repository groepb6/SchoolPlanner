package data.sampledata;

import data.persons.Person;
import data.persons.Teacher;
import data.rooms.Classroom;
import data.rooms.Room;
import data.schoolrelated.Group;
import data.schoolrelated.School;
import data.schoolrelated.Subject;

public class SampleData {
    private School school = new School("School");

    public SampleData() {
        addGroups('A');
        addTeachers();
        addSubjects();
        addLocations();
    }

    private void addGroups(Character classChar) {
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
        school.getTeachers().add(new Teacher("Johan Talboom", Person.Gender.MALE));
        school.getTeachers().add(new Teacher("Maurice Snoeren", Person.Gender.MALE));
        school.getTeachers().add(new Teacher("Etiënne Goossens", Person.Gender.MALE));
        school.getTeachers().add(new Teacher("Paul de Mast", Person.Gender.MALE));
        school.getTeachers().add(new Teacher("Peter Kailuhu", Person.Gender.MALE));
        school.getTeachers().add(new Teacher("Pieter Kop Jansen", Person.Gender.MALE));
        school.getTeachers().add(new Teacher("Genevieve Smid", Person.Gender.FEMALE));
        school.getTeachers().add(new Teacher("Hans van der Linden", Person.Gender.MALE));
        school.getTeachers().add(new Teacher("Jessica van der Heijden", Person.Gender.FEMALE));
        school.getTeachers().add(new Teacher("Jan Oostindie", Person.Gender.MALE));
        school.getTeachers().add(new Teacher("Bart Oerlemans", Person.Gender.MALE));
        school.getTeachers().add(new Teacher("Paul Lindelauf", Person.Gender.MALE));
    }

    private void addLocations() {
        school.getRooms().add(new Classroom("LA001"));
        school.getRooms().add(new Classroom("LA002"));
        school.getRooms().add(new Classroom("LA003"));
        school.getRooms().add(new Classroom("LA004"));
        school.getRooms().add(new Classroom("LA005"));
    }

    public School getSchool() {
        return school;
    }
}
