package gui.components.menu;

import gui.assistclasses.Image;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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

    public BorderPane getUpperBar() {
        return upperBar;
    }
}
