package gui.components.frames;

import gui.Image;
import gui.Plan;
import gui.components.window.Sizeable;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.*;
import java.util.ArrayList;

public class FancyView extends Sizeable {
    private Stage stage;
    private BorderPane borderPane = new BorderPane();
    private HBox searchGroupBar = new HBox();
    private HBox selectGroupBar = new HBox();
    private HBox graphGroupBar = new HBox();
    private HBox graphDrawBar = new HBox();
    private VBox topElements = new VBox();
    private TextField textField = new TextField("Search group");
    private Button searchGroupButton = new Button("Search");
    private Button submitGroupButton = new Button("Submit");
    private Button clearViewButton = new Button("Clear last plan");
    private Image errorMessageSearch = new Image("errormessage", "functionimages", ".png", "error", "No results found");
    private Image successMessageSearch = new Image("successfulmessage", "functionimages", ".png", "successful", "Search successful");
    private Image errorMessageSubmit = new Image("errorMessage", "functionimages", ".png", "error", "No group selected");
    private Image successMessageSubmit = new Image("Successfulmessage", "functionimages", ".png", "error", "Submit successful");
    private boolean errorSearchImagePlaced = false;
    private boolean successSearchImagePlaced = false;
    private boolean errorSubmitImagePlaced = false;
    private boolean successSubmitImagePlaced = false;
    private ComboBox searchResults = new ComboBox();

    public FancyView(Stage stage) {
        super.setProportions(500, 2560, 0, 1080, Sizeable.ignore, 500, stage);
        buildSearchSelectGraphGroupBar();
        topElements.setSpacing(5);
        searchResults.setPromptText("Select group");
        searchGroupBar.setSpacing(5);
        selectGroupBar.setSpacing(5);
        searchResults.setMinWidth(200);
        searchResults.setMaxWidth(200);
        textField.setMinWidth(200);
        textField.setMaxWidth(200);
        submitGroupButton.setMinWidth(100);
        submitGroupButton.setMaxWidth(100);
        searchGroupButton.setMinWidth(100);
        searchGroupButton.setMaxWidth(100);
        searchGroupBar.setMinWidth(500);
    }

    private void buildSearchSelectGraphGroupBar() {
        searchGroupBar.getChildren().addAll(textField, searchGroupButton);
        selectGroupBar.getChildren().addAll(searchResults, submitGroupButton);
        graphGroupBar.getChildren().addAll(clearViewButton);
        graphDrawBar.getChildren().addAll(VirtualizedView.drawTimePanel());
        topElements.getChildren().addAll(searchGroupBar, selectGroupBar, graphGroupBar, graphDrawBar);
        borderPane.setTop(topElements);

        setSearchButtonActionOnClick();
        setSubmitButtonActionOnClick();
        setClearViewButtonActionOnClick();
    }

    //=======================BEGIN OF CODE FOR ALL ACTION ON CLICK EVENTS================================

    private void setSearchButtonActionOnClick() {
        searchGroupButton.setOnMouseClicked(event -> {
            String input = textField.getText();
            ArrayList<Plan> filteredData = search(input);
            if (filteredData.size() >= 1) {
                addSearchSuccessfulMessage();
            } else addSearchErrorMessage();
        });
    }

    private void setSubmitButtonActionOnClick() {
        submitGroupButton.setOnMouseClicked(event -> {
            if (searchResults.getSelectionModel().isEmpty())
                addSubmitErrorMessage();
            else {
                addSubmitSuccessfulMessage();
                Plan plan = (Plan) searchResults.getSelectionModel().getSelectedItem();
                graphDrawBar.getChildren().add(VirtualizedView.schedule(testDataToBeginTime(plan.getTime()), testDataToEndTime(plan.getTime()), plan.getSubject()));
            }
        });
    }

    private void setClearViewButtonActionOnClick() {
        clearViewButton.setOnMouseClicked(event -> {
            if (graphDrawBar.getChildren().size() > 1)
                graphDrawBar.getChildren().remove(graphDrawBar.getChildren().size() - 1);
        });
    }

    //=========================END OF CODE FOR ALL ACTION ON CLICK EVENTS==================================


    private ArrayList<Plan> search(String search) {
        ArrayList<Plan> data = Plan.getTestData();
        ArrayList<Plan> filteredData = new ArrayList<>();
        boolean strictSearch = false;

        if (search.contains("[strict]")) {
            fixStringForStrictSearching(search);
            strictSearch = true;
            search = fixStringForStrictSearching(search);
        }

        for (int i = 0; i < data.size(); i++) {
            if (strictSearch)
                if (data.get(i).getGroup().trim().equals(search))
                    filteredData.add(data.get(i));
            if ((data.get(i).getGroup().trim().toLowerCase().contains(search.trim().toLowerCase())) && !strictSearch)
                filteredData.add(data.get(i));
        }

        displayClassSearchResults(filteredData);
        return (filteredData);
    }

    private String fixStringForStrictSearching(String input) {
        return input.substring(0, input.indexOf("[strict]"));
    }

    private void displayClassSearchResults(ArrayList filteredData) {
        searchResults.setItems(FXCollections.observableList(filteredData));
        if (searchResults.getItems().size() > 1)
            searchResults.setPromptText(searchResults.getItems().toString());
        else searchResults.setPromptText("");
        searchResults.autosize();
    }

    //====================================================BEGIN OF CODE FOR FEEDBACK SEARCH BOX=======================================================================

    private void addSearchErrorMessage() {
        if (!errorSearchImagePlaced) {
            errorSearchImagePlaced = true;
            errorMessageSearch.getImageView().setFitWidth(25);
            errorMessageSearch.getImageView().setFitHeight(20);
            errorMessageSearch.getImageView().setPickOnBounds(true);
            searchGroupBar.getChildren().add(errorMessageSearch.getImageView());
            initActionSearchErrorMessage();
            errorMessageSearch.getImageView().setOnMouseEntered(eventEnter -> {
                Label errorMessage = new Label(this.errorMessageSearch.getDescription());
                searchGroupBar.getChildren().add(errorMessage);
                this.errorMessageSearch.getImageView().setOnMouseExited(eventExit -> {
                    searchGroupBar.getChildren().remove(searchGroupBar.getChildren().size() - 1);
                });
            });
        }
    }

    private void addSearchSuccessfulMessage() {
        if (!successSearchImagePlaced) {
            successSearchImagePlaced = true;
            successMessageSearch.getImageView().setFitWidth(25);
            successMessageSearch.getImageView().setFitHeight(20);
            successMessageSearch.getImageView().setPickOnBounds(true);
            searchGroupBar.getChildren().add(successMessageSearch.getImageView());
            initActionSearchSuccessMessage();
            successMessageSearch.getImageView().setOnMouseEntered(eventEnter -> {
                Label successMessage = new Label(successMessageSearch.getDescription());
                searchGroupBar.getChildren().add(successMessage);
                this.successMessageSearch.getImageView().setOnMouseExited(eventExit -> {
                    searchGroupBar.getChildren().remove(searchGroupBar.getChildren().size() - 1);
                });
            });
        }
    }

    private void initActionSearchErrorMessage() {
        textField.setOnMouseClicked(event -> {
            removeSearchErrorMessage();
        });

        textField.setOnMouseDragged(event -> {
            removeSearchErrorMessage();
        });
    }

    private void removeSearchErrorMessage() {
        if (errorSearchImagePlaced) {
            errorSearchImagePlaced = false;
            searchGroupBar.getChildren().remove(searchGroupBar.getChildren().size() - 1);
        }
    }

    private void initActionSearchSuccessMessage() {
        textField.setOnMouseClicked(event -> {
            removeSearchSuccessErrorMessage();
        });

        textField.setOnMouseDragged(event -> {
            removeSearchSuccessErrorMessage();
        });
    }

    private void removeSearchSuccessErrorMessage() {
        if (successSearchImagePlaced) {
            successSearchImagePlaced = false;
            searchGroupBar.getChildren().remove(searchGroupBar.getChildren().size() - 1);
        }
    }

    //====================================================END OF CODE FOR FEEDBACK SEARCH BOX=======================================================================

    //====================================================BEGIN OF CODE FOR FEEDBACK SUBMIT BOX=======================================================================
    private void addSubmitErrorMessage() {
        if (!errorSubmitImagePlaced) {
            errorSubmitImagePlaced = true;
            errorMessageSubmit.getImageView().setFitWidth(25);
            errorMessageSubmit.getImageView().setFitHeight(20);
            errorMessageSubmit.getImageView().setPickOnBounds(true);
            selectGroupBar.getChildren().add(errorMessageSubmit.getImageView());
            initActionSubmitErrorMessage();
            errorMessageSubmit.getImageView().setOnMouseEntered(eventEnter -> {
                Label errorMessage = new Label(this.errorMessageSubmit.getDescription());
                selectGroupBar.getChildren().add(errorMessage);
                this.errorMessageSubmit.getImageView().setOnMouseExited(eventExit -> {
                    selectGroupBar.getChildren().remove(selectGroupBar.getChildren().size() - 1);
                });
            });
        }
    }

    private void addSubmitSuccessfulMessage() {
        if (!successSubmitImagePlaced) {
            successSubmitImagePlaced = true;
            successMessageSubmit.getImageView().setFitWidth(25);
            successMessageSubmit.getImageView().setFitHeight(20);
            successMessageSubmit.getImageView().setPickOnBounds(true);
            selectGroupBar.getChildren().add(successMessageSubmit.getImageView());
            initActionSubmitSuccessMessage();
            successMessageSubmit.getImageView().setOnMouseEntered(eventEnter -> {
                Label successMessage = new Label(successMessageSubmit.getDescription());
                selectGroupBar.getChildren().add(successMessage);
                this.successMessageSubmit.getImageView().setOnMouseExited(eventExit -> {
                    selectGroupBar.getChildren().remove(selectGroupBar.getChildren().size() - 1);
                });
            });
        }
    }

    private void initActionSubmitErrorMessage() {
        searchResults.setOnMouseClicked(event -> {
            removeSubmitErrorMessage();
        });

        searchResults.setOnMouseDragged(event -> {
            removeSubmitErrorMessage();
        });
    }

    private void removeSubmitErrorMessage() {
        if (errorSubmitImagePlaced) {
            errorSubmitImagePlaced = false;
            selectGroupBar.getChildren().remove(selectGroupBar.getChildren().size() - 1);
        }
    }

    private void initActionSubmitSuccessMessage() {
        searchResults.setOnMouseClicked(event -> {
            removeSubmitSuccessErrorMessage();
        });

        searchResults.setOnMouseDragged(event -> {
            removeSubmitSuccessErrorMessage();
        });
    }

    private void removeSubmitSuccessErrorMessage() {
        if (successSubmitImagePlaced) {
            successSubmitImagePlaced = false;
            selectGroupBar.getChildren().remove(selectGroupBar.getChildren().size() - 1);
        }
    }

    private int testDataToBeginTime(String data) {
        int seperator = data.indexOf("-");
        data = data.substring(0, seperator - 1);
        seperator = data.indexOf(":");
        return (Integer.parseInt(data.substring(0, seperator)) - 8) * 60 + Integer.parseInt(data.substring(seperator + 1));
    }

    private int testDataToEndTime(String data) {
        int seperator = data.indexOf("-");
        data = data.substring(seperator + 2);
        seperator = data.indexOf(":");
        return (Integer.parseInt(data.substring(0, seperator)) - 8) * 60 + Integer.parseInt(data.substring(seperator + 1));
    }

    public BorderPane getFancyView() {
        return borderPane;
    }
}