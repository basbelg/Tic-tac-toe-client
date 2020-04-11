package Game;

public class Player
{
    private String playerID;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;

    public Player(String playerID, String userName, String firstName, String lastName, String password) {
        this.playerID = playerID;
        setUserName(userName);
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
