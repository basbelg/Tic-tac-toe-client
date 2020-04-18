package Messages;

import DataClasses.User;

import java.io.Serializable;

public class AccountSuccessfulMessage implements Serializable {
    private User newAccount;

    public AccountSuccessfulMessage() {
    }

    public User getNewAccount() {
        return newAccount;
    }

    public void setNewAccount(User newAccount) {
        this.newAccount = newAccount;
    }

    @Override
    public String toString() {
        return "Account Created Successfully";
    }
}
