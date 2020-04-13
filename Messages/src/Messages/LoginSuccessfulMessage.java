package Messages;

import DataClasses.User;

import java.io.Serializable;

public class LoginSuccessfulMessage implements Serializable {
    private User user;

    public LoginSuccessfulMessage(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
