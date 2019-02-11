package gui.components.window;

import javafx.stage.Stage;

public abstract class Sizeable {
    public static final int ignore = -1;

    public void setProportions(int minWidth, int maxWidth, int minHeight, int maxHeight, int prefWidth, int prefHeight, Stage stage) {
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
