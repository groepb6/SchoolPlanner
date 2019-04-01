package gui.components.frames;

import data.readwrite.DataReader;
import data.schedulerelated.Schedule;
import gui.assistclasses.Plan;
import gui.components.window.Sizeable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dustin Hendriks
 * @since 09-02-2019
 * <p>
 * The tableView class generates multiple columns in which data can be displayed. Data such as time, group, location, teacher and subject can be shown to generate a basic schedule.
 */

public class TableView extends Sizeable {
    private javafx.scene.control.TableView<Plan> tableView = new javafx.scene.control.TableView<>();
    private List<TableColumn> tableColumns = new ArrayList<>();
    private ObservableList<Plan> plan = FXCollections.observableArrayList();

    /**
     * The constructor loads in all information and places all items in the table view.
     *
     * @param stage A Stage object is needed to set proportions to the correct amount.
     */

    public TableView(Stage stage) {
        super.setProportions(0, 2560, 0, 1080, 1005, Sizeable.ignore, stage);
        buildRows();
        getData();
        plan.addAll(getData());
        tableView.setItems(plan);
    }

    /**
     * To load in data at the table view multiple columns should be creates. The text given to them (TableColumn, not to be confused with the so called "CellValueFactory", which is something completely different) represents the title of the column.
     * These tableColumns can read their data directly from an object as long as it uses simple string property's.
     * For all columns a specific width can be configured. No for-each is currently in use, since you might want to resize columns individually, instead of all at once.
     * Now if all this is setup, do not forget to add the columns to the table view, which is done in the last line of the method.
     */

    private void buildRows() {
        TableColumn timeCol = new TableColumn("Time");
        TableColumn groupCol = new TableColumn("Group");
        TableColumn roomCol = new TableColumn("Room");
        TableColumn teacherCol = new TableColumn("Teacher");
        TableColumn subjectCol = new TableColumn("Subject");

        tableColumns.add(timeCol);
        tableColumns.add(groupCol);
        tableColumns.add(roomCol);
        tableColumns.add(teacherCol);
        tableColumns.add(subjectCol);

        timeCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("time"));
        groupCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("group"));
        roomCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("location"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("teacher"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("subject"));

        tableView.setEditable(true);

        timeCol.setPrefWidth(200);
        groupCol.setPrefWidth(200);
        roomCol.setPrefWidth(200);
        teacherCol.setPrefWidth(200);
        subjectCol.setPrefWidth(200);

        tableView.getColumns().setAll(groupCol, subjectCol, teacherCol, roomCol, timeCol );
    }

    /**
     * The method getData() returns a received Plan ArrayList to an observable list (notice that every Schedule element is casted to a plan element to create the simple string property (which is not serializable), this way the table can read the data and place it in the corresponding columns.
     *
     * @return The return method returns an observable list, which enables reading by a TableView object in this case.
     */

    private ObservableList getData() {
        ObservableList data = FXCollections.observableList(retrieveDataList());
        return data;
    }

    /**
     * This method returns the final TableView object, which can later be placed in a JavaFX pathfinding object, such as a GridPane, BorderPane or any other layoutPane (Slightly irrelevant, but we are using the BorderPane here).
     *
     * @return Return the TableView object which can be placed in a layout pane later.
     */

    public javafx.scene.control.TableView getTableView() {
        return tableView;
    }

    /**
     * This method reads all available Schedules from a .txt file (using DataReader class) and casts them to a Plan.
     *
     * @return Return a List object filled with Plans.
     */

    private List retrieveDataList() {
        List list = new ArrayList<Plan>();
        try {
            ArrayList<Schedule> schedules = DataReader.readSchool().getSchedules();
            for (Schedule schedule : schedules) {
                list.add(schedule.getPlan());
            }
        } catch (Exception e) {
        }
        return list;
    }

}

