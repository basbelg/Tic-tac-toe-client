package Messages;

import java.io.Serializable;

public class DeactivateAccountMessage implements Serializable {
    private int userId;

    public DeactivateAccountMessage(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
