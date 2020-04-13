package Messages;

import DataClasses.User;

import java.io.Serializable;

public class UpdateAccountInfoMessage implements Serializable {
    private User updatedUser;

    public UpdateAccountInfoMessage(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }
}
