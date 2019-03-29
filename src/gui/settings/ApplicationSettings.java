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
}
