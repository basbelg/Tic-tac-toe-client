package Messages;

import DataClasses.User;

import java.io.Serializable;

public class AccountSuccessfulMessage implements Serializable {
    @Override
    public String toString() {
        return "Account Created Successfully";
    }
}
