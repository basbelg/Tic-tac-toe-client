package Messages;

import DataClasses.User;

import java.io.Serializable;

public class CreateAccountMessage implements Serializable {
    private User newUser;

    public CreateAccountMessage(User newUser) {
        this.newUser = newUser;
    }
}
