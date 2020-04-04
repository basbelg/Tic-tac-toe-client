package DataClasses;

public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    public User(String username, String firstName, String lastName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}
