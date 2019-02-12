package gui.components.frames;

import gui.Plan;
import gui.components.window.Sizeable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TableView extends Sizeable {
    private javafx.scene.control.TableView<Plan> tableView = new javafx.scene.control.TableView<>();
    private List<TableColumn> tableColumns = new ArrayList<>();
    private ObservableList<Plan> plan = FXCollections.observableArrayList();

    public TableView(Stage stage) {
        super.setProportions(0,2560, 0, 1080, 1005, Sizeable.ignore, stage);
        buildRows();
        getData();
        plan.addAll(getData());
        tableView.setItems(plan);
    }

    private void buildRows() {
        TableColumn timeCol = new TableColumn("Time");
        TableColumn groupCol = new TableColumn("Group");
        TableColumn locationCol = new TableColumn("Location");
        TableColumn teacherCol = new TableColumn("Teacher");
        TableColumn subjectCol = new TableColumn("Subject");

        tableColumns.add(timeCol);
        tableColumns.add(groupCol);
        tableColumns.add(locationCol);
        tableColumns.add(teacherCol);
        tableColumns.add(subjectCol);

        timeCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("time"));
        groupCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("group"));
        locationCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("location"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("teacher"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<Plan, String>("subject"));

        tableView.setEditable(true);

        timeCol.setPrefWidth(200);
        groupCol.setPrefWidth(200);
        locationCol.setPrefWidth(200);
        teacherCol.setPrefWidth(200);
        subjectCol.setPrefWidth(200);


        tableView.getColumns().setAll(groupCol, timeCol, locationCol, subjectCol, teacherCol);
    }

    private ObservableList getData() {
        ObservableList data = FXCollections.observableList(getTestData());
        return data;
    }

    public javafx.scene.control.TableView getTableView() {
        return tableView;
    }

    private List getTestData() {
        List list;
        list = Plan.getTestData();
        return list;
    }

}

