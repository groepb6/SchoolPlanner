package simulation.sims;
import gui.settings.ApplicationSettings;
import java.io.File;
import java.util.Scanner;

/**
 * @author Dustin Hendriks
 * The NameList class grabs a name out of a generated list for Sims.
 */

public class NameList {
    private Scanner scanner;

    /**
     * Create the scanner in the constructor so getName() can respond asap.
     */

    public NameList() {
        try {
            scanner = new Scanner(new File(getClass().getResource(ApplicationSettings.namePath).getPath()));
        } catch (Exception e) {
            System.out.println("Could not load names.txt from resources");
        }
    }

    /**
     * Receive the first next name out of the list.
     * @return Receive the next name out of the list.
     */

    public String getName() {
        if (scanner != null)
            if (scanner.hasNext())
                return scanner.nextLine().trim();
        return "no_name";
    }

}
