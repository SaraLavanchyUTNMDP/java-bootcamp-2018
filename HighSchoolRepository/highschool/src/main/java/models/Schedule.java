package models;

import lombok.Data;

@Data
public class Schedule {
    private String theTeacher;
    private String day;
    private String time;
    private Course course;

    public Schedule(String name, String surname, String bdDay, String bdTime, Course bdCourse) {
        this.theTeacher = name+" "+surname;
        this.day= bdDay;
        this.time=bdTime;
        this.course = bdCourse;
    }

}
