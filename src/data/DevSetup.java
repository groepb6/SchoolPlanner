package data;

import data.readwrite.DataReader;
import data.readwrite.DataWriter;
import data.sampledata.SampleData;
import gui.settings.ApplicationSettings;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class contains methods for developers to set up things that aren't synchronised within git
 */
public class DevSetup {
    /**
     * Can be used as an example.
     * Sets up the save directories (safe to run multiple times).
     */
    public static void setupSaveDirectories() {
        try {
            if (Files.notExists(Paths.get("saves")))
                Files.createDirectories(Paths.get("saves"));

            if (Files.notExists(Paths.get("saves/groups")))
                Files.createDirectories(Paths.get("saves/groups"));

            if (Files.notExists(Paths.get("saves/schedules")))
                Files.createDirectories(Paths.get("saves/schedules"));

            if (Files.notExists(Paths.get("saves/teachers")))
                Files.createDirectories(Paths.get("saves/teachers"));

            if (Files.notExists(Paths.get("saves/school")))
                Files.createDirectories(Paths.get("saves/school"));

            if (Files.notExists(Paths.get(ApplicationSettings.schoolPath))) {
                SampleData sampleData = new SampleData();
                DataWriter.writeSchool(sampleData.getSchool()); //TODO: write empty school with rooms
            } else if (!DataReader.readSchool().hasData()) {
                SampleData sampleData = new SampleData();
                DataWriter.writeSchool(sampleData.getSchool());
            }

        } catch (Exception exception) {
            System.out.println("Could not create save directories!");
        }
    }

}
