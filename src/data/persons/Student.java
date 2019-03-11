package data.persons;

public class Student extends Person {

    public Student(String name, Gender gender) {
        super(name);
        super.setGender(gender);
    }

    public Gender getGender() {
        return super.getGender();
    }
}
