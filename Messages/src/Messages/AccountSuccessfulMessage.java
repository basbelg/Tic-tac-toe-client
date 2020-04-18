package Messages;

import DataClasses.User;

import java.io.Serializable;

public class AccountSuccessfulMessage implements Serializable {
    private User newAccount;

    public AccountSuccessfulMessage(User newAccount) {
        this.newAccount = newAccount;
    }

    public User getNewAccount() {
        return newAccount;
    }

    @Override
    public String toString() {
        return "Account Created Successfully";
    }
}
