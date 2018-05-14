package repository;

import models.Course;
import models.Schedule;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HighSchoolRepository {
    JDBCConnection myConnection;
    Properties myQueries;

    public HighSchoolRepository(){
        try{
            this.myConnection =myConnection.getInstance();
            this.myQueries = new Properties();
            this.myQueries.load(new FileInputStream("config/myQueries.properties"));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List<Schedule> scheduleAndTeacher(String name){
        List<Schedule> schedulesAndTeacher = new ArrayList<Schedule>();

        try {
            String sql = myQueries.getProperty("query.schedule_teachers");
            CallableStatement teacherAndSchedule = myConnection.getConexion().prepareCall(sql);

            teacherAndSchedule.setString(1,name);
            teacherAndSchedule.execute();
            ResultSet theTandScheduleTable = teacherAndSchedule.getResultSet();
            while(theTandScheduleTable.next()){
                Schedule oneLine = new Schedule(theTandScheduleTable.getString(1),
                        theTandScheduleTable.getString(2), theTandScheduleTable.getString(3),
                        theTandScheduleTable.getString(4), theTandScheduleTable.getString(5));
                schedulesAndTeacher.add(oneLine);
            }
        }catch(SQLException e){
            e.getSQLState();
        }
        return schedulesAndTeacher;
    }

    public Course getCourse(String name){
        Course course = null;
        try{
            String sql = myQueries.getProperty("query.get_course");
            CallableStatement anCourse = myConnection.getConexion().prepareCall(sql);
            anCourse.setString(1,name);
            anCourse.execute();
            ResultSet theCourse = anCourse.getResultSet();
            if(theCourse.next()){
                course = new Course(theCourse.getInt(1),
                        theCourse.getString(2),
                        theCourse.getInt(3));
            }
        }catch(SQLException e){
            e.getSQLState();
        }
        return course;
    }

}
