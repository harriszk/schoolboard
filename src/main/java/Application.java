import Controller.Controller;
import DAO.TeacherDAO;
import Service.CourseService;
import Service.StudentService;
import Service.TeacherService;
import Util.ConnectionSingleton;
import Util.LogUtil;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import static Util.LogUtil.log;

public class Application {
    public static void main(String[] args) {
//        this line is just for testing that your tables get set up correctly
//        if not, you'll get a stack trace
        ConnectionSingleton.getConnection();
        log.info("Got connectionSingleton");
//        this line is for starting the javalin server
        CourseService courseService = new CourseService(conn);
        StudentService studentService = new StudentService(conn);
        TeacherService teacherService = new TeacherService(conn);

        Controller controller = new Controller(courseService, studentService, teacherService);
        controller.getAPI().start();

        /*
        TeacherDAO teacherDAO = new TeacherDAO(ConnectionSingleton.getConnection());
        System.out.println("Teacher with id=1"+ teacherDAO.getTeacherById(1));*/

        // Console interface
        /*
        Scanner sc = new Scanner(System.in);
        boolean theEnd = false;
        while(!theEnd){
            System.out.println("1: Find a Teacher by id. 2: Exit");
            int choice = Integer.parseInt(sc.nextLine());
            if(choice == 1){
                System.out.println("Enter id of teacher:");
                int id = Integer.parseInt(sc.nextLine());
                TeacherDAO teacherDAO = new TeacherDAO(ConnectionSingleton.getConnection());
                System.out.println("Teacher with id="+id+": "+ teacherDAO.getTeacherById(id));
            }else if(choice == 2){
                theEnd=true;
            }else if(choice ==3) {

                System.out.println("invalid choice");
            }else if(choice == 4){
                System.out.println("invalid choice");

            }else if(choice == 5){
                System.out.println("invalid choice");

            }else if(choice == 6){
                System.out.println("invalid choice");
            }else{
                System.out.println("invalid choice");
            }
        }
         */

    }
}