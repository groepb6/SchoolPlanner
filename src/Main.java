import data.DataReader;
import data.DataWriter;
import gui.Schedule;
import gui.Plan;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Plan plan = new Plan("asdf", "4g", "Breda", "Hans", "OOM");
        Plan plan2 = new Plan("324", "4g", "Breda", "Etienne", "OGP");
        Plan plan3 = new Plan("asdf", "4g", "Breda", "Johan", "Proftaak");
        ArrayList<Schedule> schedules = new ArrayList<>();
        schedules.add(new Schedule(plan));
        schedules.add(new Schedule(plan2));
        schedules.add(new Schedule(plan3));

        try {
            DataWriter.writeObject(schedules);
            DataReader.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}