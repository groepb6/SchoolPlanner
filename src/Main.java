
import data.Schedule;
import data.ScheduleLoader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test");
        Schedule schedule1 = new Schedule();
        schedule1.saveToFile("testSave");
        Schedule schedule = ScheduleLoader.loadFile("testSave");
    }
}
