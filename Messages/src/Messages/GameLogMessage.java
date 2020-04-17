package Messages;

import java.io.Serializable;

public class GameLogMessage implements Serializable {
    private String gameId;
    private List<Move> moveHistory;

    public GameLogMessage(String gameId) {
        this.gameId = gameId;
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