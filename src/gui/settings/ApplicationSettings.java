package gui.settings;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ApplicationSettings {
    public static final Color themeColor = Color.CORNFLOWERBLUE;
    public static final int minWidth = 200;
    public static final int maxWidth = 200;
    public static final int minHeight = 200;
    public static final int maxHeight = 200;
    public static final int margin = 5;
    public static final int standardTextSize = 10;
    public static final int headerTextSize = 20;
    public static final Font headerFont = new Font(javafx.scene.text.Font.getFamilies().get(7),30);

    public static ArrayList getTestNames() {
        ArrayList<String> testNames = new ArrayList<String>();
        for (int amountOfTestSubjects = 0; amountOfTestSubjects < 20; amountOfTestSubjects++) {
            testNames.add("testSubject" + Integer.toString(amountOfTestSubjects));
        }
        return testNames;
    }
}
