package data.schedulerelated;

public enum Hour {
    FIRST("8:00 - 9:00"),
    SECOND("9:00 - 10:00"),
    THIRD("10:00 - 11:00"),
    FOURTH("11:00 - 12:00"),
    FIFTH("12:00 - 13:00"),
    SIXTH("13:00 - 14:00"),
    SEVENTH("14:00 - 15:00"),
    EIGHTH("15:00 - 16:00"),
    NINTH("16:00 - 17:00");

    private String time;

    Hour(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }
}
