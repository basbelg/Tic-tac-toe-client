package Messages;

public class DeactivaeAccountMessage {
    private int userId;

    public DeactivaeAccountMessage(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
