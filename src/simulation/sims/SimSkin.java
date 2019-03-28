package simulation.sims;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
            int x =3;
            peeImage = getSpriteImage(x, rowNumber);

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private BufferedImage getSpriteImage(int pictureNumb, int rowNumb) {
        return simSpriteSheet.getSubimage(pictureNumb * tileWidth, rowNumb * tileHeight, tileWidth, tileHeight);
    }

    public BufferedImage stationaryLeft() {
        return walkLeftAnimation.get(0);
    }

    public BufferedImage stationaryRight() {
        return walkRightAnimation.get(0);
    }

    public BufferedImage stationaryUp() {
        return walkUpAnimation.get(0);
    }

    public BufferedImage stationaryDown() {
        return walkDownAnimation.get(0);
    }

    public BufferedImage walkLeft(Sim sim) {
        if (sim.index + 1 > walkLeftAnimation.size())
            sim.index = 1;
        return walkLeftAnimation.get(sim.index++);
    }

    public BufferedImage walkRight(Sim sim) {
        if (sim.index + 1 > walkRightAnimation.size())
            sim.index = 1;
        return walkRightAnimation.get(sim.index++);
    }

    public BufferedImage walkUp(Sim sim) {
        if (sim.index + 1 > walkUpAnimation.size())
            sim.index = 1;
        return walkUpAnimation.get(sim.index++);
    }

    public BufferedImage walkDown(Sim sim) {
        if (sim.index + 1 > walkDownAnimation.size())
            sim.index = 1;
        return walkDownAnimation.get(sim.index++);
    }

    public BufferedImage toiletPee(Sim sim) {
        return null;
    }

    public BufferedImage pointWithStickLeftFrontFacing(Sim sim) {
        if (sim.index + 1 > pointWithStickLeftFrontFacing.size()) {
            sim.index = 0;
            finishedAnimation = true;
        }
        return pointWithStickLeftFrontFacing.get(sim.index++);
    }

    public BufferedImage pointWithStickLeftFrontFacingStationary() {
        return pointWithStickLeftFrontFacing.get(pointWithStickLeftFrontFacing.size()-1);
    }

    public BufferedImage pointWithStickRightFrontFacing(Sim sim) {
        if (sim.index + 1 > pointWithStickRightFrontFacing.size()) {
            sim.index = 0;
            finishedAnimation = true;
        }
        return pointWithStickRightFrontFacing.get(sim.index++);
    }

    public BufferedImage pointWithStickRightFrontFacingStationary() {
        return pointWithStickRightFrontFacing.get(pointWithStickRightFrontFacing.size()-1);
    }

    public BufferedImage pointWithStickRightBackFacing(Sim sim) {
        if (sim.index + 1 > pointWithStickRightBackFacing.size()) {
            sim.index = 0;
            finishedAnimation = true;
        }
        return pointWithStickRightBackFacing.get(sim.index++);
    }

    public BufferedImage pointWithStickRightBackFacingStationary() {
        return pointWithStickRightBackFacing.get(pointWithStickRightBackFacing.size()-1);
    }

    public BufferedImage hitStudent(Sim sim) {
        if (sim.index + 1 > hitStudentWithStick.size()) {
            sim.index = 0;
            finishedAnimation = true;
        }
        return hitStudentWithStick.get(sim.index++);
    }

    public boolean animationIsFinished() {
        return finishedAnimation;
    }

    public void setFinishedAnimation(boolean isFinished) {
        this.finishedAnimation = isFinished;
    }
}
