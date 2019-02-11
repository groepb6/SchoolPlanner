package gui.components.frames;

import gui.Image;
import gui.Plan;
import gui.components.window.Sizeable;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FancyView extends Sizeable {
    private Stage stage;
    private BorderPane borderPane = new BorderPane();
    private HBox searchGroupBar = new HBox();
    private HBox selectGroupBar = new HBox();
    private VBox topElements = new VBox();
    private TextField textField = new TextField("Search group");
    private Button searchGroupButton = new Button("Search");
    private Button submitGroupButton = new Button("Submit");
    private Button openViewButton = new Button("OpenView");
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
        super.setProportions(500,2560,0, 1080, Sizeable.ignore, 500, stage);
        buildSearchSelectGroupBar();
        topElements.setSpacing(5);
        searchResults.setPromptText("Select group");
        searchGroupBar.setSpacing(5);
        selectGroupBar.setSpacing(5);
        searchResults.setMinWidth(200);
        searchResults.setMaxWidth(200);
        textField.setMinWidth(200);
        textField.setMaxWidth(200);
        selectGroupBar.getChildren().add(searchResults);
        selectGroupBar.getChildren().add(submitGroupButton);
        submitGroupButton.setMinWidth(100);
        submitGroupButton.setMaxWidth(100);
        searchGroupButton.setMinWidth(100);
        searchGroupButton.setMaxWidth(100);
        searchGroupBar.setMinWidth(500);
    }

    private void buildSearchSelectGroupBar() {
        searchGroupBar.getChildren().addAll(textField, searchGroupButton);
        topElements.getChildren().add(searchGroupBar);
        topElements.getChildren().add(selectGroupBar);
        borderPane.setTop(topElements);

        setSearchButtonActionOnClick();
        setSubmitButtonActionOnClick();
    }

    private void setSearchButtonActionOnClick() {
        searchGroupButton.setOnMouseClicked(event -> {
            String input = textField.getText();
            ArrayList<Plan> filteredData = search(input);
            if (filteredData.size() >= 1) {
                System.out.println(filteredData.toString());
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
                launchPopUp();
            }
        });
    }

    private ArrayList<Plan> search(String search) {
        ArrayList<Plan> data = getTestData();
        ArrayList<Plan> filteredData = new ArrayList<>();
        boolean strictSearch = false;

        if (search.contains("[strict]")) {
            fixStringForStrictSearching(search);
            strictSearch = true;
            search = fixStringForStrictSearching(search);
        }

        for (int i = 0; i < getTestData().size(); i++) {
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
                // errorMessageSearch.setBorder(new Border(new BorderStroke(Color.INDIANRED, BorderStrokeStyle.DASHED, null, new BorderWidths(5))));
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
                // errorMessageSearch.setBorder(new Border(new BorderStroke(Color.INDIANRED, BorderStrokeStyle.DASHED, null, new BorderWidths(5))));
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
    //====================================================END OF CODE FOR FEEDBACK SUBMIT BOX=======================================================================



    //================================================BEGIN OF CODE FOR OPENING THE VIRTUAL VIEW MODE===============================================================
    public void setOpenViewButtonActions() {
        submitGroupButton.setOnMouseClicked(event -> {
            //VirtualizedSchedule virtualizedSchedule = new VirtualizedSchedule();
        });
    }

    //=================================================END OF CODE FOR OPENING THE VIRTUAL VIEW MODE================================================================

    private void launchPopUp() {
        stage = new Stage();

    }

    public BorderPane getFancyView() {
        return borderPane;
    }

    private ArrayList getTestData() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 100; i++) {
            list.add(new Plan("time" + Integer.toString(i), " group" + Integer.toString(i), " location" + Integer.toString(i), " teacher" + Integer.toString(i), " subject" + Integer.toString(i)));
        }
        return list;
    }
}