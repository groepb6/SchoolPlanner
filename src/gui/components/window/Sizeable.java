package gui.components.window;

import javafx.stage.Stage;

/**
 * @author Dustin Hendriks
 * @since 10-02-2019
 *
 * The class Sizeable is used to change the size of a stage fast, this way you don't have to do this over and over again in each method / class or function.
 * Notice that typing "super.setProportions(vars)" is way faster than typing: "stage.setMinWidth(); stage.setMinHeight(); stage.setMaxWidth(); stage.setMaxHeight(); stage.setWidth(); stage.setHeight();"
 */

public abstract class Sizeable {
    public static final int ignore = -1;


    /**
     * This method sets the proportions of a stage, if the proportion is set to -1, it is ignored.
     * @param minWidth Defines the minimum window size (width).
     * @param maxWidth Defines the maximum window size (width).
     * @param minHeight Defines the minimum window size (height).
     * @param maxHeight Defines the maximum window size (height).
     * @param prefWidth Defines the preferable window size (width).
     * @param prefHeight Defines the preferable window size (height).
     * @param stage Defines the stage that should be scaled according to all parameters.
     */

    protected void setProportions(int minWidth, int maxWidth, int minHeight, int maxHeight, int prefWidth, int prefHeight, Stage stage) {
        if (minWidth != -1)
            stage.setMinWidth(minWidth);
        if (maxWidth != -1)
            stage.setMaxWidth(maxWidth);
        if (minHeight != -1)
            stage.setMinHeight(minHeight);
        if (maxHeight != -1)
            stage.setMaxHeight(maxHeight);
        if (prefWidth != -1)
            stage.setWidth(prefWidth);
        if (prefHeight != -1)
            stage.setHeight(prefHeight);
    }
}
