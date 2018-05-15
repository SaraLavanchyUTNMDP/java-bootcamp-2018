package repository;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Lavanchy
 */
public class JDBCConnection {

    private Connection conexion;
    static JDBCConnection instance;

    /** Singleton Patron for my ddbb
     */
    public static JDBCConnection getInstance() {
        if (instance == null) {
            instance = new JDBCConnection();
        }
        return instance;
    }

    private JDBCConnection(){
        try {
            //I create here the conection for my database.
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/highschool", "root", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }



}
