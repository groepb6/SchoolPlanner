import data.room.Classroom;

public class Main {
    public static void main(String[] args) {
        Classroom cr = new Classroom("Explora", 657);
        System.out.println(cr.getCapacity() + cr.getName() + cr.isAvailable());
    }
}
