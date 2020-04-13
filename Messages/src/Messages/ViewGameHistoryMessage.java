package Messages;

public class ViewGameHistoryMessage {
    List<List<Move>> gameHistories;

    public ViewGameHistoryMessage() {
    }

    public List<List<Move>> getGameHistories() {
        return gameHistories;
    }

    public void setGameHistories(List<List<Move>> gameHistories) {
        this.gameHistories = gameHistories;
    }
}
