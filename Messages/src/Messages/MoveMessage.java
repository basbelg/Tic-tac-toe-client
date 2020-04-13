package Messages;

public class MoveMessage {
    private Move nextMove;
    private String gameId;
    private int movingPlayerId;

    public MoveMessage(Move nextMove, String gameId, int movingPlayerId) {
        this.nextMove = nextMove;
        this.gameId = gameId;
        this.movingPlayerId = movingPlayerId;
    }

    public Move getNextMove() {
        return nextMove;
    }

    public String getGameId() {
        return gameId;
    }

    public int getMovingPlayerId() {
        return movingPlayerId;
    }
}
