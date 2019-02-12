package data.schedulerelated;

import data.schedulerelated.Schedule;
import gui.assistclasses.Plan;

import java.util.ArrayList;

public class TempSchedule {
    private ArrayList<Schedule> schedules = new ArrayList<>();

    public TempSchedule() {

    }

    public void saveAllSchedules(Schedule... schedules) {
        for (Schedule schedule : schedules) {
            this.schedules.add(schedule);
        }
    }

    public void saveAllSchedules(Plan... plans) {
        for (Plan plan : plans) {
            this.schedules.add(new Schedule(plan));
        }
    }

    public void saveNewSchedule(Schedule schedule) {
        schedules.add(schedule);
    }
    public void saveNewSchedule(Plan plan) {
        schedules.add(new Schedule(plan));
    }

    public ArrayList<Schedule> getData() {
        return schedules;
    }
}
