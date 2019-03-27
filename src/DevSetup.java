import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Noah Walsmits
 * @author Hanno Brandwijk
 */

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
            if (Files.notExists(Paths.get("saves/school"))) {
                Files.createDirectories(Paths.get("saves/school"));
            }
        } catch (Exception exception) {
            System.out.println("Error, could not create save directories!");
        }
    }

}
