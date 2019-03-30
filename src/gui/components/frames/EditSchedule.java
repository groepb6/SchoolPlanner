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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;

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
    private Button buttonLoadPreset;
    private Button buttonSavePreset;
    private int buttonWidth;
    private Stage stage;

    public EditSchedule(Stage stage) {
        this.school = DataReader.readSchool();
        setLayout();
        setComboBox();
        setActions();
        super.setProportions(0, 2560, 0, 1080, 1100, 500, stage);
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
        this.buttonLoadPreset = new Button("Load preset");
        this.buttonSavePreset = new Button("Save preset");

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
        this.hBox6.getChildren().addAll(this.buttonAddSchedule, this.buttonClearAll, this.buttonLoadPreset, this.buttonSavePreset);


        /**
         * Layout-Settings
         */
        this.buttonWidth = 150;

        this.labelGroup.setMinWidth(buttonWidth / 2);
        this.labelTime.setMinWidth(buttonWidth / 2);
        this.labelTeacher.setMinWidth(buttonWidth / 2);
        this.labelSubject.setMinWidth(buttonWidth / 2);
        this.labelRoom.setMinWidth(buttonWidth / 2);

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
        /**
         *This creates a small margin around everything.
         */
        this.borderPane.setPadding(new javafx.geometry.Insets(10, 0, 0, 10));
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
        }
        this.groupComboBox.setItems(this.groupOptions);
        this.groupComboBox.setMinWidth(150);

        for (Person t : this.school.getTeachers()) {
            this.teacherOptions.add(t.getName());
        }
        this.teacherComboBox.setItems(this.teacherOptions);
        this.teacherComboBox.setMinWidth(150);

        for (Room r : this.school.getRooms()) {
            this.roomOptions.add(r.getName());
        }
        this.roomComboBox.setItems(this.roomOptions);
        this.roomComboBox.setMinWidth(150);


        for (Subject s : this.school.getSubjects()) {
            this.subjectOptions.add(s.getName());
        }
        this.subjectComboBox.setItems(this.subjectOptions);
        this.subjectComboBox.setMinWidth(150);
    }

    /**
     * setActions sets all the actions of the buttons in the GUI
     */

    private void setActions() {
        this.buttonAddSchedule.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add this schedule?", ButtonType.YES, ButtonType.CANCEL);
            alert.setHeaderText("");
            alert.setTitle("Confirm");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {

                if (!groupComboBox.getSelectionModel().isEmpty() &&
                        !roomComboBox.getSelectionModel().isEmpty() &&
                        !timeComboBox.getSelectionModel().isEmpty() &&
                        !teacherComboBox.getSelectionModel().isEmpty() &&
                        !subjectComboBox.getSelectionModel().isEmpty()) {                                                   // This checks if none of the comboboxes are empty

                    /**
                     * All the comboboxes get filled from the school object
                     */

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

                    /**
                     * Boolean isAvailableThisTime tells the program whether the schedule that is about to be created conflicts with any existing hours planned by the Room, Teacher and Group.
                     */

                    boolean isAvailableThisTime = true;
                    isAvailableThisTime = isAvailableThisTime(teacher, room, this.getHour(this.timeComboBox.getValue().toString()), group);

                    Schedule schedule = new Schedule(
                            this.getHour(this.timeComboBox.getValue().toString()),
                            group,
                            room,
                            teacher,
                            subject
                    );

                    /**
                     * This if-statement implements the schedule into the schoolobject, and writes it to the file.
                     * It only does this when isAvailable is True and isDuplicateSchedule is false
                     */

                    if (!isDuplicateSchedule(this.school, schedule) && isAvailableThisTime) {
                        room.getHours().add(this.getHour(this.timeComboBox.getValue().toString()));
                        teacher.getHours().add(this.getHour(this.timeComboBox.getValue().toString()));
                        group.getHours().add(this.getHour(this.timeComboBox.getValue().toString()));

                        this.school.addSchedule(schedule);
                        DataWriter.writeSchool(school);
                        school = DataReader.readSchool();

                        displayInfoMessage(false, "Schedule added succesfully.");
                    } else {
                        displayInfoMessage(true, "Failed to add new schedule.");
                    }
                }
            }
        });

        /**
         * This buttonAction clears the complete data class.
         */
        this.buttonClearAll.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to erase all data?", ButtonType.YES, ButtonType.CANCEL);
            alert.setHeaderText("");
            alert.setTitle("Confirm");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                this.school.getGroups().clear();
                this.school.getSubjects().clear();
                this.school.getTeachers().clear();
                this.school.getSchedules().clear();
                DataWriter.writeSchool(school);
                this.school = DataReader.readSchool();
                displayInfoMessage(true, "All data is cleared.");
            }
        });

        /**
         * This button checks if a presetfile exists and if it does, it loads it in.
         */
        this.buttonLoadPreset.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to load the preset?", ButtonType.YES, ButtonType.CANCEL);
            alert.setHeaderText("");
            alert.setTitle("Confirm");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                File presetFile = new File("saves/school/preset.txt");
                if (presetFile.exists()) {
                    this.school = DataReader.readPreset();
                    DataWriter.writeSchool(this.school);
                    displayInfoMessage(false, "Preset loaded.");
                } else {
                    displayInfoMessage(true, "Preset not found.");
                }
            }
        });

        this.buttonSavePreset.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save the preset?", ButtonType.YES, ButtonType.CANCEL);
            alert.setHeaderText("");
            alert.setTitle("Confirm");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                DataWriter.writePreset(this.school);
                displayInfoMessage(false, "Preset saved.");
            }
        });

        /**
         * All following buttons check if the group that is about to be added already exists, and then adds it to the list if it doesnt.
         */
        this.buttonAddGroup.setOnAction(event -> {
            if (!this.groupOptions.contains(this.tfGroup.getText()) && !this.tfGroup.getText().isEmpty()) {
                this.school.getGroups().add(new Group(this.tfGroup.getText()));
                this.groupOptions.add(this.tfGroup.getText());
                this.tfGroup.clear();
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Group added succesfully.");
            } else {
                displayInfoMessage(true, "Could not add Group.");
            }
        });

        this.buttonAddTeacher.setOnAction(event -> {
            if (!this.teacherOptions.contains(this.tfTeacher.getText()) && !this.tfTeacher.getText().isEmpty()) {
                this.school.getTeachers().add(new Teacher(this.tfTeacher.getText()));
                this.teacherOptions.add(this.tfTeacher.getText());
                this.tfTeacher.clear();
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Teacher added succesfully.");
            } else {
                displayInfoMessage(true, "Could not add Teacher.");
            }
        });

        this.buttonAddSubject.setOnAction(event -> {
            if (!this.subjectOptions.contains(this.tfSubject.getText()) && !this.tfSubject.getText().isEmpty()) {
                this.school.getSubjects().add(new Subject(this.tfSubject.getText()));
                this.subjectOptions.add(this.tfSubject.getText());
                this.tfSubject.clear();
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Subject added succesfully.");
            } else {
                displayInfoMessage(true, "Could not add Subject.");
            }
        });

        this.buttonAddRoom.setOnAction(event -> {
            if (!this.roomOptions.contains(this.tfRoom.getText()) && !this.tfRoom.getText().isEmpty()) {
                this.school.getRooms().add(new Classroom(this.tfRoom.getText()));
                this.roomOptions.add(this.tfRoom.getText());
                this.tfRoom.clear();
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Room added succesfully.");
            } else {
                displayInfoMessage(true, "Could not add Room.");
            }
        });

        /**
         * The following buttons Delete the Group, Teacher or Subject, by checking the Schoolobject for the object with the same name and deleting it.
         */

        this.buttonDeleteTeacher.setOnAction(event -> {
            if (!this.teacherComboBox.getSelectionModel().isEmpty()) {
                this.school.getTeachers().remove(this.teacherComboBox.getSelectionModel().getSelectedIndex());
                this.teacherOptions.remove(this.teacherComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Teacher deleted succesfully.");
            } else {
                displayInfoMessage(true, "Could not delete Teacher.");
            }
        });

        this.buttonDeleteGroup.setOnAction(event -> {
            if (!this.groupComboBox.getSelectionModel().isEmpty()) {
                this.school.getGroups().remove(this.groupComboBox.getSelectionModel().getSelectedIndex());
                this.groupOptions.remove(this.groupComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Group deleted succesfully.");
            } else {
                displayInfoMessage(true, "Could not delete group.");
            }
        });

        this.buttonDeleteRoom.setOnAction(event -> {
            if (!this.roomComboBox.getSelectionModel().isEmpty()) {
                this.school.getRooms().remove(this.roomComboBox.getSelectionModel().getSelectedIndex());
                this.roomOptions.remove(this.roomComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Room deleted succesfully.");
            } else {
                displayInfoMessage(true, "Could not delete Room.");
            }
        });

        this.buttonDeleteSubject.setOnAction(event -> {
            if (!this.subjectComboBox.getSelectionModel().isEmpty()) {
                this.school.getSubjects().remove(this.subjectComboBox.getSelectionModel().getSelectedIndex());
                this.subjectOptions.remove(this.subjectComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
                displayInfoMessage(false, "Subject deleted succesfully.");
            } else {
                displayInfoMessage(true, "Could not delete Subject.");
            }
        });
    }

    /**
     * This method returns the right Enum-value, depending on the string you enter
     *
     * @param time
     * @return Hour
     */

    public Hour getHour(String time) {
        ArrayList<Hour> hours = new ArrayList<>();
        EnumSet.allOf(Hour.class).forEach(Hour -> hours.add(Hour));
        for (Hour h : hours) {
            if (h.getTime().equals(time)) {
                return h;
            }
        }
        return null;
    }


    /**
     * This method checks if the given Schedule is already present in the school object it is given.
     *
     * @param schoolInput
     * @param scheduleInput
     * @return boolean
     */

    public boolean isDuplicateSchedule(School schoolInput, Schedule scheduleInput) {
        School school = schoolInput;                                                                                    // Catch the school
        Schedule schedule = scheduleInput;                                                                              // Catch the schedule
        boolean duplicate = false;                                                                                      // The base stat is that the schedule is not a duplicate.

        for (Schedule s : school.getSchedules()) {                                                                      //This loop checks every schedule in school and compares them with the given schedule.
            if (schedule.getGroup().getName().equals(s.getGroup().getName())
                    && schedule.getGroup().getName().equals(s.getGroup().getName())
                    && schedule.getTeacher().getName().equals(s.getTeacher().getName())
                    && schedule.getSubject().getName().equals(s.getSubject().getName())
                    && schedule.getTime().toString().equals(s.getTime().toString())
            ) {
                duplicate = true;                                                                                       // If there's a duplicate, update the boolean
            }
        }
        return duplicate;
    }


    /**
     * This method checks if the given hour is already booked in the given Room, Teacher and Group.
     *
     * @param teacher
     * @param room
     * @param hour
     * @param group
     * @return
     */
    public boolean isAvailableThisTime(Teacher teacher, Room room, Hour hour, Group group) {
        if (teacher.getHours().contains(hour) || room.getHours().contains(hour) || group.getHours().contains(hour)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method is a helper method to change the text of the info label to the given text and change the color depending on the boolean value.
     *
     * @param color
     * @param text
     */
    private void displayInfoMessage(Boolean color, String text) {
        if (color) {
            this.labelInfo.setTextFill(Color.web("#FF0000")); //Red
        } else {
            this.labelInfo.setTextFill(Color.web("#228B22")); //Green
        }
        this.labelInfo.setText(text);
    }

    public BorderPane getEditSchedule() {
        return this.borderPane;
    }
}