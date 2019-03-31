package simulation.sims;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Dustin Hendriks
 * The SimSkin class creates animations and specific animation frames that can be used in a Sim.
 */

public class SimSkin {
    private BufferedImage simSpriteSheet;
    private ArrayList<BufferedImage> walkLeftAnimation = new ArrayList<>();
    private ArrayList<BufferedImage> walkRightAnimation = new ArrayList<>();
    private ArrayList<BufferedImage> walkUpAnimation = new ArrayList<>();
    private ArrayList<BufferedImage> walkDownAnimation = new ArrayList<>();
    private ArrayList<BufferedImage> pointWithStickRightFrontFacing = new ArrayList<>();
    private ArrayList<BufferedImage> pointWithStickLeftFrontFacing = new ArrayList<>();
    private ArrayList<BufferedImage> pointWithStickRightBackFacing = new ArrayList<>();
    private ArrayList<BufferedImage> hitStudentWithStick = new ArrayList<>();
    private BufferedImage peeImage;
    private boolean finishedAnimation= false;

    private int tileWidth = 64;
    private int tileHeight = 64;
    private int totalWidth;

    public enum Role {
        student, teacher
    }

    public Role role;

    /**
     * @author Dustin Hendriks
     * @param role The role defines if a Sim resembles a student or teacher. When it resembles a teacher an entirely different sprite sheet is used.
     * @param numberOfOutfit The numberOfOutfit defines which output should be used (don't use higher numbers then defined in the resource folder).
     */

    public SimSkin(Role role, int numberOfOutfit) {
        try {
            simSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/simsprites/" + role.toString() + Integer.toString(numberOfOutfit) + ".png"));
            int rowNumber=8;
            for (int x = 0; x < 9; x++)
                walkUpAnimation.add(getSpriteImage(x, rowNumber));
            rowNumber = 9;
            for (int x = 0; x < 9; x++)
                walkLeftAnimation.add(getSpriteImage(x, rowNumber));
            rowNumber =10;
            for (int x = 0; x < 9; x++)
                walkDownAnimation.add(getSpriteImage(x, rowNumber));
            rowNumber = 11;
            for (int x = 0; x < 9; x++)
                walkRightAnimation.add(getSpriteImage(x, rowNumber));
            rowNumber = 12;
            for (int x = 0; x < 6; x++)
                pointWithStickRightBackFacing.add(getSpriteImage(x, rowNumber));
            rowNumber = 13;
            for (int x = 0; x < 6; x++)
                pointWithStickLeftFrontFacing.add(getSpriteImage(x, rowNumber));
            rowNumber = 14;
            for (int x = 0; x < 6; x++)
                hitStudentWithStick.add(getSpriteImage(x, rowNumber));
            rowNumber = 15;
            for (int x = 0; x < 6; x++)
                pointWithStickRightFrontFacing.add(getSpriteImage(x, rowNumber));
            rowNumber = 0;
            int x =2;
            peeImage = getSpriteImage(x, rowNumber);

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Receive a specific sprite image, used as a helper method.
     * @param pictureNumb Defines which picture should be grabbed.
     * @param rowNumb Defines the row number of the picture
     * @return Return a bufferedImage of the cut out sprite sheet.
     */

    private BufferedImage getSpriteImage(int pictureNumb, int rowNumb) {
        return simSpriteSheet.getSubimage(pictureNumb * tileWidth, rowNumb * tileHeight, tileWidth, tileHeight);
    }

    /**
     * Receive the stationary left image.
     * @return stationary left image.
     */

    BufferedImage stationaryLeft() {
        return walkLeftAnimation.get(0);
    }

    /**
     * Receive the stationary right image.
     * @return stationary right image.
     */

    BufferedImage stationaryRight() {
        return walkRightAnimation.get(0);
    }

    /**
     * Receive the stationary up image.
     * @return stationary up image.
     */

    BufferedImage stationaryUp() {
        return walkUpAnimation.get(0);
    }

    /**
     * Receive the stationary down image.
     * @return stationary down image.
     */

    BufferedImage stationaryDown() {
        return walkDownAnimation.get(0);
    }

    /**
     * Receive the next walkLeft image.
     * @param sim Parse to know which image should be returned next.
     * @return Return walkLeft frame.
     */

    BufferedImage walkLeft(Sim sim) {
        if (sim.index + 1 > walkLeftAnimation.size())
            sim.index = 1;
        return walkLeftAnimation.get(sim.index++);
    }

    /**
     * Receive the next walkRight image.
     * @param sim Parse to know which image should be returned next.
     * @return Return walkRight frame.
     */

    BufferedImage walkRight(Sim sim) {
        if (sim.index + 1 > walkRightAnimation.size())
            sim.index = 1;
        return walkRightAnimation.get(sim.index++);
    }

    /**
     * Receive the next walkUp image.
     * @param sim Parse to know which image should be returned next.
     * @return Return walkUp frame.
     */

    BufferedImage walkUp(Sim sim) {
        if (sim.index + 1 > walkUpAnimation.size())
            sim.index = 1;
        return walkUpAnimation.get(sim.index++);
    }

    /**
     * Receive the next walkDown image.
     * @param sim Parse to know which image should be returned next.
     * @return Return walkDown frame.
     */

    BufferedImage walkDown(Sim sim) {
        if (sim.index + 1 > walkDownAnimation.size())
            sim.index = 1;
        return walkDownAnimation.get(sim.index++);
    }

    /**
     * Receive the image that can be used as a toilet animation.
     * @param sim --> Not used atm.
     * @return Return the corresponding frame.
     */

    BufferedImage toiletPee() {
        return peeImage;
    }

    /**
     * Teacher animation, do not use for student.
     * @param sim Defines the TEACHER.
     * @return Return the next pointWithStickLeftFrontFacing frame.
     */

    BufferedImage pointWithStickLeftFrontFacing(Sim sim) {
        if (sim.index + 1 > pointWithStickLeftFrontFacing.size()) {
            sim.index = 0;
            finishedAnimation = true;
        }
        return pointWithStickLeftFrontFacing.get(sim.index++);
    }


    /**
     * Teacher animation, do not use for student.
     * @return Return the stationary pointWithStickLeftFrontFacingStationary frame.
     */

    BufferedImage pointWithStickLeftFrontFacingStationary() {
        return pointWithStickLeftFrontFacing.get(pointWithStickLeftFrontFacing.size()-1);
    }

    /**
     * Teacher animation, do not use for student.
     * @param sim Defines the TEACHER.
     * @return Return the next pointWithStickRightFrontFacing frame.
     */

    BufferedImage pointWithStickRightFrontFacing(Sim sim) {
        if (sim.index + 1 > pointWithStickRightFrontFacing.size()) {
            sim.index = 0;
            finishedAnimation = true;
        }
        return pointWithStickRightFrontFacing.get(sim.index++);
    }

    /**
     * Teacher animation, do not use for student.
     * @return Return the stationary pointWithStickRightFrontFacingStationary frame.
     */

    BufferedImage pointWithStickRightFrontFacingStationary() {
        return pointWithStickRightFrontFacing.get(pointWithStickRightFrontFacing.size()-1);
    }

    /**
     * Teacher animation, do not use for student.
     * @param sim Defines the TEACHER.
     * @return Return the next pointWithStickRightBackFacing frame.
     */

    BufferedImage pointWithStickRightBackFacing(Sim sim) {
        if (sim.index + 1 > pointWithStickRightBackFacing.size()) {
            sim.index = 0;
            finishedAnimation = true;
        }
        return pointWithStickRightBackFacing.get(sim.index++);
    }

    /**
     * Teacher animation, do not use for student.
     * @return Return the next pointWithStickRightBackFacingStationary frame.
     */

    BufferedImage pointWithStickRightBackFacingStationary() {
        return pointWithStickRightBackFacing.get(pointWithStickRightBackFacing.size()-1);
    }

    /**
     * Teacher animation, do not use for student.
     * @param sim Defines the TEACHER.
     * @return Return the next hitStudent frame.
     */

    BufferedImage hitStudent(Sim sim) {
        if (sim.index + 1 > hitStudentWithStick.size()) {
            sim.index = 0;
            finishedAnimation = true;
        }
        return hitStudentWithStick.get(sim.index++);
    }

    /**
     * For future purposes when multiple animations should be played consequently.
     * @return Return true or false value corresponding to the attribute finishedAnimation.
     */

    public boolean animationIsFinished() {
        return finishedAnimation;
    }

    /**
     * Define if an animation is finished (can be used for future purposes).
     * @param isFinished Set true or false value for the attribute finishedAnimation.
     */

    public void setFinishedAnimation(boolean isFinished) {
        this.finishedAnimation = isFinished;
    }
}
