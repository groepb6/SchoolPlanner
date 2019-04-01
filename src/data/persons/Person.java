package data.persons;
import java.io.Serializable;

/**
 * @author Hanno Brandwijk
 * @author Wout Stevens
 */

abstract public class Person implements Serializable {
    private String name;
    public Person(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
