package gui.components.window;

import gui.assistclasses.Image;
import gui.components.frames.StartSim;
import gui.settings.ApplicationSettings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import simulation.controls.SimulationBar;

public class Window {
    private BorderPane windowPane = new BorderPane();
    private HBox topBar = new HBox();
    private VBox bars = new VBox();
    private Image backButton;
    private Scene originalScene;
    private Stage stage;
    private Scene scene;
    private StartSim startSim;
    private SimulationBar simulationBar;

    /**
     * @author Dustin Hendriks
     * @since 04-02-2019
     * @param identifier The identifier is being passed to the Window to know which frame should be loaded in.
     * @param stage Since new frames also should have some application settings - such as width, height etc - an instance of Stage is needed.
     * @param originalScene When someone wants to go back to the menu - also called tile menu - a replacement scene is needed, this is originalScene and enables stepping back in menu's.
     *
     * The window class adds the functionality to load in a frame object, it is a basic application function which enables navigating forward and back from and back to the menu.
     */

    public Window(String identifier, Stage stage, Scene originalScene) {
        this.originalScene = originalScene;
        buildBackButton();
        topBar.setBackground(new Background(new BackgroundFill(ApplicationSettings.themeColor, CornerRadii.EMPTY, Insets.EMPTY)));
        bars.getChildren().add(topBar);
        windowPane.setTop(bars);
        this.stage = stage;
        setActionOnClick();
        scene = new Scene(windowPane);
        stage.setScene(scene);

        switch (identifier) {
            case "tableview":
                windowPane.setCenter(new gui.components.frames.TableView(stage).getTableView());
                break;
            case "fancyview":
                windowPane.setCenter(new gui.components.frames.FancyView(stage).getFancyView());
                break;
            case "startsim":
                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                startSim = new StartSim(stage, scene, scrollPane);
                simulationBar = new SimulationBar(startSim);
                bars.getChildren().add(simulationBar.getHBox());
                scrollPane.setContent(startSim.getGroup());
                windowPane.setCenter(scrollPane);
                break;
            case "editschedule":
                windowPane.setCenter(new gui.components.frames.EditSchedule(stage).getEditSchedule());
                break;
        }
    }

    /**
     * Every new opened frame should have a back button, this back button is always added as top bar.
     * Hence that the command "backbutton.getImageView().setPickOnBounds(true)" enables clicking on a transparent part of the button to go back, otherwise you would have to click on non-transparent parts.
     */

    private void buildBackButton() {
        backButton = new Image("back", "functionimages", ".png", "backButton", "Go back to previous menu");
        topBar.getChildren().add(backButton.getImageView());
        backButton.getImageView().setFitHeight(40);
        backButton.getImageView().setFitWidth(40);
        backButton.getImageView().setPickOnBounds(true);
    }

    /**
     * If somebody clicks on the back button, the previous scene should be put in place (which is the tile menu).
     * After this click the stage is being locked to a certain width and height.
     */

    private void setActionOnClick() {
        backButton.getImageView().setOnMouseClicked(event -> {
            if (startSim!=null) {
                startSim.clean();
                startSim=null;
            }
            stage.setScene(originalScene);
            stage.setMinWidth(460);
            stage.setMaxWidth(200);
            stage.setMinHeight(505);
            stage.setMaxHeight(505);
        });
    }
}
