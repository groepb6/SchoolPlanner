package gui.components.frames;

import data.readwrite.DataReader;
import data.readwrite.DataWriter;
import data.schedulerelated.Schedule;
import gui.assistclasses.Plan;
import gui.components.window.Sizeable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author Dustin Hendriks
 * @since 12-02-2019
 * <p>
 * The class "EditSchedule" builds a borderPane which can add new Plans to the storage .txt file. This pane exists out of a few TextField objects and a Button object.
 * On a button click the inserted information (in the text fields) is being converted to a plan and written to a file, but only if the plan did not exist already.
 */

public class EditSchedule extends Sizeable {
    private BorderPane borderPane = new BorderPane();
    private HBox fieldBox = new HBox();
    private VBox groupColom = new VBox();
    private VBox timeColomBegin = new VBox();
    private VBox timeColomEnd = new VBox();
    private VBox locationColom = new VBox();
    private VBox subjectColom = new VBox();
    private VBox teacherColom = new VBox();
    private TextField groupField = new TextField();
    private TextField timeFieldBegin = new TextField();
    private TextField timeFieldEnd = new TextField();
    private TextField locationField = new TextField();
    private TextField subjectField = new TextField();
    private TextField teacherField = new TextField();
    private Label groupMessage = new Label();
    private Label timeBeginMessage = new Label();
    private Label timeEndMessage = new Label();
    private Label locationMessage = new Label();
    private Label subjectMessage = new Label();
    private Label teacherMessage = new Label();
    private Button addPlan = new Button("Add plan");


    /**
     * The "EditSchedule" constructor puts all elements in the correct place on a borderPane. This borderPane can be sent to the Window class later, as seen with other classes located in the frames package.
     * @param stage Is needed to scale the scene to a minimum width and height (so no elements would be cut off).
     */

    public EditSchedule(Stage stage) {
        fieldBox.getChildren().addAll(groupColom, timeColomBegin, timeColomEnd, locationColom, subjectColom, teacherColom, addPlan);
        borderPane.setTop(fieldBox);

        groupColom.getChildren().addAll(groupField, groupMessage);
        timeColomBegin.getChildren().addAll(timeFieldBegin, timeBeginMessage);
        timeColomEnd.getChildren().addAll(timeFieldEnd, timeEndMessage);
        locationColom.getChildren().addAll(locationField, locationMessage);
        subjectColom.getChildren().addAll(subjectField, subjectMessage);
        teacherColom.getChildren().addAll(teacherField, teacherMessage);
        fieldBox.setSpacing(10);
        groupField.setPromptText("Group");
        timeFieldBegin.setPromptText("From time (ab:xy format)");
        timeFieldEnd.setPromptText("Until time (ab:xy format)");
        locationField.setPromptText("Location");
        subjectField.setPromptText("Subject");
        teacherField.setPromptText("Teacher");
        super.setProportions(0, 2560, 0, 1080, 1100, 500, stage);
        setActions();
    }

    /**
     * If the addPlan method is called a check will need to be performed on all fields, since you wouldn't want invalid data to be written to the Plan save file.
     */

    private void setActions() {
        addPlan.setOnMouseClicked(event -> {
            boolean everythingCorrect = true;
            if (!checkGroup()) everythingCorrect = false;
            if (!checkBeginTime()) everythingCorrect = false;
            if (!checkEndTime()) everythingCorrect = false;
            if (!checkLocation()) everythingCorrect = false;
            if (!checkSubject()) everythingCorrect = false;
            if (!checkTeacher()) everythingCorrect = false;
            if (everythingCorrect)
                addPlan();
        });

        groupField.setOnMouseClicked(event -> groupField.setText(""));
        groupField.setOnMouseDragged(event -> groupField.setText(""));
        timeFieldBegin.setOnMouseClicked(event -> timeBeginMessage.setText(""));
        timeFieldBegin.setOnMouseDragged(event -> timeBeginMessage.setText(""));
        timeFieldEnd.setOnMouseClicked(event -> timeEndMessage.setText(""));
        timeFieldEnd.setOnMouseDragged(event -> timeEndMessage.setText(""));
        locationField.setOnMouseClicked(event -> locationMessage.setText(""));
        locationField.setOnMouseDragged(event -> locationMessage.setText(""));
        subjectMessage.setOnMouseClicked(event -> subjectMessage.setText(""));
        subjectMessage.setOnMouseDragged(event -> subjectMessage.setText(""));
        teacherMessage.setOnMouseClicked(event -> teacherMessage.setText(""));
        teacherMessage.setOnMouseDragged(event -> teacherMessage.setText(""));
    }

    /**
     * If all previous checks are performed there is one check remaining, we do not want any double data in our plane file. This is why the boolean canAdd is set to false if any double plan is found.
     */

    private void addPlan() {
        ArrayList<Schedule> scheduleArrayList;
        try {
            Schedule schedule = new Schedule(new Plan(timeFieldBegin.getText() + " - " + timeFieldEnd.getText(), groupField.getText(), locationField.getText(), teacherField.getText(), subjectField.getText()));
            scheduleArrayList = DataReader.readObject();
            boolean canAdd = true;
            for (int i = 0; i < scheduleArrayList.size(); i++) {
                if (scheduleArrayList.get(i).getPlan().isEqualTo(schedule.getPlan()))
                    canAdd = false;
            }
            if (canAdd) {
                scheduleArrayList.add(schedule);
                DataWriter.writeObject(scheduleArrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the input is correct in the checkGroup field.
     * @return Boolean which defines whether the input was correct or not.
     */

    private boolean checkGroup() {
        if (groupField.getText().length() >= 1)
            return true;
        groupMessage.setText("Group is empty");
        return false;

    }

    /**
     * Check if the input is correct in the CheckBeginTime field.
     * @return Boolean which defines whether the input was correct or not.
     */

    private boolean checkBeginTime() {
        if (checkTime(timeFieldBegin.getText()))
            return true;
        timeBeginMessage.setText("Enter time between 08:00 and 20:00");
        return false;
    }

    /**
     * Check if the input is correct in the CheckEndTime field.
     * @return Boolean which defines whether the input was correct or not.
     */

    private boolean checkEndTime() {
        if (checkTime(timeFieldEnd.getText()))
            return true;
        timeEndMessage.setText("Enter time between 08:00 and 20:00");
        return false;
    }

    /**
     * Check if the input format of a time is in the format of ab:xy, if not false is returned.
     * @param timeString The String which should be checked.
     * @return Return a true or false value depending on the check on the timeString.
     */

    private boolean checkTime(String timeString) {
        if (timeString.length() == 5)
            if (timeString.contains(":"))
                if (timeString.indexOf(":") == 2)
                    return (Integer.parseInt((timeString.substring(0, timeString.indexOf(":")))) >= 8 && Integer.parseInt((timeString.substring(0, timeString.indexOf(":")))) <= 20 && Integer.parseInt(timeString.substring(timeString.indexOf(":") + 1)) < 60);
        return false;
    }

    /**
     * Check if the location input is valid
     * @return If the location input is valid (=not null) return true, else return false.
     */

    private boolean checkLocation() {
        if (locationField.getText().length() > 0)
            return true;
        locationMessage.setText("Location is empty");
        return false;
    }

    /**
     * Check if the subject input is valid
     * @return If the subject input is valid (=not null) return true, else return false.
     */

    private boolean checkSubject() {
        if (subjectField.getText().length() > 0)
            return true;
        subjectMessage.setText("Subject is empty");
        return false;
    }

    /**
     * Check if the teacher input is valid
     * @return If the teacher input is valid (=not null) return true, else return false.
     */

    private boolean checkTeacher() {
        if (teacherField.getText().length() > 0)
            return true;
        teacherMessage.setText("Teacher is empty");
        return false;
    }

    /**
     * Retrieve the borderPane with all components on it.
     * @return Receive the borderPane with all components on it.
     */

    public BorderPane getEditSchedule() {
        return borderPane;
    }
}
