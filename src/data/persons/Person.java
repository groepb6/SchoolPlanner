package data.persons;
import simulation.sims.Sim;

import java.io.Serializable;

/**
 * @author Hanno Brandwijk
 * @author Wout Stevens
 */

abstract public class Person implements Serializable {
    private String name;
    private Sim sim;

    public Person(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setSim(Sim sim) {
        this.sim = sim;
    }

    public Sim getSim() {
        return this.sim;
    }

}
