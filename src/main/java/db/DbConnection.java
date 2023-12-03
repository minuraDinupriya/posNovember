package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    private static Connection connection;
    private DbConnection(){}
    public static Connection getConnection(){      //return DbConnection !=null ? dbConnection:(dbConnection=new DbConnection();) getInstance method is the best practice
        if (connection==null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "minura");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
