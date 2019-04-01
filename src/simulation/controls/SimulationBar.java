package simulation.controls;

import com.sun.javafx.collections.ObservableListWrapper;
import gui.components.frames.StartSim;
import gui.settings.ApplicationSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joëlle de Vries (creator of the functionality's and the class).
 * @author Dustin Hendriks (added some commentary and added a few buttons with the exception of the simulation controls).
 * The class SimulationBar pops up when opening the simulation. It contains labels to display information and buttons to run specific actions on the simulator (such as accelerating, pausing, slowing, starting firedrill, etc).
 */

public class SimulationBar {
    private HBox hBox = new HBox();
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ImageView fastBackward;
    private ImageView backward;
    private ImageView startstop;
    private ImageView forward;
    private ImageView fastForward;
    private ImageView fire;
    private ImageView control;
    private ImageView pathfind;
    private ImageView collision;
    private ComboBox timeComboBox;
    private Label timeLabel;
    private Label timerMultiplier=new Label("x1.0");
    private StartSim startSim;
    private boolean paused = false;

    /**
     * The constructor adds all images to the topbar and allocates a specific width and height for each image.
     * @param startSim Is needed to bind actions when clicking on an icon / image.
     */

    public SimulationBar(StartSim startSim) {
        String folder ="simbarimages";
        this.startSim = startSim;
        String name;
        String extension = ".png";
        addTimeComboBox();
        try {
            name = "fastbackward";
            fastBackward = getImage(folder, name, extension);
            imageViews.add(fastBackward);
            name = "backward";
            backward = getImage(folder,name,extension);
            imageViews.add(backward);
            name = "start-stop";
            startstop = getImage(folder,name,extension);
            imageViews.add(startstop);
            name = "forward";
            forward = getImage(folder,name,extension);
            imageViews.add(forward);
            name = "fastfoward";
            fastForward = getImage(folder,name,extension);
            imageViews.add(fastForward);
            name = "control";
            control = getImage(folder, name, extension);
            imageViews.add(control);
            name = "pathfind";
            pathfind = getImage(folder, name, extension);
            imageViews.add(pathfind);
            name = "collision";
            collision = getImage(folder, name, extension);
            imageViews.add(collision);
            name = "fire";
            fire = getImage(folder, name, extension);
            imageViews.add(fire);

            for (ImageView imageView : imageViews) {
                imageView.setPickOnBounds(true);
                imageView.setFitWidth(ApplicationSettings.simulatorImageWidthAndHeight);
                imageView.setFitHeight(ApplicationSettings.simulatorImageWidthAndHeight);
                hBox.getChildren().add(imageView);
            }

            fixLayoutManually();

            addTimerMultiplier();
            addTimeDisplay();
            hBox.getChildren().add(timeLabel);
            hBox.setBackground(new Background(new BackgroundFill(ApplicationSettings.themeColor, CornerRadii.EMPTY, Insets.EMPTY)));
            hBox.setSpacing(50);
            setActions();
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Was needed to change the width manually of an icon to 45, since the width and height ratio wasn't 1 : 1 or acceptably close, 4:3.
     */

    private void fixLayoutManually() {
        control.setFitWidth(45);
    }

    /**
     * The addTimeComboBox method creates a selection box with specific times.
     */

    private void addTimeComboBox() {
        this.timeComboBox = new ComboBox();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int time = 8; time < 19; time++) {
            observableList.add(Integer.toString(time)+":00");
        }
        timeComboBox.setItems(observableList);
        timeComboBox.setBackground(new Background(new BackgroundFill(Color.WHEAT, CornerRadii.EMPTY, Insets.EMPTY)));
        timeComboBox.setPrefHeight(ApplicationSettings.simulatorImageWidthAndHeight+5);
        timeComboBox.setPrefWidth(100);
        hBox.getChildren().add(timeComboBox);
    }

    /**
     * The addTimerMultiplier adds a label that displays the current multiplication.
     */

    private void addTimerMultiplier() {
        hBox.getChildren().add(timerMultiplier);
        Font font = new Font("Courier", 25);
        timerMultiplier.setFont(font);
        timerMultiplier.setBackground(Background.EMPTY);
        timerMultiplier.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.DOTTED, null, new BorderWidths(2))));
    }

    /**
     * The setActions() method binds all actions the buttons.
     */

    private void setActions() {
        fire.setOnMouseClicked(fire -> {
            startSim.getMap().fireDrill();
        });
        startstop.setOnMouseClicked(startstop -> {
            paused=!paused;
            if (paused) startSim.getSimUpdate().stopTimer();
            else startSim.getSimUpdate().startTimer();
            updateBox();
        });
        forward.setOnMouseClicked(forward-> {
            startSim.getSimUpdate().increaseSpeed();
            updateBox();
        });
        backward.setOnMouseClicked(backward -> {
            startSim.getSimUpdate().decreaseSpeed();
            updateBox();
        });
        fastForward.setOnMouseClicked(fastForward -> {
            startSim.getSimUpdate().maxSpeed();
            updateBox();
        });
        fastBackward.setOnMouseClicked(fastBackward -> {
            startSim.getSimUpdate().minSpeed();
            updateBox();
        });
        control.setOnMouseClicked(control -> {
            startSim.getMap().hijackedSim=!startSim.getMap().hijackedSim;
        });
        pathfind.setOnMouseClicked(pathfind -> {
            startSim.getMap().showDebug=!startSim.getMap().showDebug;
        });
        collision.setOnMouseClicked(collision -> {
            startSim.getMap().showCollision=!startSim.getMap().showCollision;
        });
        timeComboBox.setOnAction(timeComboBox ->{
            this.startSim.getSimUpdate().getTimerHandler().updateTime(this.timeComboBox.getValue().toString());
        });
    }

    /**
     * Add a time display label.
     */

    private void addTimeDisplay() {
        this.timeLabel = new Label(this.startSim.getSimUpdate().getTimerHandler().getDisplayTime());
        startSim.getSimUpdate().getTimerHandler().bindSimulationBar(this);
        Font font = new Font("Courier", 25);
        timeLabel.setFont(font);
        timeLabel.setBackground(Background.EMPTY);
        timeLabel.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.DOTTED, null, new BorderWidths(2))));
    }

    /**
     * Update the label.
     * @param time Defines the time.
     */

    public void updateTimeDisplay(String time) {
        this.timeLabel.setText(time);
    }

    /**
     * The updateBox method updates the label text that displays the current simulation multiplication speed.
     */

    private void updateBox() {
        timerMultiplier.setText("x"+Double.toString(startSim.getSimUpdate().getTimerMultiplier()));
    }

    /**
     * Receive the hBox to put in a borderPane / other layout component.
     * @return hBox SimulationBar (contains all images, button actions, etc).
     */

    public HBox getHBox() {
        return hBox;
    }

    /**
     * Receive an ImageView from a specific folder.
     * @param folder Defines the folder location of the ImageView.
     * @param name Defines the name of the imageView.
     * @param extension Defines the extension of the Image (most of the times .png / .jpeg
     * @return Receive the ImageView.
     */

    private ImageView getImage(String folder, String name, String extension) {
        return new ImageView(getClass().getResource("/images/" + folder + "/" + name + extension).toString());
    }

    /**
     * Receive the comboBox which contains the selected time.
     * @return Return the ComboBox which contains information about the selected time.
     */

    public ComboBox getTimeComboBox() {
        return timeComboBox;
    }
}
