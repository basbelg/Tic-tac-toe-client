package sample;

import DataClasses.User;

import java.util.List;

class DBManagerTest {

    @org.junit.jupiter.api.Test
    void testAdd() {
        boolean wasSuccess;
        wasSuccess = DBManager.getInstance().addUser(new User("2", "riley", "bradt", "1234asdf"));
        System.out.println(wasSuccess);
    }

    @org.junit.jupiter.api.Test
    void testRetrieve() {
        List<User> a = DBManager.getInstance().getFilteredUsers("active");
        System.out.println(a.size());
    }


}