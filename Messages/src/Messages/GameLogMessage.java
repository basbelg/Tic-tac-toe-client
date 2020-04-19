package Messages;

import GameInterfaces.Move;

import java.io.Serializable;
import java.util.List;

public class GameLogMessage implements Serializable {
    private int userId;
    private String gameId;
    private String winner;
    private String player1Username;
    private String player2Username;
    private List<Move> moveHistory;

    public GameLogMessage() {
    }

    public int getUserId() {
        return userId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getWinner() {
        return winner;
    }

    public String getPlayer1Username() {
        return player1Username;
    }

    public String getPlayer2Username() {
        return player2Username;
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

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setPlayer1Username(String player1Username) {
        this.player1Username = player1Username;
    }

    public void setPlayer2Username(String player2Username) {
        this.player2Username = player2Username;
    }

    public void setMoveHistory(List<Move> moveHistory) {
        this.moveHistory = moveHistory;
    }
}
