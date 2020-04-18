package Messages;

public class InactiveGameMessage {
    String finishedGameId;

    public InactiveGameMessage() {
    }

    public String getFinishedGameId() {
        return finishedGameId;
    }

    public void setFinishedGameId(String finishedGameId) {
        this.finishedGameId = finishedGameId;
    }
}
