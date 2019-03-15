package tiled;

/**
 * A main class in order to test the new Tiled classes.
 * Todo: remove this class
 */
public class TiledMain {

    public static void main(String[] args) {
        Map map = new Map(TiledReader.readMap("schoolmap.json"));
        System.out.println(map);
    }

}
