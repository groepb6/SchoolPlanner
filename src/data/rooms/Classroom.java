package data.rooms;

import data.schoolrelated.Group;
import data.persons.Teacher;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Classroom extends Room implements Serializable {

    public Classroom(String name) {
        super(name);
    }
}
