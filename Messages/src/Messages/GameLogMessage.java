package Messages;

public class GameLogMessage {
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
}
