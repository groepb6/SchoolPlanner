import data.*;
import data.person.*;

public class Main {
    public static void main(String[] args) {

        Group groupA = new Group("Group A");
        Group groupB = new Group("Group B");

        groupA.addStudents(new Student("Jan", 1));
        groupA.addStudents(new Student("Piet", 2));

        groupB.addStudents(new Student("Hans", 3));
        groupB.addStudents(new Student("Karel", 4));

        Teacher teacher = new Teacher("John", 25, "Lesson");

        Schedule schedule = new Schedule("School A");
        schedule.addGroup(groupA);

        System.out.println("\nSaving tests");
        schedule.saveToFile();
        groupB.saveToFile();
        teacher.saveToFile();

        
        Schedule loadedSchedule = Loader.loadSchedule();
        Group loadedGroup = Loader.loadGroup();
        Teacher loadedTeacher = Loader.loadTeacher();

        System.out.println("\nLoading tests");
        System.out.println(loadedSchedule);
        System.out.println(loadedGroup.getStudents());
        System.out.println(loadedTeacher.getName());

    }
}