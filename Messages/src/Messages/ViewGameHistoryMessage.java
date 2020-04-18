package Messages;

import GameInterfaces.Move;

import java.io.Serializable;
import java.util.List;

public class ViewGameHistoryMessage implements Serializable {
    private int userId;
    List<List<Move>> gameHistories;

    public ViewGameHistoryMessage() {
    }

    public int getUserId() {
        return userId;
    }

    public List<List<Move>> getGameHistories() {
        return gameHistories;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setGameHistories(List<List<Move>> gameHistories) {
        this.gameHistories = gameHistories;
    }
}
