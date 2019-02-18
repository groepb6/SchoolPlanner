package data.schedulerelated;

import java.io.Serializable;
import java.time.LocalTime;

public class Period implements Serializable {
    private LocalTime startTime;
    private LocalTime endTime;

    public Period(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean overlaps(Period period) {
        return false;
    }
}
