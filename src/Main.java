import data.readwrite.DataReader;
import data.readwrite.DataWriter;
import data.schoolrelated.School;
import gui.SchoolManager;

import java.io.File;
import java.nio.file.Files;

import static javafx.application.Application.launch;

public class Main {
    public static void main (String args[]) {
        File file = new File("saves/school/school.txt");
        File presetFile = new File("saves/school/preset.txt");

        if (!file.exists()) {
            if (presetFile.exists()) {
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
