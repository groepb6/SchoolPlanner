
import data.Schedule;
import data.ScheduleLoader;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test");
        Schedule scheduleSave = new Schedule();
        scheduleSave.saveToFile("testSave");
        Schedule scheduleLoad = ScheduleLoader.loadFile("testSave");
    }
}