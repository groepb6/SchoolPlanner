package gui.components.frames;

import data.DataReader;
import data.DataWriter;
import data.schedulerelated.Schedule;
import gui.assistclasses.Plan;
import gui.components.window.Sizeable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

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

    private void setActions() {
        addPlan.setOnMouseClicked(event -> {
            boolean everythingCorrect = true;
            if (!checkGroup()) everythingCorrect=false;
            if (!checkBeginTime()) everythingCorrect=false;
            if (!checkEndTime()) everythingCorrect=false;
            if (!checkLocation()) everythingCorrect=false;
            if (!checkSubject()) everythingCorrect=false;
            if (!checkTeacher()) everythingCorrect=false;
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

    private void addPlan() {
        ArrayList<Schedule> scheduleArrayList;

        try {
            Schedule schedule = new Schedule(new Plan(timeFieldBegin.getText() + " - " + timeFieldEnd.getText(), groupField.getText(), locationField.getText(), teacherField.getText(), subjectField.getText()));
            scheduleArrayList = DataReader.readObject();
            scheduleArrayList.add(schedule);
            DataWriter.writeObject(scheduleArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkGroup() {
        if (groupField.getText().length() >= 1)
            return true;
        groupMessage.setText("Group is empty");
        return false;

    }

    private boolean checkBeginTime() {
        if (checkTime(timeFieldBegin.getText()))
            return true;
        timeBeginMessage.setText("Enter time between 08:00 and 20:00");
        return false;
    }

    private boolean checkEndTime() {
        if (checkTime(timeFieldEnd.getText()))
            return true;
        timeEndMessage.setText("Enter time between 08:00 and 20:00");
        return false;
    }

    private boolean checkTime(String timeString) {
        if (timeString.length() == 5)
            if (timeString.contains(":"))
                if (timeString.indexOf(":") == 2)
                    return (Integer.parseInt((timeString.substring(0, timeString.indexOf(":")))) >= 8 && Integer.parseInt((timeString.substring(0, timeString.indexOf(":")))) <= 20 && Integer.parseInt(timeString.substring(timeString.indexOf(":") + 1)) < 60);
        return false;
    }


    private boolean checkLocation() {
        if (locationField.getText().length() > 0)
            return true;
        locationMessage.setText("Location is empty");
        return false;
    }

    private boolean checkSubject() {
        if (subjectField.getText().length() > 0)
            return true;
        subjectMessage.setText("Subject is empty");
        return false;
    }

    private boolean checkTeacher() {
        if (teacherField.getText().length() > 0)
            return true;
        teacherMessage.setText("Teacher is empty");
        return false;
    }

    public BorderPane getEditSchedule() {
        return borderPane;
    }
}
