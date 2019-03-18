package data;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import data.readwrite.DataReader;
import data.readwrite.DataWriter;
import data.sampledata.SampleData;
import data.schoolrelated.School;

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

            if (Files.notExists(Paths.get("saves/school/school.txt"))) {
                SampleData sampleData = new SampleData();
                DataWriter.writeSchool(sampleData.getSchool());
            } else if (!DataReader.readSchool().hasData()) {
                SampleData sampleData = new SampleData();
                DataWriter.writeSchool(sampleData.getSchool());
            }

        } catch (Exception exception) {
            System.out.println("Could not create save directories!");
        }
    }

}
