package Messages;

import GameInterfaces.Move;

import java.io.Serializable;
import java.util.List;

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
