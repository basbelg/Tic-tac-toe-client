package sample;

import DataClasses.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager
{
    private static DBManager instance = new DBManager();

    private DBManager() {}

    public static DBManager getInstance() {return instance;}

    private static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb1","root","<password>");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public boolean addUser(User user) {
        boolean wasSuccessful = true;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("insert into user (username, password, fname, lname, isActive) values (?,?,?,?,?);");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setBoolean(5, true);
        } catch (SQLException e) {
            e.printStackTrace();
            wasSuccessful = false;
        }
        finally {
            if(connection != null)
                try {connection.close();} catch (SQLException e) {e.printStackTrace();}
            if(statement != null)
                try {statement.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return wasSuccessful;
    }

    public boolean updateUser(User user) {
        boolean wasSuccessful = true;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("update user set username = ?, password = ?, fname = ?, lname = ?, isActive = ? where id = ?;");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setBoolean(5, user.isActive());
            statement.setInt(6, user.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            wasSuccessful = false;
        }
        finally {
            if(connection != null)
                try {connection.close();} catch (SQLException e) {e.printStackTrace();}
            if(statement != null)
                try {statement.close();} catch (SQLException e) {e.printStackTrace();}
        }

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

    public List<User> getAllUsers() {
        return getFilteredUsers("all");
    }

    public List<User> getFilteredUsers(String filter) {
        List<User> users = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet;

            switch (filter) {
                case "active":
                    resultSet = statement.executeQuery("select * from user where isActive=true;");
                    break;
                case "deactive":
                    resultSet = statement.executeQuery("select * from user where isActive=false;");
                    break;
                case "all":
                default:
                    resultSet = statement.executeQuery("select * from user;");
            }

            while(resultSet.next())
                users.add(new User(resultSet.getInt("id"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password"),
                                    resultSet.getString("fname"),
                                    resultSet.getString("lname"),
                                    resultSet.getBoolean("isActive")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(connection != null)
                try {connection.close();} catch (SQLException e) {e.printStackTrace();}
            if(statement != null)
                try {statement.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return users;
    }
}
