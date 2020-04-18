package Messages;

import GameInterfaces.Move;

import java.io.Serializable;
import java.util.List;

public class GameLogMessage implements Serializable {
    private int userId;
    private String gameId;
    private List<Move> moveHistory;

    public GameLogMessage() {
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setMoveHistory(List<Move> moveHistory) {
        this.moveHistory = moveHistory;
    }
}
