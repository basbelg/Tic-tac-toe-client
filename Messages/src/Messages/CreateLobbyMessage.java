package Messages;

import java.io.Serializable;

public class CreateLobbyMessage implements Serializable {
    private String gameLobbyId;

    public CreateLobbyMessage(String gameLobbyId) {
        this.gameLobbyId = gameLobbyId;
    }

    public String getGameLobbyId() {
        return gameLobbyId;
    }
}
