import Controller.Controller;
import DAO.CourseDAO;
import DAO.TeacherDAO;
import Service.CourseService;
import Service.StudentCoursesService;
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
        Connection conn = ConnectionSingleton.getConnection();
        log.info("Got connectionSingleton");
//        this line is for starting the javalin server
        Controller controller = new Controller(conn);
        controller.getAPI().start();


       /* // Console interface

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
            }else if(choice >2) {
                System.out.println("invalid choice");
            }
        }*/

    }
}