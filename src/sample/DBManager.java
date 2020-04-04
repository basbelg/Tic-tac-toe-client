package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBManager
{
    private static DBManager instance = new DBManager();

    Connection con = null;

    private DBManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb1","root","<password>");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBManager getInstance()
    {
        return instance;
    }

    public boolean addUser(User user) {
        boolean wasSuccessful = true;



        return wasSuccessful;
    }

    public boolean updateUser(User user) {
        boolean wasSuccessful = true;

        return wasSuccessful;
    }

    public boolean modifyUserStatus(String status, String username) {
        boolean wasSuccessful = true;

        if(status == "delete") {
            // delete user

        }
        else {
            // set appropriate status to user

        }

        return wasSuccessful;
    }

    public String getPassword(String username) {
        String password = null;

        return password;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        return users;
    }

    public List<User> getFilteredUsers(String filter) {
        List<User> users = new ArrayList<>();

        return users;
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
