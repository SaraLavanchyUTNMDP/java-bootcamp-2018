package Proxy;

import models.Schedule;
import repository.HighSchoolRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Proxy {
    HighSchoolRepository repository = new HighSchoolRepository();

    public List<Schedule> getSchedule(String name){
        return repository.scheduleAndTeacher(name);
    }

    public void showSchedule(String name){
       try {
           List<Schedule> Schedule = this.getSchedule(name);
           System.out.println("Course: " + Schedule.get(0).getCourse() +
                   "\nThe teacher: " + Schedule.get(0).getTheTeacher() +
                   "\nSchedule: ");
           for (Schedule theSchedule : Schedule) {
               System.out.println(theSchedule.getDay() + " " + theSchedule.getTime());
           }
       }catch(Exception e){
           e.getMessage();
       }
    }
}
