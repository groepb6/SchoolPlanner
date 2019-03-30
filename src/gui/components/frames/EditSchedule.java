package gui.components.frames;

import data.persons.Person;
import data.persons.Teacher;
import data.readwrite.DataReader;
import data.readwrite.DataWriter;
import data.rooms.Classroom;
import data.rooms.Room;
import data.schedulerelated.Hour;
import data.schedulerelated.Schedule;
import data.schoolrelated.Group;
import data.schoolrelated.School;
import data.schoolrelated.Subject;
import gui.components.window.Sizeable;
import gui.settings.ApplicationSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;

/**
 * @author Wout Stevens
 * @author Hanno Brandwijk
 * @since 12-02-2019
 * <p>
 * The class "EditSchedule" builds a borderPane which can add new Plans to the storage .txt file. This pane exists out of a few TextField objects and a Button object.
 * On a button click the inserted information (in the text fields) is being converted to a plan and written to a file, but only if the plan did not exist already.
 */

public class EditSchedule extends Sizeable {
    private School school;
    private BorderPane borderPane;
    private VBox vBox;
    private HBox hBox1;
    private HBox hBox2;
    private HBox hBox3;
    private HBox hBox4;
    private HBox hBox5;
    private HBox hBox6;
    private Label labelGroup;
    private Label labelTime;
    private Label labelTeacher;
    private Label labelRoom;
    private Label labelSubject;
    private Label labelInfo;
    private TextField tfGroup;
    private TextField tfTeacher;
    private TextField tfRoom;
    private TextField tfSubject;
    private ObservableList<String> timeOptions;
    private ComboBox timeComboBox;
    private ObservableList<String> groupOptions;
    private ComboBox groupComboBox;
    private ObservableList<String> teacherOptions;
    private ComboBox teacherComboBox;
    private ObservableList<String> roomOptions;
    private ComboBox roomComboBox;
    private ObservableList<String> subjectOptions;
    private ComboBox subjectComboBox;
    private Button buttonAddSchedule;
    private Button buttonAddGroup;
    private Button buttonAddSubject;
    private Button buttonAddTeacher;
    private Button buttonAddRoom;
    private Button buttonDeleteGroup;
    private Button buttonDeleteTeacher;
    private Button buttonDeleteRoom;
    private Button buttonDeleteSubject;
    private Button buttonClearAll;
    private Button buttonLoadDefaults;
    private Button buttonSaveDefaults;
    private Button standardButton;
    private int buttonWidth;
    private Stage stage;

    public EditSchedule(Stage stage) {
        super.setProportions(800, 2560, 0, 1080, 800, 500, stage);
        this.school = DataReader.readSchool();
        build();
        initStandardButtonBackground();
        setLayout();
        setStyle();
        setComboBox();
        setSizes();
        setActions();
        this.stage = stage;
    }

    /**
     * setLayout initiates all objects that build up the layout
     */

    private void setLayout() {
        this.borderPane = new BorderPane();
        this.vBox = new VBox();
        this.hBox1 = new HBox();
        this.hBox2 = new HBox();
        this.hBox3 = new HBox();
        this.hBox4 = new HBox();
        this.hBox5 = new HBox();
        this.hBox6 = new HBox();

        this.labelGroup = new Label("Group:");
        this.labelTime = new Label("Time:");
        this.labelTeacher = new Label("Teacher:");
        this.labelRoom = new Label("Room:");
        this.labelSubject = new Label("Subject:");
        this.labelInfo = new Label("");

        this.tfGroup = new TextField();
        this.tfTeacher = new TextField();
        this.tfSubject = new TextField();
        this.tfRoom = new TextField();

        this.timeComboBox = new ComboBox();
        this.timeOptions = FXCollections.observableArrayList();
        this.groupComboBox = new ComboBox();
        this.groupOptions = FXCollections.observableArrayList();
        this.teacherComboBox = new ComboBox();
        this.teacherOptions = FXCollections.observableArrayList();
        this.roomComboBox = new ComboBox();
        this.roomOptions = FXCollections.observableArrayList();
        this.subjectComboBox = new ComboBox();
        this.subjectOptions = FXCollections.observableArrayList();

        this.buttonAddSchedule = new Button("Add schedule");
        this.buttonAddGroup = new Button("Add Group");
        this.buttonAddSubject = new Button("Add Subject");
        this.buttonAddTeacher = new Button("Add Teacher");
        this.buttonAddRoom = new Button("Add Room");

        this.buttonDeleteGroup = new Button("Delete Group");
        this.buttonDeleteRoom = new Button("Delete Room");
        this.buttonDeleteSubject = new Button("Delete Subject");
        this.buttonDeleteTeacher = new Button("Delete Teacher");

        this.buttonClearAll = new Button("Clear all data");
        this.buttonLoadDefaults = new Button("Load defaults");
        this.buttonSaveDefaults = new Button("Save defaults");

        this.standardButton = new Button();
        standardButton.setTranslateX(1920);
        standardButton.setTranslateY(1080);
        standardButton.setMaxWidth(1);
        standardButton.setMaxHeight(1);
    }

    private void setLayout() {
        this.vBox.getChildren().addAll(
                this.hBox1,
                this.hBox2,
                this.hBox3,
                this.hBox4,
                this.hBox5,
                this.hBox6,
                this.labelInfo
        );
        this.borderPane.setTop(this.vBox);

        //This creates a small margin around everything.
        this.borderPane.setPadding(new javafx.geometry.Insets(10, 0, 0, 10));
    }

    public BorderPane getEditSchedule() {
        return this.borderPane;
    }

    private void initStandardButtonBackground() {
        Button button = new Button("");
        this.standardButton = button;
    }

    private void setStyle() {
        styleButton(buttonAddRoom);
        styleButton(buttonAddSubject);
        styleButton(buttonAddTeacher);
        styleButton(buttonAddGroup);
        styleButton(buttonDeleteRoom);
        styleButton(buttonDeleteSubject);
        styleButton(buttonDeleteTeacher);
        styleButton(buttonDeleteGroup);
    }

    private void setSizes() {
        this.hBox1.setSpacing(5);
        this.hBox2.setSpacing(5);
        this.hBox3.setSpacing(5);
        this.hBox4.setSpacing(5);
        this.hBox5.setSpacing(5);
        this.hBox6.setSpacing(5);
        this.vBox.setSpacing(5);

        this.hBox1.getChildren().addAll(this.labelGroup, this.groupComboBox, this.tfGroup, this.buttonAddGroup, this.buttonDeleteGroup);
        this.hBox2.getChildren().addAll(this.labelSubject, this.subjectComboBox, this.tfSubject, this.buttonAddSubject, this.buttonDeleteSubject);
        this.hBox3.getChildren().addAll(this.labelTeacher, this.teacherComboBox, this.tfTeacher, this.buttonAddTeacher, this.buttonDeleteTeacher);
        this.hBox4.getChildren().addAll(this.labelRoom, this.roomComboBox);
        this.hBox5.getChildren().addAll(this.labelTime, this.timeComboBox);
        this.hBox6.getChildren().addAll(this.buttonAddSchedule, this.buttonClearAll, this.buttonLoadDefaults, this.buttonSaveDefaults);


        /**
         * Layout-Settings
         */
        this.buttonWidth = 150;
        this.timeComboBox.setPrefWidth(buttonWidth);
        this.groupComboBox.setPrefWidth(buttonWidth);
        this.teacherComboBox.setPrefWidth(buttonWidth);
        this.roomComboBox.setPrefWidth(buttonWidth);
        this.subjectComboBox.setPrefWidth(buttonWidth);

        this.labelGroup.setMinWidth(buttonWidth / 2.);
        this.labelTime.setMinWidth(buttonWidth / 2.);
        this.labelTeacher.setMinWidth(buttonWidth / 2.);
        this.labelSubject.setMinWidth(buttonWidth / 2.);
        this.labelRoom.setMinWidth(buttonWidth / 2.);

        this.buttonAddGroup.setMinWidth(buttonWidth);
        this.buttonAddRoom.setMinWidth(buttonWidth);
        this.buttonAddTeacher.setMinWidth(buttonWidth);
        this.buttonAddSubject.setMinWidth(buttonWidth);

        this.buttonDeleteTeacher.setMinWidth(buttonWidth);
        this.buttonDeleteSubject.setMinWidth(buttonWidth);
        this.buttonDeleteRoom.setMinWidth(buttonWidth);
        this.buttonDeleteGroup.setMinWidth(buttonWidth);

        this.tfSubject.setMinWidth(buttonWidth);
        this.tfTeacher.setMinWidth(buttonWidth);
        this.tfGroup.setMinWidth(buttonWidth);
    }

    private void update() {
        setStyle();
    }


    /**
     * setComboBox fills all the comboBoxes in the GUI with the right items
     */

    private void setComboBox() {
        EnumSet.allOf(Hour.class).forEach(Hour -> this.timeOptions.add(Hour.getTime()));
        this.timeComboBox.setItems(this.timeOptions);
        this.timeComboBox.setMinWidth(150);

        this.groupOptions.addAll();
        for (Group g : this.school.getGroups()) {
            this.groupOptions.add(g.getName());
        EnumSet.allOf(Hour.class).forEach(Hour -> hours.add(Hour));
        }
        return null;
    }

    private void updateResults() {
        this.groupOptions.clear();
        this.subjectOptions.clear();
        this.teacherOptions.clear();
        this.timeOptions.clear();
        setStyle();
        setComboBox();
    }

    /**
     * setActions sets all the actions of the buttons in the GUI
     */

    private void setActions() {
        this.buttonAddSchedule.setOnAction(event -> {
            if (!groupComboBox.getSelectionModel().isEmpty() && !roomComboBox.getSelectionModel().isEmpty() && !timeComboBox.getSelectionModel().isEmpty() && !teacherComboBox.getSelectionModel().isEmpty() && !subjectComboBox.getSelectionModel().isEmpty()) {
                boolean isAvailableThisTime;

                if (this.school != null) {
                    Group group = new Group("temp");
                    for (Group g : this.school.getGroups()) {
                        if (g.getName().equals(groupComboBox.getValue().toString())) {
                            group = g;
                        }
                    }

                    Teacher teacher = new Teacher("temp");
                    for (Person t : this.school.getTeachers()) {
                        if (t.getName().equals(teacherComboBox.getValue().toString())) {
                            teacher = (Teacher) t;
                        }
                    }

                    Subject subject = new Subject("temp");
                    for (Subject s : this.school.getSubjects()) {
                        if (s.getName().equals(subjectComboBox.getValue().toString())) {
                            subject = s;
                        }
                    }

                    Room room = new Classroom("temp");
                    for (Room r : this.school.getRooms()) {
                        if (r.getName().equals(roomComboBox.getValue().toString())) {
                            room = r;
                        }
                    }
                    isAvailableThisTime = isAvailableThisTime(teacher, room, this.getHour(this.timeComboBox.getValue().toString()), group);

                    Schedule schedule = new Schedule(
                            this.getHour(this.timeComboBox.getValue().toString()),
                            group,
                            room,
                            teacher,
                            subject
                    );

                    if (!isDuplicateSchedule(this.school, schedule) && isAvailableThisTime) { // && canAdd
                        room.getHours().add(this.getHour(this.timeComboBox.getValue().toString()));
                        teacher.getHours().add(this.getHour(this.timeComboBox.getValue().toString()));
                        group.getHours().add(this.getHour(this.timeComboBox.getValue().toString()));
                        this.school.addSchedule(schedule);
                        DataWriter.writeSchool(school);
                        school = DataReader.readSchool();
                        addScheduleSucceed();
                        displayInfoMessage(false, "Schedule added successfully.");
                    } else {
                        addScheduleFailed("Your schedule was not added due to duplicate data, please check your input data");
                        displayInfoMessage(true, "Could not add schedule.");
                    }
                } else {
                    addScheduleFailed("Your schedule does not contain any data");
                    displayInfoMessage(true, "Could not add schedule.");
                }
            } else {
                addScheduleFailed("You can not add a plan with an empty field, please make sure you have entered a value for every specification");
                displayInfoMessage(true, "Could not add schedule.");
            }
        });

        this.buttonAddGroup.setOnMouseExited(event -> {
            styleButton(this.buttonAddGroup);
        });

        this.buttonAddGroup.setOnMouseEntered(event -> {
            applyEffects(buttonAddGroup);
        });

        this.buttonAddTeacher.setOnMouseExited(event -> {
            styleButton(buttonAddTeacher);
        });

        this.buttonAddTeacher.setOnMouseEntered(event -> {
            applyEffects(buttonAddTeacher);
        });

        this.buttonAddRoom.setOnMouseExited(event -> {
            styleButton(buttonAddRoom);
        });

        this.buttonAddRoom.setOnMouseEntered(event -> {
            applyEffects(buttonAddRoom);
        });

        this.buttonAddSubject.setOnMouseExited(event -> {
            styleButton(buttonAddSubject);
        });

        this.buttonAddSubject.setOnMouseEntered(event -> {
            applyEffects(buttonAddSubject);
        });

        this.buttonDeleteTeacher.setOnMouseExited(event -> {
            styleButton(buttonDeleteTeacher);
        });

        this.buttonDeleteTeacher.setOnMouseEntered(event -> {
            applyEffects(buttonDeleteTeacher);
        });

        this.buttonDeleteGroup.setOnMouseExited(event -> {
            styleButton(buttonDeleteGroup);
        });

        this.buttonDeleteGroup.setOnMouseEntered(event -> {
            applyEffects(buttonDeleteGroup);
        });

        this.buttonDeleteRoom.setOnMouseExited(event -> {
            styleButton(buttonDeleteRoom);
        });

        this.buttonDeleteRoom.setOnMouseEntered(event -> {
            applyEffects(buttonDeleteRoom);
        });

        this.buttonDeleteSubject.setOnMouseExited(event -> {
            styleButton(buttonDeleteSubject);
        });

        this.buttonDeleteSubject.setOnMouseEntered(event -> {
            applyEffects(buttonDeleteSubject);
        });

        //This buttonAction clears the complete data class.

        this.buttonClearAll.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to erase all data?", ButtonType.YES, ButtonType.CANCEL);
            setAlertPos(alert);
            alert.setHeaderText("");
            alert.setTitle("Confirm");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                displayInfoMessage(false, "Cleared all data");
                school.clearAll();
                DataWriter.writeSchool(school);
                school = DataReader.readSchool();
                update();
                showSuccessfullyCleared();
                updateResults();
            }
        });

        //This button checks if a defaults file exists and if it does, it loads it in.

        this.buttonLoadDefaults.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to load the defaults?", ButtonType.YES, ButtonType.CANCEL);
            setAlertPos(alert);
            alert.setHeaderText("");
            alert.setTitle("Confirm");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                File defaultsFile = new File(ApplicationSettings.saveSlotPath);
                if (defaultsFile.exists()) {
                    displayInfoMessage(false, "Defaults loaded.");
                    this.school = DataReader.readDefaults();
                    DataWriter.writeSchool(this.school);
                    updateResults();
                } else {
                    displayInfoMessage(true, "Defaults not found.");
                }
            }
        });

        //This button saves a defaults file

        this.buttonSaveDefaults.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save the defaults?", ButtonType.YES, ButtonType.CANCEL);
            setAlertPos(alert);
            alert.setHeaderText("");
            alert.setTitle("Confirm");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                DataWriter.writeDefaults(this.school);
                displayInfoMessage(false, "Defaults saved.");
            }
        });

        //All following buttons check if the group that is about to be added already exists, and then adds it to the list if it doesnt.

        this.buttonAddGroup.setOnAction(event -> {
            if (!this.groupOptions.contains(this.tfGroup.getText()) && !this.tfGroup.getText().isEmpty()) {
                this.school.getGroups().add(new Group(this.tfGroup.getText()));
                this.groupOptions.add(this.tfGroup.getText());
                this.tfGroup.clear();
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Group added successfully.");
                succeedButton(buttonAddGroup);
            } else {
                displayInfoMessage(true, "Could not add Group.");
                errorButton(buttonAddGroup);
            }
        });

        this.buttonAddTeacher.setOnAction(event -> {
            if (!this.teacherOptions.contains(this.tfTeacher.getText()) && !this.tfTeacher.getText().isEmpty()) {
                this.school.getTeachers().add(new Teacher(this.tfTeacher.getText()));
                this.teacherOptions.add(this.tfTeacher.getText());
                this.tfTeacher.clear();
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Teacher added successfully.");
                succeedButton(buttonAddTeacher);
            } else {
                displayInfoMessage(true, "Could not add Teacher.");
                errorButton(buttonAddTeacher);
            }
        });

        this.buttonAddSubject.setOnAction(event -> {
            if (!this.subjectOptions.contains(this.tfSubject.getText()) && !this.tfSubject.getText().isEmpty()) {
                this.school.getSubjects().add(new Subject(this.tfSubject.getText()));
                this.subjectOptions.add(this.tfSubject.getText());
                this.tfSubject.clear();
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Subject added successfully.");
                succeedButton(buttonAddSubject);
            } else {
                displayInfoMessage(true, "Could not add Subject.");
                errorButton(buttonAddSubject);
            }
        });

        this.buttonAddRoom.setOnAction(event -> {
            if (!this.roomOptions.contains(this.tfRoom.getText()) && !this.tfRoom.getText().isEmpty()) {
                this.school.getRooms().add(new Classroom(this.tfRoom.getText()));
                this.roomOptions.add(this.tfRoom.getText());
                this.tfRoom.clear();
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Room added successfully.");
                succeedButton(buttonAddRoom);
            } else {
                displayInfoMessage(true, "Could not add Room.");
                errorButton(buttonAddRoom);
            }
        });

        //The following buttons Delete the Group, Teacher or Subject, by checking the Schoolobject for the object with the same name and deleting it.

        this.buttonDeleteTeacher.setOnAction(event -> {
            if (!this.teacherComboBox.getSelectionModel().isEmpty()) {
                this.school.getTeachers().remove(this.teacherComboBox.getSelectionModel().getSelectedIndex());
                this.teacherOptions.remove(this.teacherComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Teacher deleted successfully.");
                succeedButton(buttonDeleteTeacher);
            } else {
                displayInfoMessage(true, "Could not delete Teacher.");
                errorButton(buttonDeleteTeacher);
            }
        });

        this.buttonDeleteGroup.setOnAction(event -> {
            if (!this.groupComboBox.getSelectionModel().isEmpty()) {
                this.school.getGroups().remove(this.groupComboBox.getSelectionModel().getSelectedIndex());
                this.groupOptions.remove(this.groupComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Group deleted successfully.");
                succeedButton(buttonDeleteGroup);
            } else {
                displayInfoMessage(true, "Could not delete group.");
                errorButton(buttonDeleteGroup);
            }
        });

        this.buttonDeleteRoom.setOnAction(event -> {
            if (!this.roomComboBox.getSelectionModel().isEmpty()) {
                this.school.getRooms().remove(this.roomComboBox.getSelectionModel().getSelectedIndex());
                this.roomOptions.remove(this.roomComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Room deleted successfully.");
                succeedButton(buttonDeleteRoom);
            } else {
                displayInfoMessage(true, "Could not delete Room.");
                errorButton(buttonDeleteRoom);
            }
        });

        this.buttonDeleteSubject.setOnAction(event -> {
            if (!this.subjectComboBox.getSelectionModel().isEmpty()) {
                this.school.getSubjects().remove(this.subjectComboBox.getSelectionModel().getSelectedIndex());
                this.subjectOptions.remove(this.subjectComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Subject deleted successfully.");
                succeedButton(buttonDeleteSubject);
            } else {
                displayInfoMessage(true, "Could not delete Subject.");
                errorButton(buttonDeleteSubject);
            }
        });
    }

    private void addScheduleFailed(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.CLOSE);
        setAlertPos(alert);
        alert.setHeaderText("");
        alert.setTitle("Add failed");
        alert.showAndWait();
    }

    private void addScheduleSucceed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your schedule has been added", ButtonType.CLOSE);
        setAlertPos(alert);
        alert.setHeaderText("");
        alert.setTitle("Add succeeded");
        alert.showAndWait();
    }

    private void showSuccessfullyCleared() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your schedule has been cleared", ButtonType.CLOSE);
        setAlertPos(alert);
        alert.setHeaderText("");
        alert.setTitle("Remove successful");
        alert.showAndWait();
    }

    private void setAlertPos(Alert alert) {
        alert.setX(stage.getX() + 5);
        alert.setY(stage.getY() + stage.getHeight() - 137);
    }

    private void styleButton(Button button) {
        button.setEffect(null);
        button.setBackground((new Background(new BackgroundFill(ApplicationSettings.themeColor, CornerRadii.EMPTY, Insets.EMPTY))));
    }

    private void errorButton(Button button) {
        applyEffects(button);
        button.setBackground(new Background(new BackgroundFill(Color.INDIANRED, new CornerRadii(5.), Insets.EMPTY)));
    }

    private void succeedButton(Button button) {
        applyEffects(button);
        button.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(5.), Insets.EMPTY)));
    }

    private void applyEffects(Button button) {
        button.setEffect(new DropShadow());
    }

    private boolean isDuplicateSchedule(School school, Schedule schedule) {
        boolean duplicate = false;
        for (Schedule s : school.getSchedules()) {
            if (schedule.getGroup().getName().equals(s.getGroup().getName())
                    && schedule.getGroup().getName().equals(s.getGroup().getName())
                    && schedule.getTeacher().getName().equals(s.getTeacher().getName())
                    && schedule.getSubject().getName().equals(s.getSubject().getName())
                    && schedule.getTime().toString().equals(s.getTime().toString())
            )
                duplicate = true;
        }
        return duplicate;
    }

    private void displayInfoMessage(Boolean color, String text) {
        if (color)
            this.labelInfo.setTextFill(Color.web("#FF0000")); //Red
        else
            this.labelInfo.setTextFill(Color.web("#228B22")); //Green
        this.labelInfo.setText(text);
    }

    private boolean isAvailableThisTime(Teacher teacher, Room room, Hour hour, Group group) {
        return (!(teacher.getHours().contains(hour) || room.getHours().contains(hour) || group.getHours().contains(hour)));
    }
}