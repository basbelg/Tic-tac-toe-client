package sample;

import DataClasses.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBManager
{
    private static DBManager instance = new DBManager();
    private String sql_password;

    private DBManager() {
        try {
            File file = new File("R:\\CS4B\\SQL_Password.txt");
            Scanner scanner = new Scanner(file);
            sql_password = scanner.next();
        } catch (FileNotFoundException e) {e.printStackTrace();}
    }

    public static DBManager getInstance() {return instance;}

    private Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb1","root",
                    sql_password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }

    //------------------------------------------------------------------------------------------------------------------
    //                                          USER TABLE FUNCTIONS
    //------------------------------------------------------------------------------------------------------------------
    public boolean addUser(User user) {
        boolean wasSuccessful = true;
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // insert user into database
            statement = connection.prepareStatement("insert into user (username, password, fname, lname," +
                    " isActive) values (?,?,?,?,?);");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setBoolean(5, true);
            statement.executeUpdate();
            statement.close();

            // retrieve auto-generated id
            statement = connection.prepareStatement("select id from user where username = ?;");
            statement.setString(1, user.getUsername());
            resultSet = statement.executeQuery();
            resultSet.next();
            user.setId(resultSet.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
            wasSuccessful = false;
        }
        finally {
            if(resultSet != null)
                try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
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
            statement = connection.prepareStatement("update user set username = ?, password = ?, fname = ?," +
                    " lname = ?, isActive = ? where id = ?;");
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

    public User getUser(String username) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            statement = connection.prepareStatement("select * from user where username = ?;");
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            resultSet.next();
            user = new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("password"), resultSet.getString("fname"),
                    resultSet.getString("lname"), resultSet.getBoolean("isActive"));
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            if(resultSet != null)
                try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
            if(connection != null)
                try {connection.close();} catch (SQLException e) {e.printStackTrace();}
            if(statement != null)
                try {statement.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return user;
    }

    public boolean deleteUser(User user) {
        boolean wasSuccessful = true;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("delete from user where id = ?;");
            statement.setInt(1, user.getId());
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

    public List<User> getAllUsers() {return getFilteredUsers("all");}

    public List<User> getFilteredUsers(String filter) {
        List<User> users = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();

            switch (filter) {
                case "active":
                    resultSet = statement.executeQuery("select * from user where isActive = true;");
                    break;
                case "deactive":
                    resultSet = statement.executeQuery("select * from user where isActive = false;");
                    break;
                case "all":
                default:
                    resultSet = statement.executeQuery("select * from user;");
            }

            while(resultSet.next())
                users.add(new User(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("fname"),
                        resultSet.getString("lname"), resultSet.getBoolean("isActive")));
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            if(resultSet != null)
                try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
            if(connection != null)
                try {connection.close();} catch (SQLException e) {e.printStackTrace();}
            if(statement != null)
                try {statement.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return users;
    }

    //------------------------------------------------------------------------------------------------------------------
    //                                          GAME TABLE FUNCTIONS
    //------------------------------------------------------------------------------------------------------------------
    public boolean addGame(Game game) {
        boolean wasSuccessful = true;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // insert user into database
            statement = connection.prepareStatement("insert into game (id, start, end, player1, player2, startingPlayer, winner) values (?,?,?,?,?,?,?);");
            statement.setString(1, game.getId());
            statement.setDate(2, game.getStartingTime());
            statement.setDate(3, game.getEndTime());
            statement.setInt(4, game.getPlayer1Id());
            statement.setInt(5, game.getPlayer2Id());
            statement.setInt(6, game.getStartingPlayerId());
            statement.setInt(7, game.getWinningPlayerId());
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
}
