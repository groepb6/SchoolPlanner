package data.schedulerelated;

import data.schedulerelated.Schedule;
import gui.assistclasses.Plan;

import java.util.ArrayList;

public class TempSchedule {
    private ArrayList<Schedule> schedules = new ArrayList<>();

    public void saveAllSchedules(Schedule... schedules) {
        for (Schedule schedule : schedules) {
            this.schedules.add(schedule);
        }
    }

    public ArrayList<Schedule> getData() {
        return schedules;
    }
}
