package Messages;

public class LoginMessage {
    private String username;
    private String password;

    public LoginMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
