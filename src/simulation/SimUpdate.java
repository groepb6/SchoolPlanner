package simulation;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;
import simulation.sims.Sim;
import simulation.sims.SimSkin;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;

public class SimUpdate {
    private FXGraphics2D g2d;
    private Canvas canvas;
    private Scene scene;
    private double delay = 0.;
    private int counter = 0;
    private SchoolMap schoolMap;
    private ArrayList<Sim> sims = new ArrayList<>();
    public AnimationTimer animationTimer;
    private ArrayList<SimSkin> simSkins = new ArrayList<>();

    public SimUpdate(FXGraphics2D g2d, Canvas canvas, Scene scene, SchoolMap schoolMap) {
        this.g2d = g2d;
        this.canvas = canvas;
        this.scene = scene;
        this.schoolMap = schoolMap;
        createSims();

        animationTimer = new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1.e9);
                last = now;
            }
        };
        animationTimer.start();
    }

    private void createSims() {
        for (int i =0; i < 9; i++)
            simSkins.add(new SimSkin(SimSkin.Role.student, i)); // 0 was i
        for (int i = 0; i < 105; i++) {
            sims.add(new Sim(new Point2D.Double(Math.random() * canvas.getWidth(), Math.random() * canvas.getHeight()), g2d, simSkins.get((int) (Math.random() * simSkins.size()-1)), canvas));
        }
    }

    public void stopTimer() {
        animationTimer.stop();
    }

    public void update(double deltatime) {
        delay+=deltatime;
        counter = (int)(Math.round(delay*10.)/10.);
        if (delay >= 0.075 ) {
            delay=0;
            schoolMap.restoreCanvas();
            updatePositionSims();
            drawSims();
            schoolMap.drawWalls();
            //schoolMap.drawLeaves();
            //schoolMap.drawCollision();
        }
    }

    public void drawSims() {
        sims.sort(new Comparator<Sim>() {
            @Override
            public int compare(Sim sim1, Sim sim2) {
                return (int)sim1.getCurrentPos().getY() < sim2.getCurrentPos().getY() ? -1 : sim1.getCurrentPos().getY() == sim2.getCurrentPos().getY() ? 0 : 1;
            }
        });
        for (Sim sim : sims) {
            sim.update(sims);
            sim.draw();
        }
    }

    public void updatePositionSims() {
        canvas.setOnMouseMoved(e -> {
            for (Sim sim : sims)
                if (sim.getSpeed()!=0)
                    sim.setTargetPos(new Point2D.Double(e.getX(), e.getY()));
                else sim.setTargetPos(new Point2D.Double(50, 50));
        });


    }
}
