package simulation.sims;

import gui.settings.ApplicationSettings;

import java.io.File;
import java.util.Scanner;

public class NameList {
    private Scanner scanner;

    public NameList() {
        try {
            scanner = new Scanner(new File(getClass().getResource(ApplicationSettings.namePath).getPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        if (scanner != null)
            if (scanner.hasNext())
                return scanner.nextLine().trim();
        return null;
    }

}