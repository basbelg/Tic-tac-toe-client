package Messages;

import GameInterfaces.Move;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MoveMessage implements Serializable {
    private Move nextMove;
    private LocalDateTime timeMade;
    private String gameId;
    private int movingPlayerId;

    public MoveMessage(Move nextMove, LocalDateTime timeMade, String gameId, int movingPlayerId) {
        this.nextMove = nextMove;
        this.timeMade = timeMade;
        this.gameId = gameId;
        this.movingPlayerId = movingPlayerId;
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
}
