import proxy.Proxy;

import java.util.Scanner;

public class Main {
    public static void main(String [] args)
    {
        Proxy anDataBaseProxy = new Proxy();

        Scanner entrada = new Scanner(System.in);
        String teacherName;
        System.out.print("Please insert the teacher's name: ");
        teacherName = entrada.nextLine();

        anDataBaseProxy.getCourse(1);
        anDataBaseProxy.showSchedule(teacherName);




    }

}
