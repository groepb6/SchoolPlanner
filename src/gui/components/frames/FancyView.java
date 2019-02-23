package gui.components.frames;

import data.readwrite.DataReader;
import data.readwrite.DataWriter;
import data.schedulerelated.Schedule;
import data.schoolrelated.School;
import gui.assistclasses.Image;
import gui.assistclasses.Plan;
import gui.components.window.Sizeable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

/**
 * @author Dustin Hendriks
 * @since 08-02-2019
 * <p>
 * The FancyView class creates a frame which can search Plans, and visualize them by plotting using the VirtualizedView class. Also plans can be removed completely using the search box and strict searching is also enabled (using [strict] after the search String).
 */

public class FancyView extends Sizeable {
    private Stage stage;
    private BorderPane borderPane = new BorderPane();
    private HBox searchGroupBar = new HBox();
    private HBox selectGroupBar = new HBox();
    private HBox graphGroupBar = new HBox();
    private HBox graphDrawBar = new HBox();
    private VBox topElements = new VBox();
    private ArrayList<Plan> allVirtualizedItems = new ArrayList<>();
    private TextField textField = new TextField("Search string");
    private Button searchGroupButton = new Button("Search");
    private Button submitGroupButton = new Button("Submit");
    private Button addAll = new Button("Add all");
    private Button clearViewButton = new Button("Clear last");
    private Button removePlanButton = new Button("Remove plan completely");
    private Image errorMessageSearch = new Image("errormessage", "functionimages", ".png", "error", "No results found");
    private Image successMessageSearch = new Image("successfulmessage", "functionimages", ".png", "successful", "Search successful");
    private Image errorMessageSubmit = new Image("errorMessage", "functionimages", ".png", "error", "No group selected / already added");
    private Image successMessageSubmit = new Image("Successfulmessage", "functionimages", ".png", "error", "Submit successful");
    private boolean errorSearchImagePlaced = false;
    private boolean successSearchImagePlaced = false;
    private boolean errorSubmitImagePlaced = false;
    private boolean successSubmitImagePlaced = false;
    private ComboBox searchResults = new ComboBox();

    /**
     * The constructor of FancyView handles the layout and already "builds" all HBox components, using the method "buildSearchSelectGraphGroupBar()".
     *
     * @param stage Stage is needed to set the super positions (define the window minimum, maximum and preferred size.
     */

    public FancyView(Stage stage) {
        super.setProportions(500, 2560, 0, 1080, Sizeable.ignore, 500, stage);
        buildSearchSelectGraphGroupBar();
        topElements.setSpacing(5);
        searchResults.setPromptText("Select plan");
        searchGroupBar.setSpacing(5);
        selectGroupBar.setSpacing(5);
        graphGroupBar.setSpacing(5);
        graphDrawBar.setSpacing(5);
        searchResults.setMinWidth(250);
        searchResults.setMaxWidth(250);
        textField.setMinWidth(250);
        textField.setMaxWidth(250);
        submitGroupButton.setMinWidth(100);
        submitGroupButton.setMaxWidth(100);
        searchGroupButton.setMinWidth(100);
        clearViewButton.setMinWidth(90);
        searchGroupButton.setMaxWidth(100);
        searchGroupBar.setMinWidth(500);
        removePlanButton.setMinWidth(260);
        this.borderPane.setPadding(new javafx.geometry.Insets(10, 0, 0, 10));

    }

    /**
     * Search list for duplicate input, if so don't add. Else add.
     */

    private void setAllButton() {
        addAll.setOnMouseClicked(event -> {
            boolean canAdd = true;
            String input = textField.getText();
            ArrayList<Plan> filteredData = search(input);
            for (int j = 0; j < filteredData.size(); j++) {
                for (int i = 0; i < allVirtualizedItems.size(); i++)
                    if (j>allVirtualizedItems.size()-1)
                        canAdd=true;
                    else if (allVirtualizedItems.get(i).isEqualTo(filteredData.get(j))) {
                        canAdd = false;
                    }
                if (canAdd) {
                    graphDrawBar.getChildren().add(VirtualizedView.schedule(testDataToBeginTime(filteredData.get(j).getTime()), testDataToEndTime(filteredData.get(j).getTime()), filteredData.get(j).getSubject()));
                    allVirtualizedItems.add(filteredData.get(j));
                }
            }
        });
    }

    /**
     * After adding a plan in the schedule using the "EditSchedule" class one might consider removing an invalid Plan. The method "setRemovePlanButton()" handles what should happen whenever the corresponding button is pressed, and which Plan should be removed.
     * Which plan will be removed is decided by comparing two Plan objects, one selected and one in the .txt file. If those are the same (=duplicate file found) the modified List is being written to the .txt file (thus has at least 1 removed element), afterwards the search box is updated and removed elements are also removed as search result.
     */

    private void setRemovePlanButton() {
        removePlanButton.setOnMouseClicked(event -> {
            boolean foundDuplicate = false;
            try {
                School school = DataReader.readSchool();

                ArrayList<Schedule> scheduleData = school.getSchedules();
                if (searchResults.getSelectionModel().getSelectedItem() != null) {
                    Plan plan;
                    for (int i = 0; i < scheduleData.size(); i++) {
                        plan = (Plan) searchResults.getSelectionModel().getSelectedItem();
                        if (plan.isEqualTo(scheduleData.get(i).getPlan())) {
                            scheduleData.remove(i);
                            i--;
                            foundDuplicate = true;
                        }
                    }
                }
                searchResults.getSelectionModel().selectFirst();
                if (foundDuplicate) {
                    school.setSchedules(scheduleData);
                    DataWriter.writeSchool(school);
                    search(textField.getText());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * The method "buildSearchSelectGraphGroupBar()" adds all elements to HBox'es (referred to as bars), consecutively all bars are added to a VBox (topElements) which is then put into a BorderPane.
     */

    private void buildSearchSelectGraphGroupBar() {
        searchGroupBar.getChildren().addAll(textField, searchGroupButton);
        selectGroupBar.getChildren().addAll(searchResults, submitGroupButton);
        graphGroupBar.getChildren().addAll(clearViewButton, removePlanButton, addAll);
        graphDrawBar.getChildren().addAll(VirtualizedView.drawTimePanel());
        topElements.getChildren().addAll(searchGroupBar, selectGroupBar, graphGroupBar, graphDrawBar);
        borderPane.setTop(topElements);
        setTextfieldOnClick();
        setSearchButtonActionOnClick();
        setSubmitButtonActionOnClick();
        setClearViewButtonActionOnClick();
        setRemovePlanButton();
        setAllButton();
    }

    private void setTextfieldOnClick(){
        textField.setOnMouseClicked(event -> {
            textField.clear();
        });
    }

    //=======================BEGIN OF CODE FOR ALL ACTION ON CLICK EVENTS================================

    /**
     * The "setSearchButtonActionOnClick()" method defines what should happen when someone clicks on the search group button. If the search is successful a green check mark is shown, else a red fail mark is shown.
     */

    private void setSearchButtonActionOnClick() {
        searchGroupButton.setOnMouseClicked(event -> {
            String input = textField.getText();
            ArrayList<Plan> filteredData = search(input);
            if (filteredData.size() >= 1) {
                addSearchSuccessfulMessage();
            } else addSearchErrorMessage();
        });
    }

    /**
     * The "setSubmitButtonActionOnClick()" method defines what should happen when someone clicks on the submit plan button.
     * If this button is pressed a graph bar is drawed and the item is being planned in the visualized / virtualized schedule.
     */

    private void setSubmitButtonActionOnClick() {
        submitGroupButton.setOnMouseClicked(event -> {
            if (searchResults.getSelectionModel().isEmpty())
                addSubmitErrorMessage();
            else {
                Plan plan = (Plan) searchResults.getSelectionModel().getSelectedItem();
                boolean doubleDetected =false;
                for (int i = 0; i < allVirtualizedItems.size(); i++) {
                    if (allVirtualizedItems.get(i).isEqualTo(plan)) doubleDetected=true;
                }
                if (!doubleDetected) {
                    graphDrawBar.getChildren().add(VirtualizedView.schedule(testDataToBeginTime(plan.getTime()), testDataToEndTime(plan.getTime()), plan.getSubject()));
                    allVirtualizedItems.add(plan);
                    addSubmitSuccessfulMessage();
                } else  {
                    addSubmitErrorMessage();

                }
            }
        });
    }

    /**
     * The "setClearViewButtonActionOnClick()" removes the last visualized / virtualized graph canvas, except number 1 since that is a timescale and shouldn't be removed.
     */

    private void setClearViewButtonActionOnClick() {
        clearViewButton.setOnMouseClicked(event -> {
            if (graphDrawBar.getChildren().size() > 1) {
                graphDrawBar.getChildren().remove(graphDrawBar.getChildren().size() - 1);
                allVirtualizedItems.remove(allVirtualizedItems.size() - 1);
            }
        });
    }

    //=========================END OF CODE FOR ALL ACTION ON CLICK EVENTS==================================

    /**
     * The retrieveScheduleData method reads all data from a text file and packs it in a list.
     *
     * @return Returns an arrayList of Plans.
     */

    private ArrayList<Plan> retrieveScheduleData() {
        List list = new ArrayList<Plan>();
        try {
            ArrayList<Schedule> schedules = DataReader.readSchool().getSchedules();
            for (Schedule schedule : schedules) {
                list.add(schedule.getPlan());
            }

        } catch (Exception e) {
        }
        return (ArrayList<Plan>) list;
    }

    /**
     * The method search can look for a specific text String in the list of groups. If a corresponding item is found it is returned.
     *
     * @param search Defines which item should be looked for (if [strict] is typed after the search String the quick search is disabled and the search string should be exactly the same)
     * @return Returns a list of filtered data, which can be plotted later.
     */

    private ArrayList<Plan> search(String search) {
        ArrayList<Plan> data = retrieveScheduleData();
        ArrayList<Plan> filteredData = new ArrayList<>();
        boolean strictSearch = false;
        if (search.contains("[strict]")) {
            fixStringForStrictSearching(search);
            strictSearch = true;
            search = fixStringForStrictSearching(search);
        }
        for (Plan plan : data) {
            if (strictSearch)
                if (plan.getGroup().trim().equals(search) || plan.getTeacher().trim().equals(search) || plan.getLocation().trim().equals(search))
                    filteredData.add(plan);
            if (((plan.getGroup().trim().toLowerCase().contains(search.trim().toLowerCase())) || plan.getTeacher().trim().toLowerCase().contains(search.trim().toLowerCase()) || plan.getLocation().trim().toLowerCase().contains(search.trim().toLowerCase()) || plan.getSubject().trim().toLowerCase().contains(search.trim())) && !strictSearch)
                filteredData.add(plan);
        }
        displayClassSearchResults(filteredData);
        return (filteredData);
    }

    /**
     * Seperate the strict keyword from the actual command.
     *
     * @param input Defines the input including the keyword [strict]
     * @return Return the true input without [strict]
     */
    private String fixStringForStrictSearching(String input) {
        return input.substring(0, input.indexOf("[strict]"));
    }

    /**
     * Retrieve the filtered data and place it in the search results (must be observable).
     *
     * @param filteredData Data arrayList of all results taking into account the keyword.
     */

    private void displayClassSearchResults(ArrayList filteredData) {
        searchResults.setItems(FXCollections.observableList(filteredData));
        if (searchResults.getItems().size() > 0)
            searchResults.setPromptText(searchResults.getItems().toString());
        else searchResults.setPromptText("");
        searchResults.autosize();
    }

    //====================================================BEGIN OF CODE FOR FEEDBACK SEARCH BOX=======================================================================

    /**
     * If the search fails an error icon + message is being displayed.
     * Hence that the message is only being displayed when a hover action is performed, a user doesn't want to read that message all the time, an icon is enough information when familiar with the system.
     */

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

    /**
     * If the search succeeds a succeed icon + message is being displayed.
     * Hence that the message is only being displayed when a hover action is performed, a user doesn't want to read that message all the time, an icon is enough information when familiar with the system.
     */

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

    /**
     * If a user clicks or drags over the search field it is obvious the user wants to edit their search string, so either the succeed or fail icon and message should be removed.
     */

    private void initActionSearchErrorMessage() {
        textField.setOnMouseClicked(event -> {
            removeSearchErrorMessage();
        });

        textField.setOnMouseDragged(event -> {
            removeSearchErrorMessage();
        });
    }

    /**
     * Removes the search error message.
     */

    private void removeSearchErrorMessage() {
        if (errorSearchImagePlaced) {
            errorSearchImagePlaced = false;
            searchGroupBar.getChildren().remove(searchGroupBar.getChildren().size() - 1);
        }
    }

    /**
     * If a user clicks or drags over the search field it is obvious the user wants to edit their search string, so either the succeed or fail icon and message should be removed.
     */

    private void initActionSearchSuccessMessage() {
        textField.setOnMouseClicked(event -> {
            removeSearchSuccessErrorMessage();
        });

        textField.setOnMouseDragged(event -> {
            removeSearchSuccessErrorMessage();
        });
    }

    /**
     * Remove the success error message.
     */

    private void removeSearchSuccessErrorMessage() {
        if (successSearchImagePlaced) {
            successSearchImagePlaced = false;
            searchGroupBar.getChildren().remove(searchGroupBar.getChildren().size() - 1);
        }
    }

    //====================================================END OF CODE FOR FEEDBACK SEARCH BOX=======================================================================

    //====================================================BEGIN OF CODE FOR FEEDBACK SUBMIT BOX=======================================================================


    /**
     * Submitting the group could cause an error if it somehow does not exist anymore, or never was selected in the normal case.
     */

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

    /**
     * Submitting the group normally succeeds, consider a user would want feedback on their button click. This is why a succeed icon is being displayed.
     */

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

    /**
     * When someone clicks on the search results button (to change the result) the potential error message should disappear (if the user is going to change it, it doesn't serve a purpose anymore, thus can be cleaned up).
     */

    private void initActionSubmitErrorMessage() {
        searchResults.setOnMouseClicked(event -> {
            removeSubmitErrorMessage();
        });

        searchResults.setOnMouseDragged(event -> {
            removeSubmitErrorMessage();
        });
    }

    /**
     * Remove the error message icon.
     */

    private void removeSubmitErrorMessage() {
        if (errorSubmitImagePlaced) {
            errorSubmitImagePlaced = false;
            selectGroupBar.getChildren().remove(selectGroupBar.getChildren().size() - 1);
        }
    }

    /**
     * When a user wants to change his / her search result any message should be removed since it is no longer relevant for the program.
     */

    private void initActionSubmitSuccessMessage() {
        searchResults.setOnMouseClicked(event -> {
            removeSubmitSuccessErrorMessage();
        });

        searchResults.setOnMouseDragged(event -> {
            removeSubmitSuccessErrorMessage();
        });
    }

    /**
     * When a user wants to change his search result the succeed message can be removed since it is no longer relevant for the string the user wants to enter consecutively.
     */

    private void removeSubmitSuccessErrorMessage() {
        if (successSubmitImagePlaced) {
            successSubmitImagePlaced = false;
            selectGroupBar.getChildren().remove(selectGroupBar.getChildren().size() - 1);
        }
    }

    /**
     * Retrieve amount of minutes from a time string.
     *
     * @param data Time string, for example: ax:by
     * @return Return a time in minutes.
     */

    private int testDataToBeginTime(String data) {
        int seperator = data.indexOf("-");
        data = data.substring(0, seperator - 1);
        seperator = data.indexOf(":");
        return (Integer.parseInt(data.substring(0, seperator)) - 8) * 60 + Integer.parseInt(data.substring(seperator + 1));
    }

    /**
     * Retrieve amount of minutes from a time string.
     *
     * @param data Time string, for example: ax:by
     * @return Return a time in minutes.
     */

    private int testDataToEndTime(String data) {
        int seperator = data.indexOf("-");
        data = data.substring(seperator + 2);
        seperator = data.indexOf(":");
        return (Integer.parseInt(data.substring(0, seperator)) - 8) * 60 + Integer.parseInt(data.substring(seperator + 1));
    }

    /**
     * Send the borderPane which stores the entire graphical panel
     *
     * @return Return the borderPane on which all nodes and elements are stored.
     */

    public BorderPane getFancyView() {
        return borderPane;
    }
}