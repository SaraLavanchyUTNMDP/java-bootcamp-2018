import Proxy.Proxy;
import models.Course;
import models.Schedule;
import repository.HighSchoolRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String [] args)
    {
        Proxy anDataBaseProxy = new Proxy();

        Scanner entrada = new Scanner(System.in);
        String teacherName;
        System.out.print("Please insert the teacher's name: ");
        teacherName = entrada.nextLine();


        anDataBaseProxy.showSchedule(teacherName);




    }

}
