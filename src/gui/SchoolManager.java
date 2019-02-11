package gui;

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

    public void start(Stage stage) {
        System.out.println(getClass().getResource("images"));

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
