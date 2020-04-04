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
        con= DriverManager.getConnection("jdbc:mysql://localhost:3306/mysqljdbc","system","password");
        return con;

    }

    public List<User> getAllUsers() {

    }

    public String getPassword(String username) {
        String password = null;



        return password;
    }
}
