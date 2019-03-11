package data.persons;

import java.io.Serializable;

abstract public class Person implements Serializable {
    private String name;

    public enum Gender {
        MALE, FEMALE, UNSPECIFIED
    }

    private Gender gender;

    public Person(String name) {
        this.name = name;
        this.gender = Gender.UNSPECIFIED;
    }

    public String getName() {
        return name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }
}
