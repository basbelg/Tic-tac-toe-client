package Messages;

import java.io.Serializable;

public class ViewGameHistoryMessage implements Serializable {
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
