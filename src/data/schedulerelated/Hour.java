package data.schedulerelated;

import java.io.Serializable;
import java.util.ArrayList;

public enum Hour implements Serializable {
    FIRST("8:00 - 9:00"),
    SECOND("9:00 - 10:00"),
    THIRD("10:00 - 11:00"),
    FOURTH("11:00 - 12:00"),
    FIFTH("12:00 - 13:00"),
    SIXTH("13:00 - 14:00"),
    SEVENTH("14:00 - 15:00"),
    EIGHTH("15:00 - 16:00"),
    NINTH("16:00 - 17:00"),
    TENTH("17:00 - 18:00");

    public String time;

    Hour(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }
}

