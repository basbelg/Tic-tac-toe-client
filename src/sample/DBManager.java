package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
        con = DriverManager.getConnection(/*URL, user, password*/);
        return con;
    }

    public static Statement getStatement()
    {
        Statement s = null;
        try
        {
            s = getConnection().createStatement();
        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        return s;
    }
}
