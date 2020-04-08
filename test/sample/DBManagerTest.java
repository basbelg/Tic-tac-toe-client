package sample;

import DataClasses.*;
import database.DBManager;

import java.time.LocalDateTime;
import java.util.List;

class DBManagerTest {
    @org.junit.jupiter.api.Test
    void addUser() {
        boolean wasSuccess;
        User user = new User("updated_rbradt", "Riley", "Bradt", "1234asdf");

        wasSuccess = DBManager.getInstance().addUser(user);

        if(wasSuccess)
            System.out.println(user.getId());
    }

    @org.junit.jupiter.api.Test
    void updateUser() {
        User user = DBManager.getInstance().getUser("rbradt");

        user.setUsername("updated_rbradt");

        DBManager.getInstance().updateUser(user);
    }

    @org.junit.jupiter.api.Test
    void printAllActiveUsers() {
        List<User> users = DBManager.getInstance().getFilteredUsers("active");

        System.out.println("Active Users:");
        for(User user: users)
            System.out.printf("id: %d | user: %s | pass: %s | first name: %s | last name: %s\n",
                    user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName());
    }

    @org.junit.jupiter.api.Test
    void clearUsers() {
        List<User> users = DBManager.getInstance().getFilteredUsers("active");

        for(User user: users)
            DBManager.getInstance().deleteUser(user);
    }

    @org.junit.jupiter.api.Test
    void addGame() {
        Game gameLocal;
        Game gameFromDB;

        User startingPlayer = DBManager.getInstance().getUser("updated_rbradt");
        User player2 = DBManager.getInstance().getUser("rbradt2");
        gameLocal = new Game(LocalDateTime.now(), startingPlayer.getId(), player2.getId(), startingPlayer.getId());

        DBManager.getInstance().addGame(gameLocal);

        gameFromDB = DBManager.getInstance().getGame(gameLocal.getId());

        System.out.printf("id: %s | start: %s | end: %s | p1: %d | p2: %d | sp: %d | wp: %d ",
                gameFromDB.getId(), gameFromDB.getStartingTime().toString(),
                (gameFromDB.getEndTime() == null)? "null": gameFromDB.getEndTime().toString(),
                gameFromDB.getPlayer1Id(), gameFromDB.getPlayer2Id(), gameFromDB.getStartingPlayerId(),
                gameFromDB.getWinningPlayerId());
    }
}