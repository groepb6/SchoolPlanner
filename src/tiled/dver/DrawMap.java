package tiled.dver;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import javax.json.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawMap {
    private FXGraphics2D g2d;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    private ArrayList<BufferedImage> subImages = new ArrayList<>();
    private int tileWidth;
    private int tileHeight;
    private int width;
    private int height;
    private JsonArray layers;
    private JsonObject mapFile;

    private double scaleMultiplier=1.;
    private Canvas canvas;
    private Scene scene;
    private int[][] map;
    private Camera camera;

    private double totalTime;

    DrawMap(FXGraphics2D g2d, Canvas canvas, Scene scene) {
        this.g2d = g2d;
        this.scene = scene;
        this.canvas = canvas;
        saveSprites("schoolmap.json");
        this.camera = new Camera(canvas);
        drawMap();

        totalTime = 0;
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                drawMap();
            }
        }.start();
    }

    private void saveSprites(String map) {
        JsonReader jsonReader = Json.createReader(getClass().getResourceAsStream("/tiles/maps/" + map + ".json"));
        mapFile = jsonReader.readObject();
        width = mapFile.getInt("width");
        height = mapFile.getInt("height");
        tileWidth = mapFile.getInt("tilewidth");
        tileHeight = mapFile.getInt("tileheight");

        JsonArray data = mapFile.getJsonArray("tilesets");

        for (int i = 0; i < data.size(); i++) {
            try {
                sprites.add(ImageIO.read(getClass().getResourceAsStream("/tiles/tilesets/" + data.getJsonObject(i).getString("link"))));
            } catch (Exception exception) {
                System.out.println("Could not find images");
            }
        }
        sprites.forEach(sprite -> {
            for (int y = 0; y < sprite.getHeight(); y += tileHeight) {
                for (int x = 0; x < sprite.getWidth(); x += tileWidth) {
                    subImages.add(sprite.getSubimage(x, y, tileWidth, tileHeight));
                }
            }
        });
    }

    private void drawMap() {
        g2d.setBackground(Color.BLACK);
        g2d.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        this.g2d.setTransform(this.camera.getTransform());
        int index = 0;
        AffineTransform affineTransform;
        for (int i = 0; i < mapFile.getJsonArray("layers").size() - 1; i++) {
            index = 0;
            layers = mapFile.getJsonArray("layers").getJsonObject(i).getJsonArray("data");
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    affineTransform = new AffineTransform();
                    affineTransform.translate(x * tileWidth, y * tileHeight);
                    if (layers.getInt(index) != 0) {
                        g2d.drawImage(subImages.get(layers.getInt(index) - 1), affineTransform, null);
                    }
                    index++;
                }
            }
        }
    }

    private void update(double time) {
        this.totalTime += time;
        if (this.totalTime >= 0.1) {
            this.drawMap();
            this.totalTime = 0;
        }
    }

    private void setActions() {
//        scene.setOnMouseDragged(event -> {
//            if (event.getButton() == MouseButton.MIDDLE) {
//                if (oldXdrag == 0)
//                    oldXdrag = event.getX();
//                if (oldYdrag == 0)
//                    oldYdrag = event.getY();
//                canvas.setTranslateX(event.getX() - oldXdrag + mapPosX);
//                canvas.setTranslateY(event.getY() - oldYdrag + mapPosY);
//                check();
//                drawMap();
//            }
//        });
//        scene.setOnMouseReleased(event -> {
//            mapPosX += event.getX() - oldXdrag;
//            mapPosY += event.getY() - oldYdrag;
//            oldXdrag = 0;
//            oldYdrag = 0;
//            event.consume();
//        });


        scene.setOnKeyTyped(event -> {
            System.out.println(event.getCharacter());
            if (event.getCharacter().equals("=") || event.getCharacter().equals("+")) {
                g2d.scale(2, 2);
                scaleMultiplier*=2;
                //clearMap();
                drawMap();
            }
            if (event.getCharacter().equals("-")) {
                if (scaleMultiplier>1) {
                    g2d.scale(0.5, 0.5);
                    scaleMultiplier *= 0.5;
                    drawMap();
                }
            }
            if (event.getCharacter().equals("d"))
                if (canvas.getTranslateX()*scaleMultiplier + tileWidth * width > scene.getWidth())
                    canvas.setTranslateX(canvas.getTranslateX() - 100);
                else canvas.setTranslateX(scene.getWidth() - tileWidth * width);
            if (event.getCharacter().equals("a"))
                if (canvas.getTranslateX()*scaleMultiplier < 0)
                    canvas.setTranslateX(canvas.getTranslateX() + 100);
                else canvas.setTranslateX(0);
            if (event.getCharacter().equals("w"))
                if (canvas.getTranslateY() < 0)
                    canvas.setTranslateY(canvas.getTranslateY() + 100);
                else canvas.setTranslateY(0);
            if (event.getCharacter().equals("s"))
                if (canvas.getTranslateY() + tileWidth * width > scene.getHeight())
                    canvas.setTranslateY(canvas.getTranslateY() - 100);
                else canvas.setTranslateY(scene.getHeight() - tileHeight * height);
            event.consume();
        });
    }

    private void check() {
        if (!(canvas.getTranslateX() + tileWidth * width > scene.getWidth()))
            canvas.setTranslateX(scene.getWidth() - tileWidth * width);
        if (!(canvas.getTranslateX() < 0))
            canvas.setTranslateX(0);
        if (!(canvas.getTranslateY() < 0))
            canvas.setTranslateY(0);
        if (!(canvas.getTranslateY() + tileWidth * width > scene.getHeight()))
            canvas.setTranslateY(scene.getHeight() - tileHeight * height);
    }


}
