package data;

public class Teacher extends Person {

    private Subject subject;

    public Teacher(String name, int id, String subjectName) {
        super(name, id);
        this.subject = new Subject(subjectName);
    }
}
