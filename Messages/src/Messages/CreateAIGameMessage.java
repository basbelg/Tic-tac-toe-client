package Messages;

import java.io.Serializable;

public class CreateAIGameMessage implements Serializable {
    private int player1Id;
    private String gameLobbyId; // Player2 of this game will always be an AI player (whose ID is predefined to be 1)

    public CreateAIGameMessage(int player1Id, String gameLobbyId) {
        this.player1Id = player1Id;
        this.gameLobbyId = gameLobbyId;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public String getGameLobbyId() {
        return gameLobbyId;
    }
}
