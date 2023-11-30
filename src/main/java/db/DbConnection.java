package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    private static Statement statement;
    private DbConnection(){}
    public static Statement getStatement(){
        if (statement==null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "minura");
                statement = connection.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return statement;
    }

}
