package Messages;

import java.io.Serializable;

public class SpectateMessage implements Serializable {
    private int spectatorId;
    private String gameId;
    private int[][] gameBoard;

    public SpectateMessage(int spectatorId, String gameId) {
        this.spectatorId = spectatorId;
        this.gameId = gameId;
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

    public void setGameBoard(int[][] gameBoard) {
        this.gameBoard = gameBoard;
    }
}
