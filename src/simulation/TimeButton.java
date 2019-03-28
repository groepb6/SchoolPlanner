package simulation;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TimeButton {

    public BorderPane timeButtons(BorderPane borderPane){
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

            /*scene.setOnMouseMoved(event -> {
                x=(int) event.getX();
                y = (int) event.getY();
            });

            scene.setOnMouseClicked(event -> {
                stage1.setX(x);
                stage1.setY(y);
            });*/

            stage1.setMaxWidth(450);
            stage1.setMaxHeight(200);
            stage1.show();
            return borderPane;
            //vBox.getChildren().add(borderPane);
        } catch (Exception e) {e.printStackTrace();}
        return borderPane;
    }

    private ImageView getImage(String folder, String name, String extension) {
        return new ImageView(getClass().getResource("/images/" + folder + "/" + name + extension).toString());
    }
}
