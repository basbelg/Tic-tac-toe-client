package sample;

import DataClasses.User;

import java.util.List;

class DBManagerTest {

    @org.junit.jupiter.api.Test
    void addUser() {
        boolean wasSuccess;
        User user = new User("rbradt", "Riley", "Bradt", "1234asdf");

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
}