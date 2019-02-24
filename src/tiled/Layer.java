package tiled;

import javax.imageio.ImageIO;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Layer {
    private BufferedImage image;
    private String name;
    private int height; // can be same as map
    private int width; //can be same as map
    private List<Integer> data;
    private String drawOrder;
    private String encoding;
    private int id;
    //private List<Layer> layers; //not sure if we need this or how to apply it
    private double offsetX; //default = 0
    private double offsetY; //default = 0
    private double opacity;
    private List<String> properties;
    private String transparentColor;
    private String type;
    private boolean visible;
    private int x; //always 0
    private int y; //always 0

    /**
     * todo: full documentation
     *
     * @param jsonLayer This JsonObject can be obtained from TiledReader.readLayer()
     */
    public Layer(JsonObject jsonLayer) {
        if (jsonLayer.getString("type").equals("tilelayer")) {
            try {
                File imageFile = new File(this.getClass().getResource("tiled/error/") + jsonLayer.getString("image") + ".bmp"); //todo: fix, decide load path
                System.out.println("Attempting to load from: " + imageFile);
                this.image = ImageIO.read(imageFile);
            } catch (IOException exception) {
                System.out.println("Image loading failed!");
            }
            this.name = jsonLayer.getString("name");
            this.height = jsonLayer.getInt("height");
            this.width = jsonLayer.getInt("width");
            this.addData();
            this.drawOrder = jsonLayer.getString("draworder");
            this.encoding = jsonLayer.getString("encoding");
            this.id = jsonLayer.getInt("id");
            this.offsetX = jsonLayer.getJsonNumber("offsetx").doubleValue();
            this.offsetY = jsonLayer.getJsonNumber("offsety").doubleValue();
            this.opacity = jsonLayer.getJsonNumber("opacity").doubleValue();
            this.addProperties(jsonLayer);
            this.transparentColor = jsonLayer.getString("transparentcolor");
            this.type = jsonLayer.getString("type");
            this.visible = jsonLayer.getBoolean("visible", true);
            this.x = 0;
            this.y = 0;
        } else {
            System.out.println("You tried loading a non-layer file into a layer object!");
        }
    }

    public void draw() {

    }

    /**
     * Adds the properties from the json file
     *
     * @param jsonLayer This JsonObject can be obtained in the constructor
     */
    private void addProperties(JsonObject jsonLayer) {
        this.properties = new ArrayList<>();
        JsonArray jsonArray = jsonLayer.getJsonArray("properties");
        for (int i = 0; i < jsonArray.size(); i++) {
            this.properties.add(jsonArray.getString(i));
        }
    }

    private void addData() {
        //todo: create addData method
    }

}
