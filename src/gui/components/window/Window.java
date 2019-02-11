package gui.components.window;

import gui.Image;
import gui.settings.ApplicationSettings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Window {
    private BorderPane windowPane = new BorderPane();
    private HBox topBar = new HBox();
    private Image backButton;
    private Scene originalScene;
    private Stage stage;
    private Scene scene;

    public Window(String identifier, Stage stage, Scene originalScene) {
        this.originalScene = originalScene;
        buildBackButton();
        topBar.setBackground(new Background(new BackgroundFill(ApplicationSettings.themeColor, CornerRadii.EMPTY, Insets.EMPTY)));
        windowPane.setTop(topBar);
        this.stage = stage;
        setActionOnClick();

        switch (identifier) {
            case "tableview":
                windowPane.setCenter(new gui.components.frames.TableView(stage).getTableView());
                scene = new Scene(windowPane);
                stage.setScene(scene);
                break;
            case "fancyview":
                windowPane.setCenter(new gui.components.frames.FancyView(stage).getFancyView());
                scene = new Scene(windowPane);
                stage.setScene(scene);
                break;
            case "startsim":
                //windowPane.setCenter(new gui.components.frames.StartSim().getSim());
                break;
            case "editschedule":
                //windowPane.setCenter(new gui.components.frames.EditSchedule().getSchedule);
                break;
        }
    }

    private void buildBackButton() {
        backButton = new Image("back", "functionimages", ".png", "backButton", "Go back to previous menu");
        topBar.getChildren().add(backButton.getImageView());
        backButton.getImageView().setFitHeight(40);
        backButton.getImageView().setFitWidth(40);
        backButton.getImageView().setPickOnBounds(true);
    }

    private void setActionOnClick() {
        backButton.getImageView().setOnMouseClicked(event -> {
            stage.setScene(originalScene);
            stage.setMinWidth(460);
            stage.setMaxWidth(200);
            stage.setMinHeight(505);
            stage.setMaxHeight(505);
        });
    }
}
