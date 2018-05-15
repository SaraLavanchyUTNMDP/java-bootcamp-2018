package proxy;

import models.Course;
import models.Schedule;
import repository.HighSchoolRepository;
import java.util.List;


public class Proxy {
    HighSchoolRepository repository = new HighSchoolRepository();

    public List<Schedule> getSchedule(String name){
        return repository.scheduleAndTeacher(name);
    }

    public void showSchedule(String name){
       try {
           List<Schedule> Schedule = this.getSchedule(name);
           System.out.println("Course: " + Schedule.get(0).getCourse().getName() +
                   "\nThe teacher: " + Schedule.get(0).getTheTeacher() +
                   "\nSchedule: ");
           for (Schedule theSchedule : Schedule) {
               System.out.println(theSchedule.getDay() + " " + theSchedule.getTime());
           }
       }catch(Exception e){
           e.getMessage();
       }
    }

    public void getCourse(int c){
        Course x = this.repository.getCourse(c);
        System.out.println(x);
    }
}
