package Messages;

import java.io.Serializable;

public class SpectateMessage implements Serializable {
    private int spectatorId;
    private String gameId;
    private int[][] gameBoard;

    public SpectateMessage() {
    }

    public int getSpectatorId() {
        return spectatorId;
    }

    public String getGameId() {
        return gameId;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setSpectatorId(int spectatorId) {
        this.spectatorId = spectatorId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setGameBoard(int[][] gameBoard) {
        this.gameBoard = gameBoard;
    }
}
