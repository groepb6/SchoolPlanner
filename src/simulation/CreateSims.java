package simulation;

import data.persons.Person;
import data.readwrite.DataReader;
import data.schoolrelated.School;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;
import simulation.data.Area;
import simulation.sims.Sim;
import simulation.sims.SimSkin;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates Sim objects for the simulation.
 * todo: documentation
 */
public class CreateSims {
    private School school;
    private SchoolMap map;
    private Sim[] sims;

    public static final int STUDENTSPERGROUP = 2;
    public static final String SPAWNAREA = "ParkingLot";
    public static final int MAXSPAWNATTEMPTS = 10;

    public CreateSims(School school, SchoolMap map, FXGraphics2D graphics, Canvas canvas) {
        this.school = school;
        this.map = map;
        this.checkSchool();
        this.school.createStudents(this.STUDENTSPERGROUP);
        this.createSims(graphics, canvas);
        this.map.setSims(this.sims);
    }

    public School getSchool() {
        return school;
    }

    public SchoolMap getMap() {
        return map;
    }

    public Sim[] getSims() {
        return sims;
    }

    /**
     * Checks if the School has 6 rooms.
     */
    private void checkSchool() {
        if (!DataReader.hasRooms(this.school)) {
            this.school = DataReader.emergencySchool();
        }
    }

    /**
     * Puts sims from a list into the sims array.
     * @param tempSims The List of Sim objects the simulation should have.
     */
    private void makeSimsArray(List<Sim> tempSims) {
        int index = 0;
        this.sims = new Sim[tempSims.size()];
        for (Sim sim : tempSims) {
            this.sims[index] = sim;
            index++;
        }
    }

    /**
     * Attaches a Sim to each Person in the School.
     *
     * @param g2d    An FXGraphics2D object required in the constructor of Sim.
     * @param canvas A Canvas object required in the constructor of Sim.
     * @author Dustin Hendriks
     */
    private void createSims(FXGraphics2D g2d, Canvas canvas) {
        //Create SimSkins
        SimSkin[] simSkins = new SimSkin[9];
        for (int i = 0; i < 9; i++) {
            simSkins[i] = new SimSkin(SimSkin.Role.student, i);
        }

        //Get spawnArea
        Area spawnArea = null;
        for (Area area : this.map.areas) {
            if (area.areaName.equals(this.SPAWNAREA)) {
                spawnArea = area;
            }
        }
        if (spawnArea == null) {
            System.out.println("Spawn area could not be found!");
            System.out.println("Your resources might not support areas, or the area is not referenced correctly!");
        }

        //Spawning sims
        List<Sim> tempSims = new ArrayList<>();
        int amountOfTries = 0;
        boolean noneNeeded = false;
        while (!noneNeeded) {
            noneNeeded = true;
            for (Person person : this.school.getPeople()) {
                if (person.getSim() == null) {
                    noneNeeded = false;
                    Point2D spawnPos = new Point2D.Double(spawnArea.x + (Math.random() * spawnArea.areaWidth), spawnArea.y + (Math.random() * spawnArea.areaHeight));
                    if (map.getCollisionLayer()[(int) Math.round(spawnPos.getX() / 32)][(int) Math.round(spawnPos.getY() / 32)].walkable) {
                        if (canAdd(spawnPos, tempSims)) {
                            Sim sim = new Sim(spawnPos, g2d, simSkins[((int) (Math.random() * simSkins.length - 1))], canvas, map.areas, "");
                            sim.setTargetArea(this.map.searchArea(this.SPAWNAREA));
                            tempSims.add(sim);
                            person.setSim(sim);
                        }
                    }

                }
            }

            //Gives up after too many tries
            amountOfTries++;
            if (amountOfTries >= this.MAXSPAWNATTEMPTS) {
                System.out.println("Was unable to spawn all sims after " + this.MAXSPAWNATTEMPTS + " tries!");
                System.out.println("Created " + tempSims.size() + " sims out of " + this.school.getPeople().size());
                break;
            }
        }
        this.makeSimsArray(tempSims);
    }

    /**
     * Can add decides whether a character can be spawned without being spawned inside another character.
     *
     * @param spawnPos Defines the spawn position a new Sim could receive.
     * @return Returns true or false depending if the spot is free.
     * @author Dustin Hendriks
     */
    private boolean canAdd(Point2D spawnPos, List<Sim> tempSims) {
        boolean canAdd = true;
        for (Sim sim : tempSims) {
            if (!canAddHelper(spawnPos, sim.getCurrentPos())) {
                canAdd = false;
                break;
            }
        }
        return canAdd;
    }

    /**
     * Defines whether the distance is smaller or bigger than 64. Helper function for the canAdd() emthod.
     *
     * @param spawnPos    SpawnPos defines the potential new spawn position.
     * @param otherSimPos otherSImPos defines the position of another sim.
     * @return Is the distance between these points smaller than 64?
     * @author Dustin Hendriks
     */
    private boolean canAddHelper(Point2D spawnPos, Point2D otherSimPos) {
        return (!(spawnPos.distance(otherSimPos) < 64));
    }

}
