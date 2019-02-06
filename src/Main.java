import data.*;
import data.person.Student;

public class Main {
    public static void main(String[] args) {

        Schedule schedule = new Schedule("School A");

        Group groupA = new Group("Group A");
        Group groupB = new Group("Group B");

        groupA.addStudents(new Student("Jan", 1));
        groupA.addStudents(new Student("Piet", 2));

        groupB.addStudents(new Student("Hans", 3));
        groupB.addStudents(new Student("Karel", 4));

        schedule.addGroup(groupA);

        schedule.saveToFile();
//        Schedule schedule = Loader.loadFile();

    }
}