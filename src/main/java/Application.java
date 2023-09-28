import Controller.Controller;
import Util.ConnectionSingleton;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;

import static Util.LogUtil.log;

public class Application {
    public static void main(String[] args) {
//        this line is just for testing that your tables get set up correctly
//        if not, you'll get a stack trace
        Connection conn = ConnectionSingleton.getConnection();
        log.info("Got connectionSingleton");
//        this line is for starting the javalin server
        populateTables(conn);
        Controller controller = new Controller(conn);
        controller.getAPI().start();
    }

    // Probably a better way to do this but this loads in extra data that we
    // want to use when actually running the application. It is separate from the
    // the testing data that is used by how testing files.
    private static void populateTables(Connection connection) {
        try {
            FileReader sqlReader = new FileReader("src/main/resources/data.sql");
            RunScript.execute(connection, sqlReader);
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}