package Messages;

import java.io.Serializable;

public class SpectateMessage implements Serializable {
    private String gameId;
    private int[][] gameBoard;

    public SpectateMessage(String gameId) {
        this.gameId = gameId;
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
