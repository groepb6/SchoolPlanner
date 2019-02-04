import data.Group;
import data.School;
import data.person.*;
import data.room.*;

public class Main {
    public static void main(String[] args) {

        School school = new School("School A");

        Group groupA = new Group("Group A");
        Group groupB = new Group("Group B");

        groupA.addStudents(new Student("Jan", 1));
        groupA.addStudents(new Student("Piet", 2));

        groupB.addStudents(new Student("Hans", 3));
        groupB.addStudents(new Student("Karel", 4));

        school.addGroup(groupA);

    }
}