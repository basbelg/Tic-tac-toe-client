package Messages;

import GameInterfaces.Move;

import java.io.Serializable;
import java.util.List;

public class GameLogMessage implements Serializable {
    private int userId;
    private String gameId;
    private List<Move> moveHistory;

    public GameLogMessage(int userId, String gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public String getGameId() {
        return gameId;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public void setMoveHistory(List<Move> moveHistory) {
        this.moveHistory = moveHistory;
    }
}
