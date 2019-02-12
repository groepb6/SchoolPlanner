package gui.components.menu;

import gui.components.window.Window;
import gui.assistclasses.Image;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author Dustin Hendriks
 * @since 29-01-2019
 * Bugs known 1: Multiple squares can be selected, but the deselected squares sometimes are not de-textured correctly. <-- Solved
 */

public class MainPane {
    private Stage stage;
    private GridPane mainPane = new GridPane();
    private ArrayList<Image> buttonImages = new ArrayList<>();
    private Scene scene;

    /**
     * Add images that represent the buttons (not yet) placed in the grid. Also set the horizontal and vertical gap between the images. The onHover(); function is being called since after the initialization all images should be ready for hover action.
     */

    public MainPane(Stage stage, Scene scene) {
        initialize();
        this.scene = scene;
        this.stage = stage;
        mainPane.setHgap(5);
        mainPane.setVgap(5);
        mainPane.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        putImagesInGrid();
        onHover();
        stage.setMinWidth(460);
        stage.setMinHeight(505);
        stage.setMaxWidth(460);
        stage.setMaxHeight(505);
    }

    /**
     * The initialize() method adds new images to the "buttonImages" grid, which is being used later when the grid creates a button of every image. Hence that nothing is hardcoded, so feel free to add your own button here.
     */

    private void initialize() {
        buttonImages.add(new Image("tableview", "menuimages", ".jpg", "tableview", "Open the tableview"));
        buttonImages.add(new Image("fancyview", "menuimages", ".jpg", "fancyview", "Open the fancyview"));
        buttonImages.add(new Image("startsim", "menuimages", ".jpg", "startsim", "Start the simulation"));
        buttonImages.add(new Image("editschedule", "menuimages", ".jpg", "editschedule", "Edit the schedule"));
    }

    /**
     * Images are being put in the grid in pairs of two horizontally, with an unlimited available height. Example:
     * []h[]
     * v   v
     * []h[]
     * v   v
     * []h[]
     * v   v
     * []h[]
     * <p>
     * h = horizontal gap
     * v = vertical gap
     * [] = image
     */

    private void putImagesInGrid() {
        int x = 0;
        int y = 0;

        for (int i = 0; i < buttonImages.size(); i++) {
            mainPane.add(buttonImages.get(i).getImageView(), x, y);
            x++;
            if (x > 1) {
                x = 0;
                y++;
            }
        }
    }

    /**
     * When a specific image is selected the mainPane should be prepared for a rebuild. This is why all children rooms are being removed, and the images are being reloaded and placed in the grid.
     */

    private void selectedImage() {
        mainPane.getChildren().clear();
        putImagesInGrid();
    }

    /**
     * The method "getMainPane()" returns the mainPane for further implementation in other classes.
     *
     * @return mainPane that contains the images (instance of a gridPane filled with images)
     */

    public GridPane getMainPane() {
        return mainPane;
    }

    /**
     * When someone is hovering over an image, it should change color to blue. This function prepares every image for a possible hover action, using the "setActionOnHover" method.
     */

    private void onHover() {
        for (int i = 0; i < buttonImages.size(); i++)
            setActionOnHover(i);
    }

    /**
     * When a new image is selected, the textures (possibly changed by a hover event) should be reverted, this method reverts every image except the image that a user is hovering on.
     *
     * @param image This is an integer that tells which image should not be reverted. This function checks if an iterator number not equals "image", if so it can be reverted to its original state.
     */

    private void allImagesToStandardExcept(int image) {
        for (int i = 0; i < buttonImages.size(); i++) {
            if (i != image)
                buttonImages.get(i).editImage(false);
        }
    }

    /**
     * When someone is hovering over an image the image should change to a highlighted image, which tells the user on which image the hover action is performed.
     *
     * @param image This parameter initializes image "image" for a hover event. When a hover event is performed the following methods / frames are being executed:
     *              <p>
     *              "allImagesToStandardExcept()" - see above explanation -
     *              "editImage()" - which defines a hover event could be performed, or not. -
     *              "setActionOnClick" - After a hover event the program should check if the user decides to click on the icon, since the hover event will always come first.
     *              "setActionOnExit" - Off course a user can also decide to not click on the image, but leave it.
     *              "selectedImage();" - Loads the correct image. -
     */

    private void setActionOnHover(int image) {
        buttonImages.get(image).getImageView().setOnMouseEntered(event -> {
            allImagesToStandardExcept(image);
            buttonImages.get(image).editImage(true);
            setActionOnClick(image);
            setActionOnExit(image);
            selectedImage();
        });
    }

    /**
     * The setActionOnClick function sets a specific action using
     *
     * @param image
     */

    private void setActionOnClick(int image) {
        buttonImages.get(image).getImageView().setOnMouseClicked(event -> {
                new Window(buttonImages.get(image).getIdentifier(), stage, scene);
           });
    }

    /**
     * When someone exits an action every image should go back to the original non-pressed texture view.
     *
     * @param image Defines which image should be set to the false state while checking for a exit event (Does not have to check for every image, since you can only exit it if you have entered it, better for system performance).
     */

    private void setActionOnExit(int image) {
        buttonImages.get(image).getImageView().setOnMouseExited(event -> {
            buttonImages.get(image).editImage(false);
            onHover();
        });
    }
}