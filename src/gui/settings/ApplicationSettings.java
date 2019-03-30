package gui.settings;

import javafx.scene.paint.Color;

import java.awt.*;

/**
 * @author Dustin Hendriks
 * @since 04-02-2019
 * This class enables easy reading of some basic application settings. If there are any specific settings such as text size or window sizes those settings can be added here.
 * There currently is some obsolete data here, but this can be cleaned up in the final version.
 */

public class ApplicationSettings {
    public static final Color themeColor = Color.CORNFLOWERBLUE;
    public static final int simulatorImageWidthAndHeight=35;
    public static final int maxSimSpeed=32;
    public static final int minWidth = 200;
    public static final int maxWidth = 200;
    public static final int minHeight = 200;
    public static final int maxHeight = 200;
    public static final int margin = 5;
    public static final int standardTextSize = 10;
    public static final int headerTextSize = 20;
    public static final java.awt.Font font = new java.awt.Font("Courier", Font.PLAIN, 15);
    public static final String saveSlotPath = "saves/school/defaults.txt";
    public static final String schoolPath = "saves/school/school.txt";
    public static final String saveNodePath = "saves/school/nodes.txt";
    public static final String namePath = "/additional/names.txt";
    public static final double SIMDEFAULTSPEED = 3;
    public static final double TIMERDEFAULTSPEED = 1.0;
    public static final double TIMERMINSPEED = 0.1;
    public static final double TIMERMAXSPEED = 3;
    public static final int SIMULATIONSTARTINGHOUR = 8;

    public static boolean getHorizontalFirst(String room) {
        boolean horizontalFirst = false;
        switch (room) {
            case "LA001": horizontalFirst=false; break;
            case "LA002": horizontalFirst=false; break;
            case "LA003": horizontalFirst=false; break;
            case "LA004": horizontalFirst=false; break;
            case "LA005": horizontalFirst=true; break;
            case "LA006": horizontalFirst=false; break;
            case "Library": horizontalFirst=false; break;
            case "Teachersroom": horizontalFirst=false; break;
            case "DiningRoom": horizontalFirst=true; break;
            case "Square": horizontalFirst=false; break;
        }
        return horizontalFirst;
    }
}
