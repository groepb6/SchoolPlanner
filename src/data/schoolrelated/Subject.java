package data.schoolrelated;

import java.io.Serializable;

/**
 * @author Hanno Brandwijk
 * @author Wout Stevens
 */

public class Subject implements Serializable {

    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
