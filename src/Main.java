import data.readwrite.DataReader;
import data.readwrite.DataWriter;
import data.schedulerelated.TempSchedule;
import data.schoolrelated.Group;
import data.schoolrelated.School;
import gui.SchoolManager;
import gui.assistclasses.Plan;

import static javafx.application.Application.launch;

/**
 * @author Dustin Hendriks
 * @since 11-02-2019
 *
 * The main class writes some test data to a .txt file, which can later be read by DataReader. (new plans are added using DataWriter, for example: the GUI uses dataWriter to add new plans and dataReader to read plans and put them in a table).
 */

public class Main {
    public static void main (String args[]) {
        School school = new School("School");
        //DataWriter.writeSchool(school);

        //        boolean noData = true;
//        try {
//            //noData = (DataReader.readScheduleList().size()<1);
//        } catch (Exception e) {}
//        if (noData) {
//            TempSchedule tempSchedule = new TempSchedule();
//            tempSchedule.saveAllSchedules(
////                    new Plan("08:45 - 09:45", "23TIVT1B6", "LA424", "Johan", "OGP"),
////                    new Plan("10:00 - 12:00", "23TIVT1B6", "LA218", "Pieter", "Maths"),
////                    new Plan("13:30 - 14:00", "23TIVT1B6", "LA672", "Genevieve", "P&OC"),
////                    new Plan("15:00 - 16:30", "23TIVT1B6", "LD726", "Jan", "OOM"),
////                    new Plan("17:00 - 20:00", "23TIVT1B6", "HA300", "Peter", "HI"),
////                    new Plan("09:00 - 10:00", "23TIVT1A5", "HD520", "Etiënne", "OGP"),
////                    new Plan("12:00 - 14:00", "23TIVT1A2", "LD120", "Maurice", "OGP"),
////                    new Plan("16:00 - 20:00", "23TIVT1C2", "HA130", "Jessica", "OOM"),
////                    new Plan("12:00 - 13:00", "23TIVT1B6", "LD130", "Paul", "XP"),
////                    new Plan("12:00 - 13:00", "23TIVT1C2", "LD130", "Paul", "OGP"),
////                    new Plan("15:20 - 17:39", "23TIVT1A0", "LD212", "Hans", "Alg")
//            );
//            try {
//                //DataWriter.writeScheduleList(tempSchedule.getData());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        launch(SchoolManager.class);
    }
}
