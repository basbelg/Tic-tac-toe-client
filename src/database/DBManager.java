package database;

import DataClasses.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBManager implements DataSource {
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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tic-tac-toe","root",
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
                    " is_active) values (?,?,?,?,?);");
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
                    " lname = ?, is_active = ? where id = ?;");
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
                    resultSet.getString("lname"), resultSet.getBoolean("is_active"));
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
                    resultSet = statement.executeQuery("select * from user where is_active = true;");
                    break;
                case "deactive":
                    resultSet = statement.executeQuery("select * from user where is_active = false;");
                    break;
                case "all":
                default:
                    resultSet = statement.executeQuery("select * from user;");
            }

            while(resultSet.next())
                users.add(new User(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("fname"),
                        resultSet.getString("lname"), resultSet.getBoolean("is_active")));
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
            // insert game into database
            statement = connection.prepareStatement("insert into game (id, start_time, end_time, player1, player2," +
                    " starting_player, winner) values (?,?,?,?,?,?,?);");
            statement.setString(1, game.getId());
            statement.setTimestamp(2, Timestamp.valueOf(game.getStartingTime()));
            statement.setTimestamp(3, null);
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

    public boolean updateGame(Game game) {
        boolean wasSuccessful = true;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("update game set start_time = ?, end_time = ?, player1 = ?," +
                    " player2 = ?, starting_player = ?, winner = ? where id = ?;");
            statement.setTimestamp(1, Timestamp.valueOf(game.getStartingTime()));
            statement.setTimestamp(2, (game.getEndTime() == null)? null :Timestamp.valueOf(game.getEndTime()));
            statement.setInt(3, game.getPlayer1Id());
            statement.setInt(4, game.getPlayer2Id());
            statement.setInt(5, game.getStartingPlayerId());
            statement.setInt(5, game.getWinningPlayerId());
            statement.setString(6, game.getId());
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

    public Game getGame(String id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Game game = null;

        try {
            statement = connection.prepareStatement("select * from game where id = ?;");
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            resultSet.next();
            Timestamp end_time = resultSet.getTimestamp("end_time");
            LocalDateTime end_time_ldt = (end_time == null)? null : end_time.toLocalDateTime();
            game = new Game(resultSet.getString("id"),
                    resultSet.getTimestamp("start_time").toLocalDateTime(),
                    end_time_ldt,
                    resultSet.getInt("player1"), resultSet.getInt("player2"),
                    resultSet.getInt("starting_player"), resultSet.getInt("winner"));
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            if(resultSet != null)
                try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
            if(connection != null)
                try {connection.close();} catch (SQLException e) {e.printStackTrace();}
            if(statement != null)
                try {statement.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return game;
    }

    private ResultSet executeQuery(String sql) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            if(connection != null)
                try {connection.close();} catch (SQLException e) {e.printStackTrace();}
            if(statement != null)
                try {statement.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return resultSet;
    }

    private boolean executeUpdate(String sql) {
        boolean wasSuccessful = true;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
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

    @Override
    public boolean insert(Object obj) {
        boolean wasSuccessful = true;

        if(obj instanceof Game) {
            // insert game into database
            Game game = (Game) obj;
            StringBuilder sql = new StringBuilder("insert into game (id, start_time, player1, player2, starting_player, winner) values (?,?,?,?,?,?);");
            sql.replace(sql.indexOf("?"), sql.indexOf("?"), game.getId());
            sql.replace(sql.indexOf("?"), sql.indexOf("?"), Timestamp.valueOf(game.getStartingTime()).toString());
   //         sql.replace(sql.indexOf("?"), sql.indexOf("?"), String.valueOf(game.getPlayer1Id())));
            sql.replace(sql.indexOf("?"), sql.indexOf("?"), String.valueOf(game.getPlayer2Id()));
            sql.replace(sql.indexOf("?"), sql.indexOf("?"), String.valueOf(game.getStartingPlayerId()));
            sql.replace(sql.indexOf("?"), sql.indexOf("?"), String.valueOf(game.getWinningPlayerId()));

            wasSuccessful = executeUpdate(sql.toString());
        }
        else if(obj instanceof User) {
            ResultSet resultSet;
            User user = (User) obj;

            StringBuilder sql = new StringBuilder("insert into user (username, password, fname, lname, is_active) values (?,?,?,?,?);");
            sql.replace(sql.indexOf("?"), sql.indexOf("?"), user.getUsername());
            sql.replace(sql.indexOf("?"), sql.indexOf("?"), user.getPassword());
            sql.replace(sql.indexOf("?"), sql.indexOf("?"), user.getFirstName());
            sql.replace(sql.indexOf("?"), sql.indexOf("?"), user.getLastName());
            sql.replace(sql.indexOf("?"), sql.indexOf("?"), String.valueOf(true));

            wasSuccessful = executeUpdate(sql.toString());

            // retrieve auto-generated id
            sql = new StringBuilder("select id from user where username = ?;");
            sql.replace(sql.indexOf("?"), sql.indexOf("?"), user.getUsername());
            resultSet = executeQuery(sql.toString());
            try {
                resultSet.next();
                user.setId(resultSet.getInt("id"));
            } catch (SQLException e) {e.printStackTrace();}
            finally {
                if(resultSet != null)
                    try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
            }
        }

        return wasSuccessful;
    }

    @Override
    public boolean delete(Object obj) {
        return true;
    }

    @Override
    public boolean update(Object obj) {
        return true;
    }

    @Override
    public Object get(String s) {
        ResultSet resultSet = null;
        Object obj;


        if(s.indexOf('-') == -1) {
            // user
        }
        else {
            // game
            try {
                    statement = connection.prepareStatement("select * from game where id = ?;");
                    statement.setString(1, id);
                    resultSet = statement.executeQuery();

                    resultSet.next();
                    Timestamp end_time = resultSet.getTimestamp("end_time");
                    LocalDateTime end_time_ldt = (end_time == null)? null : end_time.toLocalDateTime();
                    obj = new Game(resultSet.getString("id"),
                            resultSet.getTimestamp("start_time").toLocalDateTime(),
                            end_time_ldt,
                            resultSet.getInt("player1"), resultSet.getInt("player2"),
                            resultSet.getInt("starting_player"), resultSet.getInt("winner"));
                } catch (SQLException e) {e.printStackTrace();}
                finally {
                    if (resultSet != null)
                        try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
                }
        }



        return true;
    }

    @Override
    public List<Object> list(Object obj) {


        return new ArrayList<>();
    }

    @Override
    public List<Object> query(Object obj, String filter) {
        return new ArrayList<>();
    }
}
