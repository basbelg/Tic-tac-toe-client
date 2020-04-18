package Messages;

import GameInterfaces.Move;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MoveMessage implements Serializable {
    private Move nextMove;
    private LocalDateTime timeMade;
    private String gameId;
    private int movingPlayerId;

    public MoveMessage() {
    }

    public Move getNextMove() {
        return nextMove;
    }

    public LocalDateTime getTimeMade() {
        return timeMade;
    }

    public String getGameId() {
        return gameId;
    }

    public int getMovingPlayerId() {
        return movingPlayerId;
    }

    public void setNextMove(Move nextMove) {
        this.nextMove = nextMove;
    }

    public void setTimeMade(LocalDateTime timeMade) {
        this.timeMade = timeMade;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setMovingPlayerId(int movingPlayerId) {
        this.movingPlayerId = movingPlayerId;
    }
}
