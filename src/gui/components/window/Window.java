package gui.components.window;

import data.schoolrelated.School;
import gui.assistclasses.Image;
import gui.components.frames.StartSim;
import gui.settings.ApplicationSettings;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simulation.TimeButton;

import javax.imageio.ImageIO;

public class Window {
    private BorderPane windowPane = new BorderPane();
    private HBox topBar = new HBox();
    private Image backButton;
    private Scene originalScene;
    private Stage stage;
    private Scene scene;
    private StartSim startSim;
    private VBox vBox;

    private int x;
    private int y;

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
        vBox = new VBox();
        this.originalScene = originalScene;
        buildBackButton();
        topBar.setBackground(new Background(new BackgroundFill(ApplicationSettings.themeColor, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.getChildren().add(topBar);
        windowPane.setTop(vBox);
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
                scrollPane.setContent(startSim.getGroup());
                windowPane.setCenter(scrollPane);
                TimeButton timeButton = new TimeButton();
                vBox.getChildren().add(timeButton.timeButtons(new BorderPane()));
                //timeButtons(new BorderPane());
                //windowPane.setCenter(startSim.getGroup());
                break;
            case "editschedule":
                windowPane.setCenter(new gui.components.frames.EditSchedule(stage).getEditSchedule());
                this.stage.setMinWidth(730);
                this.stage.setMinHeight(295);
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

   /* private ImageView getImage(String folder, String name, String extension) {
        return new ImageView(getClass().getResource("/images/" + folder + "/" + name + extension).toString());
    }

    private void timeButtons(BorderPane borderPane){
        HBox hBox = new HBox();
        HBox hBox1 = new HBox();
        ComboBox comboBox = new ComboBox();

        String folder ="timeimages";
        String name="";
        String extension = ".png";
        try {
            name = "fastbackward";
            ImageView imageView1 = getImage(folder, name, extension);

            name = "backward";
            ImageView imageView2 = getImage(folder,name,extension);

            name = "start-stop";
            ImageView imageView3 = getImage(folder,name,extension);

            name = "forward";
            ImageView imageView4 = getImage(folder,name,extension);

            name = "fastfoward";
            ImageView imageView5 = getImage(folder,name,extension);

            imageView1.setPickOnBounds(true);
            imageView2.setPickOnBounds(true);
            imageView3.setPickOnBounds(true);
            imageView4.setPickOnBounds(true);
            imageView5.setPickOnBounds(true);

            imageView1.setFitWidth(50);
            imageView1.setFitHeight(50);
            imageView2.setFitWidth(50);
            imageView2.setFitHeight(50);
            imageView3.setFitWidth(50);
            imageView3.setFitHeight(50);
            imageView4.setFitWidth(50);
            imageView4.setFitHeight(50);
            imageView5.setFitWidth(50);
            imageView5.setFitHeight(50);

            System.out.println("test");
            //HBox hBox = new HBox(imageView1,imageView2,imageView3,imageView4,imageView5);
            hBox.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

            //hBox.getItems().addAll(imageView1, imageView2, imageView3, imageView4, imageView5);
            //hBox.setLayoutX(50);
            hBox.getChildren().addAll(imageView1, imageView2, imageView3, imageView4, imageView5);
           // hBox.setMaxWidth(50);
            hBox.setMinWidth(1000);
           // borderPane.setCenter(hBox);
            hBox.setSpacing(50);
            hBox.setMaxWidth(450);
            Stage stage1 = new Stage();
            Scene scene = new Scene(hBox);
            stage1.setScene(scene);

            scene.setOnMouseMoved(event -> {
                x=(int) event.getX();
                y = (int) event.getY();
            });

            scene.setOnMouseClicked(event -> {
                stage1.setX(x);
                stage1.setY(y);
            });

            stage1.setMaxWidth(450);
            stage1.setMaxHeight(200);
            stage1.show();
            vBox.getChildren().add(borderPane);
        } catch (Exception e) {e.printStackTrace();}
    }*/
}
