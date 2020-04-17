package Messages;

import java.io.Serializable;

public class CreateAIGameMessage implements Serializable {
    private String gameLobbyId; // Player2 of this game will always be an AI player (whose ID is predefined to be 1)

    public CreateAIGameMessage(String gameLobbyId) {
        this.gameLobbyId = gameLobbyId;
    }

    public String getGameLobbyId() {
        return gameLobbyId;
    }
}
