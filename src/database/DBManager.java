package database;

import DataClasses.*;

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

    @Override
    public boolean insert(Object obj) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean wasSuccessful = true;

        try {
            if(obj instanceof Game) {
                // insert game into database
                Game game = (Game) obj;

                statement = connection.prepareStatement("insert into game (id, start_time, end_time, player1," +
                        " player2, starting_player, winner) values (?,?,?,?,?,?,?);");
                statement.setString(1, game.getId());
                statement.setTimestamp(2, Timestamp.valueOf(game.getStartingTime()));
                statement.setTimestamp(3, null);
                statement.setInt(4, game.getPlayer1Id());
                statement.setInt(5, game.getPlayer2Id());
                statement.setInt(6, game.getStartingPlayerId());
                statement.setInt(7, game.getWinningPlayerId());

                statement.executeUpdate();
            }
            else if(obj instanceof User) {
                // insert user into database
                User user = (User) obj;

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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            wasSuccessful = false;
        }
        finally {
            if(connection != null)
                try {connection.close();} catch (SQLException e) {e.printStackTrace();}
            if(statement != null)
                try {statement.close();} catch (SQLException e) {e.printStackTrace();}
            if(resultSet != null)
                try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return wasSuccessful;
    }

    @Override
    public boolean delete(Object obj) {
        boolean wasSuccessful = true;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            if(obj instanceof Game) {
                Game game = (Game) obj;
                statement = connection.prepareStatement("delete from game where id = ?;");
                statement.setString(1, game.getId());
                statement.executeUpdate();
            }
            else if(obj instanceof User) {
                User user = (User) obj;
                statement = connection.prepareStatement("delete from user where id = ?;");
                statement.setInt(1, user.getId());
                statement.executeUpdate();
            }
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
    public boolean update(Object obj) {
        boolean wasSuccessful = true;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            if(obj instanceof  User) {
                User user = (User) obj;

                statement = connection.prepareStatement("update user set username = ?, password = ?, fname = ?," +
                        " lname = ?, is_active = ? where id = ?;");
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getFirstName());
                statement.setString(4, user.getLastName());
                statement.setBoolean(5, user.isActive());
                statement.setInt(6, user.getId());

                statement.executeUpdate();
            }
            else if(obj instanceof Game) {
                Game game = (Game) obj;

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
            }
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
    public Object get(Class classType, String id) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Object obj = null;


        try {
            if(classType == Game.class) {
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
            }
            else if(classType == User.class) {
                statement = connection.prepareStatement("select * from user where username = ?;");
                statement.setString(1, id);
                resultSet = statement.executeQuery();

                resultSet.next();
                obj = new User(resultSet.getInt("id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("fname"),
                        resultSet.getString("lname"), resultSet.getBoolean("is_active"));
            }
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            if(resultSet != null)
                try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
            if(connection != null)
                try {connection.close();} catch (SQLException e) {e.printStackTrace();}
            if(statement != null)
                try {statement.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return obj;
    }

    @Override
    public List<Object> list(Class classType) {
        List<Object> objs = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            if(classType == User.class) {
                resultSet = statement.executeQuery("select * from user;");

                while(resultSet.next())
                    objs.add(new User(resultSet.getInt("id"), resultSet.getString("username"),
                            resultSet.getString("password"), resultSet.getString("fname"),
                            resultSet.getString("lname"), resultSet.getBoolean("is_active")));
            }
            else if(classType == Game.class) {
                resultSet = statement.executeQuery("select * from game;");

                while(resultSet.next())
                    objs.add(new Game(resultSet.getString("id"),
                            resultSet.getTimestamp("start_time").toLocalDateTime(),
                            resultSet.getTimestamp("end_time").toLocalDateTime(),
                            resultSet.getInt("player1"), resultSet.getInt("player2"),
                            resultSet.getInt("starting_player"), resultSet.getInt("winner")));
            }
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            if(resultSet != null)
                try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
            if(connection != null)
                try {connection.close();} catch (SQLException e) {e.printStackTrace();}
            if(statement != null)
                try {statement.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return objs;
    }

    @Override
    public List<Object> query(Class classType, String filter) {
        List<Object> objs = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            if(classType == User.class) {
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
                    objs.add(new User(resultSet.getInt("id"), resultSet.getString("username"),
                            resultSet.getString("password"), resultSet.getString("fname"),
                            resultSet.getString("lname"), resultSet.getBoolean("is_active")));
            }
            else if(classType == Game.class) {
                switch (filter) {
                    default:
                        resultSet = statement.executeQuery("select * from game;");
                }

                while(resultSet.next())
                    objs.add(new Game(resultSet.getString("id"),
                            resultSet.getTimestamp("start_time").toLocalDateTime(),
                            resultSet.getTimestamp("end_time").toLocalDateTime(),
                            resultSet.getInt("player1"), resultSet.getInt("player2"),
                            resultSet.getInt("starting_player"), resultSet.getInt("winner")));
            }
        } catch (SQLException e) {e.printStackTrace();}
        finally {
            if(resultSet != null)
                try {resultSet.close();} catch (SQLException e) {e.printStackTrace();}
            if(connection != null)
                try {connection.close();} catch (SQLException e) {e.printStackTrace();}
            if(statement != null)
                try {statement.close();} catch (SQLException e) {e.printStackTrace();}
        }

        return objs;
    }
}
