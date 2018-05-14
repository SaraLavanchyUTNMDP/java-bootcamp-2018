package models;

import lombok.Data;

@Data
public class Course {
    private int idCourse;
    private String name;
    private int weekHour;

    public Course(int id, String course, int hours) {
        this.idCourse = id;
        this.name = course;
        this.weekHour = hours;
    }

    public String getName() {
        return this.name;
    }
}
