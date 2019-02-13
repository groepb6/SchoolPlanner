import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class contains methods for developers to set up things that aren't synchronised within git
 */
public class DevSetup {

    /**
     * Main method to run the setup
     * @param args
     */
    public static void main(String[] args) {
        DevSetup.setupEverything();
    }

    /**
     * Executes all DevSetup methods (might not be safe to run multiple
     */
    public static void setupEverything() {
        DevSetup.setupSaveDirectories();
    }

    /**
     * OUTDATED, but can be used as an example
     * Sets up the save directories (safe to run multiple times)
     */
    public static void setupSaveDirectories() {
        try {
            if (Files.exists(Paths.get("saves/groups"))) {
                Files.createDirectories(Paths.get("saves/groups"));
            }
            if (Files.exists(Paths.get("saves/schedules"))) {
                Files.createDirectories(Paths.get("saves/schedules"));
            }
            if (Files.exists(Paths.get("saves/teachers"))) {
                Files.createDirectories(Paths.get("saves/teachers"));
            }
        } catch (Exception exception) {
            System.out.println("Error, could not create save directories!");
        }
    }

}
