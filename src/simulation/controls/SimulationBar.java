package simulation.controls;

import gui.components.frames.StartSim;
import gui.settings.ApplicationSettings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SimulationBar {
    private Stage controller = new Stage();
    private HBox hBox = new HBox();
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ImageView fastBackward;
    private ImageView backward;
    private ImageView startstop;
    private ImageView forward;
    private ImageView fastForward;
    private ImageView fire;
    private Label timerMultiplier=new Label("Speed x1.0");
    private Label timeDisplay;
    private StartSim startSim;
    //private boolean paused = false;

    public SimulationBar(StartSim startSim) {
        String folder ="simbarimages";
        this.startSim = startSim;
        String name;
        String extension = ".png";
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
            name = "fire";
            fire = getImage(folder, name, extension);
            imageViews.add(fire);
            for (ImageView imageView : imageViews) {
                imageView.setPickOnBounds(true);
                imageView.setFitWidth(ApplicationSettings.simulatorImageWidthAndHeight);
                imageView.setFitHeight(ApplicationSettings.simulatorImageWidthAndHeight);
                hBox.getChildren().add(imageView);
            }
            addTimerMultiplier();
            this.addTimeDisplay();
            hBox.setBackground(new Background(new BackgroundFill(ApplicationSettings.themeColor, CornerRadii.EMPTY, Insets.EMPTY)));
            hBox.setSpacing(50);
            setActions();
        } catch (Exception e) {e.printStackTrace();}
    }

    private void addTimerMultiplier() {
        hBox.getChildren().add(timerMultiplier);
        Font font = new Font("Courier", 25);
        timerMultiplier.setFont(font);
        timerMultiplier.setBackground(Background.EMPTY);
        timerMultiplier.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.DOTTED, null, new BorderWidths(2))));
    }

    private void addTimeDisplay() {
        this.timeDisplay = new Label(this.startSim.getSimulation().getTime().getDisplay());
        hBox.getChildren().add(this.timeDisplay);
        Font font = new Font("Courier", 25);
        timeDisplay.setFont(font);
        timeDisplay.setBackground(Background.EMPTY);
        timeDisplay.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.DOTTED, null, new BorderWidths(2))));
        this.startSim.getSimulation().getTime().connectToSimulationBar(this);
    }

    private void setActions() {
        fire.setOnMouseClicked(fire -> {
            this.startSim.getSimulation().fireDrill();
        });
        startstop.setOnMouseClicked(startstop -> {
            this.startSim.getSimulation().pausePlay();
            updateBox();
        });
        forward.setOnMouseClicked(forward-> {
            this.startSim.getSimulation().changeSpeed(0.5);
            updateBox();
        });
        backward.setOnMouseClicked(backward -> {
            this.startSim.getSimulation().changeSpeed(-0.5);
            updateBox();
        });
        fastForward.setOnMouseClicked(fastForward -> {
            this.startSim.getSimulation().maxSpeed();
            updateBox();
        });
        fastBackward.setOnMouseClicked(fastBackward -> {
            this.startSim.getSimulation().minSpeed();
            updateBox();
        });
    }

    private void updateBox() {
        timerMultiplier.setText("Speed x"+Double.toString(startSim.getSimulation().getTime().getSpeed()));
    }

    public void updateTimeDisplay(String display) {
        this.timeDisplay.setText(display);
    }

    public HBox getHBox() {
        return hBox;
    }

    private ImageView getImage(String folder, String name, String extension) {
        return new ImageView(getClass().getResource("/images/" + folder + "/" + name + extension).toString());
    }
}
