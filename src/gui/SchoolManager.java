package gui;

import data.DevSetup;
import gui.components.menu.MainPane;
import gui.components.menu.UpperBar;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Dustin Hendriks
 * @since 04-02-2019
 */

public class SchoolManager extends javafx.application.Application {
    private BorderPane layoutPane;

    /**
     * The start method initializes the School manager application.
     * @param stage Received by extending Application (JAVAFX).
     */

    public void start(Stage stage) {
        DevSetup.setupSaveDirectories();
        stage.setTitle("School manager");
        layoutPane = new BorderPane();
        Scene scene = new Scene(layoutPane);
        MainPane LOMainPane = new MainPane(stage, scene);
        UpperBar LOUpperBar = new UpperBar();
        layoutPane.setCenter(LOMainPane.getMainPane());
        layoutPane.setTop(LOUpperBar.getUpperBar());
        stage.setScene(scene);
        stage.show();
    }
}
