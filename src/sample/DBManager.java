package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager
{
    private static DBManager instance = new DBManager();

    private DBManager() { }

    public static DBManager getInstance()
    {
        return instance;
    }

    private static Connection getConnection()throws ClassNotFoundException, SQLException
    {

        Connection con = null;
        Class.forName("com.mysql.jdbc.Driver");
        con= DriverManager.getConnection();
        return con;

    }
}
