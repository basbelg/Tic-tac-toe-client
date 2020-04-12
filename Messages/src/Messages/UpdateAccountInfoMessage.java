package Messages;

import DataClasses.User;

import java.io.Serializable;

public class UpdateAccountInfoMessage implements Serializable {
    private User updatedUser;
}
