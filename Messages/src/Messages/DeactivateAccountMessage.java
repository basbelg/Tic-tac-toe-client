package Messages;

public class DeactivateAccountMessage {
    private int userId;

    public DeactivateAccountMessage(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
