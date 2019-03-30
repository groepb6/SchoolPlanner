package data;

import data.readwrite.DataReader;
import data.readwrite.DataWriter;
import gui.settings.ApplicationSettings;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class contains methods for developers to set up things that aren't synchronised within git
 * @author Dustin Hendriks
 */
public class DevSetup {
    /**
     * Can be used as an example.
     * Sets up the save directories (safe to run multiple times).
     */
    public static void setupSaveDirectories() { //TODO: do we need these save files?
        try {
            if (Files.notExists(Paths.get("saves"))) {
                Files.createDirectories(Paths.get("saves"));
            }
            if (Files.notExists(Paths.get("saves/school"))) {
                Files.createDirectories(Paths.get("saves/school"));
            }
            if (Files.notExists(Paths.get(ApplicationSettings.schoolPath))) {
                DataWriter.writeSchool(DataReader.emergencySchool()); //TODO: do we want empty or preset school?
            } else if (!DataReader.readSchool().hasData()) {
                DataWriter.writeSchool(DataReader.emergencySchool());
            }

        } catch (Exception exception) {
            System.out.println("Could not create save directories!");
        }
    }

}
