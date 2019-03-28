import data.readwrite.DataReader;
import data.readwrite.DataWriter;
import data.schoolrelated.School;
import gui.SchoolManager;

import java.io.File;
import java.nio.file.Files;

import static javafx.application.Application.launch;

public class Main {
    public static void main (String args[]) {
        DevSetup.setupEverything();
        File fileSchool = new File("saves/school/school.txt");
        File filePreset = new File("saves/school/preset.txt");

        if (!fileSchool.exists()) {
            if (filePreset.exists()) {
                School school = DataReader.readPreset();
                DataWriter.writeSchool(school);
            } else {
                School school = new School("");
                DataWriter.writeSchool(school);
            }
        }
        launch(SchoolManager.class);
    }
}

//TODO: search no longer automatically empties search box when searching before box is clicked
//TODO: remove unneeded attributes from data classes
//TODO: remove unused methods to avoid confusion