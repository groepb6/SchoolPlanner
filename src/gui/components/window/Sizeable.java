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
