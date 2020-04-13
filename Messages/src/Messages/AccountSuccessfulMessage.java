package Messages;

import DataClasses.User;

public class AccountSuccessfulMessage {
    private User createdUser;

    public AccountSuccessfulMessage(User createdUser) {
        this.createdUser = createdUser;
    }

    public User getCreatedUser() {
        return createdUser;
    }
}
