package Messages;

import DataClasses.User;

public class LoginSuccessfulMessage {
    private User user;

    public LoginSuccessfulMessage(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
