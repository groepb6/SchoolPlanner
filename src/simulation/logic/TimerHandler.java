package simulation.logic;

import data.readwrite.DataReader;
import data.schedulerelated.Hour;
import data.schedulerelated.Schedule;
import data.schoolrelated.Group;
import data.schoolrelated.School;
import simulation.Map;
import simulation.SimUpdate;
import simulation.controls.SimulationBar;
import simulation.data.Area;
import simulation.sims.Sim;
import simulation.sims.SimHandler;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;

public class TimerHandler {
    private School school = DataReader.readSchool();
    private SimUpdate simUpdate;
    private Map map;
    private LocalTime localTime;
    private SimulationBar simulationBar;
    private boolean bound = false;
    public int lastBlock = 0;
    private int lastMinute = 0;
    private ArrayList<Sim> simsOnToilet = new ArrayList<>();

    public TimerHandler(SimUpdate simUpdate, Map map) {
        this.simUpdate = simUpdate;
        this.map = map;
        initTime();
        readSchedules();
    }

    public String getDisplayTime() {
        if (localTime.toString().length() >= 8)
            return localTime.toString().substring(0, 8);
        return localTime.toString();
    }

    public void update(double deltaTime) {
         deltaTime = (deltaTime * (simUpdate.getTimerMultiplier()));
        if (map.getPathFinder().loaded)
            if (deltaTime < 1)
                this.localTime = this.localTime.plusNanos((int) (deltaTime * 1E9));
            else this.localTime = this.localTime.plusSeconds((int) deltaTime);
        if (bound)
            simulationBar.updateTimeDisplay(getDisplayTime());
        if (localTime.getHour() != lastBlock && !map.fireDrill) {
            lastBlock = localTime.getHour();
                updateSchedule();
        }
        else if (localTime.getMinute()!=lastMinute && !map.fireDrill) {
            if (Math.round(Math.random()*10)==5) {
                Sim sim = simUpdate.simStudents.get((int)(Math.random()*(simUpdate.simStudents.size()-1)));
                simsOnToilet.add(sim);
                map.gotoToilet(sim);
                if (simsOnToilet.size()>3) {
                    simsOnToilet.get(0).goBackToPreviousLocation();
                    simsOnToilet.remove(0);
                }
            }
            if (Math.round(Math.random()*10)==2) {
                if (simsOnToilet.size()>0) {
                    simsOnToilet.get(0).goBackToPreviousLocation();
                    simsOnToilet.remove(0);
                }
            }
            lastMinute = localTime.getMinute();
        }
    }

    private void initTime() {
        this.localTime = LocalTime.of(8, 0, 0, 0);
    }

    public void updateTime(String selectionBox) {
        int hours = Integer.parseInt(selectionBox.substring(0, selectionBox.indexOf(":")));
        int minutes = Integer.parseInt(selectionBox.substring(selectionBox.indexOf(":") + 1, selectionBox.indexOf(":") + 2));
        this.localTime = LocalTime.of(hours, minutes);
    }

    private void readSchedules() {
        SimHandler simHandler = new SimHandler(school, simUpdate);
    }

    private void updateSchedule() {
        if (lastBlock >= 18)
            simUpdate.gotoParkingLot();
        else {
            simUpdate.freeSchedule();
            for (Schedule schedule : school.getSchedules()) {
                String hourString = schedule.getTime().getTime().substring(0, schedule.getTime().getTime().indexOf(":"));
                int hour = Integer.parseInt(hourString);
                if (hour == lastBlock) {
                    implement(schedule);
                }
            }
        }
    }

    public void implement(Schedule schedule) {
        Group group = schedule.getGroup();
        String target = schedule.getRoom().getName();
        Area targetArea = roomToArea(target);
        simUpdate.setStudentTargetGroup(group, targetArea);
        simUpdate.setTeacherTarget(schedule.getTeacher().getName(), targetArea);
    }

    private Area roomToArea(String room) {
        for (Area targetArea : map.areas) {
            if (targetArea.areaName.equals(room)) {
               return targetArea;
            }
        }
        return null;
    }

    public void bindSimulationBar(SimulationBar simulationBar) {
        this.simulationBar = simulationBar;
        bound = true;
    }

    private int getTimeInt(String time) {
        if (time.indexOf(":") > 0)
            return Integer.parseInt(time.substring(0, time.indexOf(":")));
        else return 0;
    }


}
