package gui.components.menu;

import gui.assistclasses.Image;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author Dustin Hendriks
 * @since 03-02-2019
 * <p>
 * This class creates a nice HBox filled with some pushpin icons for a fancy bar. No functionality's whatsoever, so feel free to ignore this class.
 */

public class UpperBar {
    private BorderPane upperBar = new BorderPane();
    private static final int size = 40;

    public UpperBar() {
        Image pushPin = new Image("pushpin2", "menuimages", ".png", "pushpin", "it is a pushpin image that is used for illustration only");
        Image pushPin2 = new Image("pushpin2", "menuimages", ".png", "pushpin", "it is a pushpin image that is used for illustration only");
        pushPin2.getImageView().setFitHeight(size);
        pushPin2.getImageView().setFitWidth(size);
        pushPin.getImageView().setFitHeight(size);
        pushPin.getImageView().setFitWidth(size);
        upperBar.setLeft(pushPin2.getImageView());
        upperBar.setRight(pushPin.getImageView());
        upperBar.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

    }


    /**
     * @return Returns the HBox object (can add this to a node later).
     */

    public BorderPane getUpperBar() {
        return upperBar;
    }
}
