import data.readwrite.DataWriter;
import data.schoolrelated.School;
import gui.SchoolManager;
import java.io.File;
import static javafx.application.Application.launch;

/**
 * @author Dustin Hendriks
 * Launch the application
 */

public class Main {
    public static void main (String args[]) {
        File file = new File("saves/school/school.txt");
        if (!file.exists()) {
            School school = new School("School");
            DataWriter.writeSchool(school);
        }
        launch(SchoolManager.class);
    }
}
