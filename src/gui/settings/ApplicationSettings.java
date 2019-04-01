package gui.settings;

import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Dustin Hendriks
 * @since 04-02-2019
 * This class enables easy reading of some basic application settings. If there are any specific settings such as text size or window sizes those settings can be added here.
 * There currently is some obsolete data here, but this can be cleaned up in the final version.
 */

public class ApplicationSettings {
    public static final Color themeColor = Color.CORNFLOWERBLUE;
    public static final int simulatorImageWidthAndHeight=35;
    public static final int maxSimSpeed=8;
    public static final int minWidth = 200;
    public static final int studentsPerGroup = 15;
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
    public static final String[] restAreas = { "DiningRoom", "DiningRoom", "DiningRoom", "DiningRoom", "Square" , "Square", "Hallway", "Library"};
    public static final String[] spawnAreas = { "Exit1", "Exit2", "Exit3", "ParkingLot"};
    public static final String[] teacherAreas = { "Teachersroom" };
    public static final Point2D[] toilets = { new Point2D.Double(35,17), new Point2D.Double(39,17), new Point2D.Double(43,17) };
    public static final Point2D[] teachSpots = { new Point2D.Double(20,18), new Point2D.Double(43,40), new Point2D.Double(60,40), new Point2D.Double(87, 41), new Point2D.Double(91,53), new Point2D.Double(87,17)};
    public static final String[] teachRooms = { "LA001", "LA002", "LA003", "LA004", "LA005", "LA006"};
    /**
     * Receive the horizontal / vertical movement first boolean for chairs.
     * @param room Defines the room
     * @return Defines true or false depending on horizontal / vertical first. No other option than hardcode (except from defining in the schoolmap JSON file, but takes too long to implement. Not obsolete for future purposes).
     */

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
            case "Toilets" : horizontalFirst=true; break;
        }
        return horizontalFirst;
    }
}
