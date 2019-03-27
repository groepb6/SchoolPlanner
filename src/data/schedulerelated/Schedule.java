package data.schedulerelated;

import data.persons.Teacher;
import data.rooms.Room;
import data.schoolrelated.Group;
import data.schoolrelated.Subject;
import gui.assistclasses.Plan;

import java.io.Serializable;

public class Schedule implements Serializable {

    private Hour time;
    private Group group;
    private Room room;
    private Teacher teacher;
    private Subject subject;

    public Schedule(Hour time, Group group, Room room, Teacher teacher, Subject subject) {
        this.time = time;
        this.group = group;
        this.room = room;
        this.teacher = teacher;
        this.subject = subject;
    }

    public Plan getPlan() {
        return new Plan(this.time.getTime(), this.group.getName(), this.room.getName(), this.teacher.getName(), this.subject.getName());
    }

    public Hour getTime() {
        return time;
    }

    public void setTime(Hour time) {
        this.time = time;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * Gets the path to the directory that Schedule objects should be saved to
     *
     * @return "saves/schedules/" which is the directory where the schedules should be saved
     */
    public String getPath() {
        return "saves/schedules/";
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "time='" + time + '\'' +
                ", group='" + group + '\'' +
                ", location='" + room + '\'' +
                ", teacher='" + teacher + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
