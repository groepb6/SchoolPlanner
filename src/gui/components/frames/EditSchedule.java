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
import gui.assistclasses.Plan;
import gui.components.window.Sizeable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * @author Dustin Hendriks
 * @author Wout Stevens
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
    private int buttonWidth;




    public EditSchedule(Stage stage) {
        this.borderPane = new BorderPane();
        this.school = DataReader.readSchool();
        //this.school = new School("");
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
        this.buttonClearAll = new Button("Clear all");


        this.buttonDeleteGroup = new Button("Delete Group");
        this.buttonDeleteRoom = new Button("Delete Room");
        this.buttonDeleteSubject = new Button("Delete Subject");
        this.buttonDeleteTeacher = new Button("Delete Teacher");

        EnumSet.allOf(Hour.class).forEach(Hour -> this.timeOptions.add(Hour.getTime()));
        this.timeComboBox.setItems(this.timeOptions);
        this.timeComboBox.setMinWidth(150);

        this.groupOptions.addAll();
        for (Group g : this.school.getGroups()) {
            this.groupOptions.add(g.getName());
        }
        this.groupComboBox.setItems(this.groupOptions);
        this.groupComboBox.setMinWidth(150);

        //this.teacherOptions.addAll("Johan", "Etienne", "Jan", "Hans");
        for (Person t : this.school.getTeachers()) {
            this.teacherOptions.add(t.getName());
        }
        this.teacherComboBox.setItems(this.teacherOptions);
        this.teacherComboBox.setMinWidth(150);

        //this.roomOptions.addAll("LA110", "Kantine", "LD127", "HA512");
        for (Room r : this.school.getRooms()) {
            this.roomOptions.add(r.getName());
        }
        this.roomComboBox.setItems(this.roomOptions);
        this.roomComboBox.setMinWidth(150);

        //this.subjectOptions.addAll("Maths", "Physics");
        for (Subject s : this.school.getSubjects()) {
            this.subjectOptions.add(s.getName());
        }
        this.subjectComboBox.setItems(this.subjectOptions);
        this.subjectComboBox.setMinWidth(150);



        this.hBox1.setSpacing(5);
        this.hBox2.setSpacing(5);
        this.hBox3.setSpacing(5);
        this.hBox4.setSpacing(5);
        this.hBox5.setSpacing(5);
        this.hBox6.setSpacing(5);

        this.vBox.setSpacing(5);

        this.hBox1.getChildren().addAll(this.labelGroup, this.groupComboBox, this.tfGroup, this.buttonAddGroup,this.buttonDeleteGroup);
        this.hBox2.getChildren().addAll(this.labelSubject, this.subjectComboBox, this.tfSubject, this.buttonAddSubject,this.buttonDeleteSubject);
        this.hBox3.getChildren().addAll(this.labelTeacher, this.teacherComboBox, this.tfTeacher, this.buttonAddTeacher,this.buttonDeleteTeacher);
        this.hBox4.getChildren().addAll(this.labelRoom, this.roomComboBox, this.tfRoom, this.buttonAddRoom,this.buttonDeleteRoom);
        this.hBox5.getChildren().addAll(this.labelTime, this.timeComboBox);
        this.hBox6.getChildren().addAll(this.buttonAddSchedule,this.buttonClearAll);

        this.buttonWidth = 150;

        this.labelGroup.setMinWidth(buttonWidth/2);
        this.labelTime.setMinWidth(buttonWidth/2);
        this.labelTeacher.setMinWidth(buttonWidth/2);
        this.labelSubject.setMinWidth(buttonWidth/2);
        this.labelRoom.setMinWidth(buttonWidth/2);

        this.buttonAddGroup.setMinWidth(buttonWidth);
        this.buttonAddRoom.setMinWidth(buttonWidth);
        this.buttonAddTeacher.setMinWidth(buttonWidth);
        this.buttonAddSubject.setMinWidth(buttonWidth);

        this.buttonDeleteTeacher.setMinWidth(buttonWidth);
        this.buttonDeleteSubject.setMinWidth(buttonWidth);
        this.buttonDeleteRoom.setMinWidth(buttonWidth);
        this.buttonDeleteGroup.setMinWidth(buttonWidth);

        this.buttonAddSchedule.setOnAction(event -> {
            if (!groupComboBox.getSelectionModel().isEmpty() && !roomComboBox.getSelectionModel().isEmpty() && !timeComboBox.getSelectionModel().isEmpty() && !teacherComboBox.getSelectionModel().isEmpty() && !subjectComboBox.getSelectionModel().isEmpty()) {
                boolean isAvailableThisTime = true;

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
                isAvailableThisTime = isAvailableThisTime(teacher,room,this.getHour(this.timeComboBox.getValue().toString()));
                room.getHours().add(this.getHour(this.timeComboBox.getValue().toString()));
                teacher.getHours().add(this.getHour(this.timeComboBox.getValue().toString()));

                Schedule schedule = new Schedule(
                        this.getHour(this.timeComboBox.getValue().toString()),
                        group,
                        room,
                        teacher,
                        subject
                );

                System.out.println("" + this.getHour(this.timeComboBox.getValue().toString()) + group + room + teacher + subject);



                if (!isDuplicateSchedule(this.school,schedule) && isAvailableThisTime) {
                    this.school.addSchedule(schedule);
                    DataWriter.writeSchool(school);
                    school = DataReader.readSchool();
                }
            }
        });

        this.buttonClearAll.setOnAction(event -> {
            this.school = new School("School");
            DataWriter.writeSchool(school);
        });

        this.buttonAddGroup.setOnAction(event -> {
            if (!this.groupOptions.contains(this.tfGroup.getText())) {
                this.school.getGroups().add(new Group(this.tfGroup.getText()));
                this.groupOptions.add(this.tfGroup.getText());
                this.tfGroup.clear();
                DataWriter.writeSchool(school);
            }
        });

        this.buttonAddTeacher.setOnAction(event -> {
            if (!this.teacherOptions.contains(this.tfTeacher.getText())) {
                this.school.getTeachers().add(new Teacher(this.tfTeacher.getText()));
                this.teacherOptions.add(this.tfTeacher.getText());
                this.tfTeacher.clear();
                DataWriter.writeSchool(school);
            }
        });

        this.buttonAddSubject.setOnAction(event -> {
            if (!this.subjectOptions.contains(this.tfSubject.getText())) {
                this.school.getSubjects().add(new Subject(this.tfSubject.getText()));
                this.subjectOptions.add(this.tfSubject.getText());
                this.tfSubject.clear();
                DataWriter.writeSchool(school);
            }
        });

        this.buttonAddRoom.setOnAction(event -> {
            if (!this.roomOptions.contains(this.tfRoom.getText())) {
                this.school.getRooms().add(new Classroom(this.tfRoom.getText()));
                this.roomOptions.add(this.tfRoom.getText());
                this.tfRoom.clear();
                DataWriter.writeSchool(school);
            }
        });

        this.buttonDeleteTeacher.setOnAction(event -> {
            if (!this.teacherComboBox.getSelectionModel().isEmpty()) {
                System.out.println(teacherComboBox.getSelectionModel().getSelectedIndex());
                this.school.getTeachers().remove(this.teacherComboBox.getSelectionModel().getSelectedIndex());
                this.teacherOptions.remove(this.teacherComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
            }
        });

        this.buttonDeleteGroup.setOnAction(event -> {
            if (!this.groupComboBox.getSelectionModel().isEmpty()) {
                System.out.println(groupComboBox.getSelectionModel().getSelectedIndex());
                this.school.getGroups().remove(this.groupComboBox.getSelectionModel().getSelectedIndex());
                this.groupOptions.remove(this.groupComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
            }
        });

        this.buttonDeleteRoom.setOnAction(event -> {
            if (!this.roomComboBox.getSelectionModel().isEmpty()) {
                System.out.println(roomComboBox.getSelectionModel().getSelectedIndex());
                this.school.getRooms().remove(this.roomComboBox.getSelectionModel().getSelectedIndex());
                this.roomOptions.remove(this.roomComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
            }
        });

        this.buttonDeleteSubject.setOnAction(event -> {
            if (!this.subjectComboBox.getSelectionModel().isEmpty()) {
                System.out.println(subjectComboBox.getSelectionModel().getSelectedIndex());
                this.school.getSubjects().remove(this.subjectComboBox.getSelectionModel().getSelectedIndex());
                this.subjectOptions.remove(this.subjectComboBox.getSelectionModel().getSelectedIndex());
                DataWriter.writeSchool(school);
            }
        });



        this.vBox.getChildren().addAll(
                this.hBox1,
                this.hBox2,
                this.hBox3,
                this.hBox4,
                this.hBox5,
                this.hBox6
        );

        this.borderPane.setTop(this.vBox);
        this.borderPane.setPadding(new javafx.geometry.Insets(10, 0, 0, 10));

        super.setProportions(0, 2560, 0, 1080, 1100, 500, stage);
    }

    public BorderPane getEditSchedule() {
        return this.borderPane;
    }

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

    public boolean isDuplicateSchedule(School schoolInput, Schedule scheduleInput) {
        School school = schoolInput;
        Schedule schedule = scheduleInput;
        boolean duplicate = false;

        for (Schedule s : school.getSchedules()) {
            if (schedule.getGroup().getName().equals(s.getGroup().getName())
                    && schedule.getGroup().getName().equals(s.getGroup().getName())
                    && schedule.getTeacher().getName().equals(s.getTeacher().getName())
                    && schedule.getSubject().getName().equals(s.getSubject().getName())
                    && schedule.getTime().toString().equals(s.getTime().toString())
            ) {
                duplicate = true;
            }
        }

        return duplicate;
    }

    public boolean isAvailableThisTime(Teacher teacher, Room room, Hour hour) {
        if (teacher.getHours().contains(hour) || room.getHours().contains(hour)) {
            return false;
        } else {
            return true;
        }

    }




/**
 * If the addSchedule method is called a check will need to be performed on all fields, since you wouldn't want invalid data to be written to the Plan save file.
 * <p>
 * If all previous checks are performed there is one check remaining, we do not want any double data in our plane file. This is why the boolean canAdd is set to false if any double plan is found.
 * <p>
 * If all previous checks are performed there is one check remaining, we do not want any double data in our plane file. This is why the boolean canAdd is set to false if any double plan is found.
 */

//    private void setActions() {
//        addSchedule.setOnMouseClicked(event -> {
//            boolean everythingCorrect = true;
//            if (!checkGroup()) everythingCorrect = false;
//            if (!checkBeginTime()) everythingCorrect = false;
//            if (!checkEndTime()) everythingCorrect = false;
//            if (!checkRoom()) everythingCorrect = false;
//            if (!checkSubject()) everythingCorrect = false;
//            if (!checkTeacher()) everythingCorrect = false;
//            if (everythingCorrect)
//                addSchedule();
//        });
//


//        groupField.setOnMouseClicked(event -> groupField.setText(""));
//        groupField.setOnMouseDragged(event -> groupField.setText(""));
//        timeFieldBegin.setOnMouseClicked(event -> timeBeginMessage.setText(""));
//        timeFieldBegin.setOnMouseDragged(event -> timeBeginMessage.setText(""));
//        timeFieldEnd.setOnMouseClicked(event -> timeEndMessage.setText(""));
//        timeFieldEnd.setOnMouseDragged(event -> timeEndMessage.setText(""));
//        RoomField.setOnMouseClicked(event -> RoomMessage.setText(""));
//        RoomField.setOnMouseDragged(event -> RoomMessage.setText(""));
//        subjectMessage.setOnMouseClicked(event -> subjectMessage.setText(""));
//        subjectMessage.setOnMouseDragged(event -> subjectMessage.setText(""));
//        teacherMessage.setOnMouseClicked(event -> teacherMessage.setText(""));
//        teacherMessage.setOnMouseDragged(event -> teacherMessage.setText(""));
//    }

/**
 * If all previous checks are performed there is one check remaining, we do not want any double data in our plane file. This is why the boolean canAdd is set to false if any double plan is found.
 */

//    private void addSchedule() {
//        ArrayList<Schedule> scheduleArrayList;
//        try {
//            Schedule schedule = new Schedule(new Plan(
//                    this.timeComboBox.getValue().toString(),
//                    this.groupComboBox.getValue().toString(),
//                    this.roomComboBox.getValue().toString(),
//                    this.teacherComboBox.getValue().toString(),
//                    this.subjectComboBox.getValue().toString()
//            ));
//            scheduleArrayList = (ArrayList<Schedule>) DataReader.readScheduleList();
//            boolean canAdd = true;
//            for (int i = 0; i < scheduleArrayList.size(); i++) {
//                if (scheduleArrayList.get(i).getPlan().isEqualTo(schedule.getPlan()))
//                    canAdd = false;
//            }
//            if (canAdd) {
//                scheduleArrayList.add(schedule);
//                DataWriter.writeScheduleList(scheduleArrayList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Check if the input is correct in the checkGroup field.
     * @return Boolean which defines whether the input was correct or not.
     */

//    private boolean checkGroup() {
//        if (this.groupComboBox.getValue().toString().length() >= 1)
//            return true;
//        groupMessage.setText("Group is empty");
//        return false;
//
//    }

    /**
     * Check if the input is correct in the CheckBeginTime field.
     * @return Boolean which defines whether the input was correct or not.
     */

//    private boolean checkBeginTime() {
//        if (checkTime(timeFieldBegin.getText()))
//            return true;
//        timeBeginMessage.setText("Enter time between 08:00 and 20:00");
//        return false;
//    }

    /**
     * Check if the input is correct in the CheckEndTime field.
     * @return Boolean which defines whether the input was correct or not.
     */

//    private boolean checkEndTime() {
//        if (checkTime(timeFieldEnd.getText()))
//            return true;
//        timeEndMessage.setText("Enter time between 08:00 and 20:00");
//        return false;
//    }

    /**
     * Check if the input format of a time is in the format of ab:xy, if not false is returned.
     *
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
     * Check if the Room input is valid
     * @return If the Room input is valid (=not null) return true, else return false.
     */

//    private boolean checkRoom() {
//        if (RoomField.getText().length() > 0)
//            return true;
//        RoomMessage.setText("Room is empty");
//        return false;
//    }

    /**
     * Check if the subject input is valid
     * @return If the subject input is valid (=not null) return true, else return false.
     */

//    private boolean checkSubject() {
//        if (subjectField.getText().length() > 0)
//            return true;
//        subjectMessage.setText("Subject is empty");
//        return false;
//    }

    /**
     * Check if the teacher input is valid
     * @return If the teacher input is valid (=not null) return true, else return false.
     */
//
//    private boolean checkTeacher() {
//        if (teacherField.getText().length() > 0)
//            return true;
//        teacherMessage.setText("Teacher is empty");
//        return false;
//    }

    /**
     * Retrieve the borderPane with all components on it.
     * @return Receive the borderPane with all components on it.
     */

}