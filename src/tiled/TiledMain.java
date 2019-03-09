package tiled;

public class TiledMain {

    public static void main(String[] args) {
        Map map = new Map(TiledReader.readMap("schoolmap"));
        System.out.println(map);
    }

}
