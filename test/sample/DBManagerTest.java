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

        wasSuccess = DBManager.getInstance().insert(user);

        if(wasSuccess)
            System.out.println(user.getId());
    }

    @org.junit.jupiter.api.Test
    void updateUser() {
        User user = (User) DBManager.getInstance().get(User.class, "rbradt");

        user.setUsername("updated_rbradt");

        DBManager.getInstance().update(user);
    }

    @org.junit.jupiter.api.Test
    void printAllActiveUsers() {
        List<Object> users = DBManager.getInstance().query(User.class, "active");

        System.out.println("Active Users:");
        for(Object obj: users) {
            User user = (User) obj;
            System.out.printf("id: %d | user: %s | pass: %s | first name: %s | last name: %s\n",
                    user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName());
        }
    }

    @org.junit.jupiter.api.Test
    void clearUsers() {
        List<Object> users = DBManager.getInstance().list(User.class);

        for(Object obj: users) {
            User user = (User) obj;
            DBManager.getInstance().delete(user);
        }
    }

    @org.junit.jupiter.api.Test
    void addGame() {
        Game gameLocal;
        Game gameFromDB;

        User startingPlayer = (User) DBManager.getInstance().get(User.class, "updated_rbradt");
        User player2 = (User) DBManager.getInstance().get(User.class, "rbradt2");
        gameLocal = new Game(LocalDateTime.now(), startingPlayer.getId(), player2.getId(), startingPlayer.getId());

        DBManager.getInstance().insert(gameLocal);

        gameFromDB = (Game) DBManager.getInstance().get(Game.class, gameLocal.getId());

        System.out.printf("id: %s | start: %s | end: %s | p1: %d | p2: %d | sp: %d | wp: %d ",
                gameFromDB.getId(), gameFromDB.getStartingTime().toString(),
                (gameFromDB.getEndTime() == null)? "null": gameFromDB.getEndTime().toString(),
                gameFromDB.getPlayer1Id(), gameFromDB.getPlayer2Id(), gameFromDB.getStartingPlayerId(),
                gameFromDB.getWinningPlayerId());
    }
}